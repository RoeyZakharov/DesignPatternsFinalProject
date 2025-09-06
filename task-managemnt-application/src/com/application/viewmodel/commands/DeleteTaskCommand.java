package com.application.viewmodel.commands;

import com.application.model.ITask;
import com.application.model.ITasksDAO;
import com.application.model.TasksDAOException;

public class DeleteTaskCommand implements Command{
    private final ITasksDAO dao;
    private final int taskId;
    private ITask deletedTask; // נשמרה המשימה שנמחקה

    public DeleteTaskCommand(ITasksDAO dao, int taskId) {
        this.dao = dao;
        this.taskId = taskId;
    }

    @Override
    public void execute() throws TasksDAOException {
        deletedTask = dao.getTask(taskId);
        dao.deleteTask(taskId);
    }

    @Override
    public void undo() throws TasksDAOException {
        if (deletedTask != null) {
            dao.addTask(deletedTask); // מחזירים משימה שנמחקה
        }
    }
}
