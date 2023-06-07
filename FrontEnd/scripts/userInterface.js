if (window.sessionStorage.getItem("token") !== null) {
    // Versuche, das Element mit der ID "loginButton1" abzurufen
    var loginButton = document.getElementById('loginButtonOverlay');
  
    if (loginButton) {
      // Erstelle den Auslogg-Button
      var logoutButton = document.createElement('button');
      logoutButton.id = 'logoutButtonOverlay';
      logoutButton.type = 'button';
      logoutButton.className = 'btn btn-outline-secondary ms-3';
      logoutButton.setAttribute('data-bs-toggle', 'modal');
      logoutButton.setAttribute('data-bs-target', '#navbarModal');
      logoutButton.innerHTML = 'Ausloggen';
  
      // Ersetze den Anmelden-Button mit dem Auslogg-Button
      loginButton.parentNode.replaceChild(logoutButton, loginButton);
    } else {
      console.log('Das Element mit der ID "loginButtonOverlay" wurde nicht gefunden.');
    }
  }