package se.cedrik.workrole;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WorkRoleDAOTest {

    private static Connection connection;
    private WorkRoleDAO workRoleDAO;

    @BeforeAll
    public static void setUpDatabase() {
        try {
        connection = JDBCHelper.getConnection();

            try (var statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE work_role (" +
                        "role_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "title VARCHAR(255), " +
                        "description VARCHAR(255), " +
                        "salary DOUBLE, " +
                        "creation_date DATE" +
                        ")");
            }
        } catch (SQLException e) {
            fail("Failed: " + e.getMessage());
        }
    }

    @BeforeEach
    public void setUpDAO() {
        workRoleDAO = new WorkRoleDAO(connection);
    }

    @Test
    public void testInsertAndRetrieveWorkRole() {
        try {

            WorkRole newRole = new WorkRole("Utvecklare", "Arbetar med kod", 50000, java.sql.Date.valueOf("2024-01-01"));
            workRoleDAO.insertWorkRole(newRole);


            List<WorkRole> roles = workRoleDAO.getAllWorkRoles();


            assertEquals(1, roles.size());
            WorkRole retrievedRole = roles.get(0);
            assertEquals("Utvecklare", retrievedRole.getTitle());
            assertEquals("Arbetar med kod", retrievedRole.getDescription());
            assertEquals(50000, retrievedRole.getSalary());
            assertEquals(java.sql.Date.valueOf("2024-01-01"), retrievedRole.getCreationDate());
        } catch (SQLException e) {
            fail("SQL exception occurred: " + e.getMessage());
        }
    }

    @AfterAll
    public static void tearDownDatabase() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
