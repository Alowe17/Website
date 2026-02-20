async function loadUpdateNpc () {
    const response = await fetch('/api/admin', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const data = await response.json();
        const path = window.location.pathname;
        const partPath = path.split('/');
        const username = partPath[partPath.length - 1];
        const container = document.getElementById('welcomeMessage');
        container.textContent = data.message;   
        loadNpcData (username);
        return;
    } else if (response.status == 401) {
        const refreshed = await refreshAccessToken ();

        if (refreshed) {
            return loadUpdateNpc ();
        } else {
            window.location.href = "/login";
        }
    } else if (response.status == 403) {
        const data = await response.json();
        showForbiddenMessage (data);
        return;
    } else {
        const data = await response.json();
        return;
    }
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
    })

    if (response.ok) {
        return true;
    } else {
        return false;
    }
}

async function loadNpcData (username) {
    const response = await fetch('/api/admin/info-npc/' + username, {
        method: 'GET',
        credentials: 'include'
    });

    if (response.status == 200) {
        const data = await response.json();
        showDataNpc (data);
    } else if (response.status == 403) {
        const data = await response.json();
        showForbiddenMessage(data);
    } else if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return loadUpdateNpc();
        } else {
            window.location.href = "/login";
        }
    } else {
        const data = await response.json();
    }
}

function showDataNpc (data) {
    const username = document.getElementById('username-old');
    username.textContent = data.username;
}

document.getElementById('update-npc-data').addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById('name').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const email = document.getElementById('email').value;
    const phone = document.getElementById('phone').value;
    const role = document.getElementById('role').value;
    const salary = document.getElementById('salary').value;
    const bonus = document.getElementById('bonus').value;
    const usernameOld = document.getElementById('username-old').textContent;

    const response = await fetch('/api/admin/update-npc/' + usernameOld, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'include',
        body: JSON.stringify({
            name: name,
            username: username,
            password: password,
            email: email,
            phone: phone,
            role: role,
            salary: parseInt(salary),
            bonus: parseInt(bonus)
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
    } else {
        container.classList.add('bad-answer');
    }

    container.textContent = data.message;
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

loadUpdateNpc();