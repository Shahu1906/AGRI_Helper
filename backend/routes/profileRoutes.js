const express = require('express');
const router = express.Router();
const { protect } = require('../middleware/authMiddleware');
const mongoose = require('mongoose');
const logUserEvent = require('../utils/logger'); // <-- Logger import

// Profile Mongoose Model
const ProfileSchema = new mongoose.Schema({
    user: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true, unique: true },
    name: { type: String, default: '' },
    location: { type: String, default: '' },
    contactNumber: { type: String, default: '' },
    gender: { type: String, default: '' },
    farmName: { type: String, default: '' },
    farmSize: { type: String, default: '' },
    primaryCrops: { type: String, default: '' },
    soilType: { type: String, default: '' }
}, { timestamps: true });

const Profile = mongoose.model('Profile', ProfileSchema);


// --- GET USER PROFILE ---
router.get('/', protect, async (req, res) => {
    try {
        let profile = await Profile.findOne({ user: req.user.id }).populate('user', ['email']);
        if (!profile) {
            profile = new Profile({ user: req.user.id });
            await profile.save();
            profile = await Profile.findOne({ user: req.user.id }).populate('user', ['email']);
        }
        const profileObject = profile.toObject();
        profileObject.email = profile.user.email;
        delete profileObject.user;
        res.json(profileObject);
    } catch (err) {
        console.error(err.message);
        res.status(500).send('Server Error');
    }
});


// --- UPDATE USER PROFILE ---
router.post('/', protect, async (req, res) => {
    const { name, location, contactNumber, gender, farmName, farmSize, primaryCrops, soilType } = req.body;

    const profileFields = { user: req.user.id, name, location, contactNumber, gender, farmName, farmSize, primaryCrops, soilType };

    try {
        let profile = await Profile.findOneAndUpdate(
            { user: req.user.id },
            { $set: profileFields },
            { new: true, upsert: true }
        ).populate('user', ['email']);

        // --- LOGGING STEP ---
        logUserEvent(`Profile updated for user: ${profile.user.email} (ID: ${req.user.id})`);
        // ---

        const profileObject = profile.toObject();
        profileObject.email = profile.user.email;
        delete profileObject.user;

        res.json(profileObject);
    } catch (err) {
        console.error(err.message);
        res.status(500).send('Server Error');
    }
});

module.exports = router;