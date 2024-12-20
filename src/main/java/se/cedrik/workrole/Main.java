package se.cedrik.workrole;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String DB_URL = "jdbc:hsqldb:hsql://localhost/project3";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        try (Connection connection = JDBCHelper.getConnection()) {
            WorkRoleDAO workRoleDAO = new WorkRoleDAO(connection);

            while (true) {
                System.out.println("\nVälj en operation:");
                System.out.println("1. Skapa en ny arbetsroll");
                System.out.println("2. Hämta alla arbetsroller");
                System.out.println("3. Hämta en arbetsroll");
                System.out.println("4. Uppdatera en arbetsroll");
                System.out.println("5. Ta bort en arbetsroll");
                System.out.println("6. Avsluta");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        createWorkRole(scanner, workRoleDAO);
                        break;
                    case 2:
                        getAllWorkRoles(workRoleDAO);
                        break;
                    case 3:
                        getWorkRoleById(scanner, workRoleDAO);
                        break;
                    case 4:
                        updateWorkRole(scanner, workRoleDAO);
                        break;
                    case 5:
                        deleteWorkRole(scanner, workRoleDAO);
                        break;
                    case 6:
                        System.out.println("Avslutar programmet...");
                        return;
                    default:
                        System.out.println("Ogiltigt val, försök igen.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createWorkRole(Scanner scanner, WorkRoleDAO workRoleDAO) {
        try {
            System.out.println("\nAnge titel på arbetsrollen:");
            String title = scanner.nextLine();

            System.out.println("Ange beskrivning:");
            String description = scanner.nextLine();

            System.out.println("Ange lön: ");
            double salary = scanner.nextDouble();

            System.out.println("Ange skapelsedatum (YYYY-MM-DD):");
            String creationDateStr = scanner.next();
            java.sql.Date creationDate = java.sql.Date.valueOf(creationDateStr);

            WorkRole newRole = new WorkRole(title, description, salary, creationDate);
            workRoleDAO.insertWorkRole(newRole);
            System.out.println("Arbetsroll skapad: " + title);
        } catch (SQLException e) {
            System.out.println("Fel vid skapande av arbetsroll: " + e.getMessage());
        }
    }

    private static void getAllWorkRoles(WorkRoleDAO workRoleDAO) {
        try {
            List<WorkRole> roles = workRoleDAO.getAllWorkRoles();
            if (roles.isEmpty()) {
                System.out.println("Inga arbetsroller finns i databasen.");
            } else {
                System.out.println("\nAlla arbetsroller:");
                for (WorkRole role : roles) {
                    System.out.println(role.getRoleId() + ": " + role.getTitle() + " - " + role.getDescription());
                }
            }
        } catch (SQLException e) {
            System.out.println("Fel vid hämtning av arbetsroller: " + e.getMessage());
        }
    }

    private static void getWorkRoleById(Scanner scanner, WorkRoleDAO workRoleDAO) {
        try {
            System.out.println("\nAnge ID för arbetsrollen att hämta:");
            int id = scanner.nextInt();
            scanner.nextLine();

            WorkRole role = workRoleDAO.getWorkRoleById(id);
            if (role != null) {
                System.out.println("Arbetsroll: " + role.getTitle() + " - " + role.getDescription());
            } else {
                System.out.println("Ingen arbetsroll hittades med ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Fel vid hämtning av arbetsroll: " + e.getMessage());
        }
    }

    private static void updateWorkRole(Scanner scanner, WorkRoleDAO workRoleDAO) {
        try {
            System.out.println("\nAnge ID för arbetsrollen att uppdatera:");
            int id = scanner.nextInt();
            scanner.nextLine();

            WorkRole role = workRoleDAO.getWorkRoleById(id);
            if (role != null) {
                System.out.println("Nuvarande titel: " + role.getTitle());
                System.out.println("Ange ny titel:");
                String newTitle = scanner.nextLine();

                System.out.println("Nuvarande beskrivning: " + role.getDescription());
                System.out.println("Ange ny beskrivning:");
                String newDescription = scanner.nextLine();

                System.out.println("Nuvarande lön: " + role.getSalary());
                System.out.println("Ange ny lön:");
                double newSalary = scanner.nextDouble();
                scanner.nextLine();

                role.setTitle(newTitle);
                role.setDescription(newDescription);
                role.setSalary(newSalary);

                workRoleDAO.updateWorkRole(role);
                System.out.println("Arbetsrollen har uppdaterats.");
            } else {
                System.out.println("Ingen arbetsroll hittades med ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Fel vid uppdatering av arbetsroll: " + e.getMessage());
        }
    }

    private static void deleteWorkRole(Scanner scanner, WorkRoleDAO workRoleDAO) {
        try {
            System.out.println("\nAnge ID för arbetsrollen att ta bort:");
            int id = scanner.nextInt();
            scanner.nextLine();

            workRoleDAO.deleteWorkRole(id);
            System.out.println("Arbetsrollen med ID " + id + " har tagits bort.");
        } catch (SQLException e) {
            System.out.println("Fel vid borttagning av arbetsroll: " + e.getMessage());
        }
    }

}
