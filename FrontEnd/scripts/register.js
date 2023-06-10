<<<<<<< HEAD
document.getElementById("signIn-form").addEventListener("submit", function(event) {
    event.preventDefault();
    
    const formData = new FormData();
    formData.append("first_name", document.querySelector('input[type="text"][id="inputFirstName"]').value);
    formData.append("last_name", document.querySelector('input[type="text"][id="inputLastName"]').value);
    formData.append("gender", document.querySelector('select[aria-label=".form-select-sm example"]').value);
    formData.append("email", document.querySelector('input[type="email"][id="inputEmail4"]').value);
    formData.append("password", document.querySelector('input[type="password"][id="inputPassword4"]').value);
    formData.append("address1", document.querySelector('input[type="text"][id="inputAddress"]').value);
    formData.append("address2", document.querySelector('input[type="text"][id="inputAddress2"]').value);
    formData.append("city", document.querySelector('input[type="text"][id="inputCity"]').value);
    formData.append("country", document.querySelector('select[id="inputState"]').value);
    formData.append("disctrict", document.querySelector('input[type="text"][id="inputDistrict"]').value);
    
    $.ajax({
    url: 'http://localhost:8080/api/users',
    method: 'POST',
    dataType: 'json',
    contentType: 'application/json',
    data: JSON.stringify({
    firstName: document.querySelector('input[type="text"][id="inputFirstName"]').value,
    lastName: document.querySelector('input[type="text"][id="inputLastName"]').value,
    gender: document.querySelector('select[aria-label=".form-select-sm example"]').value,
    eMail: document.querySelector('input[type="email"][id="inputEmail4"]').value,
    password: document.querySelector('input[type="password"][id="inputPassword4"]').value,
    address: document.querySelector('input[type="text"][id="inputAddress"]').value,
    address2: document.querySelector('input[type="text"][id="inputAddress2"]').value,
    city: document.querySelector('input[type="text"][id="inputCity"]').value,
    country: document.querySelector('select[id="inputState"]').value,
    district: document.querySelector('input[type="text"][id="inputDistrict"]').value
    }),
    success: function (response) { console.log('Registration successful') },
    error: function (error) { console.error(error) }
    });
    });
=======
/*checkt ob beide Passwörter übereinstimmen und ob AGBs bestätigt sind*/ 
document.addEventListener('DOMContentLoaded', function() {
  const registrationForm = document.getElementById('registrationForm');
  const passwordInput = document.getElementById('password');
  const confirmPasswordInput = document.getElementById('confirmPassword');
  const agreementCheckbox = document.getElementById('agreementCheckbox');
  const togglePasswordImage = document.getElementById('togglePassword');

  registrationForm.addEventListener('submit', function(event) {
    event.preventDefault();

    if (passwordInput.value !== confirmPasswordInput.value) {
      alert('Die Passwörter stimmen nicht überein!');
      return;
    }

    if (!agreementCheckbox.checked) {
      alert('Bitte stimmen Sie den AGB zu!');
      return;
    }

    if (sessionStorage.getItem("token")) {
      alert('Bitte melden Sie sich zuerst ab!');
      return;
    }

    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = passwordInput.value;

    const user = {
      username: username,
      email: email,
      password: password,
      admin: false
    };

    $.ajax({
      url: 'http://localhost:8080/users/createUser',
      type: 'POST',
      contentType: 'application/json',
      headers: { "Authorization": sessionStorage.getItem("token") },
      data: JSON.stringify(user),
      success: function(response) {
        console.log('Daten erfolgreich gesendet:', response);

        // Login

        $.post({
          url: "http://localhost:8080/login",
          contentType: "application/json",
          data: JSON.stringify(user),
          success: function(data) {
            var timestamp = new Date().toISOString(); // Aktuellen Zeitpunkt erfassen
            sessionStorage.setItem("token", data);
            sessionStorage.setItem("loginTimestamp", timestamp); // Timestamp speichern
            location.href = "index.html";
            console.log("Eingeloggt");
          },
          error: console.error
        });
        alert("Sie haben sich erfolgreich Registriert!")
      },
      error: function(xhr, status, error) {
        console.error('Fehler beim Senden der Daten:', error);
        // Do something on error, such as showing an error message
      }
    });
  });

  // Password Show/Hide

  togglePasswordImage.addEventListener('click', function() {
    if (passwordInput.type === 'password') {
      passwordInput.type = 'text';
      confirmPasswordInput.type = 'text';
      togglePasswordImage.src = './img/hidepassword_48.png';
    } else {
      passwordInput.type = 'password';
      confirmPasswordInput.type = 'password';
      togglePasswordImage.src = './img/showpassword_48.png';
    }
  });

  // ...
});









>>>>>>> 2830e02aeb93e34278cb00e67bfd5b8d5777cc2e
