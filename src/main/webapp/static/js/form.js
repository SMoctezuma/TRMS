
getForm();

if(document.cookie !== null) {
	let account = getCookie("account");
    if(account != null) {
        if(account.roleId.name == "Employee") {
            hideFormButtons();
        } else if(account.roleId.name == "BenCo") {
            document.getElementById("bencoOver").style.display = "block";
            document.getElementById("bencoInput").style.display = "block";
        } else {

        }
    }
}
function hideFormButtons() {
    document.getElementById("amountAwardedDiv").style.display = "none";
    document.getElementById("rDiv").style.display = "none";
}
function showgDiv() {
    document.getElementById("gDiv").style.display = "block";
}
async function getForm() {
    let account = getCookie("account");
    let formId = localStorage.getItem('form');
    const res = await fetch(url+`form/f/${formId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    res.json().then((data) => {
        console.log(data);
        if(data.status.name != "Pending") {
            hideFormButtons();
            if(data.status.name == "Accepted" && !data.confirmed && account.roleId.name == "Employee") {
                showgDiv();
            }
        }
        createListWithHeading("Name:",data.account.name);
        createListWithHeading("Status:", data.status.name);
        createListWithHeading("Event:", data.event.name);
        createListWithHeading("Time:", data.time);
        createListWithHeading("Cost:", "$"+data.cost);
        createListWithHeading("Location:", data.location);
        createListWithHeading("Date of Event:", parseDate(data.dateOfEvent));
        createListWithHeading("Date Submitted:", parseDate(data.dateSubmitted));
        createListWithHeading("Description:", data.description);
        createListWithHeading("Work related reason:", data.workRelatedReason);
        createListWithHeading("Grading Format:", data.gradingFormat.format);
        createListWithHeading("Grade/Presentation:", data.gradeOrPresentation);
        createListWithHeading("Urgent:", data.urgent);
        if(data.approvalByEmail)
            createListWithHeading("Aprroval by email:", data.approvalByEmail.email);
        if(data.directSupervisor)
            createListWithHeading("Direct Supervisor:", data.directSupervisor.approvalBy.email);
        if(data.directHead)
            createListWithHeading("Direct Head:", data.directHead.approvalBy.email);
        if(data.benco)
            createListWithHeading("BenCo:", data.benco.approvalBy.email);
        createListWithHeading("Confirmed:", data.confirmed);
        createListWithHeading("Withdrawn:", data.widthdrawn);
    }).catch((err) => {
        console.log(err);
        if(res.status == 400) {
        }
    });
}

async function formAccept() {
    let aw = document.getElementById("amountAwarded");
    if(aw == null) {
        aw = "";
    } else {
        aw = document.getElementById("amountAwarded").value;
    }
    let account = getCookie("account");
    let formId = localStorage.getItem('form');
    const data = {
        form: formId,
        method: "accepted",
        acc: account.id,
        amountAwarded: aw,
    }
    const res = await fetch(url+`form/m/`, {
        method: 'PUT',
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
    });
}

async function formReject() {
    //info cannot be empty.
    if(document.getElementById("info") == null || document.getElementById("info").value == "") {
        document.getElementById("error").style.display = "block";
        return;
    }
    let account = getCookie("account");
    let formId = localStorage.getItem('form');
    const data = {
        form: formId,
        method: "rejected",
        acc: account.id,
        reason: document.getElementById("info").value
    }
    const res = await fetch(url+`form/m/`, {
        method: 'PUT',
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
    });
}

async function requestMoreInfo() {
    //info cannot be empty.
    if(document.getElementById("info") == null || document.getElementById("info").value == "") {
        document.getElementById("error").style.display = "block";
        return;
    }
    let account = getCookie("account");
    let formId = localStorage.getItem('form');
    const data = {
        form: formId,
        method: "more",
        acc: account.id,
        reason: document.getElementById("info").value,
        requestInfoFrom: ""
    }
    const res = await fetch(url+`form/m/`, {
        method: 'PUT',
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
    });

}

async function fromUploadGrade() {
    if(document.getElementById("grade") == null || document.getElementById("grade").value == "") {
        return;
    }
    let answer = document.getElementById("grade").value;

    let account = getCookie("account");
    let formId = localStorage.getItem('form');
    const data = {
        form: formId,
        grade: answer
    }
    const res = await fetch(url+`form/update/`, {
        method: 'PUT',
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
    });

}