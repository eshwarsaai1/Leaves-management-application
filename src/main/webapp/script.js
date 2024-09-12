var employee = {};
var leaveTypes = ["", "SICK", "PERSONAL TIME OFF", "MATERNITY", "PATERNITY"];
var annualLeaves;
var myTeam = [];
var leavesTaken;

var profileIcon = document.getElementById("profile-icon");
var dialogBox = document.getElementById("dialog");
var profileIconClone = document.getElementById("profile-icon-clone");
var profileName = document.getElementById("profile-name");
var profileEmail = document.getElementById("profile-email");
var myLeavesContainer = document.getElementById("my-leaves-container");
var myLeavesUl = document.getElementById("leaves-list");
var myTeamLeavesContainer = document.getElementById("my-team-leaves-container");
var myTeamLeavesUl = document.getElementById("my-team-leaves-list");
var myTeamContainer = document.getElementById("my-team-container");
var applyLeaveContainer = document.getElementById("applyLeave-container");
var leaveTypeInput = document.getElementById("leaveType");
var fromDateInput = document.getElementById("fromDate");
var toDateInput = document.getElementById("toDate");
var descriptionInput = document.getElementById("description");
var myLeavesFilter = document.getElementById("my-leaves-filter");
var myTeamLeavesFilter = document.getElementById("my-team-leaves-filter");
var leaveWarningMsg = document.getElementById("leave-warning");

fromDateInput.min = getCurrentDate();
fromDateInput.addEventListener("change", () => {
    leaveWarningMsg.classList.add("d-none");
    toDateInput.disabled = false;
    toDateInput.min = fromDateInput.value;
});
toDateInput.addEventListener("change", () => {
    leaveWarningMsg.classList.add("d-none");
    const fromDate = new Date(fromDateInput.value);
    const toDate = new Date(toDateInput.value);

    var totalDays = ((toDate - fromDate) / (1000 * 3600 * 24) + 1);
    var currentDate = fromDate;
    var flag = false;
    while (currentDate <= toDate) {
        console.log(currentDate);
        console.log(currentDate.getDay());
        if (((currentDate.getDay() == 0) || (currentDate.getDay() == 6))) {
            console.log(currentDate.getDay());
            totalDays--;
            flag = true;
        }
        currentDate.setDate(currentDate.getDate() + 1);
    }
    document.getElementById("total-days").innerHTML = totalDays;

    if (flag) {
        if (totalDays > 0) {
            leaveWarningMsg.innerHTML = "Weekends are reduced from leave duration";
        }
        else {
            leaveWarningMsg.innerHTML = "Leave cannot applied on Weekends";
        }
        leaveWarningMsg.classList.remove("d-none");
        flag = false;
    }

    if (totalDays > 0) {
        if (totalDays > (annualLeaves[leaveTypes.indexOf(leaveTypeInput.value)]) - leavesTaken[leaveTypes.indexOf(leaveTypeInput.value)]) {
            leaveSubmitBtn.disabled = true;
            // alert("Selected Number of leaves are not available");
            leaveWarningMsg.innerHTML += "\nSelected Number of leaves are not available";
            leaveWarningMsg.classList.remove("d-none");
        }
        else {
            leaveSubmitBtn.disabled = false;
        }
    }
    else {
        leaveSubmitBtn.disabled = true;
    }
})

document.getElementById("form-reset-btn").addEventListener("click", () => {
    document.getElementById("total-days").innerHTML = 0;
    myLeavesBtn.click();
})


profileIcon.addEventListener("click", () => {
    dialogBox.classList.toggle("d-none");
})
myLeavesFilter.addEventListener("change", () => {
    filterLeaves(myLeavesUl, myLeavesFilter.value);
})

myTeamLeavesFilter.addEventListener("change", () => {
    filterLeaves(myTeamLeavesUl, myTeamLeavesFilter.value);
})


var logoutBtn = document.getElementById("logout-btn");
var myLeavesBtn = document.getElementById("my-leaves-btn");
var myTeamLeavesBtn = document.getElementById("my-team-leaves-btn");
var myTeamBtn = document.getElementById("my-team-btn");
var applyLeaveBtn = document.getElementById("applyLeave-btn");
var leaveSubmitBtn = document.getElementById("submit-leave-btn")
logoutBtn.addEventListener('click', logout);


