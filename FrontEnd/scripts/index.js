document.getElementById("filter-button").onclick = function (event) {

    removeProducts();

    const manaSymbols = [];
    const checkboxes = document.querySelectorAll('input[name="ManaSymbol[]"]:checked');
    for (let i = 0; i < checkboxes.length; i++) {
        manaSymbols.push(checkboxes[i].value);
    }
    const manaSymbolsString = manaSymbols.join("");
    console.log(manaSymbolsString);

    const searchterm = document.getElementById('search').value;
    console.log(searchterm);

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/products?manasymbols=" + encodeURIComponent(manaSymbolsString) + "&searchterm=" + encodeURIComponent(searchterm),
        cors: true,
        success: function (products) { addProductstoPage(products) },
        error: function (error) { console.error(error) }
    });
};


document.getElementById("search").addEventListener("keydown", function(event) {
    if (event.key === "Enter") {
    event.preventDefault();
    document.getElementById("filter-button").click();
    }
});

$(document).on('click', '#add-to-cart-button', function() {
  const productId = $(this).data('product-id');
  const cartId = 1; // replace with your user's cart ID
  $.ajax({
    type: "POST",
    url: "http://localhost:8080/api/shoppingcarts/" + cartId + "/products/" + productId,
    success: function(data) {
      alert('Product added to cart!');
    },
    error: function(jqXHR, textStatus, errorThrown) {
      alert('Error adding product to cart: ' + textStatus);
    }
  });
});

function removeProducts() {
  $("#productsContainer").empty();
}