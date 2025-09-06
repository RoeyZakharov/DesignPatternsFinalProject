package com.application;

import com.application.model.ITasksDAO;
import com.application.model.ITask;
import com.application.model.Task;
import com.application.model.TaskState;
import com.application.model.TasksDAOProxy;
import com.application.model.TasksDAOException;
import com.application.visitor.ReportVisitor;
import com.application.visitor.TaskVisitor;

public class Main {
    public static void main(String[] args) {
        try {
            // יוצרים את ה-DAO דרך ה-Proxy (Singleton + Proxy)
            ITasksDAO dao = TasksDAOProxy.getInstance();

            // מנקים טבלה (כדי שכל הרצה תהיה נקייה)
            dao.deleteTasks();

            // מוסיפים משימות
            ITask task1 = new Task(1, "Study Visitor", "Learn Visitor pattern", TaskState.IN_PROGRESS);
            ITask task2 = new Task(2, "Submit Project", "Final submission in Moodle", TaskState.TODO);
            ITask task3 = new Task(3, "Celebrate", "Eat pizza!", TaskState.COMPLETED);

            dao.addTask(task1);
            dao.addTask(task2);
            dao.addTask(task3);

            // שולפים משימות
            ITask[] tasks = dao.getTasks();
            System.out.println("=== Loaded Tasks from DB ===");
            for (ITask t : tasks) {
                System.out.println(t.getId() + ": " + t.getTitle() + " [" + t.getState() + "]");
            }

            // מריצים Visitor
            System.out.println("\n=== Report Visitor Output ===");
            TaskVisitor visitor = new ReportVisitor();
            for (ITask t : tasks) {
                t.accept(visitor);
            }

        } catch (TasksDAOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
