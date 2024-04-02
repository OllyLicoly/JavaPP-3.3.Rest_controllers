$(async function () {
    await getAllUsers();
    getUser()
})


async function getAllUsers() {
    let table = $('#AllUsers tbody');
    table.empty();

    fetch('/api/admin/users')
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                let tabContent = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${user.roles.map(role => role.role.substring(5)).join(" ")}</td>
                            <td>
                                <button type="button" class="btn btn-success" data-toggle="modal" onclick="editModal(${user.id})"  
                                data-target="#modalEdit" >Edit</button>                                
                            </td> 
                            <td>
                                <button type="button" class="btn btn-danger" data-toggle="modal" onclick="deleteModal(${user.id})" 
                                data-target="#modalDelete">Delete</button>
                            </td>
                        </tr>
                )`;
                table.append(tabContent);
            })
        })

}


function getUser() {
    let table = $('#CurrentUser tbody');
    table.empty();

    fetch("/api/admin/current").then(res => res.json())
        .then(data => {
            currentUser = data;
            // console.log(data)
            let tabContent = `$(
                        <tr>
                            <td>${currentUser.id}</td>
                            <td>${currentUser.username}</td>
                            <td>${currentUser.age}</td>
                            <td>${currentUser.email}</td>
                            <td>${currentUser.roles.map(role => role.role.substring(5)).join(" ")}</td>
                        </tr>
                )`;
            table.append(tabContent);

            document.getElementById("headerUsername").innerText = currentUser.username;
            document.getElementById("headerUserRoles").innerText = currentUser.roles.map(role => role.role.substring(5)).join(" ");
        })
}


let addForm = document.getElementById('formNew')
addForm.addEventListener('submit', addUser)
addForm.addEventListener('submit', successAdd)

function successAdd() {
    alert("User added!")
}

function addUser() {
    let roles = [];
    for (let i = 0; i < addForm.roles.options.length; i++) {
        if (addForm.roles.options[i].selected) roles.push({
            id: addForm.roles.options[i].value,
            role: "ROLE_" + addForm.roles.options[i].text
        });
    }
    fetch("/api/admin/user", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: addForm.username.value,
            age: addForm.age.value,
            email: addForm.email.value,
            password: addForm.password.value,
            roles: roles
        })
    }).then(() =>
        getAllUsers()
    );
}


async function getOneUser(id) {
    let response = await fetch("/api/admin/user/" + id);
    return await response.json();
}

// function rolesUser(event) {
//     const rolesUser = createRole(1, "ROLE_USER");
//     const rolesAdmin = createRole(2, "ROLE_ADMIN");
//     let roles = [];
//     let allRoles = [];
//     let sel = document.querySelector(event);
//     for (let i = 0, n = sel.options.length; i < n; i++) {
//         if (sel.options[i].selected) {
//             roles.push(sel.options[i].value);
//         }
//     }
//     if (roles.includes('1')) {
//         allRoles.push(rolesUser);
//     }
//     if (roles.includes('2')) {
//         allRoles.push(rolesAdmin);
//     } else if (roles.length === 0) {
//         allRoles.push(rolesUser)
//     }
//     return allRoles;
// }
//
// function createRole(roleId, roleName) {
//     return {
//         roleId,
//         roleName,
//     };
// }


// Delete user

async function fillModalDelete(form, modal, id) {
    let user = await getOneUser(id);
    let roles = user.roles.map(role => role.role.substring(5)).join(" ");
    document.getElementById('idDel').setAttribute('value', user.id);
    document.getElementById('nameDel').setAttribute('value', user.username);
    document.getElementById('ageDel').setAttribute('value', user.age);
    document.getElementById('emailDel').setAttribute('value', user.email);
    document.getElementById('passwordDel').setAttribute('value', user.password);
    document.getElementById('userRolesDel').setAttribute('value', roles)
}

let deleteForm = document.forms["deleteUser"]
async function deleteModal(id) {
    const modal = new bootstrap.Modal(document.querySelector('#modalDelete'));
    await fillModalDelete(deleteForm, modal, id);
    deleteForm.addEventListener("submit", ev => {
        fetch("api/admin/user/" + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(() => {
            getAllUsers();
        });
    });
}


// Edit user

async function fillModalEdit(form, modal, id) {
    let user = await getOneUser(id);
    let roles = user.roles.map(role => role.role.substring(5)).join(" ");
    // console.log(roles)
    document.getElementById('idEd').setAttribute('value', user.id);
    document.getElementById('nameEd').setAttribute('value', user.username);
    document.getElementById('ageEd').setAttribute('value', user.age);
    document.getElementById('emailEd').setAttribute('value', user.email);
    document.getElementById('passwordEd').setAttribute('value', user.password);
    // document.getElementById('rolesEd').setAttribute('value', roles)
    // if ((user.roles.map(role => role.id)) === 1 && ((user.roles.map(role => role.id)) === 2)) {
    //     document.getElementById('user').setAttribute('selected', 'true');
    //     document.getElementById('admin').setAttribute('selected', 'true');
    // } else if ((user.roles.map(role => role.id)) === 1) {
    //     document.getElementById('user').setAttribute('selected', 'true');
    // } else if (user.roles.map(role => role.id) === 2) {
    //     document.getElementById('admin').setAttribute('selected', 'true');
    // }
    // document.getElementById('rolesEd').setAttribute('value', roles)
    if (roles === "USER") {
        document.editUserForm.rolesEd.options[1].setAttribute('selected', 'true');
    } else if (roles === "ADMIN") {
        document.editUserForm.rolesEd.options[0].setAttribute('selected', 'true');
    }
}


let editForm = document.forms["editUser"]

// function selectRole(){
//     const roleSelect = document.editUserForm.rolesEd;
//     let roles = [];
//
//     function changeOption(){
//         const selectedOption = roleSelect.options[roleSelect.selectedIndex];
//         roles.push({
//             id: selectedOption.value,
//             role: "ROLE_" + selectedOption.text
//         })
//         console.log(selectedOption.value)
//         console.log("ROLE_" + selectedOption.text)
//         console.log(roles)
//     }
//
//     roleSelect.addEventListener("change", changeOption);
// }

async function editModal(id) {
    const modal = new bootstrap.Modal(document.querySelector('#modalEdit'));
    await fillModalEdit(editForm, modal, id);

    let roles = [];
    const roleSelect = document.editUserForm.rolesEd;
    function changeOption(){
        const selectedOption = roleSelect.options[roleSelect.selectedIndex];
        roles.push({
            id: selectedOption.value,
            role: "ROLE_" + selectedOption.text
        })
    }
    roleSelect.addEventListener("change", changeOption);

    // async function sameRole() {
    //
    //     let user = await getOneUser(id);
    //     console.log(user.roles)
    //     roles = user.roles
    //     console.log(roles)
    //     // console.log(roles.id.valueOf())
    //     // console.log(roles.role.valueOf())
    //     roles.push({
    //         id: roles.id,
    //         role: roles.role
    //     })
    //
    // }
    // editForm.addEventListener("submit", sameRole())


    editForm.addEventListener("submit", ev => {
        ev.preventDefault();

        fetch("api/admin/user/" + id, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editForm.id.value,
                username: editForm.name.value,
                age: editForm.age.value,
                email: editForm.email.value,
                password: editForm.password.value,
                // roles: roles
                roles: roles
            })}
        ).then(() => {
            $('#closeEdit').click();
            getAllUsers();
        });
    });
}