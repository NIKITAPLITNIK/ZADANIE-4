import java.util.List;
import java.util.Scanner;

public class PhoneDirectoryView {
    private Scanner scanner;

    public PhoneDirectoryView() {
        this.scanner = new Scanner(System.in);
    }

    public int displayMenuAndGetChoice() {
        System.out.println("Выберите действие: 1 - добавить контакт, 2 - удалить контакт, 3 - поиск контакта, 4 - выход");
        return scanner.nextInt();
    }

    public Contact getContactDetails() {
        scanner.nextLine(); // Consume newline
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
        return new Contact(firstName, lastName, phoneNumber1, phoneNumber2, phoneNumber3);
    }

    public String getLastNameForOperation(String operation) {
        scanner.nextLine(); // Consume newline
        System.out.print("Введите фамилию для " + operation + ": ");
        return scanner.nextLine();
    }

    public void displayContacts(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            System.out.println("Контакты не найдены.");
        } else {
            contacts.forEach(System.out::println);
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
