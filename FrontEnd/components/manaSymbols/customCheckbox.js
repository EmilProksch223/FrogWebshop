//funkioniert nicht


// Ersetze die Checkboxen durch Bilder
var imageCheckboxes = document.querySelectorAll(".image-checkbox");

for (var i = 0; i < imageCheckboxes.length; i++) {
  var imageCheckbox = imageCheckboxes[i];
  var input = imageCheckbox.querySelector("input[type='checkbox']");
  input.style.display = "none"; // Verstecke die Checkbox

  // Füge Klick-Eventlistener zum imageCheckbox-Element hinzu
  imageCheckbox.addEventListener("click", function (e) {
    var input = this.querySelector("input[type='checkbox']");
    input.checked = !input.checked; // Ändere den Status der Checkbox
    this.classList.toggle("image-checkbox-checked"); // Wechsle die Klasse des Bildes
    e.preventDefault();
  });
}
