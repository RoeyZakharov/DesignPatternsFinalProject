package com.application.model;

import java.time.LocalDateTime;

public interface ITasksDAO {
    ITask[] getTasks() throws TasksDAOException;
    ITask getTask(int id) throws TasksDAOException;
    void addTask(ITask task) throws TasksDAOException;;
    void updateTask(ITask task) throws TasksDAOException;
    void deleteTasks() throws TasksDAOException;
    void deleteTask(int id) throws TasksDAOException;
    public ITask[] getTasksCreatedBefore(LocalDateTime dateTime) throws TasksDAOException;
    public ITask[] getTasksCreatedAfter(LocalDateTime dateTime) throws TasksDAOException;
}
