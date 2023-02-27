package pl.javastart.task;

public class Lecturer extends Person {
    private int id;
    private String degree;

    public Lecturer(String firstName, String lastName, int id, String degree) {
        super(firstName, lastName);
        this.id = id;
        this.degree = degree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    String pirntinfo() {
        return ", Prowadzący: " + degree + " " + getFirstName() + " " + getLastName();
    }
}
