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



// Test für Useranzeige
$.get({
  url: 'http://localhost:8080/user',
  cors: true, // CORS enabled
  success: function (users) { addUsersToPage(users) }, // On success, add the users to the page
  error: console.error
});

function addUsersToPage(users) {
  const userContainer = $("#userContainer"); // Selects the product container element
  userContainer.empty();
}

function createUser(user) {

  const userContainer = $(`<div class="row">`);

  const id = $(`<div class="col">${product.id}</div>`);
  const address = $(`<div class="col"></div>`);
  const address2 = $(`<div class="col"></div>`);
  const city = $(`<div class="col"></div>`);
  const country = $(`<div class="col"></div>`);
  const district = $(`<div class="col"></div>`);
  const eMail = $(`<div class="col"></div>`);
  const firstName = $(`<div class="col"></div>`);
  const lastName = $(`<div class="col"></div>`);
  const gender = $(`<div class="col"></div>`);
  const password = $(`<div class="col"></div>`);
  const userRights = $(`<div class="col"></div>`);

  userContainer.append(id, address, address2, city, country, district, eMail, firstName, lastName, gender, password, userRights);

  return userContainer;

}