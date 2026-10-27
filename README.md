# AI-Based Travel Itinerary Generator for Karnataka

## Overview

The **AI-Based Travel Itinerary Generator** is a full-stack web application designed to simplify travel planning by helping users explore tourist destinations and create personalized, day-wise itineraries across all 31 districts of Karnataka.

The application provides two itinerary planning modes: **Automatic Mode**, which uses intelligent algorithms to analyze user preferences and recommend suitable destinations, and **Manual Mode**, which allows users to create, edit, and customize their travel plans. The system also includes user authentication, destination management, feedback collection, and an administrator dashboard.

## Features

- **User Authentication:** Registration and login functionality with role-based access.
- **Preference Questionnaire:** Collects travel preferences such as mood, budget, duration, and interests.
- **Automatic Itinerary Planning:** Uses implemented algorithms to analyze responses and recommend suitable travel categories and destinations.
- **Manual Itinerary Planning:** Allows users to create, modify, and manage itineraries.
- **Karnataka Destination Coverage:** Supports tourist destinations across all 31 districts of Karnataka.
- **Destination Management:** Enables administrators to add, update, and delete destination information.
- **Feedback Management:** Allows users to submit feedback about destinations and itineraries.
- **Maps Integration:** Supports map-based destination visualization and route-related information where configured.
- **Gemini AI Integration:** Provides AI-assisted travel support where configured.

## Technologies Used

| Component | Technology |
|---|---|
| Frontend | React.js, HTML5, CSS3, JavaScript |
| Backend | Java, Spring Boot |
| API Communication | RESTful APIs |
| Database | MySQL |
| ORM | Spring Data JPA |
| Build Tools | Maven, Node.js, NPM |
| External Integrations | Maps API, Gemini AI API (where configured) |

## Implemented Algorithms

The project uses the following algorithms to support preference collection and personalized travel planning:

1. **Question Selection Algorithm (QSA):** Selects questions while avoiding repetition.
2. **Category Classification Algorithm (CCA):** Identifies the user's preferred travel category based on responses.
3. **Role-Based Travel Group Algorithm (RBTGA):** Adjusts travel category recommendations according to the selected group composition.
4. **Adaptive Response Algorithm (ARA):** Tracks user responses and supports adaptive questionnaire behavior.

These algorithms support the implemented preference-analysis workflow. Advanced route optimization and collaborative filtering should be considered separate features unless they have been implemented and tested in the application.

## System Architecture

The application follows a modular architecture consisting of:

- **Presentation Layer:** Provides the user interface using React.js.
- **Application Layer:** Handles business logic and REST API requests using Spring Boot.
- **Database Layer:** Stores user information, destinations, itineraries, and feedback using MySQL.
- **External Integration Layer:** Connects to mapping and AI assistance services when configured.

## Application Workflow

1. Users register or log in to the application.
2. Users explore districts and available tourist destinations.
3. Users provide travel preferences through the questionnaire.
4. Users choose automatic itinerary generation or manual planning.
5. The application processes the required information and displays the itinerary.
6. Users can save, edit, or manage their travel plans.
7. Administrators manage destination data and review feedback.

## Prerequisites

Install the following software before running the project:

- Java Development Kit (JDK)
- Node.js and NPM
- MySQL Server
- Maven
- Git

## Installation and Setup

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd <project-folder>
```

Replace the placeholders with your actual repository URL and project directory.

### 2. Configure the MySQL Database

Create a database in MySQL:

```sql
CREATE DATABASE travel_itinerary_db;
```

Configure the database URL, username, and password in the Spring Boot application's `application.properties` file.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/travel_itinerary_db
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Configure any additional JPA properties according to your backend implementation. Use environment variables for credentials rather than committing passwords to GitHub.

### 3. Run the Spring Boot Backend

Navigate to the backend directory containing `pom.xml`:

```bash
mvn spring-boot:run
```

The backend will start on the port configured in the Spring Boot application.

### 4. Run the React Frontend

Open another terminal and navigate to the frontend directory containing `package.json`:

```bash
npm install
npm run dev
```

Use the local URL displayed in the terminal to open the application in your browser. If your project uses a different frontend start script, follow the scripts defined in `package.json`.

## User Roles

**Traveler**
- Register and log in.
- Explore destinations and districts.
- Submit travel preferences.
- Create and customize itineraries.
- View saved plans and submit feedback.

**Administrator**
- Manage destination information.
- Maintain travel-related records.
- Review user feedback.
- Manage authorized administrative operations.

## Project Scope

The application focuses on travel planning across Karnataka. Its modular design provides a foundation for future improvements, including real-time weather and transport information, multilingual support, expanded destination coverage, and more advanced recommendation and route-optimization techniques.

## Future Enhancements

- Integration of real-time weather, traffic, and transport data.
- Expansion to destinations across India and other countries.
- Multilingual support, including Kannada and Hindi.
- Mobile application development.
- Enhanced recommendation models using larger datasets.
- Integration with hotel, transport, and ticket-booking services.

## Author

**Skanda Sharma P**  
National Institute of Engineering

## License

Add a suitable open-source license to this repository if you intend to distribute the project publicly. Otherwise, specify the terms under which the project may be used.
