package com.todoapp.todo.services;

import com.todoapp.todo.dtos.TodoDto;
import com.todoapp.todo.exceptions.NotFoundException;
import com.todoapp.todo.models.Todo;
import com.todoapp.todo.repositories.TodoRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Todo items.
 */
@Service
public class TodoService {

    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);
    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    public TodoService(TodoRepository todoRepository, ModelMapper modelMapper) {
        this.todoRepository = todoRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves all Todo items from the repository.
     *
     * This method fetches all Todo objects stored in the MongoDB database
     * using the Todo repository. If no Todo items are found, an empty list
     * is returned. The returned list is unmodifiable to prevent external
     * modifications.
     *
     * @return An unmodifiable list of all Todo items. If no items exist,
     *         an empty list is returned.
     */
    public List<TodoDto> getAllTodos() {
        logger.info("Fetching all Todo items from the repository");

        List<Todo> todos = todoRepository.findAll();
        List<TodoDto> todoDtos = todos.stream().map(todo -> modelMapper.map(todo, TodoDto.class)).toList();
        logger.info("Retrieved {} Todo items", todos.size());

        return todoDtos;
    }

    /**
     * Retrieves a Todo item by its unique identifier.
     *
     * This method fetches a Todo object associated with the specified ObjectId
     * from the repository. If the Todo item is found, it is returned wrapped in
     * an Optional; otherwise, a custom NotFoundException is thrown.
     *
     * @param todoId The unique identifier of the Todo item to retrieve.
     * @return An Optional containing the Todo item if found.
     * @throws NotFoundException if no Todo item with the specified ID exists.
     */
    public Optional<TodoDto> getTodoById(ObjectId todoId) {
        logger.info("Fetching Todo item with ID: {}", todoId);
        Optional<Todo> todo = todoRepository.findById(todoId)
                .or(() -> {
                    logger.warn("Todo item with ID {} not found", todoId);
                    throw new NotFoundException("Todo item not found with ID: " + todoId);
                });
        return Optional.ofNullable(modelMapper.map(todo, TodoDto.class));
    }


    /**
     * Creates a new Todo item in the repository.
     *
     * This method takes a Todo object, sets its creation timestamp to the
     * current date and time, and then saves it to the MongoDB database
     * using the Todo repository. The saved Todo object, which includes
     * any generated fields (like ID), is returned.
     *
     * @param todoDto The Todo object to be created. It should not be null
     *             and must contain the necessary fields (title, description).
     * @return The saved Todo object, including its generated ID and timestamp.
     * @throws IllegalArgumentException if the provided Todo object is null
     *                                  or does not contain required fields.
     */
    public TodoDto createTodo(TodoDto todoDto) {
        logger.info("Attempting to create a new Todo item");

        if (todoDto == null) {
            logger.error("Failed to create Todo: Todo cannot be null");
            throw new IllegalArgumentException("Todo cannot be null");
        }

        if (todoDto.getTitle() == null || todoDto.getDescription() == null) {
            logger.error("Failed to create Todo: Title and description cannot be null");
            throw new IllegalArgumentException("Todo title and description cannot be null");
        }

        Todo todo = modelMapper.map(todoDto, Todo.class);

        todo.setDateTime(LocalDateTime.now());
        Todo savedTodo = todoRepository.save(todo);
        logger.info("Successfully created Todo item with ID: {}", savedTodo.getTodoId());

        return modelMapper.map(savedTodo, TodoDto.class);
    }

    /**
     * Updates an existing Todo item in the repository.
     *
     * This method retrieves a Todo item by its unique identifier, updates
     * its fields with the provided values, and saves it back to the
     * MongoDB database. If the Todo item does not exist, a
     * NotFoundException is thrown.
     *
     * @param todoDto The Todo object containing updated values. It should not be null.
     * @param id The unique identifier of the Todo item to update.
     * @return The updated Todo object after saving it to the repository.
     * @throws NotFoundException if no Todo item with the specified ID exists.
     * @throws IllegalArgumentException if the provided Todo object is null.
     */
    public TodoDto updateTodo(TodoDto todoDto, String id) {
        if (todoDto == null) {
            throw new IllegalArgumentException("Todo cannot be null");
        }

        ObjectId todoId = new ObjectId(id);

        logger.info("Updating Todo item with ID: {}", todoId);

        // Fetch existing Todo item
        Optional<Todo> existingTodoOptional = todoRepository.findById(todoId);
        if (existingTodoOptional.isEmpty()) {
            logger.warn("Todo item with ID {} not found", todoId);
            throw new NotFoundException("Todo item not found with ID: " + todoId);
        }

        // Convert TodoDto to Todo
        Todo todo = modelMapper.map(todoDto, Todo.class);

        // Update fields
        Todo existingTodo = existingTodoOptional.get();
        existingTodo.setTitle(todo.getTitle() != null && !todo.getTitle().isEmpty() ? todo.getTitle() : existingTodo.getTitle());
        existingTodo.setDescription(todo.getDescription() != null && !todo.getDescription().isEmpty() ? todo.getDescription() : existingTodo.getDescription());
        existingTodo.setDateTime(LocalDateTime.now()); // Optionally update timestamp

        // Save updated Todo
        Todo updatedTodo = todoRepository.save(existingTodo);
        logger.info("Successfully updated Todo item with ID: {}", updatedTodo.getTodoId());

        return modelMapper.map(updatedTodo, TodoDto.class);
    }


    /**
     * Deletes a Todo item by its unique identifier.
     *
     * This method checks if a Todo item with the specified ID exists in
     * the repository. If it exists, it deletes the item; otherwise, it
     * throws a NotFoundException.
     *
     * @param id The unique identifier of the Todo item to delete.
     * @throws NotFoundException if no Todo item with the specified ID exists.
     */
    public void deleteTodoById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Todo ID cannot be null");
        }

        logger.info("Attempting to delete Todo item with ID: {}", id);

        ObjectId todoId = new ObjectId(id);
        // Check if the Todo item exists
        Optional<Todo> existingTodoOptional = todoRepository.findById(todoId);
        if (existingTodoOptional.isEmpty()) {
            logger.warn("Todo item with ID {} not found", todoId);
            throw new NotFoundException("Todo item not found with ID: " + todoId);
        }

        // Perform deletion
        todoRepository.deleteById(todoId);
        logger.info("Successfully deleted Todo item with ID: {}", todoId);
    }


}
