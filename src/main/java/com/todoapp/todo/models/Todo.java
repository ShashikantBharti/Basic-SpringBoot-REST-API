package com.todoapp.todo.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * Represents a Todo item in the application.
 *
 * This class is mapped to the "todos" collection in the MongoDB database
 * and serves as a data model for Todo items. Each Todo item has an
 * identifier, title, description, and a timestamp indicating when it
 * was created or updated.
 *
 * The class is annotated with @Data from Lombok, which automatically
 * generates getters, setters, equals, hashCode, and toString methods
 * for its fields.
 *
 * Attributes:
 * - todoId: Unique identifier for the Todo item (automatically generated).
 * - title: The title of the Todo item.
 * - description: A detailed description of the Todo item.
 * - dateTime: The date and time when the Todo item was created or last updated.
 */
@Data
@Document(collection = "todos")
public class Todo {
    @Id
    private ObjectId todoId;
    private String title;
    private String description;
    private LocalDateTime dateTime;
}
