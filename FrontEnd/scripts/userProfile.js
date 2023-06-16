$(document).ready(function () {

    const token = sessionStorage.getItem('token');

    // Überprüfen, ob ein Token vorhanden ist
    if (token) {
        // Token aufteilen und den Payload extrahieren
        const tokenParts = token.split('.');
        const payloadBase64 = tokenParts[1];
        const payload = JSON.parse(atob(payloadBase64));

        // Überprüfen, ob der Benutzer ein Administrator ist
        const ID = payload.id;
        let url = "http://localhost:8080/users/" + ID;

        $.ajax({
            url: url, // Hier die richtige URL mit der Benutzer-ID einsetzen
            type: "GET",
            headers: { "Authorization": sessionStorage.getItem("token") },
            success: function (user) {
                $("#username").text(user.username);
                $("#email").text(user.email);
            },
            error: function (xhr, status, error) {
                console.error(error); // Bei Fehlern die Fehlermeldung in der Konsole anzeigen
            }
        });

    } else {
        return false;
    }

});
