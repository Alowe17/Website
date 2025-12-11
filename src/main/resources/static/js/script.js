const password = document.getElementById('password');
const button = document.getElementById('button-change-password');

function change_password () {
    if (password.type === 'password') {
        password.type = 'text';
        button.textContent = 'Скрыть';
    } else {
        password.type = 'password';
        button.textContent = 'Показать';
    }
};