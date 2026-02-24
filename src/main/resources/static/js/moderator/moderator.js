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

async function refreshAccessToken () {
    const response = await fetch("/api/auth/refresh", {
        method: 'POST',
        credentials: 'include'
    });
    return response.ok;
}

async function loadModeratorPanel () {
    const response = await fetch('/api/moderator', {
        method: 'GET',
        credentials: 'include'
    });
    
    if (response.ok) {
        const data = await response.text();
        showWelcomeMessage(data);
        loadDataSupportTicketsNew();
        loadDataSupportTicketsAnswered();
    } else if (response.status === 401) {
        const refreshed = await refreshAccessToken();
        if (refreshed) {
            return loadModeratorPanel();
        } else {
            showAuthError();
            return;
        }
    } else {
        const data = await response.json().catch(() => ({}));
        showErrorMessage(data, response.status);
    }
}

function showWelcomeMessage (data) {
    const container = document.getElementById('welcome-message');
    container.textContent = `Добро пожаловать, ${data}! 👋`;
}

function showAuthError() {
    const container = document.getElementById('container');
    container.innerHTML = '';

    const div = document.createElement('div');
    div.classList.add('global-error');
    div.innerHTML = `
        <div class="error-icon">🔑</div>
        <h3>Сессия истекла</h3>
        <p>Пожалуйста, войдите в аккаунт заново</p>
        <a href="/login" class="back-link">→ Перейти на страницу входа</a>
    `;
    container.appendChild(div);
}

function showErrorMessage (data, status) {
    const container = document.getElementById('container');
    container.innerHTML = ''; // полностью очищаем всё

    const message = data?.message || 
                   (status === 403 ? "У вас недостаточно прав для доступа к модераторской панели." : 
                   `Произошла ошибка (${status})`);

    const div = document.createElement('div');
    div.classList.add('global-error');
    div.innerHTML = `
        <div class="error-icon">🚫</div>
        <h3>Доступ запрещён</h3>
        <p>${message}</p>
        <a href="/index" class="back-link">← Вернуться на главную</a>
    `;
    container.appendChild(div);
}

async function loadDataSupportTicketsNew () {
    const response = await fetch('/api/moderator/support-tickets/new', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const data = await response.json();
        showSupportTicketsNew(data);
    } else {
        const data = await response.json().catch(() => ({}));
        showErrorMessage(data, response.status);
    }
}

function showSupportTicketsNew (data) {
    const container = document.getElementById('container-tickets-new');
    container.innerHTML = "";

    if (!data || data.length === 0) {
        const div = document.createElement('div');
        div.classList.add('server-response');
        div.textContent = "Пока нет новых обращений. Отличная работа! ✨";
        container.appendChild(div);
        return;
    }

    data.forEach(ticket => {
        const card = document.createElement('div');
        card.classList.add('ticket-card');

        card.innerHTML = `
            <h4>${ticket.user.username}</h4>
            <div class="ticket-user">@${ticket.user.username}</div>
            <div class="ticket-date">${ticket.date}</div>
            <div class="ticket-message">${ticket.message}</div>
            
            <a href="/moderator/support-answer/${ticket.id}" class="ticket-button">
                Ответить на обращение
            </a>
        `;

        container.appendChild(card);
    });
}

async function loadDataSupportTicketsAnswered () {
    const response = await fetch('/api/moderator/support-tickets/answered', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const data = await response.json();
        showSupportTicketsAnswered(data);
    } else {
        const data = await response.json().catch(() => ({}));
        showErrorMessage(data, response.status);
    }
}

function showSupportTicketsAnswered (data) {
    const container = document.getElementById('container-tickets-answered');
    container.innerHTML = "";

    if (!data || data.length === 0) {
        const div = document.createElement('div');
        div.classList.add('server-response');
        div.textContent = "Пока нет обработанных обращений.";
        container.appendChild(div);
        return;
    }

    data.forEach(ticket => {
        const card = document.createElement('div');
        card.classList.add('ticket-card');

        card.innerHTML = `
            <h4>${ticket.user.username}</h4>
            <div class="ticket-date">${ticket.date}</div>
            
            <!-- Обращение пользователя -->
            <div class="ticket-user-message">
                <strong>Обращение пользователя:</strong><br>
                ${ticket.message}
            </div>
            
            <!-- Ответ модератора -->
            <div class="ticket-answer">
                <strong>Ответ модератора:</strong><br>
                ${ticket.answer || "Ответ ещё не добавлен"}
            </div>
            
            <div class="ticket-admin">
                Ответил: ${ticket.administrator ? ticket.administrator.username : '—'}
            </div>
        `;

        container.appendChild(card);
    });
}

loadModeratorPanel();