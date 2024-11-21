import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Класс Contact - описание контакта
class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber1;
    private String phoneNumber2;
    private String phoneNumber3;

    public Contact(String firstName, String lastName, String phoneNumber1, String phoneNumber2, String phoneNumber3) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.phoneNumber3 = phoneNumber3;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber1() { return phoneNumber1; }
    public String getPhoneNumber2() { return phoneNumber2; }
    public String getPhoneNumber3() { return phoneNumber3; }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber1='" + phoneNumber1 + '\'' +
                ", phoneNumber2='" + phoneNumber2 + '\'' +
                ", phoneNumber3='" + phoneNumber3 + '\'' +
                '}';
    }
}

// Класс DatabaseManager - управление базой данных
class DatabaseManager {
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

// Класс PhoneDirectory - управление контактами
class PhoneDirectory {
    private DatabaseManager dbManager;

    public PhoneDirectory(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void addContact(Contact contact) {
        dbManager.addContact(contact);
    }

    public void deleteContact(String lastName) {
        dbManager.deleteContact(lastName);
    }

    public List<Contact> searchByLastName(String lastName) {
        return dbManager.findContactsByLastName(lastName);
    }
}

// Класс PhoneDirectoryApp - консольное приложение
public class PhoneDirectoryApp {
    private PhoneDirectory phoneDirectory;
    private Scanner scanner;

    public PhoneDirectoryApp(PhoneDirectory phoneDirectory) {
        this.phoneDirectory = phoneDirectory;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("Выберите действие: 1 - добавить контакт, 2 - удалить контакт, 3 - поиск контакта, 4 - выход");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    deleteContact();
                    break;
                case 3:
                    searchContact();
                    break;
                case 4:
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Некорректный выбор");
            }
        }
    }

    private void addContact() {
        System.out.print("Введите имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        String lastName = scanner.nextLine();
        System.out.print("Введите телефон 1: ");
        String phoneNumber1 = scanner.nextLine();
        System.out.print("Введите телефон 2 (или оставьте пустым): ");
        String phoneNumber2 = scanner.nextLine();
        System.out.print("Введите телефон 3 (или оставьте пустым): ");
        String phoneNumber3 = scanner.nextLine();

        Contact contact = new Contact(firstName, lastName, phoneNumber1, phoneNumber2, phoneNumber3);
        phoneDirectory.addContact(contact);
        System.out.println("Контакт добавлен.");
    }

    private void deleteContact() {
        System.out.print("Введите фамилию для удаления: ");
        String lastName = scanner.nextLine();
        phoneDirectory.deleteContact(lastName);
        System.out.println("Контакт удален.");
    }

    private void searchContact() {
        System.out.print("Введите фамилию для поиска: ");
        String lastName = scanner.nextLine();
        List<Contact> contacts = phoneDirectory.searchByLastName(lastName);
        if (contacts.isEmpty()) {
            System.out.println("Контакты не найдены.");
        } else {
            contacts.forEach(System.out::println);
        }
    }

    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        PhoneDirectory phoneDirectory = new PhoneDirectory(dbManager);
        PhoneDirectoryApp app = new PhoneDirectoryApp(phoneDirectory);
        app.run();
    }
}
