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
        let id = 0;
        const card = document.createElement('div');
        const h4Username = document.createElement('h4');
        const ticketUser = document.createElement('div');
        const ticketDate = document.createElement('div');
        const ticketMessage = document.createElement('div');
        const link = document.createElement('a');

        ticketUser.classList.add('ticket-user');
        ticketDate.classList.add('ticket-date');
        ticketMessage.classList.add('ticket-message');
        link.classList.add('ticket-button');
        link.href = "/moderator/support-answer/" + id;
        card.classList.add('ticket-card');

        h4Username.textContent = ticket.user.username;
        ticketUser.textContent = ticket.user.username;
        ticketDate.textContent = ticket.date;
        ticketMessage.textContent = ticket.message;
        link.textContent = "Ответить на обращение";

        card.appendChild(h4Username);
        card.appendChild(ticketUser);
        card.appendChild(ticketDate);
        card.appendChild(ticketMessage);
        card.appendChild(link);
        container.appendChild(card);
        id++;
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
        const h4Username = document.createElement('h4');
        const ticketDate = document.createElement('div');
        const ticketBlockMessage = document.createElement('div');
        const strongMessage = document.createElement('strong');
        const messageText = document.createElement('div');
        const brMessage = document.createElement('br');
        const ticketBlockAnswer = document.createElement('div');
        const strongAnswer = document.createElement('strong');
        const answerText = document.createElement('div');
        const brAnswer = document.createElement('br');
        const ticketBlockAdmin = document.createElement('div');

        card.classList.add('ticket-card');
        ticketDate.classList.add('ticket-date');
        ticketBlockMessage.classList.add('ticket-user-message');
        ticketBlockAnswer.classList.add('ticket-answer');
        ticketBlockAdmin.classList.add('ticket-admin');
        
        h4Username.textContent = ticket.user.username;
        ticketDate.textContent = ticket.date;
        strongMessage.textContent = "Обращение пользователя:";

        ticketBlockMessage.appendChild(strongMessage);
        ticketBlockMessage.appendChild(brMessage);
        messageText.textContent = ticket.message;
        ticketBlockMessage.appendChild(messageText);
        strongAnswer.textContent = "Ответ модератора:";
        ticketBlockAnswer.appendChild(strongAnswer);
        ticketBlockAnswer.appendChild(brAnswer);
        answerText.textContent = ticket.answer || "Ответ ещё не добавлен";
        ticketBlockAnswer.appendChild(answerText);
        ticketBlockAdmin.textContent = "Ответил: " + ticket.administrator ? ticket.administrator.username : '—';
        card.appendChild(h4Username);
        card.appendChild(ticketDate);
        card.appendChild(ticketBlockMessage);
        card.appendChild(ticketBlockAnswer);
        card.appendChild(ticketBlockAdmin);
        container.appendChild(card);
    });
}

loadModeratorPanel();