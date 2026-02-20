async function loadCreateNewProduct() {
    const response = await fetch('/api/admin', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const data = await response.json();
        const h3 = document.getElementById('welcomeMessage');
        h3.style.color = "green";
        h3.style.fontWeight = "bold";
        h3.textContent = data.message;
        return;
    } else if (response.status === 401) {
        const refreshed = await refreshAccessToken();
        if (refreshed) {
            return loadCreateNewProduct();
        }

        window.location.href = "/login";
        return;
    }

    const data = await response.json();
    showError(data);
}

function showError(data) {
    const container = document.getElementById('container');
    container.innerHTML = "";
    container.style.color = "red";
    container.style.fontWeight = "bold";

    let text = "";
    if (data.error) {
        text += data.error + ": ";
    }
    
    text += data.message;
    container.textContent = text;
}

async function refreshAccessToken () {
    const response = await fetch('/api/auth/refresh', {
        method: 'POST',
        credentials: 'include'
    });

    if (response.ok) {
        return true;
    } else {
        return false;
    }
}

document.getElementById('create-new-product').addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById('name').value;
    const price = document.getElementById('price').value;
    const category = document.getElementById('category').value;

    const response = await fetch ("/api/admin/create-new/product", {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify ({
            name: name,
            price: parseInt(price),
            category: category
        })
    });

    if (response.ok) {
        const data = await response.json();
        showAnswerServer(data, true);
    } else {
        const data = await response.json();
        showAnswerServer (data, false);
    }
})

function showAnswerServer (data, status) {
    const container = document.getElementById('container-answer');
    container.innerHTML = "";
    container.classList.add('visible');

    if (status) {
        container.classList.add('good-answer');
        container.textContent = data.message;
    } else {
        container.classList.add('bad-answer');
        container.textContent = data.message;
    }
}

document.getElementById('logoutForm').addEventListener("submit", async (e) => {
    e.preventDefault();

    const response = await fetch('/api/auth/logout', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: "include"
    });

    if (response.ok) {
        window.location.href = "/login";
    } else {
        alert('Увы, что-то пошло не так. Обратитесь в поддержку проекта!');
    }
});

loadCreateNewProduct();