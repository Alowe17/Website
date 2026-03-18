async function loadProfile() {
    const response = await fetch('/role-master/api/profile', {
        method: 'GET',
        credentials: "include",
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken()

        if (refreshed) {
            return loadProfile();
        } else {
            window.location.href = "/role-master/login";
            return;
        }
    } else {
        const data = await response.json();
        loadDate (data);
    }
}

function loadDate (data) {
    // Данные пользователя
    const elementWelcomeName = document.getElementById('welcome-name');
    const elementName = document.getElementById('name');
    const elementUsername = document.getElementById('username');
    const elementEmail = document.getElementById('email');
    const elementPhone = document.getElementById('phone');
    const elementBirthdate = document.getElementById('birthdate');
    const elementBalance = document.getElementById('balance');
    const elementRole = document.getElementById('role');
    const elementUserProgress = document.getElementById('user-progress');
    const dateObj = new Date(data.userDto.birthdate);

    // Новые данные пользователя
    const elementNewName = document.getElementById('new-name');
    const elementNewUsername = document.getElementById('new-username');
    const elementNewEmail = document.getElementById('new-email');
    const elementNewPhone = document.getElementById('new-phone');
    const elementNewPassword = document.getElementById('new-password');

    // Отображение данных пользователя в полях
    elementWelcomeName.textContent = data.userDto.name;
    elementName.textContent = data.userDto.name;
    elementUsername.textContent = data.userDto.username;
    elementEmail.textContent = data.userDto.email;
    elementPhone.textContent = data.userDto.phone;
    elementBirthdate.textContent = dateObj.toLocaleDateString();
    elementBalance.textContent = data.userDto.balance;

    if (data.userDto.role == "ADMINISTRATOR") {
        elementRole.classList.add('role-admin');
        elementRole.textContent = "Администратор";
    } else if (data.userDto.role == "MODERATOR") {
        elementRole.classList.add('role-moder');
        elementRole.textContent = "Модератор";
    } else if (data.userDto.role == "NARRATIVEDESIGNER") {
        elementRole.classList.add('role-narrative-designer');
        elementRole.textContent = "Нарративный дизайнер";
    } else if (data.userDto.role == "TESTER") {
        elementRole.classList.add('role-tester');
        elementRole.textContent = "Тестировщик";
    } else if (data.userDto.role == "PREMIUM_USER") {
        elementRole.classList.add('role-premium-user');
        elementRole.textContent = "Подписчик";
    } else if (data.userDto.role == "PLAYER") {
        elementRole.classList.add('role-user');
        elementRole.textContent = "Игрок";
    } else {
        elementRole.classList.add('role-not-info');
        elementRole.textContent = "Роль не определена";
    }

    elementUserProgress.textContent = data.chapterDto.number;
}

async function refreshAccessToken () {
    const response = await fetch("/role-master/api/auth/refresh", {
        method: 'POST',
        credentials: 'include'
    });

    if (response.ok) {
        return true;
    } else {
        return false;
    }
}

document.getElementById('updateForm').addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById('new-name');
    const username = document.getElementById('new-username');
    const email = document.getElementById('new-email');
    const phone = document.getElementById('new-phone');
    const password = document.getElementById('new-password');
    const container = document.getElementById('container-result');
    const h3 = document.createElement('h3');

    container.innerHTML = "";

    const response = await fetch('/role-master/api/profile/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: "include",
        body: JSON.stringify({
            name: name.value,
            username: username.value,
            password: password.value,
            email: email.value,
            phone: phone.value
        })
    });


    const data = await response.json()

    if (response.status == 200) {
        h3.classList.add('successful-data-update');
        h3.textContent = data.message;
        container.appendChild(h3);
    } else {
        h3.classList.add('error');
        h3.textContent = data.message;
        container.appendChild(h3);
    }
})

document.getElementById('logoutForm').addEventListener("submit", async (e) => {
    e.preventDefault();

    const response = await fetch('/role-master/api/auth/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: "include"
    })

    if (response.ok) {
        window.location.href = "/role-master/login";
    } else {
        alert('Увы, что-то пошло не так и не получилось сохранить данные о выходе из аккаунта. Обратитесь в поддержку проекта!');
    }
})

document.getElementById('form-promo').addEventListener('submit', async (e) => {
    e.preventDefault();

    const promo = document.getElementById('promo').value;
    const answerDiv = document.getElementById('answer-server');

    answerDiv.innerHTML = "";

    const response = await fetch('/role-master/api/promo/code', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            promoCode: promo
        })
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return loadProfile();
        } else {
            window.location.href = "/role-master/login";
            return;
        }
    }

    const data = await response.json();

    showAnswerServer(data, response.ok);

    if (response.ok) {
        document.getElementById('promo').value = '';
        setTimeout(loadProfile, 1500);
    }
})

function showAnswerServer(data, status) {
    const answer = document.getElementById('answer-server');

    answer.innerHTML = "";
    const messageElement = document.createElement('div');
    messageElement.textContent = data.message;

    if (status) {
        messageElement.classList.add('successful-data-update');
    } else {
        messageElement.classList.add('error');
    }

    answer.appendChild(messageElement);
}

loadProfile();