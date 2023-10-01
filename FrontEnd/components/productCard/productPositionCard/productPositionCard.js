$.ajax({
    url: "http://localhost:8080/carts/{userId}/positions", // Du kannst 1 als Hartcodierung verwenden, falls dies beabsichtigt ist
    headers: { "Authorization": sessionStorage.getItem("token") },
    cors: true,
    success: function (positions) { addPositionsToPage(positions); },
    error: function (error) { console.error(error); }
});

//Produkt-Container vorbereiten
function addPositionsToPage(positions) {
    const positionsContainer = $("#positionsContainer");
    positionsContainer.empty();

    let row;

    for (let i = 0; i < positions.length; i++) {
        if (i % 2 === 0) {
            row = $(`<div class="row"></div>`);
            positionsContainer.append(row);
        }
        let positionCol = $(`<div class="col-12 col-lg-4 col-xxl-6 d-flex justify-content-center mb-3" id="positionCol${positions[i].id}"></div>`);
        row.append(positionCol);

        loadPositionCard(positions[i]);
    }
}

//HTML Component hinzufügen und mit Produktdaten befüllen
function loadPositionCard(position) {
    const colId = "positionCol" + position.id;
    const positionCol = document.getElementById(colId);

    console.log(colId);
    fetch('/frontend/components/productCard/productPositionCard/productPositionCard.html')
        .then(response => response.text())
        .then(html => {
            positionCol.innerHTML = html;

            console.log(position);

            setupPosition(position);

            const fields = ["Name", "Image", "Description", "Price", "Quantity"];
            fields.forEach(field => {
                const element = positionCol.querySelector(`#position${field}`);
                switch (field) {
                    case "Name":
                        element.innerHTML = `${position.product.name}`
                        break;
                    case "Description":
                        element.innerHTML = `${position.product.description}`
                    case "Image":
                        element.src = `http://localhost:8080/files/${position.product.imageUrl}`;
                        break;
                    case "Price":
                        element.innerHTML = `${position.product.price.toFixed(2)} €`;
                        break;
                    case "Quantity":
                        element.innerHTML = `${position.quantity} Stk.`;
                        break;
                    default:
                        element.innerHTML = position[field.toLowerCase()];
                        break;
                }
            });



        })
        .catch(error => {
            console.error(error);
        });
}
function setupPosition(position) {
    const positionDeleteButtonCard = document.getElementById("positionDelete");
    console.log(positionDeleteButtonCard);

    positionDeleteButtonCard.id = "positionDelete" + position.product.id;

    const positionDeleteButton = document.getElementById("positionDelete" + position.product.id);

    positionDeleteButton.addEventListener("click", function () {
        removePositionFromCart(position.id);
    });
}


function removePositionFromCart(positionId) {
    if (confirm("Are you sure you want to remove this product from your cart?")) {
        $.ajax({
            url: "http://localhost:8080/positions/" + positionId,
            method: "DELETE",
            headers: { "Authorization": sessionStorage.getItem("token") },
            success: function (response) {

                const positionsContainer = $(`#positionCol${positionId}`);
                positionsContainer.remove();
                console.log("Remove product from Cart:", response);
            },
            error: function (error) {
                console.error(error);
            }
        });
    }
}