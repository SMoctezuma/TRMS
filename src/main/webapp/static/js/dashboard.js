function checkLogin() {
    if(document.cookie !== null) {
        let account = getCookie("account");
        if(account != null) {
            if(account.roleId.name == "Employee") {
                //Might use if I need logic for only employee role
            }
            if(account.roleId.name != "Employee") {
                document.getElementById("section-header").innerHTML = "Reinbursement Submissions";
                document.getElementById("employee-button").style.display = "none";
            }
        }
    }
}

async function getMyPendingRequests() {
    checkLogin();
    console.log("getting pending requests");
    const res = await fetch(url+`form/${getCookie("account").id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    res.json().then((data) => {
        console.log(data);
        for(let i = 0; i < data.length; i++) {
            if(data[i].status.name == "Pending") {
                if(data[i].directSupervisor) {
                    createListItemWithApprovals(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, "Direct Supervisor Approval", data[i].urgent);
                } else if(data[i].directHead) {
                    createListItemWithApprovals(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, "Direct Head Approval", data[i].urgent);
                } else if(data[i].benco) {
                    createListItemWithApprovals(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, "Benco Approval", data[i].urgent);
                } else {
                    createListItem(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, data[i].urgent);
                }
            }
        }
    }).catch((err) => {
        console.log(err);
        if(res.status == 400) {
        
        }
    });
}

function createListItem(id, accName, d, amountProvided, isUrgent) {
    let listParent = document.getElementById("list"),
    newListItem = document.createElement('div');
    newListItem.id = id;
    newListItem.className = "col-lg-12 bg-dark list-format";

    let accountName = document.createElement("p");
    accountName.className = "account-brief";
    accountName.innerHTML = accName;
    let date = document.createElement("p");
    date.className = "account-brief";
    date.innerHTML = parseDate(d);
    let amount = document.createElement("p");
    amount.className = "account-brief";
    amount.innerHTML = "$"+amountProvided;

    //Append all values to items
    newListItem.appendChild(accountName);
    newListItem.appendChild(date);
    newListItem.appendChild(amount);
    
    //Only if urgent
    if(isUrgent) {
        let urgent = document.createElement("p");
        urgent.className = "account-brief";
        urgent.innerHTML = "Urgent";
        urgent.style.color = "red";
        urgent.style.fontWeight = "700";
        newListItem.appendChild(urgent);
    }

    newListItem.addEventListener('click', function (event) {
        console.log('you clicked ' + id);
        window.localStorage.setItem('form', id);
        window.location.href = homepage+"formoutline.html";
    });

    listParent.appendChild(newListItem);
}

async function getAcceptedRequests() {
    console.log("getting accepted requests");
    let account = getCookie("account");
    const res = await fetch(url+`form/accepted/${account.id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    res.json().then((data) => {
        console.log(data);
        for(let i = 0; i < data.length; i++) {
            // if(data[i].gradeOrPresentation == "" && account.roleId.name != "Employee")
            //     continue;
            if(data[i].benco) {
                createListItemWithApprovals(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, "Benco Approval", data[i].urgent);
            } else if(data[i].directHead) {
                createListItemWithApprovals(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, "Direct Head Approval", data[i].urgent);
            } else if(data[i].directSupervisor) {
                createListItemWithApprovals(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, "Direct Supervisor Approval", data[i].urgent);
            } else {
                createListItem(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, data[i].urgent);
            }
        }
    }).catch((err) => {
        console.log(err);
        if(res.status == 400) {
        
        }
    });
}

async function getRejectedRequests() {
    console.log("getting rejected requests");
    const res = await fetch(url+`form/rejected/${getCookie("account").id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    res.json().then((data) => {
        console.log(data);
        for(let i = 0; i < data.length; i++) {
            if(data[i].directSupervisor.status.name == "Rejected") {
                createListItemWithApprovals(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, "Direct Supervisor Rejected", data[i].urgent);
            } else if(data[i].directHead.status.name == "Rejected") {
                createListItemWithApprovals(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, "Direct Head Rejected", data[i].urgent);
            } else if(data[i].benco.status.name == "Rejected") {
                createListItemWithApprovals(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, "Benco Rejected", data[i].urgent);

            } else {
                createListItem(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, data[i].urgent);
            }
        }
    }).catch((err) => {
        console.log(err);
        if(res.status == 400) {
        
        }
    });
}



function createListItemWithApprovals(id, accName, d, amountProvided, str, isUrgent) {
    let listParent = document.getElementById("list"),
    newListItem = document.createElement('div');
    newListItem.id = id;
    newListItem.className = "col-lg-12 bg-dark list-format";

    let accountName = document.createElement("p");
    accountName.className = "account-brief";
    accountName.innerHTML = accName;
    let date = document.createElement("p");
    date.className = "account-brief";
    date.innerHTML = parseDate(d);
    let amount = document.createElement("p");
    amount.className = "account-brief";
    amount.innerHTML = "$"+amountProvided;
    let acceptedBy = document.createElement("p");
    acceptedBy.className = "account-brief";
    acceptedBy.innerHTML = str;

    //Append all values to items
    newListItem.appendChild(accountName);
    newListItem.appendChild(date);
    newListItem.appendChild(amount);
    newListItem.appendChild(acceptedBy);
    
    //Only if urgent
    if(isUrgent) {
        let urgent = document.createElement("p");
        urgent.className = "account-brief";
        urgent.innerHTML = "Urgent";
        urgent.style.color = "red";
        urgent.style.fontWeight = "700";
        newListItem.appendChild(urgent);
    }

    newListItem.addEventListener('click', function (event) {
        console.log('you clicked ' + id);
        window.localStorage.setItem('form', id);
        window.location.href = homepage+"formoutline.html";
    });

    listParent.appendChild(newListItem);
}

async function getConfirmationRequests() {
    const res = await fetch(url+`form/confirmation/${getCookie("account").id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    res.json().then((data) => {
        console.log(data);
        for(let i = 0; i < data.length; i++) {
            if(data[i].directSupervisor) {
                createListItemWithApprovals(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, "Direct Supervisor Approval", data[i].urgent);
            } else if(data[i].directHead) {
                createListItemWithApprovals(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, "Direct Head Approval", data[i].urgent);
            } else if(data[i].benco) {
                createListItemWithApprovals(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, "Benco Approval", data[i].urgent);
            } else {
                createListItem(data[i].id, data[i].account.name, data[i].dateSubmitted, data[i].cost, data[i].urgent);
            }
        }
    }).catch((err) => {
        console.log(err);
        if(res.status == 400) {
        
        }
    });
}