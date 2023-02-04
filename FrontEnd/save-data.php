<?php

// Verbindung zur Datenbank aufbauen
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "test";

// Verbindung herstellen
$conn = mysqli_connect($servername, $username, $password, $dbname);

// Überprüfen, ob Verbindung erfolgreich
if (!$conn) {
    die("Verbindung fehlgeschlagen: " . mysqli_connect_error());
}

// Daten aus dem Formular auslesen
$titel = $_POST['titel'];
$beschreibung = $_POST['beschreibung'];
$preis = $_POST['preis'];

// Bild aus dem Formular auslesen
$bild = $_FILES['bild']['name'];
$bild_tmp = $_FILES['bild']['tmp_name'];

// Bild in den Ordner "images" hochladen
move_uploaded_file($bild_tmp, "images/$bild");

// SQL-Befehl für die Einfügung der Daten
$sql = "INSERT INTO produkte (titel, beschreibung, preis, bild)
VALUES ('$titel', '$beschreibung', '$preis', '$bild')";

// Überprüfen, ob Einfügung erfolgreich
if (mysqli_query($conn, $sql)) {
    echo "Daten erfolgreich eingefügt.";
} else {
    echo "Fehler bei der Einfügung: " . mysqli_error($conn);
}

// Verbindung schließen
mysqli_close($conn);

?>
