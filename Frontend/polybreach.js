function run(){
    check().then(() => {
        document.getElementById("home").addEventListener("click", () => {
            document.getElementById("loader-home").style.display = "block";
            setTimeout(function() {
                window.location.href = "http://localhost:8080/home.html";
            }, 2500);
        });
    })
}

async function check(){
    const connectID = localStorage.getItem("connectID");
    console.log(connectID);
    const response = await fetch("http://localhost:8080/verifyconnectid", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ connectID })
    });
    if (response.status !== 200) {
        window.location.href = "http://localhost:8080/login.html";
    }
}

window.addEventListener("load", run);