async function loadUpdateProduct () {
    const response = await fetch('/api/admin', {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const data = await response.json();
        const path = window.location.pathname;
        const partPath = path.split('/');
        const id = parseInt(partPath[partPath.length - 1]);
        const container = document.getElementById('welcomeMessage');
        container.textContent = data.message;   
        loadProductData (id);
        return;
    } else if (response.status == 401) {
        const refreshed = await refreshAccessToken ();

        if (refreshed) {
            return loadUpdateProduct ();
        } else {
            window.location.href = "/login";
        }
    } else if (response.status == 403) {
        const data = await response.json();
        showForbiddenMessage (data);
        return;
    } else {
        console.warn("Возникла другая ошибка. Статус ответа сервера: " + response.status);
        return;
    }
}

async function loadProductData (id) {
    const response = await fetch('/api/admin/info-product/' + id, {
        method: 'GET',
        credentials: 'include'
    });

    if (response.ok) {
        const data = await response.json();
        const container = document.getElementById('name-old');
        container.textContent = data.name;
    } else if (response.status == 401) {
        const refreshed = await refreshAccessToken();

        if (refreshed) {
            return loadUpdateProduct();
        } else {
            const data = await response.json();
            return showErrorMessage (data);
        }
    } else {
        console.error("Ответ сервера: " + response.status);
        const data = await response.json();
        showErrorMessage (data);
    }
}

function showErrorMessage (data) {
    const containerContent = document.getElementById('container').innerHTML = "";
    const container = document.getElementById('container-answer');
    container.innerHTML = "";
    container.style.color = "red";
    container.style.fontWeight = "bold";
    container.textContent = data.message;
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

document.getElementById('update-product-data').addEventListener('submit', async (e) => {
    e.preventDefault();

    const path = window.location.pathname;
    const partPath = path.split('/');
    const id = parseInt(partPath[partPath.length - 1]);
    const name = document.getElementById('name').value;
    const price = document.getElementById('price').value;
    const category = document.getElementById('category').value;
    const nameOld = document.getElementById('name-old').textContent;

    const response = await fetch('/api/admin/update-product/' + id, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: name,
            price: parseInt(price == null ? nameOld.price : price),
            category: (category == "notinfo" ? nameOld.category : category),
        })
    });

    if (response.ok) {
        const data = await response.json();
        showAnswerServer (data, true);
    } else {
        const data = await response.json();
        showAnswerServer (data, false);
    }
})

function showAnswerServer (data, status) {
    const container = document.getElementById('container-answer');
    container.innerHTML = "";

    if (status) {
        container.classList.add('good-answer');
        container.textContent = data.message;
    } else {
        container.classList.add('bad-answer');
        container.textContent = data.message;
    }
}

loadUpdateProduct();