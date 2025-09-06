package com.application.view;

import com.application.model.ITask;
import com.application.model.Task;
import com.application.model.TaskState;
import com.application.viewmodel.TasksViewModel;

import javax.swing.*;
import java.awt.*;

public class TaskForm extends JDialog{
    private final JTextField idField;
    private final JTextField titleField;
    private final JTextField descField;
    private final JComboBox<TaskState> stateBox;

    public TaskForm(JFrame parent, TasksViewModel viewModel) {
        super(parent, "Add Task", true);

        setSize(400, 300);
        setLocationRelativeTo(parent);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        idField = new JTextField();
        panel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Title:"), gbc);

        gbc.gridx = 1;
        titleField = new JTextField();
        panel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        descField = new JTextField();
        panel.add(descField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("State:"), gbc);

        gbc.gridx = 1;
        stateBox = new JComboBox<>(TaskState.values());
        panel.add(stateBox, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        buttonsPanel.add(saveBtn);
        buttonsPanel.add(cancelBtn);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(buttonsPanel, gbc);

        add(panel);

        cancelBtn.addActionListener(e -> dispose());

        saveBtn.addActionListener(e -> {
            try {
                if (idField.getText().isBlank() || titleField.getText().isBlank()) {
                    JOptionPane.showMessageDialog(this, "ID and Title are required!");
                    return;
                }

                int id = Integer.parseInt(idField.getText());
                String title = titleField.getText();
                String desc = descField.getText();
                TaskState state = (TaskState) stateBox.getSelectedItem();

                ITask task = new Task(id, title, desc, state);
                viewModel.addTask(task);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID must be a number!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }
}
