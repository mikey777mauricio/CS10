import java.util.Map;
import java.util.TreeMap;

public class BankBranch <T>{
    int numLines;
    BST<Integer, SLLQueue<T>> lines;
    Map<Integer, Integer> sizes;
    public BankBranch(int size){
        this.numLines = size;
        this.lines = new BST<Integer, SLLQueue<T>>(0, new SLLQueue<T>());
        sizes = new TreeMap<>();
        for(int i = 0; i < numLines; i++){
            lines.insert(i, new SLLQueue<>());
            sizes.put(i, 0); // initially all sizes 0
        }
    }
    public void addCustomer(T customer){
        int minLine = 0;
        for(int i : sizes.keySet()){
            if(sizes.get(i) < sizes.get(minLine)) minLine = i;
        }
        lines.find(minLine).enqueue(customer);
        sizes.put(minLine, sizes.get(minLine)+1);
    }

    public T nextCustomer(int i) throws Exception {
        if(sizes.get(i) == 0) return null;
        if(i < 0 | i >= numLines) return null;
        sizes.put(i, sizes.get(i) - 1);
        return lines.find(i).dequeue();
    }

    public String toString(){
        String res = "";
        for(int i = 0; i<numLines; i++){
            res += "Line " + i + "\n";
            res += "\t" + "Number waiting: " + sizes.get(i) + "\n";
            try {
                res += "\t" + "Next customer: " + lines.find(i).front() + "\n";
            }
            catch (Exception e){
                res += "\t" + "Empty \n";
            }
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        BankBranch test = new BankBranch(3);
        test.addCustomer("Mikey");
        test.addCustomer("Julie");
        test.addCustomer("Rory");
        test.addCustomer("Tofirstline");
        System.out.println(test);
        test.nextCustomer(0);
        System.out.println(test);
        test.addCustomer("Mikey");
        test.addCustomer("Julie");
        test.addCustomer("Rory");
        test.addCustomer("Tofirstline");
        System.out.println(test);
        test.nextCustomer(0);
        test.nextCustomer(0);
        test.nextCustomer(0);
        test.nextCustomer(1);
        test.nextCustomer(1);
        test.nextCustomer(2);
        test.nextCustomer(2);
        System.out.println(test);
        System.out.println(test.lines);
        test.nextCustomer(4);

    }

}
