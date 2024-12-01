import java.util.List;

public class PhoneDirectoryController {
    private DatabaseManager dbManager;
    private PhoneDirectoryView view;

    public PhoneDirectoryController(DatabaseManager dbManager, PhoneDirectoryView view) {
        this.dbManager = dbManager;
        this.view = view;
    }

    public void run() {
        while (true) {
            int choice = view.displayMenuAndGetChoice();
            switch (choice) {
                case 1:
                    Contact contact = view.getContactDetails();
                    dbManager.addContact(contact);
                    view.displayMessage("Контакт добавлен.");
                    break;
                case 2:
                    String lastNameToDelete = view.getLastNameForOperation("удаления");
                    dbManager.deleteContact(lastNameToDelete);
                    view.displayMessage("Контакт удален.");
                    break;
                case 3:
                    String lastNameToSearch = view.getLastNameForOperation("поиска");
                    List<Contact> contacts = dbManager.findContactsByLastName(lastNameToSearch);
                    view.displayContacts(contacts);
                    break;
                case 4:
                    view.displayMessage("Выход...");
                    return;
                default:
                    view.displayMessage("Некорректный выбор");
            }
        }
    }
}
