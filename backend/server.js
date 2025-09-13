const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const connectDB = require('./config/db');

// Import all the route handlers
const userRoutes = require('./routes/userRoutes');
const profileRoutes = require('./routes/profileRoutes');

// Initialize the Express application
const app = express();

// --- Middleware ---
// Enables Cross-Origin Resource Sharing for your app
app.use(cors());
// Parses incoming request bodies into JSON
app.use(bodyParser.json());

// --- Database Connection ---
connectDB();

// --- API Routes ---
// Use the userRoutes for any URL that starts with '/api/users'
app.use('/api/users', userRoutes);
// Use the profileRoutes for any URL that starts with '/api/profile'
app.use('/api/profile', profileRoutes);

// Define the port for the server to run on
const PORT = process.env.PORT || 5000;

// Start the server
app.listen(PORT, () => {
  console.log(`Server is up and running on http://localhost:${PORT}`);
});

