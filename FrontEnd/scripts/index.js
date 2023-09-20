document.getElementById("filter-button").onclick = function (event) {

    removeProducts();

    const manaSymbols = [];
    const checkboxes = document.querySelectorAll('input[name="ManaSymbolCreate[]"]:checked');
    for (let i = 0; i < checkboxes.length; i++) {
        manaSymbols.push(checkboxes[i].value);
    }
    const manaSymbolsString = manaSymbols.join("");
    const searchterm = document.getElementById('search').value;

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/products/active?manasymbols=" + encodeURIComponent(manaSymbolsString) + "&searchterm=" + encodeURIComponent(searchterm),
        cors: true,
        success: function (products) { addProductstoPage(products) },
        error: function (error) { console.error(error) }
    });
};


document.getElementById("search").addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
        event.preventDefault();
        document.getElementById("filter-button").click();
    }
});

function removeProducts() {
    $("#productsContainer").empty();
}
