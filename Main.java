public class Main {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        PhoneDirectoryView view = new PhoneDirectoryView();
        PhoneDirectoryController controller = new PhoneDirectoryController(dbManager, view);
        controller.run();
    }
}
