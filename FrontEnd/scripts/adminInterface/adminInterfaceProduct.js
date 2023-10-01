// Produkte als Liste hinzufügen

$(document).ready(function () {
  loadProducts();
});

function loadProducts() {
  $.ajax({
    url: "http://localhost:8080/products",
    method: "GET",
    headers: { "Authorization": sessionStorage.getItem("token") },
    success: function (products) {
      allProducts = products;
      currentPage = 1;
      addProductsToList(products);
    },
    error: function (error) {
      console.error(error);
    }
  });
}

//Filter Produkte / Produkte laden

$(document).on('click', '#filterButton', function () {
  let productTableBody = $("#productTableBody");
  productTableBody.empty();

  const searchterm = document.getElementById("search").value;
  let activeFilter = "";

  if (document.getElementById("create-activeCheckbox").checked === true) {
    activeFilter = true;
  } else if (document.getElementById("create-inactiveCheckbox").checked === true) {
    activeFilter = false;
  }

  $.ajax({
    url: "http://localhost:8080/products?activeFilter=" + activeFilter + "&searchterm=" + encodeURIComponent(searchterm),
    type: "GET",
    headers: { "Authorization": sessionStorage.getItem("token") },
    success: function (products) {
      allProducts = products; // Alle Produkte aktualisieren
      currentPage = 1;
      addProductsToList(products)
    },
    error: function (error) { console.error(error) }
  });
});


function addProductsToList(products) {
  const divPages = $("#pagesButton");
  divPages.show();
  divPages.find("p").text(currentPage);

  const productsPerPage = 7;
  const startIndex = (currentPage - 1) * productsPerPage;
  const endIndex = startIndex + productsPerPage;
  const productsToShow = products.slice(startIndex, endIndex);
  const productTableBody = $("#productTableBody");

  productTableBody.empty();

  productsToShow.forEach(function (product) {
    const row = $("<tr>");
    row.append($("<td>").text(product.id));
    row.append($("<td>").text(product.name));
    row.append($("<td class='text-end d-none d-md-table-cell'>").text(product.price.toFixed(2) + "€"));
    row.append($("<td class='text-center d-none d-md-table-cell'>").html(product.active ? "&#10004;&#65039;" : "&#10060;"));
    row.append($("<td class='text-end pe-4 d-none d-md-table-cell'>").text(product.quantity));
    const editButton = $("<button class='btn btn-primary mx-1' id='editButton'><span class='text-nowrap'><span class='d-none d-sm-inline'>Bearbeiten</span><span>&#x2692;&#xFE0F;</span></span></button>");
    editButton.click(function () {
      editProduct(product);
    });
    const deleteButton = $("<button class='btn btn-danger mx-1' id='deleteButton'>&#x1F5D1;&#xFE0F;</button>");
    deleteButton.click(function () {
      deleteProduct(product.id);
    })



    const handler = $("<td class='text-end pe-0'>");
    handler.append(editButton, deleteButton);
    row.append(handler);

    productTableBody.append(row);
  });
}

//Produkte bearbeiten

function editProduct(product) {

  let productListContainer = document.getElementById("productListContainer");
  let productEditContainer = document.getElementById("productEditContainer");

  productListContainer.style.display = "none";
  productEditContainer.style.display = "block";

  const sliderEl = document.querySelector("#editProductManaCost");
  const sliderValue = document.querySelector(".rangeValueEdit")

  rangeSlider(sliderEl, sliderValue);

  const container = $("#productEditContainer");
  container.find("h2").text(`Produkt ID: ${product.id}`);

  $("#editProductName").val(product.name);
  $("#editProductActive").val(product.active.toString());

  $("#editProductPrice").val(product.price);
  $("#editProductQuantity").val(product.quantity);
  $("#showProductImage").attr("href", "http://localhost:8080/files/" + product.imageUrl);
  $("#editProductDescription").val(product.description);
  $("#editProductManaCost").val(product.manaCost);


  loadManaSymbols2(product.manaType);

  $("#saveEditProduct").off("click").on("click", function () {
    saveProduct(product);
  })

}

$(document).on('click', '#cancelEditProduct', function () {
  displayProductEditOrList("block", "none");
});


// Produkt löschen

function deleteProduct(productId) {
  if (confirm("Are you sure you want to delete this product?")) {
    $.ajax({
      url: "http://localhost:8080/products/" + productId,
      method: "DELETE",
      headers: { "Authorization": sessionStorage.getItem("token") },
      success: function (response) {
        console.log("Deleted product:", response);
        loadProducts();
      },
      error: function (error) {
        console.error(error);
      }
    });
  }
};


// Produkte speichern

function saveProduct(product) {
  // Datei-Upload, falls eine Datei ausgewählt wurde
  const fileInput = document.getElementById("updateProductImg");
  const file = fileInput.files[0];

  if (file) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("fileId", product.id);

    $.ajax({
      url: 'http://localhost:8080/files/update',
      type: 'PUT',
      contentType: false,
      processData: false,
      headers: { "Authorization": sessionStorage.getItem("token") },
      data: formData,
      success: function (response) {
        console.log('File erfolgreich geändert:', response);
        alert('File erfolgreich geändert!');
      }
    });
  }

  // Produkt-Upload

  // Mana-Symbole sammeln
  const manaSymbols = [...document.querySelectorAll('input[name="ManaSymbol[]"]:checked')].map(input => input.value).join("");

  // Aktualisierte Produktinformationen
  const updatedProduct = {
    id: product.id,
    name: $("#editProductName").val().trim() || product.name,
    price: parseFloat($("#editProductPrice").val()) || product.price,
    quantity: parseInt($("#editProductQuantity").val()) || product.quantity,
    manaType: manaSymbols,
    manaCost: $("#editProductManaCost").val() || product.manaCost,
    imageUrl: product.imageUrl,
    description: $("#editProductDescription").val().trim() || product.description,
    active: $("#editProductActive").val() === "true"
  };

  // Produkt aktualisieren
  $.ajax({
    url: "http://localhost:8080/products/update",
    method: "PUT",
    headers: { "Authorization": sessionStorage.getItem("token") },
    data: JSON.stringify(updatedProduct),
    contentType: "application/json",
    success: function (response) {
      console.log("Updated product: ", response);
      loadProducts();
    },
    error: function (error) {
      console.error(error);
    }
  });
}

$(document).on('click', '#saveEditProduct', function () {
  let productListContainer = document.getElementById("productListContainer");
  let productEditContainer = document.getElementById("productEditContainer");

  productListContainer.style.display = "block";
  productEditContainer.style.display = "none";
});

//Switch between List and Edit

function displayProductEditOrList(displayProductList, displayProductEdit) {
  let productListContainer = document.getElementById("productListContainer");
  let productEditContainer = document.getElementById("productEditContainer");

  productListContainer.style.display = displayProductList;
  productEditContainer.style.display = displayProductEdit;

}

//Produkte pro Seite und Seite wechsel

$(document).on('click', '#previousPage', function () {
  if (currentPage > 1) {
    currentPage--;
    addProductsToList(allProducts);
  }

  updatePageButtons();
});

$(document).on('click', '#nextPage', function () {
  const totalPages = Math.ceil(allProducts.length / 7);
  if (currentPage < totalPages) {
    currentPage++;
    addProductsToList(allProducts);
  }

  updatePageButtons();
});

function updatePageButtons() {
  const totalPages = Math.ceil(allProducts.length / 7);
  $('#previousPage').prop('disabled', currentPage === 1);
  $('#nextPage').prop('disabled', currentPage === totalPages);
}