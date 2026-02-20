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
})

async function loadAdmin () {
    const response = await fetch('/api/admin', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken()

        if (refreshed) {
            return loadAdmin();
        } else if (response.status == 403) {
            const data = await response.json();
            showError (data, response.status);
            return;
        } else {
            window.location.href = "/login";
            return;
        }
    }
    
    if (response.ok) {
        const data = await response.json();
        showWelcomeMessage (data);
        loadAdminDataUser();
        loadAdminDataEmployee();
        loadAdminDataDish();
        loadAdminDataProduct();
        loadAdminDataSupport();
    } else {
        const data = await response.json();
        showError(data, response.status);
    }
}

function showWelcomeMessage (data) {
    const container = document.getElementById('welcomeMessage');
    container.textContent = data.message    ;
}

function showError (data, status) {
    const container = document.getElementById('container');
    container.innerHTML = "";

    if (status == 403) {
        if (data.error) {
            container.textContent += data.error + ": ";
        }

        container.textContent += data.message;
        container.classList.add('server-response', 'bad-answer');
    } else {
        container.textContent += "Ошибка " + status + ": ";
        container.textContent += data.message;
        container.classList.add('server-response', 'bad-answer');
    }
}

async function loadAdminDataUser () {
    const response = await fetch('/api/admin-list/user', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return loadAdminDataUser();
        } else {
            window.location.href = "/login";
            return;
        }
    } else if (response.ok) {
        const data = await response.json();
        showAdminDataUser (data);
    }
}

async function loadAdminDataEmployee () {
    const response = await fetch('/api/admin-list/employee', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return loadAdminDataEmployee();
        } else {
            window.location.href = "/login";
            return;
        }
    } else if (response.ok) {
        if (response.status == 204) {
            showAdminDataEmployee (null);
            return;
        }

        const data = await response.json();
        showAdminDataEmployee (data);
    } else {
        showAdminDataEmployee (null);
    }
}

async function loadAdminDataDish () {
    const response = await fetch('/api/admin-list/dish', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return loadAdminDataDish();
        } else {
            window.location.href = "/login";
            return;
        }
    } else if (response.ok) {
        if (response.status == 204) {
            showAdminDataDish (null);
            return;
        }

        const data = await response.json();
        showAdminDataDish (data);
    } else {
        showAdminDataDish(null);
    }
}

async function loadAdminDataProduct () {
    const response = await fetch('/api/admin-list/product', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return loadAdminDataProduct();
        } else {
            window.location.href = "/login";
            return;
        }
    } else if (response.ok) {
        if (response.status == 204) {
            showAdminDataProduct (null);
            return;
        }

        const data = await response.json();
        showAdminDataProduct (data);
    } else {
        showAdminDataProduct (null);
    }
}

async function loadAdminDataSupport () {
    const response = await fetch('/api/admin-list/support', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return loadAdminDataSupport();
        } else {
            window.location.href = "/login";
            return;
        }
    } else if (response.status == 204) {
        showAdminDataSupport (null);
        return;
    } else if (response.ok) {
        const data = await response.json();
        showAdminDataSupport (data);
    }
}

