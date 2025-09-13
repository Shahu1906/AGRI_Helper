const jwt = require('jsonwebtoken');

// This function acts as middleware to protect routes.
const protect = (req, res, next) => {
    let token;

    // Check if the 'Authorization' header exists and starts with 'Bearer'
    if (req.headers.authorization && req.headers.authorization.startsWith('Bearer')) {
        try {
            // Get token from the header (e.g., "Bearer eyJhbGci...")
            token = req.headers.authorization.split(' ')[1];

            // Verify the token using your secret key
            const JWT_SECRET = "my_super_secret_and_long_key_for_jwt";
            const decoded = jwt.verify(token, JWT_SECRET);
            
            // Attach the user's ID to the request object for later use
            req.user = decoded.user;

            // Move to the next function (the actual route handler)
            next();
        } catch (error) {
            console.error('Token verification failed:', error);
            res.status(401).json({ message: 'Not authorized, token failed' });
        }
    }

    if (!token) {
        res.status(401).json({ message: 'Not authorized, no token' });
    }
};

module.exports = { protect };
