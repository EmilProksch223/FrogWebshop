document.addEventListener('DOMContentLoaded', function() {
    const formCreateProduct = document.getElementById('formCreateProduct');
    const descriptionInput = document.getElementById('description');
  
    formCreateProduct.addEventListener('submit', function(event) {
      event.preventDefault();
  
      // Überprüfen der Checkboxen
      const manaSymbolsCheckboxes = document.querySelectorAll('input[name="ManaSymbol[]"]:checked');
      if (manaSymbolsCheckboxes.length === 0) {
        alert('Bitte wählen Sie mindestens einen Mana-Symbol-Checkbox aus!');
        return;
      }
  
      // Daten aus dem Formular abrufen
      const productName = document.getElementById('inputProductName').value;
      const productPrice = document.getElementById('inputProductPrice').value;
      const productQuantity = document.getElementById('inputProductQuantity').value;
      const productDescription = descriptionInput.value;
  
      // Erstellen des Datenobjekts
      const manaSymbols = [];
      for (let i = 0; i < manaSymbolsCheckboxes.length; i++) {
        manaSymbols.push(manaSymbolsCheckboxes[i].value);
      }
      
      const manaSymbolsString = manaSymbols.join("");
      console.log(manaSymbolsString)
  
      const product = {
        name: productName,
        description: productDescription,
        price: productPrice,
        quantity: productQuantity,
        manaType: manaSymbolsString // Hinzufügen des manaSymbolsString zur Datenstruktur
      };
  
      // Ajax-Anfrage senden
      $.ajax({
        url: 'http://localhost:8080/products', // Anpassen des Endpunkts
        type: 'POST',
        contentType: 'application/json',
        headers: { "Authorization": sessionStorage.getItem("token") },
        data: JSON.stringify(product),
        success: function(response) {
          console.log('Daten erfolgreich gesendet:', response);
          alert('Daten erfolgreich gesendet!');
          // Weitere Aktionen nach dem Speichern ausführen
        },
        error: function(xhr, status, error) {
          console.error('Fehler beim Senden der Daten:', error);
          // Aktionen bei Fehler durchführen, wie z.B. Fehlermeldung anzeigen
        }
      });
    });
  });
  