function showAdminDataUser (data) {
    const container = document.getElementById('table-user');
    const table = container.parentElement;
    let id = 0;
    if (data === null) {
        const errorText = document.createElement('h3');
        errorText.textContent = "В базе данных не найдено пользователей!";
        errorText.style.color = "#FF2400";
        errorText.style.fontWeight = "bold";
        container.style.display = "none";
        table.appendChild(errorText);
        return;
    }

    table.style.display = "block";

    data.forEach(element => {
        const tbody = document.createElement('tbody');
        const tr = document.createElement('tr');
        const tdId = document.createElement('td');
        const tdName = document.createElement('td');
        const tdUsername = document.createElement('td');
        const tdEmail = document.createElement('td');
        const tdPhone = document.createElement('td');
        const tdBirthDate = document.createElement('td');
        const tdRole = document.createElement('td');
        const tdBalance = document.createElement('td');
        const tdUserProgress = document.createElement('td');
        const tdFunctions = document.createElement('td');
        const hrefFunction1 = document.createElement('a');
        hrefFunction1.href = "/admin/update-user/" + element.userDto.username;
        const hrefFunction2 = document.createElement('a');
        hrefFunction2.href = "/admin/change-password/" + element.userDto.username;
        const buttonFunction1 = document.createElement('button');
        const buttonFunction2 = document.createElement('button');

        tdFunctions.classList.add('action-buttons-cell');
        buttonFunction1.classList.add("action-button", "primary-action");
        buttonFunction2.classList.add("action-button", "secondary-action");

        tdName.textContent = element.userDto.name;
        tdUsername.textContent = element.userDto.username;
        tdEmail.textContent = element.userDto.email;
        tdPhone.textContent = element.userDto.phone;
        tdBirthDate.textContent = element.userDto.birthdate;
        tdRole.textContent = element.userDto.role;
        tdBalance.textContent = element.userDto.balance;
        tdUserProgress.textContent = element.chapterDto.number;
        buttonFunction1.textContent = "Обновить аккаунт";
        buttonFunction2.textContent = "Сменить пароль";
        tdId.textContent = id++;

        tr.appendChild(tdId);
        tr.appendChild(tdName);
        tr.appendChild(tdUsername);
        tr.appendChild(tdEmail);
        tr.appendChild(tdPhone);
        tr.appendChild(tdBirthDate);
        tr.appendChild(tdRole);
        tr.appendChild(tdBalance);
        tr.appendChild(tdUserProgress);
        hrefFunction1.appendChild(buttonFunction1);
        hrefFunction2.appendChild(buttonFunction2);
        tdFunctions.appendChild(hrefFunction1);
        tdFunctions.appendChild(hrefFunction2);
        tr.appendChild(tdFunctions);
        tbody.appendChild(tr);
        container.appendChild(tbody);
    });
}

function showAdminDataEmployee (data) {
    const container = document.getElementById('table-employee');
    const table = container.parentElement;
    let id = 0;
    if (data === null) {
        const errorText = document.createElement('h3');
        errorText.textContent = "В базе данных не найдено NPC!";
        errorText.style.color = "#FF2400";
        errorText.style.fontWeight = "bold";
        container.style.display = "none";
        table.appendChild(errorText);
        return;
    }

    table.style.display = "block";

    data.forEach(element => {
        const tbody = document.createElement('tbody');
        const tr = document.createElement('tr');
        const tdId = document.createElement('td');
        const tdName = document.createElement('td');
        const tdUsername = document.createElement('td');
        const tdEmail = document.createElement('td');
        const tdPhone = document.createElement('td');
        const tdRole = document.createElement('td');
        const tdSalary = document.createElement('td');
        const tdBonus = document.createElement('td');
        const tdFunctions = document.createElement('td');
        const hrefFunction1 = document.createElement('a');
        hrefFunction1.href = "/admin/create-new/npc";
        const hrefFunction2 = document.createElement('a');
        hrefFunction2.href = "/admin/update-npc/" + element.username;
        const buttonFunction1 = document.createElement('button');
        const buttonFunction2 = document.createElement('button');

        tdFunctions.classList.add('action-buttons-cell');
        buttonFunction1.classList.add("action-button", "primary-action");
        buttonFunction2.classList.add("action-button", "secondary-action");

        tdName.textContent = element.name;
        tdUsername.textContent = element.username;
        tdEmail.textContent = element.email;
        tdPhone.textContent = element.phone;
        tdRole.textContent = element.role;
        tdSalary.textContent = element.salary;
        tdBonus.textContent = element.bonus;
        buttonFunction1.textContent = "Создать NPC";
        buttonFunction2.textContent = "Обновить NPC";
        tdId.textContent = id++;

        tr.appendChild(tdId);
        tr.appendChild(tdName);
        tr.appendChild(tdUsername);
        tr.appendChild(tdEmail);
        tr.appendChild(tdPhone);
        tr.appendChild(tdRole);
        tr.appendChild(tdSalary);
        tr.appendChild(tdBonus);
        hrefFunction1.appendChild(buttonFunction1);
        hrefFunction2.appendChild(buttonFunction2);
        tdFunctions.appendChild(hrefFunction1);
        tdFunctions.appendChild(hrefFunction2);
        tr.appendChild(tdFunctions);
        tbody.appendChild(tr);
        container.appendChild(tbody);
    });
}

