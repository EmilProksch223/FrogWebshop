document.getElementById("signIn-form").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent the default form submission
  
    // Get the form input values
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const isAdmin = document.getElementById("isAdmin").checked;
  
    const user = {
      username: username,
      password: password,
      admin: isAdmin
    };
  
    $.ajax({
      url: 'http://localhost:8080/users',
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify(user),
      success: function(response) {
        console.log('Daten erfolgreich gesendet:', response);
        // Do something on success, such as showing a success message or redirecting to another page
      },
      error: function(xhr, status, error) {
        console.error('Fehler beim Senden der Daten:', error);
        // Do something on error, such as showing an error message
      }
    });
  });
  