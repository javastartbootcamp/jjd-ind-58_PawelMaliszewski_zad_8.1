package pl.javastart.task;

public class UniversityApp {

    private static final int MAX_LECTURES = 110;
    private static final int MAX_GROUPS = 110;
    private int lectureCount = 0;
    Lecturer[] lecturers = new Lecturer[MAX_LECTURES];
    private int groupCount = 0;
    Group[] groups = new Group[MAX_GROUPS];

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
        if (id > 0 && id > lectureCount && id < lectureCount + 2) {
            lecturers[id] = new Lecturer(firstName, lastName, id, degree);
            lectureCount++;
        } else if (id > 0 && id <= lectureCount) {
            System.out.println("Prowadzący z id " + id + " już istnieje");
        }
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
        if (groupCount == 0) {
            groups[groupCount] = new Group(code, name, lecturers[lecturerId]);
            groupCount++;
        } else if (groupCount > 0) {
            for (int i = 0; i < groupCount; i++) {
                if (isGroupSameAsCode(i, code)) {
                    System.out.println("Grupa " + code + " już istnieje");
                    break;
                } else if (lecturers[lecturerId] == null) {
                    System.out.println("Prowadzący o id " + lecturerId + " nie istnieje");
                } else {
                    groups[groupCount] = new Group(code, name, lecturers[lecturerId]);
                    groupCount++;
                    break;
                }
            }
        }
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
        if (groupCount == 0) {
            System.out.println("Grupa " +  groupCode +  " nie istnieje");
        }
        for (int i = 0; i < groupCount; i++) {
            if (isGroupSameAsCode(i, groupCode)) {
                if (!groups[i].checkIfStudentExists(index)) {
                    groups[i].addStudent(index, firstName, lastName);
                    break;
                } else {
                    System.out.println("Student o indeksie " + index + " jest już w grupie " + groupCode);
                }
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
        if (groupCount == 0) {
            System.out.println("Grupa " + groupCode +  " nie znaleziona");
        }
        for (int i = 0; i < groupCount + 1; i++) {
            if (groups[i] == null) {
                System.out.println("Grupa " + groupCode +  " nie znaleziona");
                break;
            }
            if (isGroupSameAsCode(i, groupCode)) {
                String info = groups[i].groupInfo() + groups[i].lecturer.pirntinfo();
                System.out.println(info);
                break;
            }
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
        if (groupCount == 0) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
        }
        for (int i = 0; i < groupCount; i++) {
            if (isGroupSameAsCode(i, groupCode)) {
                if (!groups[i].checkIfStudentExists(studentIndex)) {
                    System.out.println("Student o indeksie " + studentIndex
                            + " nie jest zapisany do grupy " + groupCode);
                    break;
                }
                for (int j = 0; j < groups[i].getStudentsCounter(); j++) {
                    if (groups[i].students[j].getIndex() == studentIndex) {

                        if (groups[i].students[j].getGrade() > 0.0) {
                            System.out.println("Student o indeksie " + studentIndex
                                    + " ma już wystawioną ocenę dla grupy " + groupCode);
                        } else {
                            groups[i].students[j].setGrade(grade);
                            break;
                        }
                    }
                }
            }
        }
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
        double[] gradeSum = new double[groupCount];
        for (int i = 0; i < groupCount; i++) {
            for (int j = 0; j < groups[i].getStudentsCounter(); j++) {
                if (groups[i].students[j].getIndex() == index)
                    gradeSum[i] += groups[i].students[j].getGrade();
            }
            System.out.println(groups[i].getName() + ": " + gradeSum[i]);
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
        if (groupCount == 0) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
        }
        for (int i = 0; i < groupCount; i++) {
            if (isGroupSameAsCode(i, groupCode)) {
                for (int j = 0; j < groups[i].getStudentsCounter(); j++) {
                    System.out.println(groups[i].students[j].getFirstName() + " "
                            + groups[i].students[j].getLastName() + ": " + groups[i].students[j].getGrade());
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
        int newArrayLength = 0;
        for (int i = 0; i < groupCount; i++) {
            newArrayLength += groups[i].getStudentsCounter();
        }
        int loopCounter = 0;
        Student[] printStudents = new Student[newArrayLength];
        for (int i = 0; i < groupCount; i++) {
            for (int j = 0; j < (groups[i].getStudentsCounter()); j++) {
                printStudents[loopCounter] = new Student(groups[i].students[j].getFirstName(),
                        groups[i].students[j].getLastName(), groups[i].students[j].getIndex());
                loopCounter++;
            }
        }

        for (int i = 0; i < printStudents.length; i++) {
            Student[] tmp = new Student[printStudents.length];
            for (int j = printStudents.length - 1; j > i; j--) {
                if (printStudents[j].getIndex() < printStudents[j - 1].getIndex()) {
                    tmp[1] = printStudents[j - 1];
                    printStudents[j - 1] = printStudents[i];
                    printStudents[i] = tmp[1];
                }
            }
        }
        for (int i = 0; i < printStudents.length - 1; i++) {
            if (printStudents[i].getIndex() == printStudents[(i + 1 % printStudents.length)].getIndex()) {
                printStudents[i] = null;
                i++;
            }
        }
        int finalPrintArraySize = 0;
        for (int i = 0; i < printStudents.length; i++) {
            if (printStudents[i] != null) {
                finalPrintArraySize++;
            }
        }
        Student[] finalPrint = new Student[finalPrintArraySize];
        int index = 0;
        for (int i = 0; i < printStudents.length; i++) {
            if (printStudents[i] != null) {
                finalPrint[index] = printStudents[i];
                index++;
            }
        }
        for (Student student : finalPrint) {
            student.printStudentInfo();
        }
    }
//        for (int i = 0; i < printStudents.length; i++) { // czy tak bedzie lepiej ???
//            if (printStudents[i] != null) {
//                printStudents[i].printStudentInfo();
//            }
//        }

    boolean isGroupSameAsCode(int index, String code) {
        return groups[index].getCode().equals(code);
    }

}
