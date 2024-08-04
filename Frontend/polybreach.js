function run(){
    document.getElementById("home").addEventListener("click", () => {
        document.getElementById("loader-home").style.display = "block";
        setTimeout(function() {
            window.location.href = "http://localhost:8080/home.html";
        }, 2500);
    });
}
window.addEventListener("load", run);