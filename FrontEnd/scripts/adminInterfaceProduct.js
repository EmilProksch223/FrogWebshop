function loadProducts() {
    $.ajax({
        url: "http://localhost:8080/products",
        method: "GET",
        headers: { "Authorization": sessionStorage.getItem("token") },
        success: function (products) {
            const container = $("#productListContainer");
            container.find("h2").text("Produkt Liste");

            const table = $("<table class='table table-striped'></table>");
            const thead = $("<thead><tr></tr></thead>");
            const thead1 = $("<th>ID</th><th>Name</th><th>Anzahl</th><th>Preis</th><th>Active</th>");
            const thead2 = $("<th><div class='input-group input-group-sm ms-4 pe-4 '><input class='form-control me-1 border-dark' type='search' placeholder='Search' aria-label='Search' id='search'></div></th>");

            thead.append(thead1, thead2)
            
            const tbody = $("<tbody id='productTableBody'></tbody>");

            for (let i = 0; i < products.length; i++) {
                let product = products[i];
                let row = $("<tr></tr>");
                row.append($("<td class='align-middle'>" + product.id + "</td>"));
                row.append($("<td class='align-middle'>" + product.name + "</td>"));
                row.append($("<td class='align-middle'>" + product.quantity + "</td>"));
                row.append($("<td class='align-middle'>" + product.price + " €</td>"));
                row.append($("<td class='align-middle'>" + (product.active ? "&#10004;&#65039;" : "&#10060;") + "</td>"));

                let editButton = $("<button class='btn btn-primary'>Bearbeiten</button>");
                editButton.click(createEditProductHandler(product));
                editButton.click(function () {
                    loadManaSymbols();
                });

                let deleteButton = $("<button class='btn btn-danger mx-1'>Löschen</button>");
                deleteButton.click(createDeleteProductHandler(product.id));

                let buttonCell = $("<td class='text-end'></td>").append(editButton, deleteButton);
                row.append(buttonCell);

                tbody.append(row);
            }

            table.append(thead, tbody);
            container.empty().append("<h2 class='text-center mb-0'>Produkt Liste</h2>", table);
        },
        error: function (error) {
            console.error(error);
        }
    });
}

$(document).ready(function () {
    loadProducts();
});

