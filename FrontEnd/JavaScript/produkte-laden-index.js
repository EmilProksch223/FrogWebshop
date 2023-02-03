console.log("Produkte wurden geladen/index.js");


function createProduct(product) {
    //Create the card container with col-3 and mb-3 class
    const cardContainer = $("<div>", { class: "col-12 col-lg-4 col-xxl-3 d-flex justify-content-center mb-3" });
    //Create the card element
    const card = $(`<div class="card bg-dark border border-light text-white p-3" style="width: 18rem;"></div>`);
    //Create the image element
    const image = $(`<img class="card-img-top border border-light rounded" height="250" src="${product.imageUrl}">`);
    //Create the card body element
    const cardBody = $(`<div class="card-body border rounded mt-1"></div>`);
    //Create the name element
    const name = $(`<h5 class="card-title text-center">${product.name}</h5>`);
    //Create the description element
    const description = $(`<p class="card-text">${product.description}</p>`);
    //Create the price element
    const price = $(`<p class="card-text">${product.price}</p>`);

    //Append the elements to the card
    cardBody.append(name);
    cardBody.append(description);
    cardBody.append(price);
    card.append(image);
    card.append(cardBody);
    //append the card to the container
    cardContainer.append(card);
    return cardContainer;
}


// Function to add products to the page
function addProductstoPage(products) {
    const productContainer = $("#productsContainer"); // Selects the product container element

    for (let product of products) {
        productContainer.append(createProduct(product)) // Appends the product element to the container
    }
}

$(document).ready(function(){
    // Function to create a product element
    function createProduct(product) {
    }

    // Makes a GET request to the server to retrieve the products
    $.ajax({
        url:'http://localhost:8080/products', // URL of the endpoint
        method:'GET', // HTTP method
        cors: true, // CORS enabled
        success: function (products) { addProductstoPage(products) }, // On success, add the products to the page
        error: function (error) { console.error(error) } // On error, log the error to the console
    });
});
