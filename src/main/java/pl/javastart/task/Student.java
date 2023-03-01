package pl.javastart.task;

public class Student extends Person {
    private int index;

    public Student(String firstName, String lastName, int index) {
        super(firstName, lastName);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    String studentInfo() {
        return index + " " + getFirstName() + " " + getLastName();
    }
}
