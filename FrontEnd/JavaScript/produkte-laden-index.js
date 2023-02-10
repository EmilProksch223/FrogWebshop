$.get({
    url:'http://localhost:8080/products',
    cors: true, // CORS enabled
    success: function(products) { addProductstoPage(products) }, // On success, add the products to the page
    error: console.error 
});


function addProductstoPage(products) {
    const productContainer = $("#productsContainer"); // Selects the product container element
    productContainer.empty();

    let row;
    for (let i = 0; i < products.length; i++) {
        if (i % 3 === 0) {
            row = $(`<div class="row justify-content-center"></div>`);
            productContainer.append(row);
        }
        row.append(createProduct(products[i]));
    }
}


function createProduct(product) {
        //Create the card container with col-3 and mb-3 class
        const cardContainer = $("<div>", { class: "col-12 col-lg-4 col-xxl-3 d-flex justify-content-center mb-3" });
        //Create the card element
        const card = $("<div>", { class: "card bg-dark border border-light text-white p-3", style: "width: 22rem;" });
        //Create the image element
        const image = $(`<img class="card-img-top border border-light rounded" height="350" src="${product.imageUrl}">`);
        //Create the card body element
        const cardBody = $(`<div class="card-body border rounded mt-1">`);
        //Create the name element
        const name = $(`<h5 class="card-title text-center">${product.name}</h5>`);
        //Create the description element
        const description = $(`<p class="card-text">${product.description}</p></div>`);
        //Create the price element
        const price = $(`<div class="card-footer"><p class="card-text">Preis: ${product.price} €</p></div>`);

    //Append the elements to the card
    cardBody.append(name, description);
    //Append the image and card body to the card
    card.append(image, cardBody, price);
    //Append the card to the card container
    cardContainer.append(card);
  
    //Return the card container
    return cardContainer;
  }



function removeProducts() {
    $("#productsContainer").empty();
}


