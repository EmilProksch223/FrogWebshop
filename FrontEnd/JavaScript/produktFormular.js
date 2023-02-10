document.getElementById("form-produkt").addEventListener("submit", function (event) {
  event.preventDefault();

  const formData = new FormData();
  formData.append("input-Titel-Produkt", document.querySelector("#input-Titel-Produkt").value);
  formData.append("input-Beschreibung-Produkt", document.querySelector("#input-Beschreibung-Produkt").value);
  formData.append("input-Preis-Produkt", document.querySelector("#input-Preis-Produkt").value);
  formData.append("input-Menge-Produkt", document.querySelector("#input-Menge-Produkt").value);
  formData.append("input-Bild-Produkt", document.querySelector("#input-Bild-Produkt").files[0]);

  event.preventDefault();
  var manaSymbols = [];
  var checkboxes = document.querySelectorAll('input[name="ManaSymbol[]"]:checked');
  for (var i = 0; i < checkboxes.length; i++) {
    manaSymbols.push(checkboxes[i].value);
  }
  var manaSymbolsString = manaSymbols.join("");
  console.log(manaSymbolsString);

  $.ajax({
    url: 'http://localhost:8080/products',
    method: 'POST',
    dataType: 'json',
    contentType: 'application/json',
    data: JSON.stringify({
      name: document.querySelector("#input-Titel-Produkt").value,
      description: document.querySelector("#input-Beschreibung-Produkt").value,
      price: document.querySelector("#input-Preis-Produkt").value,
      quantity: document.querySelector("#input-Menge-Produkt").value,
      imageUrl: document.querySelector("#input-Bild-Produkt").files[0],
      manaType: manaSymbolsString
    }),
    success: function (response) { console.log('Product added successfully') },
    error: function (error) { console.error(error) }
  });
});
