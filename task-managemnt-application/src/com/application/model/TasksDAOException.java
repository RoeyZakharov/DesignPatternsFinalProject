package com.application.model;

public class TasksDAOException extends Exception {
    public TasksDAOException(String message) {
        super(message);
    }
    public TasksDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
