package com.todoapp.todo.repositories;

import com.todoapp.todo.models.Todo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for accessing Todo entities in the MongoDB database.
 *
 * This interface extends the MongoRepository, providing CRUD operations
 * and additional query methods for the Todo entity. It allows for
 * easy interaction with the database without requiring boilerplate code.
 *
 * The repository is parameterized with the Todo entity type and
 * ObjectId as the identifier type.
 */
public interface TodoRepository extends MongoRepository<Todo, ObjectId> {
}
