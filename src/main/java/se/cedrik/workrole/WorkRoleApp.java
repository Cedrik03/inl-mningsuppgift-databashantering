package se.cedrik.workrole;

import java.sql.Connection;
import java.sql.SQLException;

public class WorkRoleApp {
    public static void main(String[] args) {
        try {
            Connection connection = JDBCHelper.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

