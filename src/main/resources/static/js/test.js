let dialogs = [];
let dialogIndex = 0;
let choices = [];
let isTyping = false;
let isWaitingChoice = false;

async function loadScene (sceneId) {
    const response = await fetch("/api/scenes/" + sceneId);
    const scene = await response.json();

    console.log(scene);

    dialogs = scene.dialogs;
    choices = scene.choices;
    dialogIndex = 0;
    showDialog()
}

async function sleep (time) {
    return await new Promise(resolve => {setTimeout(resolve, time)});
}

function nextDialog () {
    if (isTyping == true) {
        return;
    }

    if (dialogIndex >= dialogs.length) {
        isWaitingChoice = true;
        showChoices(true);
        return;
    }

    showDialog();
}

async function showDialog () {
    if (isTyping == true) {
        return;
    }

    const dialog = dialogs[dialogIndex];
    const container = document.getElementById('container-dialog');
    const text = document.createElement('p');
    const containerDialog = document.createElement('div').classList.add('block-dialog');
    
    container.innerHTML = "";
    isTyping = true;
    showCharacter();
    for (let i = 0; i < dialog.text.length; i++) {
        if (dialog.gameCharacterDto.type == "NPC") {
            text.classList.add('dialog-npc');
            text.textContent += dialog.text[i];
            await sleep(50);
            containerDialog.appendChild(text);
            container.appendChild(containerDialog);
        }
        
        else if (dialog.gameCharacterDto.type == "CONSOLE") {
            text.classList.add('dialog-console');
            text.textContent += dialog.text[i];
            await sleep(50);
            containerDialog.appendChild(text);
            container.appendChild(containerDialog);
        }
        
        else if (dialog.gameCharacterDto.type == "GUIDE") {
            text.classList.add('dialog-guide');
            text.textContent += dialog.text[i];
            await sleep(50);
            containerDialog.appendChild(text);
            container.appendChild(containerDialog);
        }
        
        else if (dialog.gameCharacterDto.type == "PLAYER") {
            text.classList.add('dialog-player');
            text.textContent += dialog.text[i];
            await sleep(50);
            containerDialog.appendChild(text);
            container.appendChild(containerDialog);
        }
        
        else {
            const body = document.body;
            body.innerHTML = "";
            const info = document.createElement('p').textContent = "Возникла непредвиденная ошибка.";
            info.classList.add('error');
            body.appendChild(info);
            return;
        }
    }
    
    dialogIndex++;
    isTyping = false;
}

function showChoices (isWaitingChoice) {
    if (isWaitingChoice == false) {
        return;
    }

    clearChoices();

    const container = document.getElementById('container-choice');
    
    for (let i = 0; i < choices.length; i++) {
        const button = document.createElement('button');
        button.textContent = choices[i].text;

        button.onclick = () => {
            loadScene(choices[i].nextSceneId)
        }

        container.appendChild(button);
    }
}

function showCharacter () {
    const container = document.getElementById('block-dialog');
    const dialog = dialogs[dialogIndex];
    const text = document.createElement('p');
    text.textContent = "Автор: " + dialog.gameCharacterDto.name;
    container.appendChild(text);
    clearCharacter();
}

function clearCharacter () {
    const container = document.getElementById('container-character');
    container.innerHTML = "";
}

function clearChoices () {
    document.getElementById('container-choice').innerHTML = "";
}

loadScene("CH1_START")