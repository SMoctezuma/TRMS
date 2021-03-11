'use strict';

const url = 'http://localhost:8081/project1/api/';
const homepage = 'http://localhost:8081/project1/static/';

if(window.location.href != homepage) {
	if(document.cookie !== null) {
		let account = getCookie("account");
        console.log(account);
    	if(account == null) {
    		window.location.href = homepage;
    	}
        if(account.roleId.name == "Employee") {
            if(document.getElementById("admin") != null)
                document.getElementById("admin").style.display = "none";
        }
        //Do everything after account is verified
        document.getElementById("navbardrop").innerHTML = account.name;
	}
}
async function getUpdateUser() {
    let account = getCookie("account");
    const res = await fetch(url+`user/${account.id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    res.json().then((data) => {
        console.log(data + " updated user");
        createListWithHeading("Name:", data.name);
        createListWithHeading("Email:", data.email);
        createListWithHeading("Role:", data.roleId.name);
        if(data.availableMoney == null)
            createListWithHeading("Available Money:", "Not applicable");
        else
        createListWithHeading("Available Money:", "$"+data.availableMoney);
        if(data.email !== null) {
            document.cookie = "account=" + JSON.stringify(data);
        }
    }).catch((err) => {/**Should never error. */});
}
async function handleLogin() {
    document.getElementById("error").style.display = "hidden";

    const data = {
        email: document.getElementById("email").value,
        pass: document.getElementById("password").value
    }
    if(data.email == '' || data.pass == '') {
        document.getElementById("error").innerHTML = "Please fill in all fields.";
        document.getElementById("error").style.display = "block";
        return;
    }

    // `user/login?email=${data.email}&pass=${data.pass}`
    const res = await fetch(url+`user/login?email=${data.email}&pass=${data.pass}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    res.json().then((data) => {
        //console.log(data);
        if(data.email !== null) {
            document.cookie = "account=" + JSON.stringify(data);
            window.location.href = "/project1/static/dashboard.html";
        }
    }).catch((err) => {
        if(res.status == 420) {
            document.getElementById("error").innerHTML = "Invalid password.";
            document.getElementById("error").style.display = "block";
        } else if(res.status == 421) {
            document.getElementById("error").innerHTML = "No account found.";
            document.getElementById("error").style.display = "block";
        } else {
            document.getElementById("error").innerHTML = "An error occurred.";
            document.getElementById("error").style.display = "block";
        }
    });
}

function handleLogout() {
    document.cookie = "account=; expires=Thu, 01 Jan 1970 00:00:00 UTC;";
    window.location.href = homepage;
}

function getCookie(cookieName) {
    var name = cookieName + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
      var c = ca[i];
      while (c.charAt(0) == ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return JSON.parse(c.substring(name.length, c.length));
      }
    }
    return null;
}

async function submitForm() {
    const data = {
        account: getCookie("account").email,
        eventType: document.querySelector('#eventType').value,
        dateOfEvent: document.getElementById("DOE").value,
        time: document.getElementById("time").value,
        location: document.getElementById("location").value,
        desc: document.getElementById("eventDesc").value,
        cost: document.getElementById("COE").value,
        gradingFormat: document.getElementById("gradingFormat").value,
        wrr: document.getElementById("wrr").value,
        emailApproval: document.getElementById("emailApproval").value
    }

    const res = await fetch(url+`form/`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });

    res.json().then((data) => {
        console.log(data);
        window.location.href = homepage;
    }).catch((err) => {
        console.log(err);
        if(res.status == 400) {
        
        }
    });
}

function createListWithHeading(heading, value) {
    let root = document.getElementById("data");
    let group = document.createElement("div");
    let header = document.createElement("h3");
    header.innerHTML = heading;
    header.style.display = "inline-block";
    group.appendChild(header);

    let val = document.createElement("p");
    val.innerHTML = value;
    val.style.display = "inline-block";
    val.style.marginLeft = ".5rem";
    group.appendChild(val);

    root.appendChild(group);
}

function parseDate(d) {
    let k = d.split("-");
    if(k.length == 3)
        return k[1]+"/"+k[2]+"/"+k[0];
    return;
}