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
      active: true,
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
          headers: { "Authorization": sessionStorage.getItem("token") },
          data: JSON.stringify(user),
          success: function(data) {
            let timestamp = new Date().toISOString(); // Aktuellen Zeitpunkt erfassen
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
});



document.getElementById("showFormButton").addEventListener("click", function() {
  let form = document.getElementById("addressForm");
  if (form.style.display === "none") {
    form.style.display = "block";
    document.getElementById("showFormButton").innerHTML = "Lieferadresse ausblenden &#x1F69A;";
  } else {
    form.style.display = "none";
    document.getElementById("showFormButton").innerHTML = "Lieferadresse hinzufügen &#x1F69A;";
  }
});





