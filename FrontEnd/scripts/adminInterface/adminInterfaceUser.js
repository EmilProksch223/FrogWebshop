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

    displayUserEditOrList("table-row-group", "none");

    usersToShow.forEach(function (user) {
        const row = $("<tr>");
        row.append($("<td>").text(user.id));
        row.append($("<td>").text(user.username));
        row.append($("<td class='d-none d-md-table-cell'>").text(user.email));
        row.append($("<td class='text-center'>").html(user.active ? "&#10004;&#65039;" : "&#10060;"));
        row.append($("<td class='text-center'>").html(user.admin ? "&#10004;&#65039;" : "&#10060;"));
        const userEditButton = $("<button class='btn btn-primary mx-1' id='userEditButton'><span class='text-nowrap'><span class='d-none d-sm-inline'>Bearbeiten </span><span>&#x2692;&#xFE0F;</span></span></button>");
        userEditButton.click(function () {
            editUser(user);
        });
        const userDeleteButton = $("<button class='btn btn-danger mx-1' id='userDeleteButton'>&#x1F5D1;&#xFE0F;</button>");
        userDeleteButton.click(function () {
            deleteUser(user.id);
        })

        const userHandler = $("<td class='text-end pe-0'>");
        userHandler.append(userEditButton, userDeleteButton);
        row.append(userHandler);

        userTableBody.append(row);
    });
}

function editUser(user) {

    displayUserEditOrList("none", "table-row-group");

    $("#editUserId").text(user.id);
    $("#editUserName").val(user.username);
    $("#editUserEmail").val(user.email);

    $("#editUserActive").val(user.active.toString());
    $("#editUserAdmin").val(user.admin.toString());

    $("#saveEditUser").off("click").on("click", function () {
        saveUser(user);
    });

}

$(document).on('click', '#cancelEditUser', function () {
    displayUserEditOrList("table-row-group", "none");
});


// Produkt löschen

function deleteUser(userId) {
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


function saveUser(user) {

    // Aktualisierte Userinformationen
    const updatedUser = {
        id: user.id,
        password: user.password,
        username: $("#editUserName").val().trim() || user.name,
        email: $("#editUserEmail").val().trim() || user.email,
        active: $("#editUserActive").val() === "true",
        admin: $("#editUserAdmin").val() === "true"
    };

    // User aktualisieren
    $.ajax({
        url: "http://localhost:8080/users/update",
        method: "PUT",
        headers: { "Authorization": sessionStorage.getItem("token") },
        data: JSON.stringify(updatedUser),
        contentType: "application/json",
        success: function (response) {
            console.log("Updated user: ", response);
            loadUsers();
        },
        error: function (error) {
            console.error(error);
        }
    });
}

//Switch between List and Edit

function displayUserEditOrList(displayUserList, displayUserEdit) {
    let userListTable = document.getElementById("userTableBody");
    let editUserTable = document.getElementById("editUserTable");

    userListTable.style.display = displayUserList;
    editUserTable.style.display = displayUserEdit;
}