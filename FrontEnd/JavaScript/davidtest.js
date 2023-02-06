
document.querySelector("#myForm").addEventListener("submit", function(event) {
  event.preventDefault();
  
  var file = document.querySelector("#input-Bild-Produkt").files[0];
  
  var formData = new FormData();
  formData.append("image", file);
  
  fetch("/save-image", {
    method: "POST",
    body: formData
  })
  .then(function(response) {
    return response.json();
  })
  .then(function(data) {
    console.log("Image uploaded successfully: ", data);
  })
  .catch(function(error) {
    console.error("Error uploading image: ", error);
  });
});

