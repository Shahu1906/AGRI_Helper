const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const config = require('../config/config');

// This defines the structure for a "User" document in the database.
const UserSchema = new mongoose.Schema({
  email: {
    type: String,
    required: [true, 'Email is a required field'],
    unique: true, // No two users can share an email
    lowercase: true,
    trim: true
  },
  password: {
    type: String,
    required: [true, 'Password is a required field']
  }
}, {
  // This automatically adds `createdAt` and `updatedAt` fields
  timestamps: true 
});

// This is a "pre-save hook" that runs automatically before a user is saved.
UserSchema.pre('save', async function(next) {
  // Only hash the password if it has been modified (or is new)
  if (!this.isModified('password')) {
    return next();
  }

  try {
    // Generate a salt and hash the password
    const salt = await bcrypt.genSalt(config.bcrypt.saltRounds);
    this.password = await bcrypt.hash(this.password, salt);
    next();
  } catch (error) {
    next(error);
  }
});

// Creates and exports the User model from the schema
module.exports = mongoose.model('User', UserSchema);