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

async function loadUpdateUser() {
    const response = await fetch('/api/admin', {
        method: 'GET',
        credentials: "include",
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken()

        if (refreshed) {
            return loadUpdateUser();
        } else {
            window.location.href = "/login";
            return;
        }
    } else if (response.status == 403) {
        const data = await response.json();
        showForbiddenMessage(data);
        return;
    } else if (response.ok) {
        const data = await response.json();
        showWelcomeMessage(data);
        
        const path = window.location.pathname;
        const segment = path.split('/');
        const username = segment[segment.length - 1];
        loadUserData (username);
    }
}

async function loadUserData (username) {
    const response = await fetch('/api/admin/info-user/' + username, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'include'
    })

    if (response.ok) {
        const data = await response.json();
        updateUserDataForm(data);
    }
}

async function refreshAccessToken () {
    const response = await fetch("/api/auth/refresh", {
        method: 'POST',
        credentials: 'include'
    });

    if (response.ok) {
        return true;
    } else {
        return false;
    }
}

function showWelcomeMessage (data) {
    const h3 = document.getElementById('welcome-message');
    h3.textContent = data.message;
}


function showForbiddenMessage (data) {
    const container = document.getElementById('main-container');
    const h2Cod = document.createElement('h2');
    const h2Text = document.createElement('h2');
    container.innerHTML = "";
    h2Cod.style.color = "#FF2400";
    h2Text.style.color = "#FF2400";
    h2Cod.textContent = "Код ошибки: 403";
    h2Text.textContent = data.message;
    container.appendChild(h2Cod);
    container.appendChild(h2Text);
}

function updateUserDataForm (user) {
    console.log(user);
    document.getElementById('updateUserForm').addEventListener('submit', async (e) => {
        e.preventDefault();

        const name = document.getElementById('name');
        const username = document.getElementById('username');
        const email = document.getElementById('email');
        const phone = document.getElementById('phone');
        const role = document.getElementById('role');
        const balance = document.getElementById('balance');
        const balanceValue = balance.value.trim();

        const response = await fetch('/api/admin/user-update-data/' + user.username, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include',
            body: JSON.stringify ({
                name: name.value,
                username: username.value,
                email: email.value,
                phone: phone.value,
                role: (role.value == "notinfo" ? user.role : role.value),
                balance: balanceValue !== "" ? parseInt(balanceValue) : user.balance
            })
        });

        if (response.ok) {
            const data = await response.json();
            showAnswerServer(data, true);
            return;
        } else {
            const data = await response.json();
            showAnswerServer(data, false);
            return;
        }
    })
}

function showAnswerServer (data, status) {
    const container = document.getElementById('block-answer');
    container.innerHTML = "";
    container.classList.add('visible');

    if (status) {
        container.classList.add('good-answer');
        container.textContent = data.message;
    } else {
        container.classList.add('bad-answer');
        container.textContent = data.message;
    }
}

loadUpdateUser();