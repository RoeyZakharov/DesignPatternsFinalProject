package com.application.model;

import java.time.LocalDateTime;

public class TasksDAOProxy implements ITasksDAO {
    private static TasksDAOProxy instance;
    private final DerbyTasksDAO realDao;
    private ITask[] cache;
    private TasksDAOProxy() {
        this.realDao = DerbyTasksDAO.getInstance();
    }
    public static synchronized TasksDAOProxy getInstance() {
        if (instance == null) {
            instance = new TasksDAOProxy();
        }
        return instance;
    }
    @Override
    public ITask[] getTasks() throws TasksDAOException {
        if (cache == null) {
            cache = realDao.getTasks();
        }
        return cache;
    }
    @Override
    public ITask getTask(int id) throws TasksDAOException {
        return realDao.getTask(id);
    }
    @Override
    public void addTask(ITask task) throws TasksDAOException {
        realDao.addTask(task);
        cache = null;
    }
    @Override
    public void updateTask(ITask task) throws TasksDAOException {
        realDao.updateTask(task);
        cache = null;
    }
    @Override
    public void deleteTasks() throws TasksDAOException {
        realDao.deleteTasks();
        cache = null;
    }
    @Override
    public void deleteTask(int id) throws TasksDAOException {
        realDao.deleteTask(id);
        cache = null;
    }
    @Override
    public ITask[] getTasksCreatedAfter(LocalDateTime dateTime) throws TasksDAOException {
        return realDao.getTasksCreatedAfter(dateTime);
    }
    @Override
    public ITask[] getTasksCreatedBefore(LocalDateTime dateTime) throws TasksDAOException {
        return realDao.getTasksCreatedBefore(dateTime);
    }
}
