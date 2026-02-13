document.getElementById('logoutForm').addEventListener("submit", async (e) => {
    e.preventDefault();

    const response = await fetch('/api/auth/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: "include"
    })

    if (response.ok) {
        window.location.href = "/login";
    } else {
        alert('Увы, что-то пошло не так и не получилось сохранить данные о выходе из аккаунта. Обратитесь в поддержку проекта!');
    }
})

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

async function loadData () {
    const response = await fetch('/api/chapter-list', {
        method: 'GET',
        credentials: 'include'
    })

    if (response.status == 401) {
        const refreshed = await refreshAccessToken()

        if (refreshed) {
            return loadData();
        } else {
            window.location.href = "/login";
            return;
        }
    }
    
    if (response.ok) {
        const data = await response.json();
        console.log(data);
        console.log()
    } else {
        const data = await response.json();
        console.error(data);
    }
}

loadData()