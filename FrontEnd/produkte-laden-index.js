$(document).ready(function(){
    function createProduct(product) {
        const cardContainer = $("<div>", { class: "col-12 col-lg-4 col-xxl-3 d-flex justify-content-center mb-3 p-0" });
        const card = $(`<div class="card bg-dark border border-light text-white p-3" style="width: 18rem;"></div>`);
        const image = $(`<img class="card-img-top border border-light rounded" height="250" src="${product.imageUrl}">`);
        const cardBody = $(`<div class="card-body border rounded mt-1"></div>`);
        const name = $(`<h5 class="card-title text-center">${product.name}</h5>`);
        const description = $(`<p class="card-text">${product.description}</p>`);
        const price = $(`<p class="card-text">${product.price}</p>`);
        cardBody.append(name);
        cardBody.append(description);
        cardBody.append(price);
        card.append(image);
        card.append(cardBody);
        cardContainer.append(card);
        return cardContainer;
    }
    $.ajax({
        url:'http://localhost:8080/products',
        method:'GET',
        cors: true,
        success: function (products) { addProductstoPage(products) },
        error: function (error) { console.error(error) }
    });
});