$.ajax({
    url: "http://localhost:8080/products",
    cors: true,
    headers: { "Authorization": sessionStorage.getItem("token") },
    success: function(products) { addProductstoPage(products) },
    error: function(error) { console.error(error) }
});

function addProductstoPage(products) {
    const productsContainer = $("#productsContainer");
    productsContainer.empty();

    let row;
    for (let i = 0; i < products.length; i++) {
        if (i % 3 === 0) {
            row = $(`<div class="row justify-content-center mt-3"></div>`);
            productsContainer.append(row);
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
    const drop = $(`<div class="d-flex justify-content-center mb-1 mt-2">
    <button type="button" class="btn btn-light" style="width: 1.5rem; height: 1.5rem; padding: 0;"
      data-bs-toggle="modal" data-bs-target="#descriptionModal-${product.id}" data-toggle="tooltip"
      data-placement="top" title="Beschreibung anzeigen">
      <i class="fas fa-caret-down"></i>
    </button>
  </div>`);

//Create the description element
const description = $(`<div class="modal fade" id="descriptionModal-${product.id}" tabindex="-1" aria-labelledby="descriptionModalLabel-${product.id}" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content bg-dark text-white">
              <div class="modal-header">
                <h5 class="modal-title" id="descriptionModalLabel-${product.id}">Beschreibung</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                <p>${product.description}</p>
              </div>
            </div>
          </div>
        </div>`);

//Create Footer
const cardFooter = $(`<div class="card-footer border border-1 border-top-1 d-flex justify-content-between">`);


//Create the price element
const price = $(`<p class="card-text mb-0">Preis: ${product.price.toFixed(2)} €</p>`);
//Create add Product
const addProduct = $(`<button type="button" class="btn btn-light" id="add-to-cart-button" data-product-id="${product.id}" style="width: 1.5rem; height: 1.5rem; padding: 0;" role="button" data-toggle="tooltip" data-placement="top" title="Produkt zum Warenkorb hinzufügen"><i class="fas fa-plus" style="pointer-events: none;"></i></button>
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


function addProductToCart(product, quantity) {
    const data = {
        productId: product.id,
        quantity: quantity
    }

    $.post({
        url: "http://localhost:8080/positions",
        headers: { "Authorization": sessionStorage.getItem("token") },
        cors: true,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: console.log,
        error: console.error
    });
}
