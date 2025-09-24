const mongoose = require('mongoose');
const config = require('./config');

const connectDB = async () => {
  try {
    // Use configuration for MongoDB connection string
    await mongoose.connect(config.database.mongoUri);
    console.log('SUCCESS: MongoDB is connected.');
  } catch (err) {
    console.error("ERROR: Could not connect to MongoDB.", err.message);
    // Exit the application with a failure code if the database connection fails.
    process.exit(1);
  }
};

module.exports = connectDB;