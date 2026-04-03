updatePromoCode.addEventListener('submit', async (e) => {
    e.preventDefault();

    const promoCode = document.getElementById('promo-code').value;
    const count = document.getElementById('count').value;
    const expiresAt = document.getElementById('expiration-date').value;
    const type = document.getElementById('type').value;
    const status = document.getElementById('status').value;

    const path = window.location.pathname;
    const id = path.split('/').pop();
    const response = await fetch('/role-master/api/admin/promo-codes/' + id, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            id: id,
            promoCode: promoCode,
            count: parseInt(count),
            expiresAt: expiresAt,
            type: type,
            status: status,
        })
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (!refreshed) {
            const data = await response.json();
            showAnswerServer(data, false);
            return;
        }
    }

    if (response.ok) {
        const data = await response.json();
        showAnswerServer (data, true);
    } else {
        const data = await response.json();
        showAnswerServer(data, false);
    }
});

function showAnswerServer (data, status) {
    const answerMessage = document.getElementById('answer-message');
    answerMessage.innerHTML = "";
    answerMessage.textContent = data.message;

    if (status) {
        answerMessage.style.color = "green";
        //answerMessage.classList.add('success-message');
    } else {
        answerMessage.style.color = "red";
        //answerMessage.classList.add('error-message');
    }
}

async function welcomeMessage() {
    const response = await fetch('/role-master/api/admin', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            welcomeMessage();
            return;
        } else {
            const data = await response.json();
            showAnswerServer(data, false);
            return;
        }
    }

    if (response.ok) {
        const data = await response.json();
        const welcomeMessage = document.getElementById('welcome-message');
        welcomeMessage.textContent = data.message;
    } else {
        const data = await response.json();
        showAnswerServer (data, false);
    }
}

async function refreshAccessToken() {
    const response = await fetch('/role-master/api/auth/refresh', {
        method: 'POST',
        credentials: 'include'
    });

    if (response.ok) {
        return true;
    } else {
        return false;
    }
}

async function loadPromoCodeUpdate() {
    const path = window.location.pathname;
    const id = path.split('/').pop();
    const response = await fetch('/role-master/api/admin/info-promo-codes/' + id, {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const data = await response.json();
        showPromoCode (data);
    } else {
        const data = await response.json();
        showAnswerServer (data, false);
    }
}

function showPromoCode (data) {
    console.log('Информация о промокоде: ');
    console.log(data);
}

welcomeMessage();
loadPromoCodeUpdate();