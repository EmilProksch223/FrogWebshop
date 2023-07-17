//ManaSymbole beim hinzufügen 

document.addEventListener('DOMContentLoaded', loadManaSymbols);
function loadManaSymbols() {
    fetch('/frontend/components/manaSymbols/manaSymbols.html')
        .then(response => response.text())
        .then(data => {
            document.querySelector('manaSymbols').innerHTML = data;

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