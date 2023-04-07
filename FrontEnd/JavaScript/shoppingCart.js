// Container-Element, in dem die Informationen angezeigt werden sollen
var container = document.getElementById("shopping-cart-container");

// Erste AJAX-Anfrage, um alle Warenkörbe des Kunden zu erhalten
$.ajax({
    url: "http://localhost:8080/api/shoppingcarts",
    type: "GET",
    success: function(data) {
        // Schleife durch jeden Warenkorb
        data.forEach(function(cart) {
            // Zweite AJAX-Anfrage, um Produkte im Warenkorb zu erhalten
            $.ajax({
                url: "http://localhost:8080/api/shoppingcarts/" + cart.id,
                type: "GET",
                success: function(cartData) {
                    // Erstellen des Elements, um den Warenkorb-Titel anzuzeigen
                    var cartTitle = document.createElement("h2");
                    cartTitle.innerHTML = "Warenkorb #" + cart.id;
                    container.appendChild(cartTitle);
                    
                    // Erstellen des Elements, um die Produkte im Warenkorb anzuzeigen
                    var productList = document.createElement("ul");
                    cartData.products.forEach(function(product) {
                        var productItem = document.createElement("li");
                        productItem.innerHTML = product.name + " - " + product.price;
                        productList.appendChild(productItem);
                    });
                    container.appendChild(productList);
                }
            });
        });
    }
});
