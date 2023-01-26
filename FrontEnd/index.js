console.log("Hello form FrontEnd/index.js");







//funktioniert noch nicht Unterricht fragen!



document.addEventListener("DOMContentLoaded", function() {
    //Rufe die createProduct Funktion hier auf
    const product = {
        name: "Product Name",
        description: "Product Description",
        price: "$50",
        imageUrl: "https://via.placeholder.com/250x250.png?text=Product+Image"
    };
    const card = createProduct(product);
    // Füge das erstellte Element zu einem bestimmten Bereich auf der Seite hinzu
    document.querySelector("#product-container").appendChild(card[0]);
});

