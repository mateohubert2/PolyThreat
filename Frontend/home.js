function run() {
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
}
window.addEventListener("load", run);