let dialogs = [];
let dialogIndex = 0;
let choices = [];
let isTyping = false;
let isWaitingChoice = false;

async function loadScene (sceneId) {
    const response = await fetch("http://localhost:8080/api/scenes/" + sceneId);
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

    const container = document.getElementById('container-dialog');
    const dialog = dialogs[dialogIndex];
    
    container.innerHTML = "";
    isTyping = true;
    showCharacter();
    for (let i = 0; i < dialog.text.length; i++) {
        container.textContent += dialog.text[i];
        await sleep(50);
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
    const container = document.getElementById('container-character');
    const dialog = dialogs[dialogIndex];
    container.textContent = "Автор: " + dialog.gameCharacterDto.name;
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