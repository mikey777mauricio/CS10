public class FactorialFunction {
    public FactorialFunction(){

    }
    public static int factorial(int x){
        if(x == 0) return 1;
        return x * factorial(x-1);
    }
    public static int fib(int x){
        if(x<=1) return 1;
        System.out.println(x);
        return factorial(x-1) + factorial(x-2);
    }

    public static void main(String[] args) {
        System.out.println(factorial(5));
        System.out.println(fib(5));
    }
}
