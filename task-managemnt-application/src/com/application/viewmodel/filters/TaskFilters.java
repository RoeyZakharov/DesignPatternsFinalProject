package com.application.viewmodel.filters;

import com.application.model.TaskState;
public class TaskFilters {

    public static TaskFilter byId(int id) {
        return task -> task.getId() == id;
    }

    public static TaskFilter byTitleContains(String keyword) {
        return task -> task.getTitle() != null && task.getTitle().contains(keyword);
    }

    public static TaskFilter byDescriptionContains(String keyword) {
        return task -> task.getDescription() != null && task.getDescription().contains(keyword);
    }

    public static TaskFilter byState(TaskState state) {
        return task -> task.getState() == state;
    }
}
