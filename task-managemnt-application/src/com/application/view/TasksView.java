package com.application.view;

import com.application.model.ITask;
import com.application.viewmodel.TasksViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
public class TasksView extends JFrame implements Observer{
    private final TasksViewModel viewModel;
    private final DefaultTableModel tableModel;
    private final JTable taskTable;

    public TasksView(TasksViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);

        setTitle("Tasks Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Title", "State"}, 0);
        taskTable = new JTable(tableModel);
        add(new JScrollPane(taskTable), BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton filterBtn = new JButton("Filter");
        JButton reportBtn = new JButton("Report");

        buttonsPanel.add(addBtn);
        buttonsPanel.add(editBtn);
        buttonsPanel.add(deleteBtn);
        buttonsPanel.add(filterBtn);
        buttonsPanel.add(reportBtn);

        add(buttonsPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> new TaskForm(this, viewModel).setVisible(true));

        // Need to be modified - didn't finished the function.
        editBtn.addActionListener(e -> {
            int row = taskTable.getSelectedRow();
            if (row >= 0) {
                int taskId = (int) tableModel.getValueAt(row, 0);
                String currentTitle = (String) tableModel.getValueAt(row, 1);
                String currentState = (String) tableModel.getValueAt(row, 2);

                TaskForm form = new TaskForm(this, viewModel);
                form.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task to edit.");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = taskTable.getSelectedRow();
            if (row >= 0) {
                int taskId = (int) tableModel.getValueAt(row, 0);
                try {
                    viewModel.deleteTask(taskId);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting task: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task to delete.");
            }
        });

        filterBtn.addActionListener(e -> {
            String keyword = JOptionPane.showInputDialog(this, "Enter keyword to filter by title:");
            if (keyword != null && !keyword.isBlank()) {
                try {
                    ITask[] filtered = viewModel.getFilteredTasks(
                            // Need to be changed, I add this only for testing the system
                            com.application.viewmodel.filters.TaskFilters.byTitleContains(keyword)
                    );
                    tableModel.setRowCount(0);
                    for (ITask t : filtered) {
                        tableModel.addRow(new Object[]{t.getId(), t.getTitle(), t.getState()});
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error filtering tasks: " + ex.getMessage());
                }
            }
        });

        reportBtn.addActionListener(e -> {
            try {
                StringBuilder sb = new StringBuilder("Report:\n");
                com.application.visitor.TaskVisitor visitor = new com.application.visitor.ReportVisitor();
                for (ITask t : viewModel.getAllTasks()) {
                    t.accept(visitor);
                    sb.append(t.getTitle()).append(" [").append(t.getState()).append("]\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage());
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            tableModel.setRowCount(0); // מנקה טבלה
            for (ITask task : viewModel.getAllTasks()) {
                tableModel.addRow(new Object[]{
                        task.getId(),
                        task.getTitle(),
                        task.getState()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
