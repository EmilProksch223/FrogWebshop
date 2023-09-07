$.ajax({
    url: "http://localhost:8080/carts/2/positions", // Du kannst 1 als Hartcodierung verwenden, falls dies beabsichtigt ist
    headers: { "Authorization": sessionStorage.getItem("token") },
    cors: true,
    success: function (positions) { addPositionsToPage(positions); },
    error: function (error) { console.error(error); }
});

function addPositionsToPage(positions) {
    for (var i = 0; i < positions.length; i++) {
        var position = positions[i];
        

        $("#positionName").text(position.product.name).attr("id", "positionName" + position.product.id);
        $("#positionDescription").text(position.product.description).attr("id", "positionDescription" + position.product.id);
        $("#positionPrice").text(position.product.price).attr("id", "positionPrice" + position.product.id);
        $("#positionQuantity").text(position.product.quantity).attr("id", "positionQuantity" + position.product.id);
        $("#positionImage").attr("src", "http://localhost:8080/files/" + position.product.imageUrl).attr("id", "positionImage" + position.product.id);

        console.log(position.product.imageUrl);
    }
}