import java.util.*;

public class StudentMap {
    public static void main(String[] args) {
        Map<String, List<String>> studentMap = new TreeMap<String, List<String>>();
        List<String> courses = new ArrayList<>();
        courses.add("CS10");
        courses.add("French 3");
        studentMap.put("Mikey Mauricio", courses);
        System.out.println("Mikey: " + studentMap.get("Mikey Mauricio"));

    }
}
