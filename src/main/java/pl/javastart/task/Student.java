package pl.javastart.task;

public class Student extends Person {
    private int index;

    private double grade;

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

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    void printStudentInfo() {
        System.out.println(getIndex() + " " + getFirstName() + " " + getLastName());
    }
}
