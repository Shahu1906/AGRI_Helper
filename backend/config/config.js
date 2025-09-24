require('dotenv').config();

const config = {
  // Server Configuration
  port: process.env.PORT || 5000,
  
  // JWT Configuration
  jwt: {
    secret: process.env.JWT_SECRET || "my_super_secret_and_long_key_for_jwt",
    expiresIn: process.env.JWT_EXPIRES_IN || '30d'
  },
  
  // Password Hashing Configuration
  bcrypt: {
    saltRounds: parseInt(process.env.BCRYPT_SALT_ROUNDS) || 10
  },
  
  // Database Configuration
  database: {
    mongoUri: process.env.MONGODB_URI || "mongodb+srv://jadhavvshahu06_db_user:k15VBCtcHcrxZwf6@agrihelper.lcevhno.mongodb.net/?retryWrites=true&w=majority&appName=Agrihelper"
  },
  
  // Environment
  nodeEnv: process.env.NODE_ENV || 'development',
  
  // Logging Configuration
  logging: {
    level: process.env.LOG_LEVEL || 'info'
  }
};

module.exports = config;