leaveSubmitBtn.addEventListener("click", applyLeave);
myLeavesBtn.addEventListener("click", () => {
    myTeamLeavesContainer.classList.add("d-none");
    applyLeaveContainer.classList.add("d-none");
    myLeavesContainer.classList.remove("d-none");
    myTeamContainer.classList.add("d-none");
    myLeavesBtn.classList.add("text-info");
    myTeamBtn.classList.remove("text-info");
    myTeamLeavesBtn.classList.remove("text-info");
})


myTeamLeavesBtn.addEventListener("click", () => {
    applyLeaveContainer.classList.add("d-none");
    myLeavesContainer.classList.add("d-none");
    myTeamLeavesContainer.classList.remove("d-none");
    myTeamContainer.classList.add("d-none");
    myLeavesBtn.classList.remove("text-info");
    myTeamBtn.classList.remove("text-info");
    myTeamLeavesBtn.classList.add("text-info");
})


myTeamBtn.addEventListener("click", () => {
    myTeamLeavesContainer.classList.add("d-none");
    myLeavesContainer.classList.add("d-none");
    applyLeaveContainer.classList.add("d-none");
    myTeamContainer.classList.remove("d-none");
    myLeavesBtn.classList.remove("text-info");
    myTeamBtn.classList.add("text-info");
    myTeamLeavesBtn.classList.remove("text-info");
})

applyLeaveBtn.addEventListener("click", () => {
    myTeamLeavesContainer.classList.add("d-none");
    myLeavesContainer.classList.add("d-none");
    applyLeaveContainer.classList.remove("d-none");
    myTeamContainer.classList.add("d-none");
})


function addLeave(leave, request) {
    var li = document.createElement("li");
    li.id = leave.leaveId;
    li.classList.add("d-flex", "my-1");
    var status = document.createElement("span");
    if (leave.status == "APPROVED") {
        li.classList.add("d-none");
        status.classList.add("text-success");
    }
    else if (leave.status == "REJECTED") {
        li.classList.add("d-none");
        status.classList.add("text-danger");
    }
    else {
        status.classList.add("text-warning");
    }
    status.classList.add("p-1", "col-sm-1", "overflow-auto", "text-center", "bold");
    status.innerHTML = leave.status;
    li.appendChild(status);
    var employeeName = document.createElement("span");
    if (request) {
        employeeName.classList.add("p-2", "col-sm-2", "overflow-auto", "text-center");
        employeeName.innerHTML = leave.employeeName;
        li.appendChild(employeeName);
    }
    var LeaveType = document.createElement("span");
    LeaveType.classList.add("p-2", "col-sm-2", "overflow-auto", "text-center");
    LeaveType.innerHTML = leaveTypes[leave.leaveTypeId];
    li.appendChild(LeaveType);
    var fromDate = document.createElement("time");
    fromDate.classList.add("p-2", "col-sm-1", "overflow-auto", "text-center");
    fromDate.innerHTML = leave.fromDate;
    li.appendChild(fromDate);
    var toDate = document.createElement("time");
    toDate.classList.add("p-2", "col-sm-1", "overflow-auto", "text-center");
    toDate.innerHTML = leave.toDate;
    li.appendChild(toDate);
    var applyDate = document.createElement("time")
    applyDate.classList.add("p-2", "col-sm-1", "overflow-auto", "text-center");
    applyDate.innerHTML = leave.applyDate;
    li.appendChild(applyDate);
    var days = document.createElement("span");
    days.classList.add("p-2", "col-sm-1", "overflow-auto", "text-center");
    days.innerHTML = leave.days;
    li.appendChild(days);
    var description = document.createElement("span");
    description.classList.add("p-2", "col-sm-3", "overflow-auto", "text-center");
    description.innerHTML = leave.description;
    li.appendChild(description);
    if (request) {
        if (leave.status == "PENDING") {
            li.classList.add("hoverEffect");
            li.addEventListener("click", () => {
                showDialog(leave.employeeId, parseInt(li.id))
            });
        }
        myTeamLeavesUl.appendChild(li);
    }
    else {
        if ((leave.status == "APPROVED")) {
            var currentLeaveDate = new Date(leave.fromDate);
            var todaysDate = new Date();
            if (currentLeaveDate > todaysDate) {
                document.getElementById("upcoming-leaves-container").classList.remove("d-none");
                li.classList.remove("d-none");
                document.getElementById("upcomming-leaves-list").appendChild(li.cloneNode(true));
                li.classList.add("d-none");
            }
        }
        myLeavesUl.appendChild(li);
    }
}


