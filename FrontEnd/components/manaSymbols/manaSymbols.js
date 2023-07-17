function loadManaSymbols() {
  fetch('/frontend/components/manaSymbols/manaSymbols.html')
      .then(response => response.text())
      .then(data => {
          document.querySelector('manaSymbols').innerHTML = data;
          console.log("ging durch")
      });
}

document.addEventListener('editButton', function () {
    fetch('/frontend/components/manaSymbols/manaSymbols.html')
        .then(response => response.text())
        .then(data => {
            document.querySelector('manaSymbols1').innerHTML = data;
            console.log("ging durch1")
        });
  });

document.addEventListener('DOMContentLoaded', loadManaSymbols);

document.addEventListener('load', loadManaSymbols);
document.addEventListener('readystatechange', loadManaSymbols);