# Todo Application

A simple Todo application built with Spring Boot, MongoDB, and RESTful APIs. This application allows users to create, read, update, and delete Todo items.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Error Handling](#error-handling)
- [Contributing](#contributing)
- [License](#license)

## Features

- Create new Todo items
- Retrieve all Todo items or a specific Todo by ID
- Update existing Todo items
- Delete Todo items
- RESTful API design

## Technologies Used

- **Java**: Programming language used for the application.
- **Spring Boot**: Framework for building the application.
- **MongoDB**: NoSQL database for storing Todo items.
- **Maven**: Dependency management and build tool.
- **SLF4J**: Logging framework.

## Installation

To set up the project locally, follow these steps:

1. **Clone the repository**:
    ```bash
        git clone https://github.com/yourusername/todo-app.git
        cd todo-app 
   ```
2. **Install dependencies**:
   Make sure you have Maven installed. Run the following command to install the dependencies:
    ```bash
      mvn install 
   ```
3. **Set up MongoDB**:
   Ensure you have MongoDB running locally or configure the application to connect to a remote MongoDB instance. Update the `application.properties` file with your MongoDB connection details.

4. **Run the application**:
   You can run the application using:
    ```bash
      mvn spring-boot:run 
   ```

5. **Access the application**:
   The application will be available at `http://localhost:8080/api/todos`.

## Usage

You can interact with the API using tools like Postman or cURL. Below are examples of how to use each endpoint.

## API Endpoints

### 1. Get All Todos
    
```bash
  GET /api/todos    
```

Returns a list of all Todo items.

### 2. Get Todo by ID
```bash
   GET /api/todos/{todoId}
```

Returns a specific Todo item by its ID.

### 3. Create a New Todo
```bash
   POST /api/todos
```
Request Body:
```bash
  {
    "title": "Sample Todo",
    "description": "This is a sample description."
  }
```
Creates a new Todo item.

### 4. Update an Existing Todo
```bash
   PUT /api/todos/{todoId}
```
Request Body:
```bash
  {
    "title": "Updated Todo",
    "description": "This is an updated description."
  }
```
Updates an existing Todo item by its ID.

### 5. Delete a Todo by ID
```bash
   DELETE /api/todos/{todoId}
```
Deletes a specific Todo item by its ID.

## Error Handling

The API uses custom exceptions to handle errors gracefully. If a requested resource is not found, a `NotFoundException` will be thrown with an appropriate message.

## Contributing

Contributions are welcome! If you have suggestions for improvements or new features, please open an issue or submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