function getCurrentDate() {
    var date = new Date();
    var todaysDate = date.getFullYear();
    todaysDate += ("-" + String(date.getMonth() + 1).padStart(2, '0'));
    todaysDate += ("-" + String(date.getDate()).padStart(2, '0'));
    return todaysDate;
}

async function getAnnualLeaves() {
    var url = "http://localhost:8080/LeavesManager/annualLeaves"
    await fetch(url, {
        method: 'GET',
        credentials: "include"
    }).then(response => {
        return response.json();
    }).then(result => {
        annualLeaves = result.annualLeaves;
        console.log(annualLeaves);
    }).catch(error => console.log(error));
}

getAnnualLeaves();

async function getEmployee() {
    var url = "http://localhost:8080/LeavesManager/employee";
    try {
        var response = await fetch(url, {
            method: 'GET',
            credentials: "include"
        });
        if (response.ok) {
            var result = await response.json();
            employee = result.employee;
            leavesTaken = result.leaves;
            console.log(typeof (leavesTaken));
            loadProfile();
            var managerName = employee.managerName + "";
            document.getElementById("manager-name").innerHTML = managerName.toLowerCase();
            document.getElementById("manager-email").innerHTML = employee.managerEmail;
            createTable(leavesTaken, employee.gender, document.getElementById("leave-sumary-container"));
            getMyLeaves();
            if (employee.role === "Manager") {
                myTeamLeavesBtn.classList.remove("d-none");
                myTeamBtn.classList.remove("d-none");
                getTeam();
            }
        } else {
            alert("Server: Bad request");
        }
    } catch (error) {
        console.log(error);
    }
}
getEmployee();

function loadProfile() {
    console.log(leavesTaken);
    profileName.innerHTML = employee.employeeName;
    profileEmail.innerHTML = employee.email;
    var employeeName = employee.employeeName.split(" ");
    if (employeeName.length > 1) {
        profileIcon.innerHTML = employeeName[0].charAt(0) + employeeName[1].charAt(0);
    }
    else profileIcon.innerHTML = employeeName[0].substring(0, 2);
    profileIconClone.innerHTML = profileIcon.innerHTML;
    document.getElementById("sick-leave-option").innerHTML = "SICK(" + parseInt(annualLeaves[1] - leavesTaken[1]) + ")";
    if (annualLeaves[1] - leavesTaken[1] == 0) {
        document.getElementById("sick-leave-option").disabled = true;

    }
    document.getElementById("pto-leave-option").innerHTML = "PERSONAL TIME OFF(" + parseInt(annualLeaves[2] - leavesTaken[2]) + ")";
    if (annualLeaves[2] - leavesTaken[2] == 0) {
        document.getElementById("pto-leave-option").disabled = true;
    }
    if (employee.gender == "Male") {
        document.getElementById("maternity-leave-option").classList.add("d-none");
        document.getElementById("paternity-leave-option").innerHTML = "PATERNITY(" + parseInt(annualLeaves[4] - leavesTaken[4]) + ")";
        if (annualLeaves[4] - leavesTaken[4] == 0) {
            document.getElementById("paternity-leave-option").disabled = true;
        }
    }
    else {
        document.getElementById("paternity-leave-option").classList.add("d-none");
        document.getElementById("maternity-leave-option").innerHTML = "MATERNITY(" + parseInt(annualLeaves[3] - leavesTaken[3]) + ")";
        if (annualLeaves[4] - leavesTaken[3] == 0) {
            document.getElementById("maternity-leave-option").disabled = true;
        }
    }
}

async function getMyLeaves() {
    var url = "http://localhost:8080/LeavesManager/myLeaves";
    try {
        var response = await fetch(url, {
            method: 'GET',
            credentials: "include"
        });
        if (response.ok) {
            var result = await response.json();
            var leaves = result.leaves;
            console.log(leaves);
            for (var leave of leaves) {
                addLeave(leave, false);
            }
        } else {
            alert("Server: Bad request");
        }
    } catch (error) {
        console.log(error);
    }
}

