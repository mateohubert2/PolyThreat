function run() {
    document.getElementById("home").addEventListener("click", () => {
        document.getElementById("loader-home").style.display = "block";
        setTimeout(function() {
            window.location.href = "http://localhost:8080/home.html";
        }, 2500);
    });

    const terminalDiv = document.getElementById("terminal");
    const outputDiv = document.getElementById("output");
    const commandInput = document.getElementById("command-input");
    let awaitingPassword = false;

    function appendOutput(text) {
        outputDiv.innerHTML += `<pre>${text}</pre>`;
        terminalDiv.scrollTop = terminalDiv.scrollHeight;
    }

    function focusInput() {
        commandInput.focus();
    }

    function sendCommand(command, input = null) {
        if (command.startsWith("sudo")) {
            appendOutput("Error: 'sudo' commands are not supported.");
            commandInput.disabled = false;
            focusInput();
            return;
        }

        fetch("http://localhost:8080/execute", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ command: command, input: input })
        })
        .then(response => response.text())
        .then(result => {
            appendOutput(result);
            commandInput.disabled = false;
            focusInput();
            awaitingPassword = false;
        })
        .catch(error => {
            console.error("Error:", error);
            appendOutput("Error: ${error.message}");
            commandInput.disabled = false;
            focusInput();
            awaitingPassword = false;
        });
    }

    commandInput.addEventListener("keypress", function(e) {
        if (e.key === "Enter" && !awaitingPassword) {
            const command = e.target.value.trim();
            e.target.value = "";
            appendOutput("$ ${command}");
            commandInput.disabled = true;

            sendCommand(command);
        }
    });

    focusInput();
}

window.addEventListener("load", run);