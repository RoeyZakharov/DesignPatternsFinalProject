package com.application.model;

import com.application.visitor.TaskVisitor;

public interface ITask {
    int getId();

    String getTitle();

    String getDescription();

    TaskState getState();

    void accept(TaskVisitor visitor);
}