async function getTeamLeaves() {
    var url = "http://localhost:8080/LeavesManager/teamLeaves";
    try {
        var response = await fetch(url, {
            method: 'GET',
            credentials: "include"
        });
        if (response.ok) {
            var result = await response.json();
            var leaves = result.leaves;
            leaves.sort((a, b) => {
                return ((new Date(a.applyDate)) - (new Date(b.applyDate)))
            })
            for (var leave of leaves) {
                addLeave(leave, true);
            }
        } else {
            alert("Server: Bad request");
        }
    } catch (error) {
        console.log(error);
    }
}

async function getTeam() {
    var url = "http://localhost:8080/LeavesManager/getMyTeam"
    await fetch(url, {
        method: "GET",
        credentials: "include"
    }).then(response => {
        if (response.ok) {
            return response.json();
        }
        else {
            console.error("Something went wrong in fetching team");
        }
    }).then(data => {
        myTeam = data.myTeam;
        for (var employee of myTeam) {
            // console.log(employee);
            addEmployee(employee);
        }
        getTeamLeaves();
    })
}


function addEmployee(employee) {
    var card = document.createElement("div");
    card.classList.add("card", "shadow", "cardStyle", "m-3");
    var profileImg = document.createElement("img");
    profileImg.classList.add("card-img-top");
    if (employee.gender == "Female") {
        profileImg.src = "female-user.jpeg";
    }
    else {
        profileImg.src = "8b167af653c2399dd93b952a48740620.jpg";
    }
    profileImg.alt = "Card image";
    card.appendChild(profileImg);
    var cardBody = document.createElement("div");
    cardBody.classList.add("card-body");
    var employeeName = document.createElement("h6");
    employeeName.classList.add("card-title");
    employeeName.innerHTML = employee.employeeName;
    var employeeEmail = document.createElement("p");
    employeeEmail.classList.add("card-text");
    employeeEmail.innerHTML = employee.email;
    var button = document.createElement("button");

    button.classList.add("btn", "btn-primary");
    button.innerHTML = "See profile";
    button.addEventListener("click", () => {
        showDialog(employee.employeeId, 0);
    })
    cardBody.appendChild(employeeName);
    cardBody.appendChild(employeeEmail);
    cardBody.appendChild(button);
    card.appendChild(cardBody);
    document.getElementById("cards-container").appendChild(card);
}

function showDialog(employeeId, leaveId) {
    var currentEmployee = myTeam.find((employee) => {
        return employee.employeeId == employeeId;
    })
    console.log(employeeId);
    console.log(myTeam);
    console.log(currentEmployee);
    document.getElementById("profile-dialog-name").innerHTML = currentEmployee.employeeName;
    document.getElementById("profile-dialog-email").innerHTML = currentEmployee.email;
    document.getElementById("profile-dialog-DOB").innerHTML = currentEmployee.dateOfBirth;
    document.getElementById("profile-dialog-phone").innerHTML = currentEmployee.phone;
    createTable(currentEmployee.leaveSummary, currentEmployee.gender, document.getElementById("profile-dialog-table"));
    document.getElementById("profile-dialog").classList.remove("d-none");
    document.getElementById("filter").classList.remove("d-none");
    if (leaveId != 0) {
        document.getElementById("leave-accept-btn").classList.remove("d-none");
        document.getElementById("leave-reject-btn").classList.remove("d-none");
        document.getElementById("leave-accept-btn").addEventListener("click", () => {
            leaveAction(leaveId, "APPROVED");
            document.getElementById(leaveId).removeEventListener("click", () => {
                showDialog(employeeId, leaveId)
            });
            document.getElementById("profile-dialog-close").click();
        });
        document.getElementById("leave-reject-btn").addEventListener("click", () => {
            leaveAction(leaveId, "REJECTED");
            document.getElementById(leaveId).removeEventListener("click", () => {
                showDialog(employeeId, leaveId)
            });
            document.getElementById("profile-dialog-close").click();
        });
    }
    document.getElementById("profile-dialog-close").addEventListener("click", () => {
        if (leaveId != 0) {
            document.getElementById("leave-accept-btn").removeEventListener("click", () => {
                leaveAction(leaveId, "APPROVED");
                document.getElementById("profile-dialog-close").click();
            });
            document.getElementById("leave-reject-btn").removeEventListener("click", () => {
                leaveAction(leaveId, "REJECTED");
                document.getElementById("profile-dialog-close").click();
            });
            document.getElementById("leave-accept-btn").classList.add("d-none");
            document.getElementById("leave-reject-btn").classList.add("d-none");
        }
        document.getElementById("profile-dialog-table").innerHTML = "";
        document.getElementById("profile-dialog").classList.add("d-none");
        document.getElementById("filter").classList.add("d-none");
    });
}


