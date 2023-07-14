function loadProducts() {
    $.ajax({
        url: "http://localhost:8080/products",
        method: "GET",
        headers: { "Authorization": sessionStorage.getItem("token") },
        success: function (products) {
            let productTableBody = $("#productTableBody");
            productTableBody.empty();

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

                let deleteButton = $("<button class='btn btn-danger mx-1'>Löschen</button>");
                deleteButton.click(createDeleteProductHandler(product.id));

                let buttonCell = $("<td class='text-end'></td>").append(editButton, deleteButton);
                row.append(buttonCell);

                productTableBody.append(row);
            }
        },
        error: function (error) {
            console.error(error);
        }
    });
}
function createEditProductHandler(product) {
    return function () {
        let productTableBody = $("#productTableBody");
        productTableBody.empty();

        let row1 = $("<div class='row mb-3'></div>");

        let priceCol = $("<div class='col-6 col-sm-3 mb-2 p-0'></div>");
        let priceLabel = $("<label for='inputProductPrice' class='form-label'>Preis</label>");
        let priceInput = $("<input type='text' class='form-control' id='inputProductPrice' name='inputProductPrice' aria-label='Euro amount' value='" + product.price + "'>");
        let priceInputGroup = $("<div class='input-group'></div>");
        let priceInputGroupAddon = $("<span class='input-group-text'>€</span>");

        priceInputGroup.append(priceInput, priceInputGroupAddon);
        priceCol.append(priceLabel, priceInputGroup);

        let quantityCol = $("<div class='col-6 col-sm-3'></div>");
        let quantityLabel = $("<label for='inputProductQuantity' class='form-label'>Menge</label>");
        let quantityInput = $("<input type='number' class='form-control' id='inputProductQuantity' name='inputProductQuantity' value='" + product.quantity + "'>");

        quantityCol.append(quantityLabel, quantityInput);

        let imageCol = $("<div class='col p-0'></div>");
        let imageLabel = $("<label for='inputProductImg' class='form-label'>Bild hochladen</label>");
        let imageInput = $("<input class='form-control' type='file' id='inputProductImg' name='inputProductImg' disabled>");

        imageCol.append(imageLabel, imageInput);

        row1.append(priceCol, quantityCol, imageCol);

        let row2 = $("<div class='row mb-3'></div>");

        let descriptionLabel = $("<label for='inputProductDescription' class='form-label col-md-2'>Beschreibung:</label>");
        let descriptionInput = $("<textarea class='form-control col-md-10' id='inputProductDescription' name='inputProductDescription'>" + product.description + "</textarea>");

        row2.append(descriptionLabel, descriptionInput);

        let row3 = $("<div class='row mb-3'></div>");

        let manaTypeLabel = $("<label class='col-form-label col-md-2'>Mana Type:</label>");
        let manaTypeInput = $("<input type='text' class='form-control col-md-10' id='inputProductManaType' name='inputProductManaType' value='" + product.manaType + "'>");

        row3.append(manaTypeLabel, manaTypeInput);

        productTableBody.append(row1, row2, row3);
    };
}


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
$(document).ready(function () {
    loadProducts();
});