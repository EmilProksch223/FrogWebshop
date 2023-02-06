$.ajax({
    url: 'http://localhost:8080/products', // URL des Endpunkts
    method: 'GET', // HTTP-Methode
    cors: true,
    success: function (data) {
        // Verarbeite die Daten, wenn der Request erfolgreich war
        var products = data;

        // Durchlaufe alle Produkte
        for (var i = 0; i < products.length; i++) {
            var product = products[i];

            // Greife auf den Titel des Produkts zu
            var productmanaType = product.manaType;

            // Verwende den Titel des Produkts weiter in Deinem Code
            console.log(productmanaType);
        }
    }
});


document.getElementById("search-filter").addEventListener("submit", function (event) {
    event.preventDefault();


    var manaSymbols = [];
    var checkboxes = document.querySelectorAll('input[name="ManaSymbol[]"]:checked');
    for (var i = 0; i < checkboxes.length; i++) {
        manaSymbols.push(checkboxes[i].value);
    }
    var manaSymbolsString = manaSymbols.join("");
    console.log(manaSymbolsString);
});    