function createTable(leaveSummary, gender, container) {
    var table = document.createElement("table");
    table.classList.add("table", "table-hover", "table-dark", "shadow", "mx-auto", "w-50");
    var thead = `<thead class="border text-center">
                <tr>
                    <th></th>
                    <th>SICK</th>
                    <th>PERSONAL TIME OFF</th>`;

    if (gender == "Male") {
        thead += `<th id="paternity-leave-type">PATERNITY</th>`;
    } else {
        thead += `<th id="maternity-leave-type">MATERNITY</th>`;
    }
    thead += `</tr></thead>`

    var tbody = `<tbody class="border text-center">
                <tr>
                    <td><b>Total</b></td>
                    <td>5</td>
                    <td>10</td>`;
    if (gender == "Male") {
        tbody += `<td>10</td>`
    } else {
        tbody += `<td>180</td>`
    }
    tbody += `</tr>
                <tr>
                    <td><b>Used</b></td>
                    <td>${leaveSummary[1]}</td>
                    <td>${leaveSummary[2]}</td>`;
    if (gender == "Male") {
        tbody += `<td>${leaveSummary[4]}</td>`;
    } else {
        tbody += `<td>${leaveSummary[3]}</td>`;
    }

    tbody += `</tr>
                <tr>
                    <td><b>Left</b></td>
                    <td>${15 - leaveSummary[1]}</td>
                    <td>${10 - leaveSummary[2]}</td>`;
    if (gender == "Male") {
        tbody += `<td> ${20 - leaveSummary[4]}</td>`;
    } else {
        tbody += `<td> ${180 - leaveSummary[3]}</td>`;
    }
    tbody += `</tr>
            </tbody>`
    table.innerHTML = thead + tbody;

    container.appendChild(table);
}


async function applyLeave(event) {
    event.preventDefault();
    var leave = {};
    leave.leaveTypeId = leaveTypes.indexOf(leaveTypeInput.value);
    leave.fromDate = fromDateInput.value;
    leave.toDate = toDateInput.value;
    leave.applyDate = getCurrentDate();
    leave.description = descriptionInput.value;
    leave.days = document.getElementById("total-days").innerHTML;
    console.log(leave);

    var url = "http://localhost:8080/LeavesManager/myLeaves";
    try {
        var response = await fetch(url, {
            method: 'POST',
            headers: { 'Content-type': 'application/json' },
            body: JSON.stringify(leave),
            credentials: "include"
        });
        if (response.ok) {
            leave.status = "PENDING";
            addLeave(leave, false);
            document.getElementById("form-reset-btn").click();
        } else {
            alert("Server: Bad request");
        }
    } catch (error) {
        console.log(error);
    }
}

function filterLeaves(list, status) {
    for (const row of list.children) {
        if (status == "Pending" && ((row.firstChild.innerHTML == "APPROVED") || (row.firstChild.innerHTML == "REJECTED"))) {
            row.classList.add("d-none");
        }
        else {
            row.classList.remove("d-none");
        }
    }
}

async function leaveAction(leaveId, newStatus) {
    console.log(leaveId, newStatus);
    var url = "http://localhost:8080/LeavesManager/teamLeaves?leaveId=" + leaveId + "&newStatus=" + newStatus;
    try {
        var data = await fetch(url, {
            method: 'PUT',
            credentials: "include"
        })
        if (!data.ok) {
            console.error("Something went wrong with updating leave action");
        }
    } catch (Error) {
        console.error(Error);
    }
    var currentLeaveStatusElement = document.getElementById(leaveId).children[0];
    currentLeaveStatusElement.classList.remove("text-warning");
    currentLeaveStatusElement.innerHTML = newStatus;
    if (newStatus == "APPROVED") {
        currentLeaveStatusElement.classList.add("text-success");
    }
    else {
        currentLeaveStatusElement.classList.add("text-danger");
    }
}

async function logout() {
    var url = "http://localhost:8080/LeavesManager/authentication";
    try {
        var response = await fetch(url, {
            method: 'GET',
            credentials: "include"
        });
        if (response.ok) {
            window.location.href = 'index.html';
        } else {
            alert("Server: Bad request");
        }
    } catch (error) {
        console.log(error);
    }
}