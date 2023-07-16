function loadProducts() {
    $.ajax({
        url: "http://localhost:8080/products",
        method: "GET",
        headers: { "Authorization": sessionStorage.getItem("token") },
        success: function (products) {
            const container = $("#productListContainer");
            container.find("h2").text("Produkt Liste");

            const table = $("<table class='table table-striped'></table>");
            const thead = $("<thead><tr><th>ID</th><th>Name</th><th>Anzahl</th><th>Preis</th><th>Active</th></tr></thead>");
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

                let deleteButton = $("<button class='btn btn-danger mx-1'>Löschen</button>");

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

        let activeCol = $("<div class='col mb-3'></div>");
        let activeLabel = $("<label for='inputProductActive' class='form-label p-0'>Active</label>");
        let activeDropdown = $("<select class='form-control' id='inputProductActive'></select>");
        activeDropdown.append($("<option value='true'>&#10004;&#65039;</option>"));
        activeDropdown.append($("<option value='false'>&#10060;</option>"));
        activeDropdown.val(product.active.toString());
        activeCol.append(activeLabel, activeDropdown)

        row1.append(titleCol, activeCol);

    

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

        productTableBody.append(row1, row5);

        container.append(productTableBody);

        loadManaSymbols(); // Funktion erneut aufrufen
    };
}

function createSaveProductHandler(product) {
    return function () {
        let productId = product.id;
        let newProductName = $("#editProductName").val();

        let productIsActive = $("#inputProductActive").val() === "true";

        console.log("ProductId: ", productId);
        console.log("updatedProductName: ", newProductName);
        console.log("updatedProductActive: ", productIsActive);


    };
}
