document.getElementById('loginForm').addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById('username');
    const password = document.getElementById('password');

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

    if (response.ok) {
        window.location.href = "/index";
    } else {
        const data = await response.text();
        const blockError = document.getElementById("message-error");
        blockError.textContent = data;
    }
})