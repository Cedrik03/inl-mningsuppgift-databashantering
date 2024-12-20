package se.cedrik.workrole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkRoleDAO {

    private Connection connection;

    public WorkRoleDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertWorkRole(WorkRole workRole) throws SQLException {
        String sql = "INSERT INTO work_role (title, description, salary, creation_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, workRole.getTitle());
            stmt.setString(2, workRole.getDescription());
            stmt.setDouble(3, workRole.getSalary());
            stmt.setDate(4, new java.sql.Date(workRole.getCreationDate().getTime()));
            stmt.executeUpdate();
        }
    }

    public List<WorkRole> getAllWorkRoles() throws SQLException {
        String sql = "SELECT * FROM work_role";
        List<WorkRole> workRoles = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                WorkRole workRole = new WorkRole(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDouble("salary"),
                        rs.getDate("creation_date")
                );
                workRole.setRoleId(rs.getInt("role_id"));
                workRoles.add(workRole);
            }
        }
        return workRoles;
    }


    public WorkRole getWorkRoleById(int roleId) throws SQLException {
        String sql = "SELECT * FROM work_role WHERE role_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new WorkRole(
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDouble("salary"),
                            rs.getDate("creation_date")
                    );
                }
            }
        }
        return null;
    }


    public void updateWorkRole(WorkRole workRole) throws SQLException {
        String sql = "UPDATE work_role SET title = ?, description = ?, salary = ?, creation_date = ? WHERE role_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, workRole.getTitle());
            stmt.setString(2, workRole.getDescription());
            stmt.setDouble(3, workRole.getSalary());
            stmt.setDate(4, new java.sql.Date(workRole.getCreationDate().getTime()));
            stmt.setInt(5, workRole.getRoleId());
            stmt.executeUpdate();
        }
    }


    public void deleteWorkRole(int roleId) throws SQLException {
        String sql = "DELETE FROM work_role WHERE role_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            stmt.executeUpdate();
        }
    }
}
