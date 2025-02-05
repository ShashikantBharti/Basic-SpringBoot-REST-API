package com.todoapp.todo.controllers;

import com.todoapp.todo.exceptions.NotFoundException;
import com.todoapp.todo.models.Todo;
import com.todoapp.todo.services.TodoService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Todo items.
 * This controller provides endpoints for creating, retrieving, updating,
 * and deleting Todo items. It uses the TodoService to perform the
 * necessary business logic and data access operations.
 */
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * Retrieves all Todo items.
     *
     * @return A ResponseEntity containing a list of all Todo items and an HTTP status.
     */
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        logger.info("Fetching all Todo items");
        List<Todo> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    /**
     * Retrieves a Todo item by its ID.
     *
     * @param todoId The unique identifier of the Todo item to retrieve.
     * @return A ResponseEntity containing the requested Todo item and an HTTP status.
     * @throws NotFoundException if no Todo item with the specified ID exists.
     */
    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getTodoById(@PathVariable ObjectId todoId) {
        logger.info("Fetching Todo item with ID: {}", todoId);
        Todo todo = todoService.getTodoById(todoId)
                .orElseThrow(() -> new NotFoundException("Todo item not found with ID: " + todoId));
        return ResponseEntity.ok(todo);
    }

    /**
     * Creates a new Todo item.
     *
     * @param todo The Todo object to create.
     * @return A ResponseEntity containing the created Todo item and an HTTP status.
     */
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        logger.info("Creating a new Todo item");
        Todo createdTodo = todoService.createTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    /**
     * Updates an existing Todo item by its ID.
     *
     * @param todoId The unique identifier of the Todo item to update.
     * @param todo The updated Todo object.
     * @return A ResponseEntity containing the updated Todo item and an HTTP status.
     * @throws NotFoundException if no Todo item with the specified ID exists.
     */
    @PutMapping("/{todoId}")
    public ResponseEntity<Todo> updateTodo(@PathVariable ObjectId todoId, @RequestBody Todo todo) {
        logger.info("Updating Todo item with ID: {}", todoId);
        todo.setTodoId(todoId); // Ensure the ID in the request matches the path variable
        Todo updatedTodo = todoService.updateTodo(todo, todoId);
        return ResponseEntity.ok(updatedTodo);
    }

    /**
     * Deletes a Todo item by its ID.
     *
     * @param todoId The unique identifier of the Todo item to delete.
     * @return A ResponseEntity with an HTTP status indicating success or failure.
     * @throws NotFoundException if no Todo item with the specified ID exists.
     */
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable ObjectId todoId) {
        logger.info("Deleting Todo item with ID: {}", todoId);
        todoService.deleteTodoById(todoId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
