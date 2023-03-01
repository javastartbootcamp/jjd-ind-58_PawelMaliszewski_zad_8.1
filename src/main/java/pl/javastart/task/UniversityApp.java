package pl.javastart.task;

import java.util.Objects;

public class UniversityApp {

    private static final int MAX_LECTURES = 110;
    private static final int MAX_GROUPS = 110;
    private static final int MAX_GRADES = 20;
    private static final int MAX_ADDEDSTUDENTS = 200;
    private int lectureCount = 0;
    Lecturer[] lecturers = new Lecturer[MAX_LECTURES];
    private int groupCount = 0;
    Group[] groups = new Group[MAX_GROUPS];
    private int gradesCount = 0;
    Grade[] grades = new Grade[MAX_GRADES];
    private int studentsPrintListCounter = 0;
    Student[] studentsPrintList = new Student[MAX_ADDEDSTUDENTS];

    /**
     * Tworzy prowadzącego zajęcia.
     * W przypadku gdy prowadzący z zadanym id już istnieje, wyświetlany jest komunikat:
     * "Prowadzący z id [id_prowadzacego] już istnieje"
     *
     * @param id        - unikalny identyfikator prowadzącego
     * @param degree    - stopień naukowy prowadzącego
     * @param firstName - imię prowadzącego
     * @param lastName  - nazwisko prowadzącego
     */
    public void createLecturer(int id, String degree, String firstName, String lastName) {
        Lecturer lecturer = findLecturer(id);
        if (lecturer != null) {
            System.out.println("Prowadzący z id " + id + " już istnieje");
        } else {
            lecturers[lectureCount] = new Lecturer(firstName, lastName, id, degree);
            lectureCount++;
        }
    }

    private Lecturer findLecturer(int id) {
        for (Lecturer lecturer : lecturers) {
            if (lecturer == null) {
                return null;
            } else if (Objects.equals(lecturer.getId(), id)) {
                return lecturer;
            }
        }
        return null;
    }

    /**
     * Tworzy grupę zajęciową.
     * W przypadku gdy grupa z zadanym kodem już istnieje, wyświetla się komunikat:
     * "Grupa [kod grupy] już istnieje"
     * W przypadku gdy prowadzący ze wskazanym id nie istnieje wyświetla się komunikat:
     * "Prowadzący o id [id prowadzacego] nie istnieje"
     *
     * @param code       - unikalny kod grupy
     * @param name       - nazwa przedmiotu (np. "Podstawy programowania")
     * @param lecturerId - identyfikator prowadzącego. Musi zostać wcześniej utworzony za pomocą metody {@link #createLecturer(int, String, String, String)}
     */

    public void createGroup(String code, String name, int lecturerId) {
        Group group = findGroupByCode(code);
        if (group != null) {
            System.out.println("Grupa " + code + " już istnieje");
        } else if (findLecturer(lecturerId) == null) {
            System.out.println("Prowadzący o id " + lecturerId + " nie istnieje");
        } else {
            groups[groupCount] = new Group(code, name, findLecturer(lecturerId));
            groupCount++;
        }
    }

    private Group findGroupByCode(String code) {
        for (Group group : groups) {
            if (group == null) {
                return null;
            } else if (Objects.equals(group.getCode(), code)) {
                return group;
            }
        }
        return  null;
    }

    /**
     * Dodaje studenta do grupy zajęciowej.
     * W przypadku gdy grupa zajęciowa nie istnieje wyświetlany jest komunikat:
     * "Grupa [kod grupy] nie istnieje
     *
     * @param index     - unikalny numer indeksu studenta
     * @param groupCode - kod grupy utworzonej wcześniej za pomocą {@link #createGroup(String, String, int)}
     * @param firstName - imię studenta
     * @param lastName  - nazwisko studenta
     */

    public void addStudentToGroup(int index, String groupCode, String firstName, String lastName) {
        if (findGroupByCode(groupCode) == null) {
            System.out.println("Grupa " +  groupCode +  " nie istnieje");
        }
        for (int i = 0; i < groupCount; i++) {
            if (!groups[i].checkIfStudentExists(index)) {
                groups[i].addStudent(index, firstName, lastName);
                studentsPrintList[studentsPrintListCounter] = new Student(firstName, lastName, index);
                studentsPrintListCounter++;
                break;
            } else {
                System.out.println("Student o indeksie " + index + " jest już w grupie " + groupCode);
            }
        }
    }

    /**
     * Wyświetla informacje o grupie w zadanym formacie.
     * Oczekiwany format:
     * Kod: [kod_grupy]
     * Nazwa: [nazwa przedmiotu]
     * Prowadzący: [stopień naukowy] [imię] [nazwisko]
     * Uczestnicy:
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * W przypadku gdy grupa nie istnieje, wyświetlany jest komunikat w postaci: "Grupa [kod] nie znaleziona"
     *
     * @param groupCode - kod grupy, dla której wyświetlić informacje
     */

