# Agri Helper Backend

A Node.js backend application for the Agri Helper platform with JWT authentication and MongoDB integration.

## Features

- User registration and authentication
- JWT token-based authorization
- Password hashing with bcrypt
- MongoDB integration with Mongoose
- User profile management
- Environment-based configuration
- Comprehensive logging

## Environment Variables

All sensitive configuration is managed through environment variables. Create a `.env` file in the root directory with the following variables:

### Required Environment Variables

```env
# Environment Configuration
NODE_ENV=development                    # Environment: development, production, test

# Server Configuration
PORT=5000                              # Port number for the server

# JWT Configuration
JWT_SECRET=your_super_secret_jwt_key_here    # Secret key for JWT token signing
JWT_EXPIRES_IN=30d                     # JWT token expiration time (e.g., 1h, 7d, 30d)

# Password Hashing Configuration
BCRYPT_SALT_ROUNDS=12                  # Salt rounds for bcrypt hashing (10-15 recommended)

# Logging Configuration
LOG_LEVEL=info                         # Logging level: error, warn, info, debug

# MongoDB Configuration
MONGODB_URI=your_mongodb_connection_string_here    # MongoDB connection string
```

### Environment Variable Details

- **NODE_ENV**: Set to `production` for Render deployment
- **JWT_SECRET**: Use a strong, random secret key (minimum 32 characters)
- **JWT_EXPIRES_IN**: Token expiration time - `30d` prevents frequent "token expired" errors
- **BCRYPT_SALT_ROUNDS**: Higher values = more secure but slower (12 is recommended)
- **MONGODB_URI**: Your MongoDB Atlas connection string

## Installation

1. Clone the repository
2. Install dependencies:
   ```bash
   npm install
   ```
3. Copy `.env.example` to `.env` and fill in your values:
   ```bash
   cp .env.example .env
   ```
4. Start the development server:
   ```bash
   npm start
   ```

## API Endpoints

### Authentication
- `POST /api/users/register` - User registration
- `POST /api/users/login` - User login

### Profile (Protected)
- `GET /api/profile` - Get user profile
- `POST /api/profile` - Update user profile

## Deployment to Render

1. Push your code to GitHub
2. Connect your repository to Render
3. Set the following environment variables in Render dashboard:
   - `NODE_ENV=production`
   - `JWT_SECRET=your_production_jwt_secret`
   - `JWT_EXPIRES_IN=30d`
   - `BCRYPT_SALT_ROUNDS=12`
   - `LOG_LEVEL=info`
   - `MONGODB_URI=your_mongodb_atlas_uri`

4. Render will automatically install dependencies and start your application

## Security Notes

- Never commit `.env` files to version control
- Use strong, unique JWT secrets in production
- Regularly rotate your JWT secrets
- Use HTTPS in production
- Keep your MongoDB credentials secure

## Project Structure

```
backend/
├── config/
│   ├── config.js          # Centralized configuration
│   └── db.js              # Database connection
├── middleware/
│   └── authMiddleware.js  # JWT authentication middleware
├── models/
│   └── User.js            # User model
├── routes/
│   ├── userRoutes.js      # Authentication routes
│   └── profileRoutes.js   # Profile management routes
├── utils/
│   └── logger.js          # Logging utility
├── .env.example           # Environment variables template
├── package.json           # Dependencies and scripts
└── server.js              # Main application file
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the ISC License.
