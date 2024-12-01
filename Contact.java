public class Contact {
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
