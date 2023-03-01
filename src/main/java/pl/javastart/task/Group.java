package pl.javastart.task;

public class Group {
    private static final int MAX_STUDENTS = 100;
    private String code;
    private String name;
    private int studentsCounter = 0;
    private Student[] students = new Student[MAX_STUDENTS];
    private Lecturer lecturer;

    public Group(String code, String name, Lecturer lecturer) {
        this.code = code;
        this.name = name;
        this.lecturer = lecturer;
    }

    void addStudent(int index, String firstName, String lastName) {
        students[studentsCounter] = new Student(firstName, lastName, index);
        studentsCounter++;
    }

    boolean checkIfStudentExists(int index) {
        for (int i = 0; i < studentsCounter; i++) {
            if ((students[i].getIndex() == index)) {
                return true;
            }
        }
        return false;
    }

    public Student findStudenByIndex(int index) {
        for (Student student : students) {
            if (student != null && student.getIndex() == index) {
                return student;
            }
        }
        return null;
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

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
        this.students = students;
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