    public void printGroupInfo(String groupCode) {
        Group group = findGroupByCode(groupCode);
        if (group == null) {
            System.out.println("Grupa " + groupCode +  " nie znaleziona");
        } else {
            String info = group.groupInfo() + group.getLecturer().pirntinfo();
            System.out.println(info);
        }
    }

    /**
     * Dodaje ocenę końcową dla wskazanego studenta i grupy.
     * Student musi być wcześniej zapisany do grupy za pomocą {@link #addStudentToGroup(int, String, String, String)}
     * W przypadku, gdy grupa o wskazanym kodzie nie istnieje, wyświetlany jest komunikat postaci:
     * "Grupa pp-2022 nie istnieje"
     * W przypadku gdy student nie jest zapisany do grupy, wyświetlany jest komunikat w
     * postaci: "Student o indeksie 179128 nie jest zapisany do grupy pp-2022"
     * W przypadku gdy ocena końcowa już istnieje, wyświetlany jest komunikat w postaci:
     * "Student o indeksie 179128 ma już wystawioną ocenę dla grupy pp-2022"
     *
     * @param studentIndex - numer indeksu studenta
     * @param groupCode    - kod grupy
     * @param grade        - ocena
     */

    public void addGrade(int studentIndex, String groupCode, double grade) {
        Group group = findGroupByCode(groupCode);
        if (group != null) {
            Student student = group.findStudenByIndex(studentIndex);
            boolean gradesCheck = checkIfGredeExists(student, group);
            if (!gradesCheck) {
                if (student != null) {
                    grades[gradesCount] = new Grade(student, group, grade);
                    gradesCount++;
                } else {
                    System.out.println("Student o indeksie " + studentIndex + " nie jest zapisany do grupy "
                            + groupCode);
                }
            } else {
                System.out.println("Student o indeksie " + studentIndex
                        + " ma już wystawioną ocenę dla grupy " + groupCode);
            }
        } else {
            System.out.println("Grupa " + groupCode + " nie istnieje");
        }
    }

    private boolean checkIfGredeExists(Student student, Group group) {
        for (Grade grade : grades) {
            if (grade == null) {
                return false;
            } else if (grade.getStudent() == student && grade.getGroup() == group && grade.getGrade() != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Wyświetla wszystkie oceny studenta.
     * Przykładowy wydruk:
     * Podstawy programowania: 5.0
     * Programowanie obiektowe: 5.5
     *
     * @param index - numer indesku studenta dla którego wyświetlić oceny
     */

    public void printGradesForStudent(int index) {
        for (int i = 0; i < gradesCount; i++) {
            if (grades[i].getStudent().getIndex() == index) {
                System.out.println(grades[i].grupInfo());
            }
        }
    }

    /**
     * Wyświetla oceny studentów dla wskazanej grupy.
     * Przykładowy wydruk:
     * 179128 Marcin Abacki: 5.0
     * 179234 Dawid Donald: 4.5
     * 189521 Anna Kowalska: 5.5
     *
     * @param groupCode - kod grupy, dla której wyświetlić oceny
     */

    public void printGradesForGroup(String groupCode) {
        Group group = findGroupByCode(groupCode);
        if (group == null) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
        } else {
            for (int i = 0; i < gradesCount; i++) {
                if (grades[i].getGroup() == group) {
                    System.out.println(grades[i].studentInfo());
                }
            }
        }
    }

    /**
     * Wyświetla wszystkich studentów. Każdy student powinien zostać wyświetlony tylko raz.
     * Każdy student drukowany jest w nowej linii w formacie [nr_indesku] [imie] [nazwisko]
     * Przykładowy wydruk:
     * 179128 Marcin Abacki
     * 179234 Dawid Donald
     * 189521 Anna Kowalska
     */

    public void printAllStudents() {
        Student[] tmp = new Student[studentsPrintListCounter];
        for (int i = 0; i < studentsPrintListCounter; i++) {
            for (int j = studentsPrintListCounter - 1; j > 0; j--) {
                if (studentsPrintList[j].getIndex() < studentsPrintList[j - 1].getIndex()) {
                    tmp[0] = studentsPrintList[j];
                    studentsPrintList[j] = studentsPrintList [j - 1];
                    studentsPrintList [j - 1] = tmp[0];
                }
            }
        }
        for (int i = 0; i < studentsPrintListCounter - 1; i++) {
            if (studentsPrintList[i].getIndex() == studentsPrintList[(i + 1 % studentsPrintList.length)].getIndex()) {
                studentsPrintList[i] = null;
                i++;
            }
        }
        for (int i = 0; i < studentsPrintListCounter; i++) {
            if (studentsPrintList[i] !=  null) {
                System.out.println(studentsPrintList[i].studentInfo());
            }
        }
    }
}
