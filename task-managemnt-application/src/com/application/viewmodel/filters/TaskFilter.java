package com.application.viewmodel.filters;

import com.application.model.ITask;

public interface TaskFilter {
    boolean test(ITask task);
    default TaskFilter and(TaskFilter other) {
        return task -> this.test(task) && other.test(task);
    }
    default TaskFilter or(TaskFilter other) {
        return task -> this.test(task) || other.test(task);
    }
    default TaskFilter negate() {
        return task -> !this.test(task);
    }
}
