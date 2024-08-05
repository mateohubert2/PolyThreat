function run(){
    check().then(() => {
        document.getElementById("polybreach").addEventListener("click", () => {
            document.getElementById("loader-polybreach").style.display = "block";
            setTimeout(function() {
                window.location.href = "http://localhost:8080/polybreach.html";
            }, 2500);
        });
    
        document.getElementById("polynetdefender").addEventListener("click", () => {
            document.getElementById("loader-polynetdefender").style.display = "block";
            setTimeout(function() {
                window.location.href = "http://localhost:8080/polynetdefender.html";
            }, 2500);
        });
    });
}

async function check(){
    const connectID = localStorage.getItem("connectID");
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