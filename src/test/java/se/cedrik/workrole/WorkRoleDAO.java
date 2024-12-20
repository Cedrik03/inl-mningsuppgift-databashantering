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
        List<WorkRole> roles = new ArrayList<>();
        String sql = "SELECT * FROM work_role";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                WorkRole role = new WorkRole(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDouble("salary"),
                        rs.getDate("creation_date")
                );
                role.setRoleId(rs.getInt("role_id"));
                roles.add(role);
            }
        }
        return roles;
    }
}
