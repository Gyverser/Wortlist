# Wortlist - Vocabulary Learning Application

[![Java](https://img.shields.io/badge/Java-25-blue.svg)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-green.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Wortlist is a web-based vocabulary learning application built with Spring Boot, designed to help users learn and practice new words through interactive quizzes and learning modules.

## Features

- **Word Management**: Create, read, update, and delete vocabulary words
- **Learning Module**: Interactive learning interface for practicing vocabulary
- **Quiz System**: Test your knowledge with random word quizzes
- **Leaderboard**: Track and display high scores
- **User Authentication**: Secure login system
- **Responsive Design**: Works on desktop and mobile devices

## Technologies

- **Backend**: Spring Boot 4.0.1 with Java 25
- **Frontend**: Thymeleaf templates with HTML/CSS/JavaScript
- **Database**: MySQL with JPA/Hibernate
- **Security**: Spring Security
- **Build Tool**: Maven

## Getting Started

### Prerequisites

- Java 25 or higher
- Maven 3.8+
- MySQL 8.0+
- Node.js (for frontend development, optional)

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/wortlist.git
   cd wortlist
   ```

2. **Set up the database**:
   - Create a MySQL database named `Vocab`
   - Update the database credentials in `.env` file if needed

3. **Install dependencies**:
   ```bash
   mvn clean install
   ```

4. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**:
   - Open your browser and navigate to `http://localhost:8080`
   - Login with the default admin credentials (username: `admin`, password: `admin`)

## Configuration

The application uses environment variables for configuration. Create a `.env` file in the root directory with the following variables:

```env
DB_URL=jdbc:mysql://localhost:3306/Vocab
DB_USERNAME=root
DB_PASSWORD=yourpassword

SECURITY_USER_NAME=admin
SECURITY_USER_PASSWORD=$2a$10$KOhp.tPadHm0GgMerGfaqujVMDLPKjiO5m0WeQfnVK/Q1mBG7S2Gm
SECURITY_USER_ROLES=ADMIN
```

> **Note**: The password is a bcrypt hash. You can generate your own using any bcrypt generator.

## Project Structure

```
src/
├── main/
│   ├── java/com/wortlist/wortlist/
│   │   ├── common/              # DTOs and exceptions
│   │   ├── config/              # Configuration classes
│   │   ├── controller/          # Web controllers
│   │   ├── entity/              # JPA entities
│   │   ├── repository/          # Spring Data repositories
│   │   ├── service/             # Business logic services
│   │   └── WortlistApplication.java
│   └── resources/
│       ├── static/              # Static assets (CSS, JS)
│       ├── templates/           # Thymeleaf templates
│       └── application.properties
└── test/                        # Unit and integration tests
```

## API Endpoints

### Words API
- `GET /api/words` - Get all words (paginated)
- `GET /api/words/{id}` - Get word by ID
- `POST /api/words` - Create new word
- `PUT /api/words/{id}` - Update word
- `PATCH /api/words/{id}` - Partial update word
- `DELETE /api/words/{id}` - Delete word

### Quiz API
- `GET /api/quiz/question` - Get random quiz question
- `GET /api/quiz/leaderboard` - Get leaderboard
- `POST /api/quiz/leaderboard` - Submit score to leaderboard

### Learn API
- `GET /api/learn/word` - Get random word for learning

## Web Pages

- `/` - Home page
- `/words` - Word management interface
- `/learn` - Learning module
- `/quiz` - Quiz interface
- `/leaderboard` - High scores display
- `/login` - Login page

## Contributing

Please read our [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Code of Conduct

This project adheres to the Contributor Covenant code of conduct. By participating, you are expected to uphold this code. Please report unacceptable behavior to [your email].

## Roadmap

- [ ] Add user registration system
- [ ] Implement word categories/tags
- [ ] Add progress tracking for users
- [ ] Mobile app version
- [ ] Multi-language support

## Support

For issues, questions, or suggestions, please open an issue on GitHub or contact the maintainers.

## Acknowledgments

- Spring Boot team for the excellent framework
- Thymeleaf team for the templating engine
- All contributors and users of Wortlist