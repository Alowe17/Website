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

async function loadStory () {
    const response = await fetch('/api/story/chapters-list', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const chapters = await response.json();
        console.log(chapters);
        loadUserProgress(chapters);
    } else if (response.status) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return loadStory();
        } else {
            const data = await response.json();
            showErrorMessage (data);
        }
    }
}

function showErrorMessage (data) {
    const contianer = document.getElementById('container-chapters');
    contianer.innerHTML = "";

    contianer.textContent = data.message;
}

async function loadUserProgress (chapters) {
    const response = await fetch('/api/story/users-progress', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const userProgress = await response.json();
        console.log(userProgress);
        checkProgress (userProgress, chapters);
    } else {
        const data = await response.json();
        console.error('Что-то пошло не так: ');
        console.warn(data);
    }
}

function checkProgress (userProgress, chapters) {
    const currentNumber = userProgress.chapterDto.number;

    const container = document.getElementById('container-chapters');
    container.innerHTML = '';

    chapters.forEach(chapter => {
            renderChapter(chapter, currentNumber, container);
        });
}

function renderChapter(chapter, currentNumber, container) {
    const blockChapter = document.createElement('div');
    const headerChapter = document.createElement('div');
    const h3NameChapter = document.createElement('h3');
    const spanStatusChapter = document.createElement('span');
    const blockContentChapter = document.createElement('div');
    const imageChapter = document.createElement('img');
    const descriptionChapter = document.createElement('p');
    const linkStartChapter = document.createElement('a');
    const buttonGameChapter = document.createElement('button');

    if (chapter.number < currentNumber) {
        buttonGameChapter.textContent = "✅ Пройдено";
        buttonGameChapter.disabled = true;
    } else if (chapter.number === currentNumber) {
        buttonGameChapter.textContent = "Начать игру";
        linkStartChapter.href = "/api/game/chapters/" + currentNumber;
    } else {
        buttonGameChapter.textContent = "❌ Заблокировано. Пройдите главу " + (chapter.number - 1);
        buttonGameChapter.disabled = true;
    }

    h3NameChapter.textContent = chapter.title;
    
    if (chapter.status == "PUBLISHED") {
        spanStatusChapter.textContent = "Открыта";
    } else {
        spanStatusChapter.textContent = chapter.status;
    }

    imageChapter.src = "/images/locations/" + chapter.image;
    imageChapter.style.width = "200px";
    descriptionChapter.textContent = chapter.description;

    headerChapter.appendChild(h3NameChapter);
    headerChapter.appendChild(spanStatusChapter);
    blockContentChapter.appendChild(imageChapter);
    blockContentChapter.appendChild(descriptionChapter);

    if (chapter.number === currentNumber) {
        linkStartChapter.appendChild(buttonGameChapter);
        blockContentChapter.appendChild(linkStartChapter);
    } else {
        blockContentChapter.appendChild(buttonGameChapter);
    }

    blockChapter.appendChild(headerChapter);
    blockChapter.appendChild(blockContentChapter);

    container.appendChild(blockChapter);
}

loadStory();