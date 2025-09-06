package com.application.visitor;

import com.application.model.Task;

public interface TaskVisitor {
    void visit(Task task);
}
