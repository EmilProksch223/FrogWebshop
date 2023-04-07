$.get({
    url: 'http://localhost:8080/api/products',
    cors: true, // CORS enabled
    success: function (products) { addProductstoPage(products) }, // On success, add the products to the page
    error: console.error
});

function addProductstoPage(products) {
    const productContainer = $("#productsContainer"); // Selects the product container element
    productContainer.empty();

    let row;
    for (let i = 0; i < products.length; i++) {
        if (i % 3 === 0) {
            row = $(`<div class="row justify-content-center mt-3"></div>`);
            productContainer.append(row);
        }
        row.append(createProduct(products[i]));
    }
}

function createProduct(product) {

    //Create the card container with col-3 and mb-3 class
    const cardContainer = $("<div>", { class: "col-12 col-lg-4 col-xxl-3 d-flex justify-content-center mb-3" });
    //Create the card element
    const card = $("<div>", { class: "card bg-dark border border-5 border-light text-white p-3", style: "width: 22rem;" });
    //Create the image element
    const image = $(`<img class="card-img-top border border-1 border-light rounded" height="350" src="${product.imageUrl}">`);
    //Create the card body element
    const cardBody = $(`<div class="card-body border border-1 border-bottom-0 rounded-top-1 mt-1 ">`);
    //Create the name element
    const name = $(`<h5 class="card-title text-center">${product.name}</h5>`);
    //Create Dropdown
    const drop = $(`<div class="d-flex justify-content-center mb-1 mt-2"><button type="button" class="btn btn-light" style="width: 1.5rem; height: 1.5rem; padding: 0;" data-bs-toggle="collapse" href="#plus-${product.id}" role="button" aria-expanded="false" data-toggle="tooltip" data-placement="top" title="Beschreibung anzeigen">
        <i class="fas fa-caret-down"></i>
        </button></div>`);
    //Create the description element
    const description = $(`<div class="collapse" id="plus-${product.id}"><p class="card-text">${product.description}</p></div></div>`);
    //Create Footer
    const cardFooter = $(`<div class="card-footer border border-1 border-top-1 d-flex justify-content-between">`)
//Create the price element
const price = $(`<p class="card-text mb-0">Preis: ${product.price.toFixed(2)} €</p>`);
//Create add Product
const addProduct = $(`<button type="button" class="btn btn-light" id="add-to-cart-button" data-product-id="${product.id}" style="width: 1.5rem; height: 1.5rem; padding: 0;" role="button" data-toggle="tooltip" data-placement="top" title="Produkt zum Warenkorb hinzufügen"> <i class="fas fa-plus"></i> </button>
`);

    //Append the elements to the card
    cardBody.append(name, drop, description);
    //Append the image and card body to the card
    card.append(image, cardBody, cardFooter);
    //Append the card to the card container
    cardContainer.append(card);
    //Append the footer to the card
    cardFooter.append(price, addProduct)

    //Return the card container
    return cardContainer;
}

function removeProducts() {
    $("#productsContainer").empty();
}
