document.addEventListener('DOMContentLoaded', function () {
  fetch('/frontend/components/manaSymbols/manaSymbols.html')
    .then(response => response.text())
    .then(data => {
      document.querySelector('manaSymbols').innerHTML = data;
    });
});
