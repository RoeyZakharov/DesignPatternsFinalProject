package com.application;

import com.application.model.TasksDAOProxy;
import com.application.viewmodel.TasksViewModel;
import com.application.view.TasksView;

public class Main {
    public static void main(String[] args) {
        TasksViewModel viewModel = new TasksViewModel(TasksDAOProxy.getInstance());
        TasksView view = new TasksView(viewModel);
        view.setVisible(true);
    }
}
