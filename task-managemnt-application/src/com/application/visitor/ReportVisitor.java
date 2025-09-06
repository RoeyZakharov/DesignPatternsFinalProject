package com.application.visitor;

import com.application.model.Task;
//import com.application.model.TaskState;

public class ReportVisitor implements TaskVisitor{
    @Override
    public void visit(Task task) {
        switch (task.getState()){
            case TODO -> System.out.println("[TODO]" + task.getTitle());
            case IN_PROGRESS -> System.out.println("[IN PROGRESS]" + task.getTitle());
            case COMPLETED -> System.out.println("[COMPLETE]" + task.getTitle());
        }
    }
}
