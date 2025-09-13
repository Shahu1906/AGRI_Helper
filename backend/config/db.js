const mongoose = require('mongoose');

const connectDB = async () => {
  try {
    // IMPORTANT: Paste your MongoDB Atlas connection string here.
    // Replace <username>, <password>, and myAppDB with your actual credentials and database name.
    const MONGODB_URI = "mongodb+srv://jadhavvshahu06_db_user:k15VBCtcHcrxZwf6@agrihelper.lcevhno.mongodb.net/?retryWrites=true&w=majority&appName=Agrihelper";

    await mongoose.connect(MONGODB_URI);
    console.log('SUCCESS: MongoDB is connected.');
  } catch (err) {
    console.error("ERROR: Could not connect to MongoDB.", err.message);
    // Exit the application with a failure code if the database connection fails.
    process.exit(1);
  }
};

module.exports = connectDB;