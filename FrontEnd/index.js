console.log("Hello form FrontEnd/index.js");

$("#getProductsButton").on("click", function(e){
    $ajax({
        url: "http://localhost:8080/products",
        type: "GET",
        cors: true,
        success: function(products) { console.log(products)}
        error: function(error) {console.error(error)}
    }
    