function createDeleteProductHandler(productId) {
    return function () {
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
}


function createEditProductHandler(product) {
    return function () {
        let productTableBody = $("#productTableBody");
        productTableBody.empty();

        const container = $("#productListContainer");
        container.find("h2").text(`Produkt ID: ${product.id}`);
        container.find("table").remove();

        let row1 = $("<div class='row'></div>");

        let titleCol = $("<div class='col-9 mb-3 ps-0'></div>");
        let titleLabel = $("<label for='editProductName' class='form-label p-0'>Titel</label>");
        let titleInput = $("<input type='text' class='form-control' id='editProductName' name='editProductName' value='" + product.name + "'></input>");
        titleCol.append(titleLabel, titleInput)

        let activeCol = $("<div class='col-2 mb-3'></div>");
        let activeLabel = $("<label for='editProductActive' class='form-label p-0'>Active</label>");
        let activeDropdown = $("<select class='form-control' id='editProductActive'></select>");
        activeDropdown.append($("<option value='true'>&#10004;&#65039;</option>"));
        activeDropdown.append($("<option value='false'>&#10060;</option>"));
        activeDropdown.val(product.active.toString());
        activeCol.append(activeLabel, activeDropdown)

        row1.append(titleCol, activeCol);

        let row2 = $("<div class='row mb-3'></div>");

        let col1 = $("<div class='col-6 col-sm-3 mb-2 p-0'></div>");
        let priceLabel = $("<label for='editProductPrice' class='form-label p-0'>Preis</label>");
        let priceInput = $("<div class='input-group'><input type='text' class='form-control' id='editProductPrice' name='editProductPrice' aria-label='Euro amount' value='" + product.price + "'><span class='input-group-text'>€</span></div>");
        col1.append(priceLabel, priceInput);

        let col2 = $("<div class='col-6 col-sm-3'></div>");
        let quantityLabel = $("<label for='editProductQuantity' class='form-label p-0'>Menge</label>");
        let quantityInput = $("<input type='number' class='form-control' id='editProductQuantity' name='editProductQuantity' value='" + product.quantity + "'></input>");
        col2.append(quantityLabel, quantityInput);

        let col3 = $("<div class='col p-0'></div>");
        let imgLabel = $("<label for='editProductImg' class='form-label p-0'><a>&#128444;&#65039;</a> Bild ändern</label>");
        let imgInput = $("<input type='file' class='form-control' id='editProductImg' name='editProductImg'>");
        col3.append(imgLabel, imgInput);

        row2.append(col1, col2, col3);

        let row3 = $("<div class='row mb-3'></div>");

        let descriptionLabel = $("<label for='editProductDescription' class='form-label p-0'>Beschreibung</label>");
        let descriptionInput = $("<textarea type='text' class='form-control' id='editProductDescription' name='editProductDescription' rows='3'>" + product.description + "</textarea>");
        row3.append(descriptionLabel, descriptionInput);

        let row4 = $("<div class='row mb-3'></div>");
        let manaLabel = $("<div class='row mb-2'>Mana</div>");
        let manaInput = $("<div class='row justify-content-between mb-3'><manaSymbols></manaSymbols></div>");
        row4.append(manaLabel, manaInput);

        let row5 = $("<div class='row mb-3'></div>");
        let saveCol = $("<div class='col ms-0 me-auto'></div>");
        let saveButton = $("<button class='btn btn-primary mx-1'>Speichern</button>");
        saveCol.append(saveButton);
        saveButton.click(createSaveProductHandler(product));

        let cancelCol = $("<div class='col'></div>");
        let cancelButton = $("<button class='btn btn-secondary'>Abbrechen</button>");
        cancelCol.append(cancelButton);
        cancelButton.click(function () {
            loadProducts();
        });
        row5.append(saveCol, cancelCol);

        productTableBody.append(row1, row2, row3, row4, row5);

        container.append(productTableBody);

        loadManaSymbols(); // Funktion erneut aufrufen
    };
}

function createSaveProductHandler(product) {
    return function () {
        let productId = product.id;
        let newProductName = document.getElementById("editProductName").value;
        let newProductPrice = parseFloat($("#editProductPrice").val());
        let newProductQuantity = parseInt($("#editProductQuantity").val());
        let newManaType = product.manaType;
        let newProductImg = product.imageUrl;
        let newProductDescription = $("#editProductDescription").val();
        let productIsActive = $("#editProductActive").val() === "true";

        if (newProductName.trim() === "") {
            newProductName = product.name;
        }
        if (isNaN(newProductPrice)) {
            newProductPrice = product.price;
        }

        if (isNaN(newProductQuantity)) {
            newProductQuantity = product.quantity;
        }
        if (newProductDescription.trim() === "") {
            newProductDescription = product.description;
        }

        let updatedProduct = {
            id: productId,
            name: newProductName,
            price: newProductPrice,
            quantity: newProductQuantity,
            manaType: newManaType,
            imageUrl: newProductImg,
            description: newProductDescription,
            active: productIsActive,
        };

        console.log("updatedProduct: ", updatedProduct);

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
    };
}

function searchProducts(){
    let productTableBody = $("#productTableBody");
        productTableBody.empty();

    const searchterm = document.getElementById('search').value;
    console.log(searchterm);
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/products/active?manasymbols=" + encodeURIComponent(manaSymbolsString) + "&searchterm=" + encodeURIComponent(searchterm),
        cors: true,
        success: function (products) { addProductstoPage(products) },
        error: function (error) { console.error(error) }
    });

    document.getElementById("search").addEventListener("keydown", function(event) {
        if (event.key === "Enter") {
        event.preventDefault();
        document.getElementById("filter-button").click();
        }
    });
}