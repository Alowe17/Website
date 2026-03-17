let user = [];
let dialogs = [];
let choices = [];
let dialogIndex = 0;
let isTyping = false;
let menuChoiceId = null;
let orderDishes = [];
let sceneType = null;

document.getElementById('logoutForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const response = await fetch('/role-master/api/auth/logout', {
        method: 'POST',
        credentials: 'include'
    });

    if (response.ok) {
        window.location.href = "/role-master/login";
    } else {
        alert('Что-то пошло не так и не получилось выполнить выход из аккаунта. Попробуйте снова позже или обратитесь в поддержку!');
    }
})

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

async function loadGame () {
    const response = await fetch('/role-master/api/game', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return loadGame();
        }
    }

    if (response.ok) {
        const data = await response.json();
        user = data;
        showWelcomeMessage();
        startGame();
    } else {
        const data = await response.json();
        showErrorMessage (data);
    }
}

function showWelcomeMessage () {
    const messageWelcome = document.getElementById('message-welcome');

    messageWelcome.innerHTML = "";
    messageWelcome.textContent = "Добро пожаловать, " + user.username + "!";
}

function showErrorMessage (data) {
    const container = document.getElementById('container');
    const messageError = document.getElementById('message-error');

    container.innerHTML = "";
    messageError.innerHTML = "";
    messageError.textContent = data.message;
}

async function startGame () {
    const response = await fetch('/role-master/api/game/chapters/cafe', {
        method: 'GET', 
        credentials: 'include'
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return startGame();
        }
    }

    if (response.ok) {
        document.getElementById("container-dialog").innerHTML = "";
        document.getElementById("container-choice").innerHTML = "";
        const data = await response.json();
        showScene(data);
    } else {
        const data = await response.json();
        showErrorMessage(data);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const nextButton = document.getElementById('nextDialog');
    nextButton.addEventListener('click', nextDialog);
});

function scrollToBottomSmooth() {
    const container = document.getElementById('container-dialog');
    container.scrollTo({
        top: container.scrollHeight,
        behavior: 'smooth'
    });
}

function showScene(data) {
    dialogs = data.dialogDtoList;
    choices = data.choiceDtoList;
    sceneType = data.sceneType;
    dialogIndex = 0;

    const nextButton = document.getElementById('nextDialog');
    nextButton.style.display = "block";

    nextDialog();
}

function nextDialog() {
    if (isTyping) {
        return;
    }

    if (sceneType === "MENU") {
        menuChoiceId = (choices && choices.length > 0) ? choices[0].id : null; 
        loadMenu();
        return;
    }

    if (dialogIndex >= dialogs.length) {
        if (sceneType === "DEFEAT") {
            showDefeatGame();
            return;
        } else if (sceneType === "NEXTCHAPTER") {
            showFinishGame();
            return;
        }

        showChoices();
        return;
    }

    const dialog = dialogs[dialogIndex];

    showDialog(dialog);
    dialogIndex++;
}

function showDefeatGame() {
    const container = document.getElementById('container-dialog');
    container.innerHTML = "";

    const defeatBlock = document.createElement('div');
    defeatBlock.classList.add('defeat-screen');

    const title = document.createElement('h2');
    title.textContent = "Поражение";

    const message = document.createElement('p');
    message.textContent = "Ты не справился с испытанием... Но это лишь начало пути. Можно попробовать снова!";

    const retryButton = document.createElement('button');
    retryButton.classList.add('retry-button');
    retryButton.textContent = "Выйти в сюжетную линию";

    retryButton.onclick = () => {
        window.location.href = "/role-master/story";
    };

    defeatBlock.appendChild(title);
    defeatBlock.appendChild(message);
    defeatBlock.appendChild(retryButton);

    container.appendChild(defeatBlock);

    document.getElementById('nextDialog').style.display = "none";
    document.getElementById('container-choice').innerHTML = "";
}

function showFinishGame() {
    const container = document.getElementById('container-dialog');
    container.innerHTML = "";

    const finishBlock = document.createElement('div');
    finishBlock.classList.add('finish-screen');

    const title = document.createElement('h2');
    title.textContent = "Глава пройдена!";

    const message = document.createElement('p');
    message.textContent = "Поздравляю! Ты успешно завершил первую главу. Впереди ещё много историй и тайн...";

    const nextChapterButton = document.createElement('button');
    nextChapterButton.classList.add('next-chapter-button');
    nextChapterButton.textContent = "Перейти к следующей главе";

    nextChapterButton.onclick = () => {
        window.location.href = "/role-master/story";
    };

    const backToMenu = document.createElement('button');
    backToMenu.classList.add('back-button');
    backToMenu.textContent = "Вернуться в меню сюжета";

    backToMenu.onclick = () => {
        window.location.href = "/role-master/story";
    };

    finishBlock.appendChild(title);
    finishBlock.appendChild(message);
    finishBlock.appendChild(nextChapterButton);
    finishBlock.appendChild(backToMenu);

    container.appendChild(finishBlock);

    document.getElementById('nextDialog').style.display = "none";
    document.getElementById('container-choice').innerHTML = "";
}

