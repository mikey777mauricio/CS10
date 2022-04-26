import java.util.Scanner;
public class InputExample {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Age: ");
        double age = input.nextDouble();
        System.out.println("You are " + age + " years old.");
    }
}
