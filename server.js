const net = require('net');

// Function to create a location URL from latitude and longitude
function createLocation(data) {
    const [lat, lon] = data.split("@");
    const loc = `https://www.openstreetmap.org?mlat=${lat}&mlon=${lon}`;
    console.log(`\nLocation: ${loc}\n`);
}

// Function to start the socket server
function socketServer(host = '0.0.0.0', port = 8080) {
    const server = net.createServer((clientSocket) => {
        console.log(`\n[+] Client connected: ${clientSocket.remoteAddress}:${clientSocket.remotePort}`);

        clientSocket.on('data', (data) => {
            const message = data.toString();
            createLocation(message);
            console.log(`\n[+] Message From Client --> ${message}`);

            const sendToClient = "This message from server";
            clientSocket.write(sendToClient);
        });

        clientSocket.on('error', (err) => {
            console.error(`\n[!] ERROR: ${err.message}\n`);
        });

        clientSocket.on('end', () => {
            console.log(`\n[+] Client disconnected: ${clientSocket.remoteAddress}:${clientSocket.remotePort}`);
        });
    });

    server.listen(port, host, () => {
        console.log(`\n[+] Server Listening On ${host}:${port}\n`);
    });

    server.on('error', (err) => {
        console.error(`\n[!] Server Error: ${err.message}\n`);
    });
}

// Start the server
if (require.main === module) {
    socketServer();
}
