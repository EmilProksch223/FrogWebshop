function loadManaSymbols() {
  fetch('/frontend/components/manaSymbols/manaSymbols.html')
      .then(response => response.text())
      .then(data => {
          document.querySelector('manaSymbols').innerHTML = data;
      });
}

document.addEventListener('DOMContentLoaded', loadManaSymbols);

document.addEventListener('load', loadManaSymbols);
document.addEventListener('readystatechange', loadManaSymbols);