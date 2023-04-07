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
        url: "http://localhost:8080/api/products?manasymbols=" + encodeURIComponent(manaSymbolsString) + "&searchterm=" + encodeURIComponent(searchterm),
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


/*
document.addEventListener('DOMContentLoaded', function() {
    console.log("test1");
    const addToCartButtons = document.querySelectorAll('.add-to-cart-button');
    addToCartButtons.forEach(button => {
      button.addEventListener('click', event => {
        const productId = button.getAttribute('data-product-id');
        // Hier kannst du dann die AJAX-Anfrage senden und die Produkt-ID übergeben
        console.log("test2");
        $.ajax({
            url: 'http://localhost:8080/api/shoppingcarts',
            method: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify({
                productId: productId,
                quantity: 1 // oder eine andere gewünschte Anzahl
              }),
            success: function(data) {
                console.log(data);
                alert('Produkt erfolgreich zum Warenkorb hinzugefügt!');
              },
            error: function() {
                console.log(data);
              // Fehlermeldung anzeigen oder andere Aktionen ausführen
              alert('Fehler beim Hinzufügen des Produkts zum Warenkorb!');
            }
          });
      });
    });
  });
  
*/

document.addEventListener('DOMContentLoaded', function() {
    const addToCartButtons = document.querySelectorAll('#add-to-cart-button');
    addToCartButtons.forEach(button => {
      button.addEventListener('click', event => {
        console.log('test');
        // Füge hier deinen AJAX-Aufruf ein
      });
    });
  });

  
  
