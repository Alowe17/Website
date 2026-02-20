async function loadCreateNewNPC() {
    const response = await fetch('/api/admin', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const data = await response.json();
        const h3 = document.getElementById('welcomeMessage');
        h3.style.color = "green";
        h3.style.fontWeight = "bold";
        h3.textContent = data.message;
        return;
    } else if (response.status === 401) {
        const refreshed = await refreshAccessToken();
        if (refreshed) {
            return loadCreateNewNPC();
        }

        window.location.href = "/login";
        return;
    }

    const data = await response.json();
    showError(data);
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


async function refreshAccessToken() {
    const response = await fetch('/api/auth/refresh', {
        method: 'POST',
        credentials: 'include'
    });

    return response.ok;
}

document.getElementById('create-new-npc').addEventListener('submit', async (e) => {
    e.preventDefault();

    const payload = {
        name: document.getElementById('name').value,
        username: document.getElementById('username').value,
        password: document.getElementById('password').value,
        email: document.getElementById('email').value,
        phone: document.getElementById('phone').value,
        role: document.getElementById('role').value,
        salary: parseInt(document.getElementById('salary').value),
        bonus: parseInt(document.getElementById('bonus').value)
    };

    const response = await fetch('/api/admin/create-new/npc', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify(payload)
    });

    const data = await response.json();

    if (response.ok) {
        showAnswerServer(data, true);
    } else {
        showAnswerServer(data, false);
    }
});

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

document.getElementById('logoutForm').addEventListener("submit", async (e) => {
    e.preventDefault();

    const response = await fetch('/api/auth/logout', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: "include"
    });

    if (response.ok) {
        window.location.href = "/login";
    } else {
        alert('Увы, что-то пошло не так. Обратитесь в поддержку проекта!');
    }
});

loadCreateNewNPC();