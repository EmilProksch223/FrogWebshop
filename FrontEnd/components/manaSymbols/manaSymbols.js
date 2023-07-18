//ManaSymbole beim hinzufügen 

document.addEventListener('DOMContentLoaded', loadManaSymbols);
function loadManaSymbols() {
    fetch('/frontend/components/manaSymbols/manaSymbols.html')
        .then(response => response.text())
        .then(data => {
            document.querySelector('manaSymbols').innerHTML = data;
            imgaCheckboxenNotVisbile();
            const checkboxes = document.querySelectorAll('input[type="checkbox"]');
            checkboxes.forEach(function (checkbox) {
                const currentId = checkbox.getAttribute('id');
                const newId = 'create-' + currentId;
                checkbox.setAttribute('id', newId);
                checkbox.setAttribute('name', 'ManaSymbolCreate[]')
            });
        });
}

// ManaSymbole beim editieren 
function loadManaSymbols2(manaSymbolsString) {
    fetch('/frontend/components/manaSymbols/manaSymbols.html')
        .then(response => response.text())
        .then(data => {
            document.querySelector('manaSymbols2').innerHTML = data;
            imgaCheckboxenNotVisbile();
            const chars = [...manaSymbolsString];
            for (let i = 0; i < chars.length; i++) {
                console.log(i);
                const manaSymbol = chars[i];
                const checkbox = document.getElementById(manaSymbol);
                if (checkbox) {
                    checkbox.checked = true;
                }
            }
        });
}
//keine Checkboxen, Bilder fungieren als Checkboxen mit Umrandung 
function imgaCheckboxenNotVisbile() {
    let imageCheckboxes = document.querySelectorAll(".image-checkbox");

    for (let i = 0; i < imageCheckboxes.length; i++) {
        let imageCheckbox = imageCheckboxes[i];
        let input = imageCheckbox.querySelector("input[type='checkbox']");
        input.style.display = "none"; // Verstecke die Checkbox

        // Füge Klick-Eventlistener zum imageCheckbox-Element hinzu
        imageCheckbox.addEventListener("click", function (e) {
            let input = this.querySelector("input[type='checkbox']");
            input.checked = !input.checked; // Ändere den Status der Checkbox
            this.classList.toggle("image-checkbox-checked"); // Wechsle die Klasse des Bildes
            e.preventDefault();
        });
    }
}