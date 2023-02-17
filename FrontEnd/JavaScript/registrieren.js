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
    url: 'http://localhost:8080/user',
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