package com.todoapp.todo.dtos;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Todo DTO (Data Transfer Object)
 *
 * TodoDto is used to safely share data between request and response
 * to client without leaking the information of data base because
 * Todo has database information also.
 */
@Data
public class TodoDto {
    private String id;
    private String title;
    private String description;
    private LocalDateTime dateTime;
}
