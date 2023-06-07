document.addEventListener('DOMContentLoaded', function () {
  fetch('/frontend/components/navbar/overlay.html')
    .then(response => response.text())
    .then(data => {
      document.querySelector('overlay').innerHTML = data;
    });
});


