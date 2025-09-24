const express = require('express');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const config = require('../config/config');
const User = require('../models/User');
const logUserEvent = require('../utils/logger'); // <-- Logger import

const router = express.Router();

// --- SIGNUP (REGISTER) ENDPOINT ---
router.post('/register', async (req, res) => {
  try {
    const { email, password, confirmPassword } = req.body;

    if (!email || !password || !confirmPassword) {
      return res.status(400).json({ message: 'Please enter all fields.' });
    }
    if (password.length < 6) {
      return res.status(400).json({ message: 'Password needs to be at least 6 characters.' });
    }
    if (password !== confirmPassword) {
      return res.status(400).json({ message: 'Passwords do not match.' });
    }

    const existingUser = await User.findOne({ email });
    if (existingUser) {
      return res.status(400).json({ message: 'An account with this email already exists.' });
    }

    const newUser = new User({ email, password });
    await newUser.save();

    // --- LOGGING STEP ---
    logUserEvent(`New user registered successfully: ${email}`);
    // ---

    res.status(201).json({ message: 'User registered successfully! You can now log in.' });

  } catch (error) {
    console.error("Registration Error:", error);
    res.status(500).json({ message: 'A server error occurred during registration.' });
  }
});


// --- LOGIN ENDPOINT ---
router.post('/login', async (req, res) => {
    try {
        const { email, password } = req.body;

        if (!email || !password) {
            return res.status(400).json({ message: 'Please provide both email and password.' });
        }

        const user = await User.findOne({ email });
        if (!user) {
            return res.status(401).json({ message: 'Invalid email or password.' });
        }

        const isMatch = await bcrypt.compare(password, user.password);
        if (!isMatch) {
            return res.status(401).json({ message: 'Invalid email or password.' });
        }

        const payload = {
          user: {
            id: user._id
          }
        };

        const token = jwt.sign(
            payload,
            config.jwt.secret,
            { expiresIn: config.jwt.expiresIn }
        );

        res.status(200).json({
            message: 'Login successful!',
            token: token,
            user: {
                id: user._id,
                email: user.email
            }
        });

    } catch (error) {
        console.error("Login Error:", error);
        res.status(500).json({ message: 'A server error occurred during login.' });
    }
});

module.exports = router;