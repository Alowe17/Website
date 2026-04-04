const updatePromoCodeForm = document.getElementById('updatePromoCode');
if (updatePromoCodeForm) {
    updatePromoCodeForm.addEventListener('submit', async (e) => {
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
            
            return updatePromoCodeForm.dispatchEvent(new Event('submit'));
        }

        if (response.ok) {
            const data = await response.json();
            showAnswerServer (data, true);
        } else {
            const data = await response.json();
            showAnswerServer(data, false);
        }
    });
}

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
    const promoCode = data.message;

    document.getElementById('promo-code').value = promoCode.promoCode;
    document.getElementById('count').value = promoCode.count;
    document.getElementById('expiration-date').value = promoCode.expiresAt;
    document.getElementById('type').value = promoCode.type;
    document.getElementById('status').value = promoCode.status;
}

async function loadListReward () {
    const path = window.location.pathname;
    const id = path.split('/').pop();
    const response = await fetch('/role-master/api/admin/list/rewards/' + id, {
        method: 'GET',
        credentials: 'include'
    });

    if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            loadListReward();
            return;
        } else {
            const data = await response.json();
            showAnswerServer(data, false);
            return;
        }
    }

    if (response.ok) {
        const data = await response.json();
        showListReward(data);
        return;
    } else {
        const data = await response.json();
        showAnswerServer(data, false);
        return;
    }
}

function showListReward (data) {
    const container = document.getElementById('container-reward');
    container.innerHTML = "";

    if (data === null || data.length === 0) {
        container.style.color = "red";
        container.textContent = "Нет доступных наград";
        return;
    }

    const rewards = data.message;
    const title = document.createElement('h2');
    title.textContent = "Награды для промокода";
    container.appendChild(title);

    rewards.forEach(reward => {
        const block = document.createElement('div');
        const id = document.createElement('div');
        const balanceLabel = document.createElement('label');
        const balanceInput = document.createElement('input');
        const urlLabel = document.createElement('label');
        const urlInput = document.createElement('input');
        const roleLabel = document.createElement('label');
        const roleInput = document.createElement('input');
        const button = document.createElement('button');

        block.className = 'reward-block';
        block.style.border = "1px solid #ccc";
        block.style.padding = "10px";
        block.style.margin = "10px 0";

        
        id.textContent = "ID Награды: " + reward.id;
        balanceLabel.textContent = "Монеты: ";
        balanceInput.type = "number";
        balanceInput.value = reward.balance;
        balanceInput.id = "balance-" + reward.id;
        urlLabel.textContent = "Ссылка: ";
        urlInput.type = "text";
        urlInput.value = reward.url || "";
        urlInput.id = "url-" + reward.id;
        roleLabel.textContent = "Роль: ";
        roleInput.type = "text";
        roleInput.value = reward.role || "";
        roleInput.id = "role-" + reward.id;
        button.textContent = "Обновить награду";
        
        button.addEventListener('click', function() {
            const updatedReward = {
                id: reward.id,
                balance: parseInt(document.getElementById("balance-" + reward.id).value),
                url: document.getElementById("url-" + reward.id).value,
                role: document.getElementById("role-" + reward.id).value
            };
            updateReward(updatedReward);
        });

        block.appendChild(id);
        block.appendChild(document.createElement('br'));
        block.appendChild(balanceLabel);
        block.appendChild(balanceInput);
        block.appendChild(document.createElement('br'));
        block.appendChild(urlLabel);
        block.appendChild(urlInput);
        block.appendChild(document.createElement('br'));
        block.appendChild(roleLabel);
        block.appendChild(roleInput);
        block.appendChild(document.createElement('br'));
        block.appendChild(button);
        container.appendChild(block);
    });
}

async function updateReward (reward) {
    const response = await fetch('/role-master/api/admin/rewards/' + reward.id, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: reward.id,
            balance: reward.balance,
            url: reward.url,
            role: reward.role,
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
        console.log('Успех:');
        console.log(data);
        showAnswerServerReward(data, true);
        return;
    } else {
        const data = await response.json();
        console.log('Провал:');
        console.log(data);
        showAnswerServerReward(data, false);
        return;
    }
}

function showAnswerServerReward (data, success) {
    const container = document.getElementById('answer-reward');
    container.innerHTML = "";
    container.textContent = data.message;
    
    if (success) {
        container.style.color = "green";
    } else {
        container.style.color = "red";
    }
}

loadListReward();
welcomeMessage();
loadPromoCodeUpdate();