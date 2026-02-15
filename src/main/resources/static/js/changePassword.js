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

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return loadAdminChangePassword();
        } else {
            window.location.href = "/index";
        }
    } else if (response.ok) {
        const data = await response.json();
        showWelcomeText (data);

        const path = window.location.pathname;
        const segment = path.split('/');
        const username = segment[segment.length - 1];
        const responseUser = await fetch('/api/admin/password/' + username, {
            method: 'GET',
            credentials: 'include'
        });

        if (responseUser.ok) {
            const dataUser = await responseUser.json();
            const username = document.getElementById('username');
            username.textContent = dataUser.username;
        } else if (responseUser.status == 403) {
            const container = document.getElementById('main-content');
            const h2Cod = document.createElement('h2');
            const h2Text = document.createElement('h2');
            container.innerHTML = "";
            h2Cod.style.color = "#FF2400";
            h2Cod.style.fontWeight = "bold";
            h2Text.style.color = "#FF2400";
            h2Text.style.fontWeight = "bold";
            h2Cod.textContent = "Код ошибки: " + response.status;
            h2Text.textContent += "У вас нет доступа к данной странице!";
            container.appendChild(h2Cod);
            container.appendChild(h2Text);
        } else {
            const container = document.getElementById('main-content');
            container.innerHTML = "";
            container.textContent = "Что-то пошло не так. Невозможно получить доступ к странице!";
        }
    } else if (response.status == 403) {
        const container = document.getElementById('main-content');
        const h2Cod = document.createElement('h2');
        const h2Text = document.createElement('h2');
        container.innerHTML = "";
        h2Cod.style.color = "#FF2400";
        h2Cod.style.fontWeight = "bold";
        h2Text.style.color = "#FF2400";
        h2Text.style.fontWeight = "bold";
        h2Cod.textContent = "Код ошибки: " + response.status;
        h2Text.textContent += "У вас нет доступа к данной странице!";
        container.appendChild(h2Cod);
        container.appendChild(h2Text);
    } else {
        console.log("Ответ сервера: " + response.status);
    }
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
    console.log("Сообщение: " + data.message);
}

document.getElementById('updatePassword').addEventListener('submit', async (e) => {
    e.preventDefault();

    const username = document.getElementById('username').textContent;
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
        window.location.href = "/index";
    }
})

loadAdminChangePassword();