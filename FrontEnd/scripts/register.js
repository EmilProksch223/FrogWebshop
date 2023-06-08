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
      alert('Bitte stimmen Sie den AGBs zu!');
      return;
    }

    const username = document.getElementById("username").value;
    const password = passwordInput.value;

    const user = {
      username: username,
      password: password,
      admin: false
    };

    $.ajax({
      url: 'http://localhost:8080/users',
      type: 'POST',
      contentType: 'application/json',
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









