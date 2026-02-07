async function loadProfile() {
    const token = localStorage.getItem('accessToken');

    const loadNewToken = await fetch('/api/auth/refresh', {
        method: 'POST',
        credentials: "include"
    });

    if (loadNewToken.ok) {
        const data = await loadNewToken.json();
        localStorage.setItem('accessToken', data.accessToken);
    }

    if (loadNewToken.status == 401) {
        window.location.href = "/login";
    }

    const response = await fetch('/api/profile', {
        method: 'GET',
        headers: {
            "Authorization": "Bearer " + token
        }
    });

    if (response.status == 401) {
        window.location.href = "/login";
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

loadProfile();