async function showDialog(dialog) {
    const container = document.getElementById('container-dialog');
    const block = document.createElement('div');
    const author = document.createElement('p');
    const message = document.createElement('p');

    block.classList.add('dialog-block');

    if (dialog.gameCharacterDto.type === "PLAYER") {
        block.classList.add('dialog-player');
    } else if (dialog.gameCharacterDto.type === "SYSTEM") {
        block.classList.add('dialog-system');
    } else if (dialog.gameCharacterDto.type === "GUIDE") {
        block.classList.add('dialog-guide');
    } else {
        block.classList.add('dialog-npc');
    }

    author.textContent = dialog.gameCharacterDto.name;

    block.appendChild(author);
    block.appendChild(message);

    container.appendChild(block);
    scrollToBottomSmooth();
    
    isTyping = true;
    for (let i = 0; i < dialog.text.length; i++) {
        message.textContent += dialog.text[i];
        scrollToBottomSmooth();
        await sleep(50);
    }

    isTyping = false;
}

async function sleep (time) {
    return await new Promise(resolve => {setTimeout(resolve, time)});
}

async function loadMenu () {
    const response = await fetch('/role-master/api/game/menu/dishes', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return loadMenu();
        }
    }

    if (response.ok) {
        const data = await response.json();
        const blockTable = document.createElement('div');
        const container = document.getElementById('container-dialog');
        blockTable.id = "block-table";
        container.appendChild(blockTable);
        showMenu(data);
    } else {
        const data = await response.json();
        showErrorMessage(data);
    }
}

function showMenu (data) {
    const container = document.getElementById('container-dialog');
    const table = document.createElement('table');

    if (data === null) {
        const errorText = document.createElement('h3');
        errorText.textContent = "Возникла неизвестная ошибка. Рекомендуется обратиться в поддержку проекта!";
        errorText.style.color = "#FF2400";
        errorText.style.fontWeight = "bold";
        container.style.display = "none";
        table.appendChild(errorText);
        return;
    }

    const thead = document.createElement('thead');
    const trTable = document.createElement('tr');
    const thId = document.createElement('th');
    const thName = document.createElement('th');
    const thCategory = document.createElement('th');
    const thPrice = document.createElement('th');
    const thFunction = document.createElement('th');
    const tbody = document.createElement('tbody');

    thId.textContent = "ID";
    thName.textContent = "Название";
    thCategory.textContent = "Категория";
    thPrice.textContent = "Цена";
    thFunction.textContent = "Возможность"

    trTable.appendChild(thId);
    trTable.appendChild(thName);
    trTable.appendChild(thCategory);
    trTable.appendChild(thPrice);
    trTable.appendChild(thFunction);

    thead.appendChild(trTable);
    table.appendChild(thead);
    
    data.forEach(element => {
        const tr = document.createElement('tr');
        const tdId = document.createElement('td');
        const tdName = document.createElement('td');
        const tdCategory = document.createElement('td');
        const tdPrice = document.createElement('td');
        const tdFunctions = document.createElement('td');
        const buttonFunction = document.createElement('button');

        tdFunctions.classList.add('action-buttons-cell');
        buttonFunction.classList.add("action-button", "primary-action");

        tdId.textContent = element.dishId;
        tdName.textContent = element.dishName;
        tdCategory.textContent = element.category;
        tdPrice.textContent = element.dishPrice;
        buttonFunction.textContent = "Добавить в заказ";

        buttonFunction.onclick = () => {
            addDish(element.dishId, element.dishName, element.category, element.dishPrice);
        }

        tr.appendChild(tdId);
        tr.appendChild(tdName);
        tr.appendChild(tdCategory);
        tr.appendChild(tdPrice);
        tdFunctions.appendChild(buttonFunction);
        tr.appendChild(tdFunctions);
        tbody.appendChild(tr);
    });
    
    const buyButton = document.createElement("button");
    buyButton.textContent = "Оплатить заказ";

    buyButton.onclick = () => {
        buyDish(orderDishes);
    };

    table.classList.add('menu-table');
    buyButton.classList.add('choice-button');

    table.appendChild(tbody);
    container.appendChild(table);
    container.appendChild(buyButton);
}

