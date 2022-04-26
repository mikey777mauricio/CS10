import java.util.Map;

public class question14 {
    public static double eval(BinaryTree<String> t, Map<String,Double> vars) {
        if(t.isLeaf()){
            if(vars.containsKey(t.getData())) return vars.get(t.getData());
            return Double.parseDouble(t.getData());
        }
        if(t.data == "+") return eval(t.getLeft(), vars) + eval(t.getRight(), vars);
        return eval(t.getLeft(), vars) * eval(t.getRight(),vars);

    }
}
