package com.application.viewmodel.commands;

import com.application.model.ITask;
import com.application.model.ITasksDAO;
import com.application.model.TasksDAOException;

public class UpdateTaskCommand implements Command{
    private final ITasksDAO dao;
    private final ITask newTask;
    private ITask oldTask;

    public UpdateTaskCommand(ITasksDAO dao, ITask newTask) {
        this.dao = dao;
        this.newTask = newTask;
    }

    @Override
    public void execute() throws TasksDAOException {
        oldTask = dao.getTask(newTask.getId());
        dao.updateTask(newTask);
    }

    @Override
    public void undo() throws TasksDAOException {
        if (oldTask != null) {
            dao.updateTask(oldTask);
        }
    }
}
