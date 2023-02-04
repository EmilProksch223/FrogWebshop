const form = document.querySelector('form');

form.addEventListener('submit', event => {
  event.preventDefault();

  const formData = new FormData(form);
  const data = Object.fromEntries(formData);

  fetch('http://localhost:8080/products', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  })
    .then(response => response.json())
    .then(data => {
      console.log('Success:', data);
    })
    .catch(error => {
      console.error('Error:', error);
    });
});
