console.log("Hello form FrontEnd/index.js");

$("#getProductsButton").on("click", function(e){
    $.ajax({
        url: "http://localhost:8080/products",
        type: "GET",
        cors: true,
        success: function(products) { addProductstoPage(products)},
        error: function(error) {console.error(error)}
    })
});
function addProductstoPage(products){
const productContainer = $("#productsContainer");

for(let product of products){
productContainer.append(createProduct(product))
}}
function createProduct(product){
    const name= $(`<h1>${product.name}</h1>)`);
    const description = $(`<p>${product.description}</p>`);
    const image = $(`<img width="400" src="${product.imageUrl}">`);
    const price = $(`<p>${product.price}</p>`);
    
    const wrapper = $(`<div class="product"></div>`);
    wrapper.append(name);
    wrapper.append(description);
    wrapper.append(image);
    wrapper.append(price);
    return wrapper;


}
