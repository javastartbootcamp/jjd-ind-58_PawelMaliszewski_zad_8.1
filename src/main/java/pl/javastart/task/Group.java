package pl.javastart.task;

public class Group {
    private static final int MAX_STUDENTS = 100;
    private String code;
    private String name;
    private int studentsCounter = 0;
    Student[] students = new Student[MAX_STUDENTS];
    Lecturer lecturer;

    public Group(String code, String name, Lecturer lecturer) {
        this.code = code;
        this.name = name;
        this.lecturer = lecturer;
    }

    void addStudent(int index, String firstName, String lastName) {
        students[studentsCounter] = new Student(firstName, lastName, index);
        studentsCounter++;
    }

    boolean checkIfStudentExists() {
        for (int i = 0; i < studentsCounter; i++) {
            for (int j = 0; j < studentsCounter; j++) {
                if ((students[i].getIndex() == students[j].getIndex())) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkIfStudentExists(int index) {
        for (int i = 0; i < studentsCounter; i++) {
            if ((students[i].getIndex() == index)) {
                return true;
            }
        }
        return false;
    }

    public int getStudentsCounter() {
        return studentsCounter;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String groupInfo() {
        return "Kod: " + code + " Nazwa: " + name + studentInfo();
    }

    private String studentInfo() {
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < studentsCounter; i++) {
            info.append(", Student nr: ").append(i + 1).append(" ").append(students[i]
                    .getFirstName()).append(" ").append(students[i].getLastName());
        }
        return info.toString();
    }
}
