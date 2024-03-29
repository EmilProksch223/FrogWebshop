//Produkte abrufen 
$.ajax({
    url: "http://localhost:8080/products/active",
    cors: true,
    success: function (products) { addProductstoPage(products) },
    error: function (error) { console.error(error) }
});

//Produkt-Container vorbereiten
function addProductstoPage(products) {
    const productsContainer = $("#productsContainer");
    productsContainer.empty();

    let row;
    for (let i = 0; i < products.length; i++) {
        if (i % 4 === 0) {
            row = $(`<div class="row justify-content-center"></div>`);
            productsContainer.append(row);
        }
        let productCol = $(`<div class="col-12 col-lg-4 col-xxl-3 d-flex justify-content-center mb-3" id="productCol${products[i].id}"></div>`);
        row.append(productCol);

        loadProductCard(products[i]);
    }
}

//HTML Component hinzufügen und mit Produktdaten befüllen
function loadProductCard(product) {
    const colId = "productCol" + product.id;
    const productCol = document.getElementById(colId);

    fetch('/frontend/components/productCard/productShopCard/productShopCard.html')
        .then(response => response.text())
        .then(html => {
            productCol.innerHTML = html;

            console.log(product.id);

            setupProduct(product);

            const fields = ["Name", "ModalName", "Image", "ModalImage", "ModalDescription", "Price", "ModalPrice", "ModalQuantity"];
            fields.forEach(field => {
                const element = productCol.querySelector(`#show${field}`);
                switch (field) {
                    case "Name":
                    case "ModalName":
                        element.innerHTML = `${product.name}`
                        break;
                    case "ModalDescription":
                        element.innerHTML = `${product.description}`
                    case "Image":
                    case "ModalImage":
                        element.src = `http://localhost:8080/files/${product.imageUrl}`;
                        break;
                    case "Price":
                    case "ModalPrice":
                        element.innerHTML = `${product.price.toFixed(2)} €`;
                        break;
                    case "ModalQuantity":
                        element.innerHTML = `${product.quantity} Stk. auf Lager`;
                        break;
                    default:
                        element.innerHTML = product[field.toLowerCase()];
                        break;
                }
            });



        })
        .catch(error => {
            console.error(error);
        });
}
//Produkt IDs anpassen und Events hinzufügen
function setupProduct(product) {
    const productDetailsModal = document.getElementById("productDetailsModal");
    const productDetailsModalButton = document.getElementById("productDetailsModalButton");
    const addToCartButtonCard = document.getElementById("addToCartButton");
    const addToCartButtonCardModal = document.getElementById("addToCartButtonModal");
    const manaSymbolImagesContainer = document.getElementById('manaSymbolImagesContainer');

    productDetailsModal.id = "productDetailsModal" + product.id;
    productDetailsModalButton.id = "productDetailsModalButton" + product.id;
    productDetailsModalButton.setAttribute("data-bs-target", "#productDetailsModal" + product.id);
    manaSymbolImagesContainer.id = "manaSymbolImagesContainer" + product.id;

    const showModalName = document.getElementById('showModalName');


    const manaSymbols = product.manaType.split('');

    const manaCost = product.manaCost;

    const allSymbols = [manaCost, ...manaSymbols];

    showModalName.innerHTML = product.name;
    //ManaBilder im Modal erstellen
    manaSymbolImagesContainer.innerHTML = '';

    allSymbols.forEach(symbol => {
        const manaSymbolImage = document.createElement('img');
        manaSymbolImage.src = `/frontend/components/manaSymbols/img/${symbol}.svg`;
        manaSymbolImage.alt = symbol;
        manaSymbolImage.style.width = '40px';
        manaSymbolImage.style.height = '40px';
        manaSymbolImage.classList.add('me-1');
        manaSymbolImagesContainer.appendChild(manaSymbolImage);
    });

    addToCartButtonCard.id = "addToCartButton" + product.id;
    addToCartButtonCardModal.id = "addToCartButtonModal" + product.id;
    const addToCartButton = document.getElementById(`addToCartButton${product.id}`);
    const addToCartButtonModal = document.getElementById(`addToCartButtonModal${product.id}`);

    // Füge Event-Listener zum Hinzufügen des Produkts zum Warenkorb hinzu
    addToCartButton.addEventListener("click", function () {
        addProductToCart(product);
    });
    addToCartButtonModal.addEventListener("click", function () {
        console.log(product.id);
        addProductToCart(product);
    });
}

// Add Product to Cart

function addProductToCart(product) {
    const data = {
        productId: product.id,
        quantity: 1
    }

    console.log(data);
    $.post({
        url: "http://localhost:8080/positions",
        headers: { "Authorization": sessionStorage.getItem("token") },
        cors: true,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: console.log,
        error: console.log(product.id)
    });
}

