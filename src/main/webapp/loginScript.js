var loginBtn = document.getElementById("login-btn");
var message = document.getElementById("msg");
var email = document.getElementById("email");
var password = document.getElementById("password");

loginBtn.addEventListener("click", login);
async function login(){
    const url = "/LeavesManager/authentication";
    console.log(email.value, password.value);
    const user = {
        email: email.value,
        password: password.value
    }
    console.log(JSON.stringify(user));
    try{
        var response = await fetch(url, {
            method: 'POST',
            headers: { 'Content-type' : 'application/json'},
            body: JSON.stringify(user),
            credentials: "include"
        });
    }catch(error){
        console.log(error);
    }
    var result = await response.json();
    console.log(result);
    if(result.status === "success"){
        message.classList.add("d-none");
        window.location.href = 'dashboard.html';
    } 
    else {
        message.classList.remove("d-none");
    }
}