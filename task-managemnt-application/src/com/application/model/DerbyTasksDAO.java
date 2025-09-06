package com.application.model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class DerbyTasksDAO implements ITasksDAO {
    private static DerbyTasksDAO instance;
    private final String url = "jdbc:derby:tasksDB;create=true";
    private DerbyTasksDAO() {
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("""
                        CREATE TABLE tasks (
                            id INT PRIMARY KEY,
                            title VARCHAR(255),
                            description VARCHAR(255),
                            state VARCHAR(50),
                            created_time TIMESTAMP
                        )
                    """);
        } catch (SQLException e) {
            // If the table already exist then continue the program and don't stop with exception.
            if (!e.getSQLState().equals("X0Y32")) {
                e.printStackTrace();
            }
        }
    }
    public static synchronized DerbyTasksDAO getInstance() {
        if (instance == null) {
            instance = new DerbyTasksDAO();
        }
        return instance;
    }
    @Override
    public ITask[] getTasks() throws TasksDAOException {
        List<ITask> tasks = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tasks")) {
            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        TaskState.valueOf(rs.getString("state"))
                ));
            }
        } catch (SQLException e) {
            throw new TasksDAOException("Error fetching tasks", e);
        }
        return tasks.toArray(new ITask[0]);
    }
    @Override
    public ITask getTask(int id) throws TasksDAOException {
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tasks WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        TaskState.valueOf(rs.getString("state"))
                );
            }
        } catch (SQLException e) {
            throw new TasksDAOException("Error fetching task " + id, e);
        }
        return null;
    }
    @Override
    public void addTask(ITask task) throws TasksDAOException {
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO tasks (id, title, description, state, created_time) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setInt(1, task.getId());
            stmt.setString(2, task.getTitle());
            stmt.setString(3, task.getDescription());
            stmt.setString(4, task.getState().name());
            stmt.setString(5, String.valueOf(Timestamp.valueOf(LocalDateTime.now())));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new TasksDAOException("Error adding task " + task.getId(), e);
        }
    }
    @Override
    public void updateTask(ITask task) throws TasksDAOException {
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE tasks SET title=?, description=?, state=? WHERE id=?")) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getState().name());
            stmt.setInt(4, task.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new TasksDAOException("Error updating task " + task.getId(), e);
        }
    }
    @Override
    public void deleteTasks() throws TasksDAOException {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM tasks");
        } catch (SQLException e) {
            throw new TasksDAOException("Error deleting all tasks", e);
        }
    }
    @Override
    public void deleteTask(int id) throws TasksDAOException {
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM tasks WHERE id=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new TasksDAOException("Error deleting task " + id, e);
        }
    }
    @Override
    public ITask[] getTasksCreatedAfter(LocalDateTime dateTime) throws TasksDAOException {
        List<ITask> tasks = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, title, description, state FROM tasks WHERE created_time > ?")) {
            stmt.setTimestamp(1, Timestamp.valueOf(dateTime));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        TaskState.valueOf(rs.getString("state"))
                ));
            }
        } catch (SQLException e) {
            throw new TasksDAOException("Error filtering by created_time", e);
        }
        return tasks.toArray(new ITask[0]);
    }
    @Override
    public ITask[] getTasksCreatedBefore(LocalDateTime dateTime) throws TasksDAOException {
        List<ITask> tasks = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, title, description, state FROM tasks WHERE created_time < ?")) {
            stmt.setTimestamp(1, Timestamp.valueOf(dateTime));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        TaskState.valueOf(rs.getString("state"))
                ));
            }
        } catch (SQLException e) {
            throw new TasksDAOException("Error filtering by created_time", e);
        }
        return tasks.toArray(new ITask[0]);
    }
}