function showAdminDataDish (data) {
    const container = document.getElementById('table-dish');
    const table = container.parentElement;
    let id = 0;
    if (data === null) {
        const errorText = document.createElement('h3');
        errorText.textContent = "В базе данных не найдено блюд!";
        errorText.style.color = "#FF2400";
        errorText.style.fontWeight = "bold";
        container.style.display = "none";
        table.appendChild(errorText);
        return;
    }

    table.style.display = "block";
    data.forEach(element => {
        const tbody = document.createElement('tbody');
        const tr = document.createElement('tr');
        const tdId = document.createElement('td');
        const tdName = document.createElement('td');
        const tdCategory = document.createElement('td');
        const tdPrice = document.createElement('td');
        const tdFunctions = document.createElement('td');
        const hrefFunction1 = document.createElement('a');
        hrefFunction1.href = "/admin/create-new/dish";
        const hrefFunction2 = document.createElement('a');
        hrefFunction2.href = "/admin/update-dish/" + (id + 1);
        const buttonFunction1 = document.createElement('button');
        const buttonFunction2 = document.createElement('button');

        tdFunctions.classList.add('action-buttons-cell');
        buttonFunction1.classList.add("action-button", "primary-action");
        buttonFunction2.classList.add("action-button", "secondary-action");

        tdName.textContent = element.name;
        tdCategory.textContent = element.category;
        tdPrice.textContent = element.price;
        buttonFunction1.textContent = "Создать блюдо";
        buttonFunction2.textContent = "Обновить блюдо";
        tdId.textContent = id++;

        tr.appendChild(tdId);
        tr.appendChild(tdName);
        tr.appendChild(tdCategory);
        tr.appendChild(tdPrice);
        hrefFunction1.appendChild(buttonFunction1);
        hrefFunction2.appendChild(buttonFunction2);
        tdFunctions.appendChild(hrefFunction1);
        tdFunctions.appendChild(hrefFunction2);
        tr.appendChild(tdFunctions);
        tbody.appendChild(tr);
        container.appendChild(tbody);
    });
}

function showAdminDataProduct (data) {
    const container = document.getElementById('table-product');
    const table = container.parentElement;
    let id = 0;
    if (data === null) {
        const errorText = document.createElement('h3');
        errorText.textContent = "В базе данных не найдено товаров!";
        errorText.style.color = "#FF2400";
        errorText.style.fontWeight = "bold";
        container.style.display = "none";
        table.appendChild(errorText);
        return;
    }

    table.style.display = "block";

    data.forEach(element => {
        const tbody = document.createElement('tbody');
        const tr = document.createElement('tr');
        const tdId = document.createElement('td');
        const tdName = document.createElement('td');
        const tdCategory = document.createElement('td');
        const tdPrice = document.createElement('td');
        const tdFunctions = document.createElement('td');
        const hrefFunction1 = document.createElement('a');
        hrefFunction1.href = "/admin/create-new/product";
        const hrefFunction2 = document.createElement('a');
        hrefFunction2.href = "/admin/update-product/" + (id + 1);
        const buttonFunction1 = document.createElement('button');
        const buttonFunction2 = document.createElement('button');

        tdFunctions.classList.add('action-buttons-cell');
        buttonFunction1.classList.add("action-button", "primary-action");
        buttonFunction2.classList.add("action-button", "secondary-action");

        tdName.textContent = element.name;
        tdCategory.textContent = element.category;
        tdPrice.textContent = element.price;
        buttonFunction1.textContent = "Создать товар";
        buttonFunction2.textContent = "Обновить товар";
        tdId.textContent = id++;

        tr.appendChild(tdId);
        tr.appendChild(tdName);
        tr.appendChild(tdCategory);
        tr.appendChild(tdPrice);
        hrefFunction1.appendChild(buttonFunction1);
        hrefFunction2.appendChild(buttonFunction2);
        tdFunctions.appendChild(hrefFunction1);
        tdFunctions.appendChild(hrefFunction2);
        tr.appendChild(tdFunctions);
        tbody.appendChild(tr);
        container.appendChild(tbody);
    });
}

