document.getElementById('loginForm').addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById('username');
    const password = document.getElementById('password');

    console.log("Никнейм: " + username.value);
    console.log("Пароль: " + password.value);

    const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: "include",
        body: JSON.stringify({
            username: username.value, 
            password: password.value
        })
    });

    console.log("Ответ сервера: " + response.status)

    if (response.ok) {
        window.location.href = "/index";
    } else {
        const blockError = document.querySelector(".error h3");
        blockError.style.display = "block";
    }
})