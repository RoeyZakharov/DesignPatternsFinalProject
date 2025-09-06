package com.application.viewmodel;

import com.application.model.*;
import com.application.viewmodel.commands.*;
import com.application.viewmodel.filters.TaskFilter;

import java.util.*;

public class TasksViewModel extends Observable {
    private final ITasksDAO dao;
    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();
    public TasksViewModel(ITasksDAO dao) {
        this.dao = dao;
    }
    public void addTask(ITask task) throws TasksDAOException {
        Command cmd = new AddTaskCommand(dao, task);
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
        setChanged();
        notifyObservers();
    }
    public void updateTask(ITask task) throws TasksDAOException {
        Command cmd = new UpdateTaskCommand(dao, task);
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
        setChanged();
        notifyObservers();
    }
    public void deleteTask(int id) throws TasksDAOException {
        Command cmd = new DeleteTaskCommand(dao, id);
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
        setChanged();
        notifyObservers();
    }
    public ITask[] getAllTasks() throws TasksDAOException {
        return dao.getTasks();
    }

    public ITask[] getFilteredTasks(TaskFilter filter) throws TasksDAOException {
        return Arrays.stream(dao.getTasks())
                .filter(filter::test)
                .toArray(ITask[]::new);
    }
    public void undo() throws TasksDAOException {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
            setChanged();
            notifyObservers();
        }
    }

    public void redo() throws TasksDAOException {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
            setChanged();
            notifyObservers();
        }
    }
}
