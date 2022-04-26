import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class question16 {
    public Map<String, Set<String>> getDeptToStudents(Map<String, Set<String>> deptToCourse, Map<String, Set<String>> courseToStudents){
        Map<String, Set<String>> deptToStudents = new TreeMap<>();
        for(String debt : deptToCourse.keySet()){
            Set<String> students = new TreeSet<>();
            deptToStudents.put(debt, students);
            for(String course : deptToCourse.get(debt)){

                students.addAll(courseToStudents.get(course));

            }
        }

        return deptToStudents;
    }

    public static void main(String[] args) {
        Map<String, Set<String>> deptToCourse = new TreeMap<>();
        Set<String> cs = new TreeSet<>();
        cs.add("COSC 31");cs.add("COSC 50");
        deptToCourse.put("COSC", cs);
        Set<String> chem = new TreeSet<>();
        chem.add("CHEM 5"); chem.add("CHEM 6”");
        deptToCourse.put("CHEM", chem);

        Map<String, Set<String>> courseToStudents = new TreeMap<>();
        Set<String> cosc31 = new TreeSet<>();
        cosc31.add("Alice"); cosc31.add("Bob");
        courseToStudents.put("COSC 31", cosc31);
        Set<String> cosc50 = new TreeSet<>();
        cosc50.add("Alice"); cosc50.add("Charlie");
        courseToStudents.put("COSC 50", cosc50);
        Set<String> chem5 = new TreeSet<>();
        courseToStudents.put("CHEM 5", chem5);
        Set<String> chem6 = new TreeSet<>();
        chem6.add("Alice"); chem6.add("Dory"); chem6.add("Elvis");
        courseToStudents.put("CHEM 6", chem6);
        question16 test = new question16();
        System.out.println(test.getDeptToStudents(deptToCourse, courseToStudents));

    }
}
