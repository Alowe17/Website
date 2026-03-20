async function loadPage() {
    const path = window.location.pathname;
    const url = path.split('/').pop();
    const response = await fetch('/role-master/api/promo-code/' + url, {
        method: 'GET',
        credentials: "include",
    });

    if (response.ok) {
        const data = await response.json();
        welcome(data);
        return;
    }

    if (response.status == 401) {
        const refreshed = await refreshAccessToken()

        if (refreshed) {
            return loadPage();
        } else {
            window.location.href = "/role-master/login";
            return;
        }
    } else {
        const data = await response.json();
        console.log('Ошибка: ');
        console.error(data.message);
    }
}

function welcome (data) {
    const welcomeMessage = document.getElementById('welcome-message');
    welcomeMessage.innerHTML = "";

    welcomeMessage.textContent = data.message;
}

loadPage();