import java.util.Stack;

public class question6 {
    public static boolean isPalindrome(String e){
        Stack<Character> charStack = new Stack<>();
        char[] charArray= e.toCharArray();
        String reversed = "";
        for(char c : charArray){
            charStack.add(c);
        }
        while(!charStack.isEmpty()){
            reversed += charStack.pop();
        }
        if(reversed.equals(e)) return true;
        return false;

    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("racecar"));
        System.out.println(isPalindrome("hahahahiii"));
    }
}
