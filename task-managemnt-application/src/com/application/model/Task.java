package com.application.model;

import com.application.visitor.TaskVisitor;

public record Task(int id, String title, String description, TaskState state) implements ITask {

    @Override
    public int getId() {
        return id;
    }
    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public TaskState getState() {
        return state;
    }
    @Override
    public void accept(TaskVisitor visitor) {
        visitor.visit(this);
    }
}
