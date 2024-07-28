function run() {
    document.getElementById("home").addEventListener("click", () => {
        document.getElementById("loader-home").style.display = "block";
        setTimeout(function() {
            window.location.href = "http://localhost:8080/home.html";
        }, 2500);
    });

    document.getElementById('command-input').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            const command = e.target.value;
            e.target.value = '';
            fetch('http://localhost:8080/execute', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ command: command })
            })
            .then(response => response.text())
            .then(result => {
                const outputDiv = document.getElementById('output');
                outputDiv.innerHTML += `<div><span>$</span> ${command}</div><pre>${result}</pre>`;
                const terminalDiv = document.getElementById('terminal');
                terminalDiv.scrollTop = terminalDiv.scrollHeight;
            })
            .catch(error => {
                console.error('Error:', error);
            });
        }
    });
    
}
window.addEventListener("load", run);