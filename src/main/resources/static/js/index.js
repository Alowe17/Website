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

async function loadDataChapters () {
    const response = await fetch('/api/index/chapters-list', {
        method: 'GET',
        credentials: 'include'
    })

    if (response.status === 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return loadDataChapters();
        } else {
            showAuthError("Сессия истекла. Пожалуйста, войдите в аккаунт заново.");
            return;
        }
    }
    
    if (response.ok) {
        const data = await response.json();
        showChapters(data);
        loadDataCharacters();
    } else {
        const data = await response.json();
        showErrorLoadChapters(data, response.status);
    }
}

function showAuthError(message) {
    const containers = [
        document.getElementById('cards-container-guides'),
        document.getElementById('cards-container-chapters')
    ];

    containers.forEach(container => {
        if (!container) {
            return;
        }
        
        container.innerHTML = '';

        const div = document.createElement('div');
        div.classList.add('server-response', 'bad-answer');
        div.style.maxWidth = '720px';
        div.style.margin = '60px auto';
        div.innerHTML = `
            ${message}<br><br>
            <a href="/login" class="auth-link">
                → Перейти на страницу входа
            </a>
        `;
        container.appendChild(div);
    });
}

async function loadDataCharacters () {
    const response = await fetch('/api/index/characters-list', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const data = await response.json();
        return showCharacters (data);
    } else {
        const data = await response.json();
        showErrorLoadCharacters(data, response.status);
    }
}

function showErrorLoadCharacters (data, status) {
    const container = document.getElementById('cards-container-guides');
    container.innerHTML = "";
    const div = document.createElement('div');
    div.classList.add('server-response', 'bad-answer');

    if (status === 401) {
        div.textContent = "Пожалуйста, войдите в аккаунт, чтобы увидеть гидов";
    } else if (status === 404 || !data) {
        div.textContent = "Пока нет доступных гидов. Скоро появятся!";
    } else {
        div.textContent = `Ошибка ${status}: ${data.message}`;
    }
    container.appendChild(div);
}

function showErrorLoadChapters (data, status) {
    const container = document.getElementById('cards-container-chapters');
    container.innerHTML = "";
    const div = document.createElement('div');
    div.classList.add('server-response', 'bad-answer');

    if (status === 401) {
        div.textContent = "Пожалуйста, войдите в аккаунт, чтобы увидеть главы";
    } else if (status === 404 || !data) {
        div.textContent = "Пока нет доступных глав. Скоро появятся новые!";
    } else {
        div.textContent = `Ошибка ${status}: ${data.message}`;
    }
    container.appendChild(div);
}

function showCharacters (data) {
    const container = document.getElementById('cards-container-guides');
    container.innerHTML = "";
    
    if (!data || data.length === 0) {
            const div = document.createElement('div');
            div.classList.add('server-response');
            div.textContent = "Пока нет доступных гидов. Скоро появятся!";
            container.appendChild(div);
            return;
        }

    data.forEach(element => {
        const blockCard = document.createElement('div');
        const nameCharacter = document.createElement('h3');
        const image = document.createElement('img');
        const blockText = document.createElement('div');
        const description = document.createElement('p');

        blockCard.classList.add('info-card', 'guide-card');
        image.classList.add('card-image', 'image-guide');
        blockText.classList.add('card-info');
        description.classList.add('card-text');

        nameCharacter.textContent = element.name;
        image.src = "/images/guides/" + element.imageUrl;
        description.textContent = element.description;

        blockText.appendChild(description);
        blockCard.appendChild(nameCharacter);
        blockCard.appendChild(image);
        blockCard.appendChild(blockText);
        container.appendChild(blockCard);
    });
}

function showChapters (data) {
    const container = document.getElementById('cards-container-chapters');
    container.innerHTML = "";
    
    if (!data || data.length === 0) {
            const div = document.createElement('div');
            div.classList.add('server-response');
            div.textContent = "Пока нет доступных глав. Скоро появятся новые!";
            container.appendChild(div);
            return;
        }

    data.forEach(element => {
        const blockCard = document.createElement('div');
        const titleChapter = document.createElement('h3');
        const image = document.createElement('img');
        const blockText = document.createElement('div');
        const description = document.createElement('p');

        blockCard.classList.add('info-card', 'location-card');
        image.classList.add('card-image', 'image-location');
        blockText.classList.add('card-info');
        description.classList.add('card-text');

        titleChapter.textContent = element.title;
        image.src = "/images/locations/" + element.image;
        description.textContent = element.description;

        blockText.appendChild(description);
        blockCard.appendChild(titleChapter);
        blockCard.appendChild(image);
        blockCard.appendChild(blockText);
        container.appendChild(blockCard);
    });
}

loadDataChapters();