function addDish (id, name, category, price) {
    orderDishes.push({id, name, category, price});
    showListDishes();
}

function removeDish (id) {
    const order = orderDishes;
    const index = order.findIndex(item => item.id === id);
    
    if (index !== -1) {
        order.splice(index, 1);
        showListDishes();
    }
}

function showListDishes () {
    const container = document.getElementById('container-dialog');
    const blockTable = document.getElementById('block-table');
    const table = document.createElement('table');
    const thead = document.createElement('thead');
    const trTable = document.createElement('tr');
    const thId = document.createElement('th');
    const thName = document.createElement('th');
    const thCategory = document.createElement('th');
    const thPrice = document.createElement('th');
    const thFunction = document.createElement('th');
    const tbody = document.createElement('tbody');

    blockTable.innerHTML = "";
    thId.textContent = "ID";
    thName.textContent = "Название";
    thCategory.textContent = "Категория";
    thPrice.textContent = "Цена";
    thFunction.textContent = "Возможность"

    trTable.appendChild(thId);
    trTable.appendChild(thName);
    trTable.appendChild(thCategory);
    trTable.appendChild(thPrice);
    trTable.appendChild(thFunction);

    thead.appendChild(trTable);
    table.appendChild(thead);
    
    orderDishes.forEach(element => {
        const tr = document.createElement('tr');
        const tdId = document.createElement('td');
        const tdName = document.createElement('td');
        const tdCategory = document.createElement('td');
        const tdPrice = document.createElement('td');
        const tdFunctions = document.createElement('td');
        const buttonFunction = document.createElement('button');

        tdFunctions.classList.add('action-buttons-cell');
        buttonFunction.classList.add("action-button", "primary-action");

        tdId.textContent = element.id;
        tdName.textContent = element.name;
        tdCategory.textContent = element.category;
        tdPrice.textContent = element.price;
        buttonFunction.textContent = "Удалить из заказа";

        buttonFunction.onclick = () => {
            removeDish(element.id);
        }

        tr.appendChild(tdId);
        tr.appendChild(tdName);
        tr.appendChild(tdCategory);
        tr.appendChild(tdPrice);
        tdFunctions.appendChild(buttonFunction);
        tr.appendChild(tdFunctions);
        tbody.appendChild(tr);
    });

    table.classList.add('menu-table');

    table.appendChild(tbody);
    blockTable.appendChild(table);
    container.appendChild(blockTable);
}

async function buyDish (order) {
    const list = orderDishes.map(dish => dish.id);
    const response = await fetch('/role-master/api/game/dishes', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            dishIds: list
        })
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return buyDish(order);
        }
    }

    if (response.ok) {
        const data = await response.json();
        showScene(data);
        orderDishes = [];
        showAnswerServer("Успешная оплата!");
    } else {
        const error = await response.json();
        showErrorGameMessage(error);
    }
}

function showAnswerServer (text) {
    const container = document.getElementById('container-dialog');
    const block = document.createElement('div');
    const message = document.createElement('p');

    block.classList.add('dialog-block');
    block.classList.add('dialog-system');

    message.textContent = text;

    block.appendChild(message);
    container.appendChild(block);
}

function showErrorGameMessage (data) {
    const container = document.getElementById('container-dialog');
    const answer = document.createElement('p');
    answer.innerHTML = "";

    answer.textContent = data.message || `Возникла ошибка`; // Надо добавить стили про то, что недостаточно баланса или тип того
    container.appendChild(answer);
}

function showChoices () {
    if (isTyping) {
        return;
    }

    const container = document.getElementById('container-choice');
    const nextButton = document.getElementById('nextDialog');
    nextButton.style.display = "none";
    container.innerHTML = "";

    choices.forEach(element => {
        const button = document.createElement('button');

        button.textContent = element.text;

        button.onclick = () => {
            choose(element.id);
        };

        button.classList.add('choice-button');

        container.appendChild(button);
    });
}

async function choose (id) {
    const response = await fetch('/role-master/api/game/scenes/' + id, {
        method: 'POST',
        credentials: 'include'
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return choose(id);
        }
    }

    if (response.ok) {
        document.getElementById("container-choice").innerHTML = "";
        const data = await response.json();
        dialogs = [];
        choices = [];
        sceneType = null;

        showScene(data);
    } else {
        const data = await response.json();
        showErrorMessage(data);
    }
}

loadGame();