//Benutzer als Liste hinzufügen

$(document).ready(function () {
    loadUsers();
});

function loadUsers() {
    $.ajax({
        url: "http://localhost:8080/users",
        method: "GET",
        headers: { "Authorization": sessionStorage.getItem("token") },
        success: function (users) {
            allUsers = users;
            currentPage = 1;
            addUsersToList(users);
        },
        error: function (error) {
            console.error(error);
        }
    });
}

//Benutzer laden

function addUsersToList(users) {
    const divPages = $("#usersPagesButton");
    divPages.show();
    divPages.find("p").text(currentPage);

    const usersPerPage = 6;
    const startIndex = (currentPage - 1) * usersPerPage;
    const endIndex = startIndex + usersPerPage;
    const usersToShow = users.slice(startIndex, endIndex);
    const userTableBody = $("#userTableBody");

    userTableBody.empty();

    usersToShow.forEach(function (user) {
        const row = $("<tr>");
        row.append($("<td>").text(user.id));
        row.append($("<td>").text(user.name));
        const userEditButton = $("<button class='btn btn-primary mx-1' id='userEditButton'><span class='text-nowrap'><span class='d-none d-sm-inline'>Bearbeiten </span><span>&#x2692;&#xFE0F;</span></span></button>");
        userEditButton.click(function () {
            editUser(user);
        });
        const userDeleteButton = $("<button class='btn btn-danger mx-1' id='userDeleteButton'>&#x1F5D1;&#xFE0F;</button>");
        userDeleteButton.click(function () {
            deleteProduct(user.id);
        })



        const userHandler = $("<td class='text-end pe-0'>");
        userHandler.append(userEditButton, userDeleteButton);
        row.append(userHandler);

        userTableBody.append(row);
    });
}

function editUser(user) {

    let userListContainer = document.getElementById("userListContainer");
    let userEditContainer = document.getElementById("userEditContainer");

    userListContainer.style.display = "none";
    userEditContainer.style.display = "block";

    const userContainer = $("#userEditContainer");
    userContainer.find("h2").text(`Benutzer ID: ${user.id}`);

    $("#editUserName").val(user.name);
    $("#editUserActive").prop("checked", user.active);

    $("#editUserPrice").val(user.price);
    $("#editUserQuantity").val(user.quantity);
    $("#showUserImage").attr("href", "http://localhost:8080/files/" + user.imageUrl);
    $("#editUserDescription").val(user.description);

    $("#saveEditUser").click(function () {
        saveUser(user);
    })

}

$(document).on('click', '#cancelEditUser', function () {
    let userListContainer = document.getElementById("userListContainer");
    let userEditContainer = document.getElementById("userEditContainer");

    userListContainer.style.display = "block";
    userEditContainer.style.display = "none";

});



// Produkt löschen

function deleteUser(userId) {
    return function () {
        if (confirm("Are you sure you want to delete this user?")) {
            $.ajax({
                url: "http://localhost:8080/users/" + userId,
                method: "DELETE",
                headers: { "Authorization": sessionStorage.getItem("token") },
                success: function (response) {
                    console.log("Deleted user:", response);
                    loadUsers();
                },
                error: function (error) {
                    console.error(error);
                }
            });
        }
    };
}