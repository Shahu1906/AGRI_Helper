const fs = require('fs');
const path = require('path');

// This creates a log file named 'user_monitor.log' in the root of your backend folder.
const logFilePath = path.join(__dirname, '..', 'user_monitor.log');

/**
 * Appends a message with a timestamp to the log file.
 * @param {string} message The message to log.
 */
const logUserEvent = (message) => {
    const timestamp = new Date().toISOString();
    const logMessage = `${timestamp} - ${message}\n`;

    // Asynchronously append the message to the file
    fs.appendFile(logFilePath, logMessage, (err) => {
        if (err) {
            // If logging fails, log an error to the console but don't crash the server.
            console.error('Failed to write to user monitor log file:', err);
        }
    });
};

module.exports = logUserEvent;
