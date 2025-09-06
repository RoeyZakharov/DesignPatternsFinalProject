package com.application.viewmodel.commands;

import com.application.model.TasksDAOException;

public interface Command {
    void execute() throws TasksDAOException;
    void undo() throws TasksDAOException;
}
