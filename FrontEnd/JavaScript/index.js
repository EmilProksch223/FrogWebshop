document.getElementById("filter-button").onclick = function (event) {

    removeProducts();

    var manaSymbols = [];
    var checkboxes = document.querySelectorAll('input[name="ManaSymbol[]"]:checked');
    for (var i = 0; i < checkboxes.length; i++) {
        manaSymbols.push(checkboxes[i].value);
    }
    var manaSymbolsString = manaSymbols.join("");
    console.log(manaSymbolsString);


$.ajax({
    type: "GET",
    url: "http://localhost:8080/products?search=" + encodeURIComponent(manaSymbolsString),
    cors: true, // CORS enabled
    success: function (products) { addProductstoPage(products) }, // On success, add the products to the page
    error: function (error) { console.error(error) } // On error, log the error to the console
});
};
