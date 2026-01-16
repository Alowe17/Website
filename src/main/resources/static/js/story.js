let currentDialogs = [];
let currentDialogIndex = 0;
let isTyping = false;

async function loadScene(sceneId) {
    const response = await fetch("http://localhost:8080/story/game/" + sceneId);
    const scene = await response.json();

    currentDialogs = scene.dialogs;
    currentDialogIndex = 0;

    clearDialogs();
    showCurrentDialog();
    renderChoices(scene.choices);
}

function clearDialogs() {
    document.getElementById('container-dialog').textContent = "";
}

async function showCurrentDialog() {
    isTyping = true;
    clearDialogs();
    const container = document.getElementById('container-dialog');

    const dialog = currentDialogs[currentDialogIndex];
    for (let i = 0; i < dialog.length; i++) {
        container.textContent += dialog[i];
        await sleep(50);
    }

    isTyping = false;
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

function nextDialog() {
    if (isTyping) return;

    currentDialogIndex++;
    if (currentDialogIndex < currentDialogs.length) {
        showCurrentDialog();
    } else {
        document.getElementById('container-choice').style.display = "block";
    }
}

function renderChoices(choices) {
    const container = document.getElementById('container-choice');
    container.innerHTML = "";
    container.style.display = "none";

    choices.forEach(choice => {
        const button = document.createElement('button');
        button.textContent = choice.text;
        button.onclick = () => loadScene(choice.nextSceneId);
        container.appendChild(button);
    });
}

loadScene("cafe");