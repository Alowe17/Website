document.getElementById('logoutForm').addEventListener("submit", async (e) => {
    e.preventDefault();

    const response = await fetch('/api/auth/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: "include"
    })

    if (response.ok) {
        window.location.href = "/login";
    } else {
        alert('Увы, что-то пошло не так и не получилось сохранить данные о выходе из аккаунта. Обратитесь в поддержку проекта!');
    }
})

async function loadAdminChangePassword () {
    const response = await fetch('/api/admin', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const data = await response.json();
        showWelcomeText (data);
        loadUserData ();
    } else if (response.status == 401) {
        const refreshed = await refreshAccessToken ();

        if (refreshed) {
            return loadAdminChangePassword();
        } else {
            const data = await response.json();
            showError (data, response.status);
            return;
        }
    } else {
        const data = await response.json();
        showError (data, response.status);
        return;
    }
}

function showError (data, status) {
    const container = document.getElementById('form-card');
    const h2 = document.createElement('h2');
    container.innerHTML = "";

    if (status == 403 || status == 401) {
        h2.textContent += data.error;
        h2.textContent += data.message;
    } else if (status == 404) {
        h2.textContent = "Не удалось найти данного пользователя!";
    } else {
        h2.textContent = data.message;
    }

    h2.classList.add('error-message');
    container.appendChild(h2);
}

async function loadUserData () {
    const path = window.location.pathname;
    const username = path.split('/').pop();

    const response = await fetch('/api/admin/password/' + username, {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const data = await response.json();
        showUserData (data);
    } else if (response.status == 401) {
        const refreshed = await refreshAccessToken ();

        if (refreshed) {
            return loadAdminChangePassword();
        } else {
            const data = await response.json();
            showError (data, response.status);
            return;
        }
    } else {
        showError ("", response.status);
        return;
    }
}

function showUserData (data) {
    const username = document.getElementById('username');
    username.textContent = data.username;
}

async function refreshAccessToken () {
    const response = await fetch('/api/auth/refresh', {
        method: 'POST',
        credentials: 'include'
    });

    if (response.ok) {
        return true;
    } else {
        return false;
    }
}

function showWelcomeText (data) {
    const welcomeMessage = document.getElementById('welcomeMessage');
    welcomeMessage.textContent = data.message;
}

document.getElementById('updatePassword').addEventListener('submit', async (e) => {
    e.preventDefault();

    const path = window.location.pathname;
    const username = path.split('/').pop();
    const password = document.getElementById('password');

    const response = await fetch('/api/admin/change-password', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'include',
        body: JSON.stringify({
            username: username,
            password: password.value
        })
    });

    const data = await response.json();
    const container = document.getElementById('message');
    container.className = "";

    if (response.ok) {
        container.classList.add('success-message');
        container.textContent = data.message;
        return;
    } else if (response.status == 400) {
        container.classList.add('error-message');
        container.textContent = data.message;
        return;
    } else {
        const data = await response.json();
        showError (data, response.status);
    }
})

loadAdminChangePassword();