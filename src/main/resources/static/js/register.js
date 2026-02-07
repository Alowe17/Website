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

document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const name = document.getElementById('name');
    const username = document.getElementById('username');
    const password = document.getElementById('password');
    const email = document.getElementById('email');
    const phone = document.getElementById('phone');
    const birthdate = document.getElementById('birthdate');
    
    const containerAnswer = document.getElementById('message-container');

    const response = await fetch('/api/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: "include",
        body: JSON.stringify({
            name: name.value,
            username: username.value, 
            password: password.value,
            email: email.value,
            phone: phone.value,
            birthdate: birthdate.value
        })
    });

    if (response.ok) {
        const data = await response.json();
        const h4 = document.createElement('h4');
        h4.classList.add('register-successfully');
        h4.textContent = data.message;
        containerAnswer.innerHTML = '';
        containerAnswer.appendChild(h4);
    } else {
        const data = await response.json();
        const h4 = document.createElement('h4');
        h4.classList.add('register-error');
        h4.textContent = data.message;
        containerAnswer.appendChild(h4);
    }
})