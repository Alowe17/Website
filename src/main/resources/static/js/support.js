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

async function loadSupport () {
    const response = await fetch('/api/old-message-support', {
        method: 'GET',
        credentials: 'include'
    })

    if (response.status == 401) {
        const refreshed = await refreshAccessToken()

        if (refreshed) {
            return loadSupport();
        } else {
            window.location.href = "/login";
            return;
        }
    }
    
    if (response.ok) {
        const data = await response.json();
        loadSupportData(data);
    } else {
        const data = await response.json();
    }
}

function loadSupportData (data) {
    const container = document.getElementById('old-support-requests');

    container.innerHTML = "";

    if (data.length == 0) {
        const empty = document.createElement('p');
        empty.classList.add('empty-history');
        empty.textContent = "Вы не обращались еще в поддержку!";
        container.appendChild(empty);
        return;
    }

    data.forEach(element => {
        const blockMessage = document.createElement('div');
        const message = document.createElement('p');
        const user = document.createElement('h3');
        const status = document.createElement('h4');
        const answer = document.createElement('p');
        const date = document.createElement('p');
        const administrator = document.createElement('h3');
        let statusMessage = "";

        blockMessage.classList.add('block-message-support');
        message.classList.add('block-message-response');
        user.classList.add('block-user-response');
        status.classList.add('block-status-response');
        answer.classList.add('block-answer-response');
        date.classList.add('block-date-response');
        administrator.classList.add('block-administrator-response');

        if (element.status == "NEW") {
            statusMessage = "Новое обращение";
            status.classList.add('status-new');
        } else if (element.status == "REJECTED") {
            statusMessage = "Отклонено";
            status.classList.add('status-rejected');
        } else if (element.status == "IN_PROGRESS") {
            statusMessage = "На рассмотрении";
            status.classList.add('status-review');
        } else if (element.status == "CLOSED") {
            statusMessage = "Успешно обработано";
            status.classList.add('status-closed');
        } else {
            statusMessage = "Неизвестно";
        }

        message.textContent = "Обращение: " + element.message;
        user.textContent = "Вы: " + element.user.username;
        status.textContent = "Статус: " + statusMessage;
        answer.textContent = "Ответ: " + element.answer != null ? element.answer : "Ответа еще нет!";
        date.textContent = element.date;
        administrator.textContent = "Ответ дал: " + (element.administrator?.username || "Ответа еще нет!");

        blockMessage.appendChild(user);
        blockMessage.appendChild(status);
        blockMessage.appendChild(message);
        blockMessage.appendChild(administrator);
        blockMessage.appendChild(answer);
        blockMessage.appendChild(date);

        container.appendChild(blockMessage);
    });
}

document.getElementById('contact-support-form').addEventListener("submit", async (e) => {
    e.preventDefault();

    const message = document.getElementById('message');
    const responseBlock = document.getElementById('server-response');
    const container = document.getElementById('container-asnwer');
    const answer = document.createElement('h3');

    responseBlock.classList.remove('success', 'error', 'visible');
    responseBlock.innerHTML = '<span>Отправляем...</span>';

    const response = await fetch('/api/support/message-sent', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            message: message.value, 
            createdDate: new Date().toISOString().split('T')[0]
        }),
        credentials: 'include'
    });

    const data = await response.json();

    if (response.ok) {
        responseBlock.classList.add('success', 'visible');
        responseBlock.innerHTML = `<span>${data.message || 'Обращение успешно отправлено! Спасибо ❤️'}</span>`;
        message.innerHTML = "";
    } else {
        responseBlock.classList.add('error', 'visible');
        responseBlock.innerHTML = `<span>${data.message || 'Что-то пошло не так... Попробуй позже.'}</span>`;
    }

    setTimeout(() => {
        responseBlock.classList.remove('visible');
        setTimeout(() => responseBlock.innerHTML = '', 400);
    }, 5000);
})

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

loadSupport();