// Initialisiere den Status des Inputs
var imageCheckboxes = document.querySelectorAll(".image-checkbox");

for (var i = 0; i < imageCheckboxes.length; i++) {
    var imageCheckbox = imageCheckboxes[i];
    var input = imageCheckbox.querySelector("input[type='checkbox']");
    if (input.checked) {
        imageCheckbox.classList.add("image-checkbox-checked");
    } else {
        imageCheckbox.classList.remove("image-checkbox-checked");
    }
}

// Synchronisiere den Status des Inputs mit der Klasse
for (var i = 0; i < imageCheckboxes.length; i++) {
    var imageCheckbox = imageCheckboxes[i];
    imageCheckbox.addEventListener("click", function (e) {
        var input = this.querySelector("input[type='checkbox']");
        if (this.classList.contains("image-checkbox-checked")) {
            this.classList.remove("image-checkbox-checked");
            input.checked = false;
        } else {
            this.classList.add("image-checkbox-checked");
            input.checked = true;
        }
        e.preventDefault();
    });
}

