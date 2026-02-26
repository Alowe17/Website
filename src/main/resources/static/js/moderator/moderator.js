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
    const response = await fetch('/api/management', {
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
        const data = await response.json();
        showErrorMessage(data, response.status);
    }
}

function showWelcomeMessage (data) {
    const container = document.getElementById('welcome-message');
    container.textContent = "Добро пожаловать, " + data +"! 👋";
}

function showAuthError() {
    const container = document.getElementById('container');
    container.innerHTML = '';

    const div = document.createElement('div');
    const divIcon = document.createElement('div');
    const h3Info = document.createElement('h3');
    const text = document.createElement('p');
    const link = document.createElement('a');

    divIcon.classList.add('error-icon');
    link.classList.add('back-link');
    div.classList.add('global-error');
    link.href = "/login";

    divIcon.textContent = "🔑";
    h3Info.textContent = "Сессия истекла или вы не авторизованы";
    text.textContent = "Пожалуйста, войдите в аккаунт";
    link.textContent = "→ Перейти на страницу входа";

    div.appendChild(divIcon);
    div.appendChild(h3Info);
    div.appendChild(text);
    div.appendChild(link);

    container.appendChild(div);
}

function showErrorMessage (data, status) {
    const container = document.getElementById('container');
    container.innerHTML = '';

    const message = data?.message || (status === 403 ? "У вас недостаточно прав для доступа к модераторской панели." : `Произошла ошибка (${status})`);

    const div = document.createElement('div');
    const divIcon = document.createElement('div');
    const h3Info = document.createElement('h3');
    const text = document.createElement('p');
    const link = document.createElement('a');

    divIcon.classList.add('error-icon');
    link.classList.add('back-link');
    div.classList.add('global-error');
    link.href = "/index";

    divIcon.textContent = "🚫";
    h3Info.textContent = "Доступ запрещён";
    text.textContent = message;
    link.textContent = "← Вернуться на главную";
    
    div.appendChild(divIcon);
    div.appendChild(h3Info);
    div.appendChild(text);
    div.appendChild(link);
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
    } else if (response.status == 404) {
        showSupportTicketsNew([]);
    } else {
        const data = await response.json();
        showErrorMessage(data, response.status);
    }
}

function showSupportTicketsNew (data) {
    const container = document.getElementById('container-tickets-new');
    container.innerHTML = "";

    if (!data || data.length === 0) {
        const div = document.createElement('div');
        div.classList.add('server-response', 'info');
        div.textContent = "Пока нет новых обращений. Отличная работа! ✨";
        container.appendChild(div);
        return;
    }

    data.forEach(ticket => {
        const card = document.createElement('div');
        const button = document.createElement('a');
        const date = document.createElement('div');
        const username = document.createElement('h4');
        const userMessage = document.createElement('div');

        card.classList.add('ticket-card');
        date.classList.add('ticket-date');
        userMessage.classList.add('ticket-user-message');
        button.classList.add('ticket-button');

        date.textContent = ticket.date;
        username.textContent = "Обратившийся: " + ticket.user.username;
        userMessage.textContent = ticket.message;
        button.href = "/management/support-tickets/" + ticket.id;
        button.textContent = "Ответить на обращение";

        card.appendChild(username);
        card.appendChild(date);
        card.appendChild(userMessage);
        card.appendChild(button);

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
    } else if (response.status == 404) {
        showSupportTicketsAnswered([]);
    } else {
        const data = await response.json();
        showErrorMessage(data, response.status);
    }
}

function showSupportTicketsAnswered (data) {
    const container = document.getElementById('container-tickets-answered');
    container.innerHTML = "";

    if (!data || data.length === 0) {
        const div = document.createElement('div');
        div.classList.add('server-response', 'info');
        div.textContent = "Пока нет обработанных обращений.";
        container.appendChild(div);
        return;
    }

    data.forEach(ticket => {
        const card = document.createElement('div');
        const adminInfo = document.createElement('div');
        const username = document.createElement('h4');
        const date = document.createElement('div');
        const userMessage = document.createElement('div');
        const answerBlock = document.createElement('div');

        card.classList.add('ticket-card');
        userMessage.classList.add('ticket-user-message');
        date.classList.add('ticket-date');
        answerBlock.classList.add('ticket-answer');
        adminInfo.classList.add('ticket-admin');
        
        username.textContent = "Обратившийся: " + ticket.user.username;
        date.textContent = ticket.date;
        userMessage.textContent = ticket.message;
        answerBlock.textContent = ticket.answer || "Ответ ещё не добавлен";
        adminInfo.textContent = "Ответил: " + (ticket.administrator ? ticket.administrator.username : '—');

        card.appendChild(username);
        card.appendChild(date);
        card.appendChild(userMessage);
        card.appendChild(answerBlock);
        card.appendChild(adminInfo);

        container.appendChild(card);
    });
}

loadModeratorPanel();