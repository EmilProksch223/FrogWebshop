$(document).on('shown.bs.modal', '#navbarModal', function() {
  document.getElementById("loginButton").addEventListener("click", function() {
    $.post({
      url: "http://localhost:8080/login",
      contentType: "application/json",
      data: JSON.stringify({
        username: $("#usernameInput").val(),
        password: $("#passwordInput").val()
      }),
      success: function(data) {
        var timestamp = new Date().toISOString(); // Aktuellen Zeitpunkt erfassen
        sessionStorage.setItem("token", data);
        sessionStorage.setItem("loginTimestamp", timestamp); // Timestamp speichern
        location.href = "index.html";
        console.log("Eingeloggt");
      },
      error: console.error
    });
  });
});


