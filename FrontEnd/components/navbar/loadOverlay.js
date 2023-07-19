document.addEventListener('DOMContentLoaded', function () {
  fetch('/frontend/components/navbar/overlay.html')
    .then(response => response.text())
    .then(data => {
      document.querySelector('overlay').innerHTML = data;

      checkTokenValidity();

      var loginButton = document.getElementById('loginButtonOverlay');
      if (loginButton) {
        var logoutButton = document.createElement('button');
        logoutButton.id = 'logoutButtonOverlay';
        logoutButton.type = 'button';
        logoutButton.className = 'btn btn-outline-secondary ms-3 text-white';
        logoutButton.innerHTML = 'Abmelden';

        logoutButton.addEventListener('click', function() {
          sessionStorage.removeItem('token');
          sessionStorage.removeItem('loginTimestamp');
          console.log('Token und Login-Zeitstempel wurden gelöscht');

          var currentPage = window.location.pathname; // Aktuelle Seite ermitteln
          if (currentPage === '/frontend/index.html') {
            window.location.reload(); // Seite neu laden, wenn sich auf der Indexseite befindet
          } else {
            window.location.href = '/frontend/index.html'; // Auf die Indexseite umleiten, wenn sich auf einer anderen Seite befindet
          }
          // weitere Aktionen nach dem Abmelden
        });

        replaceButtons(loginButton, logoutButton); // Funktion zum Austausch der Buttons aufrufen
      }

      var userInterfaceLink = document.getElementById('userInterfaceLink');
      if (userInterfaceLink && window.sessionStorage.getItem('token') !== null) {
        var dropdownContainer = document.createElement('span');
        dropdownContainer.className = 'dropdown';

        var dropdownToggle = document.createElement('img');
        dropdownToggle.src = '/frontend/components/navbar/img/user_48.png';
        dropdownToggle.alt = 'userInterface';
        dropdownToggle.className = 'dropdown-toggle mx-3';
        dropdownToggle.setAttribute('data-bs-toggle', 'dropdown');
        dropdownToggle.setAttribute('aria-expanded', 'false');
        dropdownToggle.width = '40';
        dropdownToggle.height = '40';

        var dropdownMenu = document.createElement('ul');
        dropdownMenu.className = 'dropdown-menu dropdown-menu-end bg-dark border border-white';
        dropdownMenu.style.position = 'absolute';
        dropdownMenu.style.left = '50%';
        dropdownMenu.style.transform = 'translateX(-50%)';
        dropdownMenu.style.top = 'calc(100% + 1.5rem)';

        var actionOne = document.createElement('li');
        var actionOneLink = document.createElement('a');
        actionOneLink.className = 'dropdown-item text-white';
        actionOneLink.href = '#';
        actionOneLink.innerHTML = 'Profil';
        actionOne.appendChild(actionOneLink);

        var actionTwo = document.createElement('li');
        var actionTwoLink = document.createElement('a');
        actionTwoLink.className = 'dropdown-item text-white';
        actionTwoLink.href = '#';
        actionTwoLink.innerHTML = 'Einkaufswagen';
        actionTwo.appendChild(actionTwoLink);

        var actionThree = document.createElement('li');
        var actionThreeLink = document.createElement('a');
        actionThreeLink.className = 'dropdown-item text-white';
        actionThreeLink.href = '#';
        actionThreeLink.innerHTML = 'Einstellungen';
        actionThree.appendChild(actionThreeLink);

        dropdownMenu.appendChild(actionOne);
        dropdownMenu.appendChild(actionTwo);
        dropdownMenu.appendChild(actionThree);

        dropdownContainer.appendChild(dropdownToggle);
        dropdownContainer.appendChild(dropdownMenu);

        userInterfaceLink.parentNode.insertBefore(dropdownContainer, userInterfaceLink.nextSibling);
      }

      var isAdmin = checkAdminStatus();
      if (isAdmin) {
        var adminLink = document.getElementById('adminInterfaceLink');
        if (adminLink) {
          adminLink.href = '/frontend/pages/adminInterface.html';
          adminLink.innerHTML = '<img class="navbar-img mx-3 my-1" src="/frontend/components/navbar/img/admin_settings_tools_48.png" width="40" height="40" alt="sale">';
        } else {
          console.log('Das Element mit der ID "adminInterfaceLink" wurde nicht gefunden.');
        }
      }

    });
});


function replaceButtons(loginButton, logoutButton) {
  if (window.sessionStorage.getItem("token") !== null) {
    if (loginButton.parentNode) {
      loginButton.parentNode.replaceChild(logoutButton, loginButton);
      console.log('Logout-Button wurde eingefügt');
    } else {
      console.log('Das Elternelement des Login-Buttons wurde nicht gefunden.');
    }
  } else {
    console.log('Token nicht vorhanden, Login-Button wird angezeigt');
  }
}


