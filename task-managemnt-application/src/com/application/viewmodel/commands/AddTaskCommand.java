package com.application.viewmodel.commands;

import com.application.model.ITask;
import com.application.model.ITasksDAO;
import com.application.model.TasksDAOException;

public class AddTaskCommand implements Command{

    private final ITasksDAO dao;
    private final ITask task;

    public AddTaskCommand(ITasksDAO dao, ITask task) {
        this.dao = dao;
        this.task = task;
    }

    @Override
    public void execute() throws TasksDAOException {
        dao.addTask(task);
    }

    @Override
    public void undo() throws TasksDAOException {
        dao.deleteTask(task.getId());
    }

}
