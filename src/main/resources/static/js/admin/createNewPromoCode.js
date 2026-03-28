document.getElementById('create-promo-code').addEventListener('submit', async (e) => {
    e.preventDefault();

    const response = await fetch('/role-master/api/admin/promo-codes', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        JSON: JSON.stringify({
            promoCode: document.getElementById('code').value,
            count: document.getElementById('count').value,
            expiresAt: document.getElementById('expiresAt').value,
            promoCodeType: document.getElementById('type').value,
            promoCodeStatus: document.getElementById('status').value
        })
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return;
        } else {
            const data = await response.json();
            showAnswerServer(data, false);
            return;
        }
    }

    if (response.ok) {
        const data = await response.json();
        showAnswerServer(data, true);
    } else {
        const data = await response.json();
        showAnswerServer(data, false);
    }
});

async function welcomeMessage () {
    const response = await fetch('/role-master/api/admin', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const data = await response.json();
        const message = document.getElementById('welcome-message');
        message.innerHTML = data.message;
    } else {
        const data = await response.json();
        showAnswerServer(data, false);
    }
}

function showAnswerServer (data, success) {
    if (success) {
        const message = document.getElementById('welcome-message');
        message.style.color = "green";
        message.innerHTML = data.message;
    } else {
        const message = document.getElementById('welcome-message');
        message.style.color = "red";
        message.innerHTML = data.message;
    }
}

welcomeMessage();