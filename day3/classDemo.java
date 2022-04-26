import java.util.ArrayList;

public class classDemo {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<Integer>();
        a.add(1);
        a.add(2);
        a.add(1,3); // takes 2 and slides it down (starts at 0)
        System.out.println(a);
        Integer b = a.get(1); // sets b to index 1 which is 3
        System.out.println(b);
        a.remove(1); // remove 3 which is at index 1, can return item this way
        System.out.println(a);
        a.set(1,4); // sets 4 to index 1, pushes others back
        System.out.println(a);
        System.out.println(a.size()); // returns the amount of items stored in teh ArrayList

    }
}