function showAdminDataSupport (data) {
    const container = document.getElementById('table-support');
    const table = container.parentElement;
    let id = 0;
    if (data === null) {
        const errorText = document.createElement('h3');
        errorText.textContent = "В базе данных не найдено обращений в поддержку!";
        errorText.style.color = "#FF2400";
        errorText.style.fontWeight = "bold";
        container.style.display = "none";
        table.appendChild(errorText);
        return;
    }

    table.style.display = "block";

    data.forEach(element => {
        const currentId = id + 1;
        const tbody = document.createElement('tbody');
        const tr = document.createElement('tr');
        const tdId = document.createElement('td');
        const tdMessage = document.createElement('td');
        const tdUserUsername = document.createElement('td');
        const tdStatus = document.createElement('td');
        const tdAnswer = document.createElement('td');
        const tdDate = document.createElement('td');
        const tdAdministrator = document.createElement('td');
        const tdFunctions = document.createElement('td');
        const hrefFunction1 = document.createElement('a');
        hrefFunction1.href = "/admin/support-answer/" + id;
        const buttonFunction1 = document.createElement('button');
        const buttonFunction2 = document.createElement('button');
        buttonFunction2.addEventListener('click', async (e) => {
            e.preventDefault();

            const response = await fetch('/api/admin/rejected-message/' + currentId, {
                method: 'POST',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    message: element.message,
                    username: element.username,
                    status: element.status,
                    answer: element.answer,
                    date: element.date,
                    administrator: null
                })
            });

            console.log('Статус ответа:', response.status);

            if (response.ok) {
                const data = await response.json();
                showAnswerServer (data, response.status);
            } else {
                const data = await response.json();
                showAnswerServer (data, response.status);
            }
        });

        let statusMessage = "";

        tdFunctions.classList.add('action-buttons-cell');
        buttonFunction1.classList.add("action-button", "primary-action");
        buttonFunction2.classList.add("action-button", "secondary-action");

        if (element.status == "NEW") {
            statusMessage = "Новое обращение";
            tdStatus.classList.add('status-new');
        } else if (element.status == "REJECTED") {
            statusMessage = "Отклонено";
            tdStatus.classList.add('status-rejected');
        } else if (element.status == "IN_PROGRESS") {
            statusMessage = "На рассмотрении";
            tdStatus.classList.add('status-review');
        } else if (element.status == "CLOSED") {
            statusMessage = "Успешно обработано";
            tdStatus.classList.add('status-closed');
        } else {
            statusMessage = "Неизвестно";
        }

        tdMessage.textContent = element.message;
        tdUserUsername.textContent = element.user.username;
        tdStatus.innerHTML = `<span class="status-badge">${statusMessage}</span>`;
        tdAnswer.textContent = element.answer != null ? element.answer : "Ответа нет";
        tdDate.textContent = element.date;
        tdAdministrator.textContent = element.administrator != null ? element.administrator.username : "Не рассмотрено";
        buttonFunction1.textContent = "Ответить";
        buttonFunction2.textContent = "Закрыть обращение!";
        tdId.textContent = currentId;

        tr.appendChild(tdId);
        tr.appendChild(tdMessage);
        tr.appendChild(tdUserUsername);
        tr.appendChild(tdStatus);
        tr.appendChild(tdDate);
        tr.appendChild(tdAdministrator);
        tr.appendChild(tdAnswer);
        hrefFunction1.appendChild(buttonFunction1);
        tdFunctions.appendChild(hrefFunction1);
        tdFunctions.appendChild(buttonFunction2);
        tr.appendChild(tdFunctions);
        tbody.appendChild(tr);
        container.appendChild(tbody);
        id++;
    })
}

function showAnswerServer (data, status) {
    const container = document.getElementById('container-answer');
    container.innerHTML = "";

    if (status == 200) {
        container.classList.add('server-response', 'good-answer');
    } else {
        container.classList.add('server-response', 'bad-answer');
    }

    container.textContent = data.message;
}

loadAdmin();