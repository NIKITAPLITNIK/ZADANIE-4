import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:phonebook.db");
            initializeDatabase();
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД: " + e.getMessage());
        }
    }

    private void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS contacts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "firstName TEXT," +
                "lastName TEXT," +
                "phoneNumber1 TEXT," +
                "phoneNumber2 TEXT," +
                "phoneNumber3 TEXT)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы: " + e.getMessage());
        }
    }

    public void addContact(Contact contact) {
        String insertSQL = "INSERT INTO contacts (firstName, lastName, phoneNumber1, phoneNumber2, phoneNumber3) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, contact.getFirstName());
            pstmt.setString(2, contact.getLastName());
            pstmt.setString(3, contact.getPhoneNumber1());
            pstmt.setString(4, contact.getPhoneNumber2());
            pstmt.setString(5, contact.getPhoneNumber3());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка добавления контакта: " + e.getMessage());
        }
    }

    public void deleteContact(String lastName) {
        String deleteSQL = "DELETE FROM contacts WHERE lastName = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setString(1, lastName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка удаления контакта: " + e.getMessage());
        }
    }

    public List<Contact> findContactsByLastName(String lastName) {
        List<Contact> contacts = new ArrayList<>();
        String searchSQL = "SELECT * FROM contacts WHERE lastName = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(searchSQL)) {
            pstmt.setString(1, lastName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                contacts.add(new Contact(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phoneNumber1"),
                        rs.getString("phoneNumber2"),
                        rs.getString("phoneNumber3")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка поиска контакта: " + e.getMessage());
        }
        return contacts;
    }
}
