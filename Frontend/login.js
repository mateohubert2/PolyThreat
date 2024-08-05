function run() {
    document.getElementById("loginForm").addEventListener("submit", async (event) => {
        event.preventDefault();
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        const response = await fetch("http://localhost:8080/verifylogs", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, password })
        });

        if (response.status === 200) {
            const result = await response.json();
            localStorage.setItem("connectID", result);
            window.location.href = "http://localhost:8080/home.html";
        } else {
            document.getElementById("error-message").innerText = "Username or password incorrect";
        }
    });
}
window.addEventListener("load", run);