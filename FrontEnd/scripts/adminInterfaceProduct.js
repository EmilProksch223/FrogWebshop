function loadProducts() {
    $.ajax({
        url: "http://localhost:8080/products",
        method: "GET",
        headers: { "Authorization": sessionStorage.getItem("token") },
        success: function (products) {
            var productTableBody = $("#productTableBody");
            productTableBody.empty();

            for (var i = 0; i < products.length; i++) {
                var product = products[i];
                var row = $("<tr></tr>");
                row.append($("<td class='align-middle'>" + product.id + "</td>"));
                row.append($("<td class='align-middle'>" + product.name + "</td>"));
                row.append($("<td class='align-middle'>" + product.quantity + "</td>"));
                row.append($("<td class='align-middle'>" + product.price + " €</td>"));
                row.append($("<td class='align-middle'>" + (product.active ? "&#10004;&#65039;" : "&#10060;") + "</td>"));
                

                var editButton = $("<button class='btn btn-primary'>Bearbeiten</button>");
                editButton.click(createEditProductHandler(product));

                var deleteButton = $("<button class='btn btn-danger mx-1'>Löschen</button>");
                deleteButton.click(createDeleteProductHandler(product.id));

                var buttonCell = $("<td class='text-end'></td>").append(editButton, deleteButton);
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
        var productTableBody = $("#productTableBody");
        productTableBody.empty();

        var row = $("<tr></tr>");

        var idCell = $("<td class='align-middle'></td>").text(product.id);
        row.append(idCell);

        var productnameInput = $("<input id='productname' type='text' class='form-control' value='" + product.name + "'>");
        var productnameCell = $("<td></td>").append(productnameInput);
        row.append(productnameCell);

        var quantityInput = $("<input id='quantity' type='text' class='form-control' value='" + product.quantity + "'>");
        var quantityCell = $("<td></td>").append(quantityInput);
        row.append(quantityCell);

        var priceInput = $("<input id='price' type='text' class='form-control' value='" + product.price + "'>");
        var priceCell = $("<td></td>").append(priceInput);
        row.append(priceCell);

        var activeDropdown = $("<select class='form-control' id='activeDropdown'></select>");
        activeDropdown.append($("<option value='true'>&#10004;&#65039;</option>"));
        activeDropdown.append($("<option value='false'>&#10060;</option>"));
        activeDropdown.val(product.active.toString());
        var activeCell = $("<td></td>").append(activeDropdown);
        row.append(activeCell);

        var saveButton = $("<button class='btn btn-primary mx-1'>Speichern</button>");
        saveButton.click(createSaveProductHandler(product));
        var cancelButton = $("<button class='btn btn-secondary'>Abbrechen</button>");
        cancelButton.click(function () {
            loadProducts();
        });

        var buttonCell = $("<td class='text-end'></td>").append(saveButton, cancelButton);
        row.append(buttonCell);

        productTableBody.append(row);
    };
}
function createSaveProductHandler(product) {
    return function () {
        var productId = product.id;
        var newProductname = $("#productname").val();
        var newQuantity = $("#quantity").val();
        var newProductPrice = $("#price").val();
        var isActive = $("#activeDropdown").val() === "true";

        // Überprüfe, ob das Eingabefeld leer ist
        if (newProductname.trim() === "") {
            newProductname = product.name;
        }
        if (newProductPrice.trim() === "") {
            newProductPrice = product.price;
        }
        if (newQuantity.trim() === "") {
            newQuantity = product.quantity;
        }
        

        var updatedProduct = {
            id: productId,
            name: newProductname,
            description: product.description,
            imageUrl: product.imageUrl,
            price: newProductPrice,
            quantity: newQuantity,
            manaType: product.manaType,
            active: isActive,
        };

        console.log("updatedProduct:", updatedProduct);

        // AJAX PUT-Anfrage senden, um den Benutzer zu aktualisieren
        $.ajax({
            url: "http://localhost:8080/products/update",
            method: "PUT",
            headers: { "Authorization": sessionStorage.getItem("token") },
            data: JSON.stringify(updatedProduct),
            contentType: "application/json",
            success: function (response) {
                // Hier kannst du den Code zum Handhaben der erfolgreichen Aktualisierung implementieren
                console.log("Updated product:", response);

                // Lade die Benutzer erneut, um die aktualisierten Daten anzuzeigen
                loadProducts();
            },
            error: function (error) {
                console.error(error);
            }
        });
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