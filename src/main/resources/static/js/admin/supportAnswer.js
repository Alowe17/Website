async function loadSupportAnswer () {
    const response = await fetch('/api/management', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const data = await response.text();
        showWelcomeMessage (data);
        loadMessage();
    } else if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return loadSupportAnswer();
        } else {
            const data = await response.json();
            showError (data);
        }
    } else if (response.status == 403) {
        const data = await response.json();
        showError (data);
    } else {
        const data = await response.json();
        showError (data);
    }
}

function showWelcomeMessage (data) {
    const h3 = document.getElementById('welcomeMessage');
    h3.textContent = "Добро пожаловать, " + data + "!";
}

function showError(data) {
    const container = document.getElementById('container');
    container.innerHTML = "";
    container.style.color = "red";
    container.style.fontWeight = "bold";

    let text = "";
    if (data.error) {
        text += data.error + ": ";
    }
    
    text += data.message;
    container.textContent = text;
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

async function loadMessage () {
    const path = window.location.pathname;
    const part = path.split('/');
    const id = part[part.length - 1];
    const response = await fetch('/api/management/load-message/' + id, {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const data = await response.json();
        showMessageInfo (data);
    } else {
        const data = await response.json();
        showError (data);
    }
}

function showMessageInfo (data) {
    const username = document.getElementById('username').textContent = data.user.username;
    const message = document.getElementById('message').textContent = data.message;
    const date = document.getElementById('date').textContent = data.date;
}

document.getElementById('reply-to-message').addEventListener("submit", async (e) => {
    e.preventDefault();

    const path = window.location.pathname;
    const part = path.split('/');
    const id = part[part.length - 1];
    const answer = document.getElementById('answer').value;
    const status = document.getElementById('status').value;

    const response = await fetch('/api/management/reply-message/' + id, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify ({
            id: id,
            status: status,
            answer: answer
        })
    });

    if (response.ok) {
        const data = await response.json();
        showAnswerServer (data, true);
    } else {
        const data = await response.json();
        showAnswerServer (data, false);
    }
})

function showAnswerServer (data, status) {
    const container = document.getElementById('container-answer');
    container.innerHTML = "";

    if (status) {
        container.classList.add('good-answer');
        container.textContent = data.message;
    } else {
        container.classList.add('bad-answer');
        container.textContent = data.message;
    }
}

document.getElementById('logoutForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const response = await fetch('/api/auth/logout', {
        method: 'POST',
        credentials: 'include'
    });

    if (response.ok) {
        window.location.href = "/login";
    } else {
        alert('Что-то пошло не так и не получилось выполнить выход из аккаунта. Попробуйте снова позже или обратитесь в поддержку!');
    }
});

loadSupportAnswer();