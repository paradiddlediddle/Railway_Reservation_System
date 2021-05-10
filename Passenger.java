package TheProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Passenger {

    public class Child {

        private String childName;
        private int childAge;
        private gender childGender;

    }

    protected enum gender {
        MALE, FEMALE
    }
    protected enum berthType {
        LOWER, UPPER, SIDE
    }
    private String name;
    private int age;
    private gender gender;
    private berthType berthPreference;
    private boolean hasChild;
    List<Child> children = new ArrayList<>();
    private Ticket ticket;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Passenger.gender getGender() {
        return gender;
    }

    public void setGender(Passenger.gender gender) {
        this.gender = gender;
    }

    public berthType getBerthPreference() {
        return berthPreference;
    }

    public void setBerthPreference(berthType berthPreference) {
        this.berthPreference = berthPreference;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public void addChildToList (Child child) {
        children.add(child);
    }
    public Ticket getTicket() { return ticket; }

    public void setTicket(Ticket ticket) { this.ticket = ticket; }

    public List<Child> getChildren() { return children; }

    public void setChildren(List<Child> children) { this.children = children; }

    // Since child is a class within a class, we create the child by writing a method within the passenger.
    // Since the child is a class within the passenger class, I'm choosing to omit getters and setters.
    public void createAndAddChild () {
        Child child = new Child();
        Scanner scanChild = new Scanner(System.in);
        System.out.println("Enter the name of your child: ");
        String childName = scanChild.nextLine();
        child.childName = childName;
        System.out.println("Enter the age of your child: ");
        int childAge = scanChild.nextInt();
        if (childAge > 5) {
            System.out.println("Please books an ordinary ticket for children above 5 years old.");
        }
        child.childAge = childAge;
        System.out.println("Select the gender of your child: \n1.Male \n2.Female");
        int childGenderSelection = scanChild.nextInt();
        if (childGenderSelection == 1 ) {  child.childGender = gender.MALE; }
        else if (childGenderSelection == 2) { child.childGender = gender.FEMALE; }
        addChildToList(child);

        System.out.println("Do you need to add another child? \n1.Yes\n2.No");
        int anotherChild = scanChild.nextInt();
        if (anotherChild == 1) { createAndAddChild(); }
    }

}