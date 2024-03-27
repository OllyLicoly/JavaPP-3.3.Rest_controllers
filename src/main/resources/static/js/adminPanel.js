
$(async function () {
    await getTableWithUsers();
})

const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },

    findAllUsers: async () => await fetch('/api/admin/users'),

}

async function getTableWithUsers() {
    let table = $('#mainTableWithUsers tbody');
    table.empty();

    await userFetchService.findAllUsers()
        .then(res => res.json())
        .then(users => {
            console.log(users)
            users.forEach(user => {
                let tableFilling = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${user.roles.map(role => role.role.substring(5)).join(" ")}</td>
                            <td>
                                <button type="button" class="btn btn-success" data-toggle="modal" data-userid="${user.id}" data-action="edit"  
                                data-target="#modalEdit">Edit</button>
                            </td> 
                            <td>
                                <button type="button" class="btn btn-danger" data-toggle="modal" data-userid="${user.id}" data-action="delete" 
                                data-target="#modalDelete">Delete</button>
                            </td>
                        </tr>
                )`;
                table.append(tableFilling);
            })
        })

}


const applicantForm = document.getElementById('formNew')
applicantForm.addEventListener('submit', createNewUser)

function createNewUser() {
        let roles = [];
        for (let i = 0; i < applicantForm.roles.options.length; i++) {
            if (applicantForm.roles.options[i].selected) roles.push({
                id: applicantForm.roles.options[i].value,
                role: "ROLE_" + applicantForm.roles.options[i].text
            });
        }
        fetch("/api/admin/user", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: applicantForm.username.value,
                age: applicantForm.age.value,
                email: applicantForm.email.value,
                password: applicantForm.password.value,
                roles: roles
            })
        });
    }



