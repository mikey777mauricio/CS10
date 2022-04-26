import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class MorseCode {
    public BinaryTree<Character> codeTree;
    private char dot;
    private char dash;
    public MorseCode(){
        codeTree = new BinaryTree<>(null);
        dot = '.';
        dash = '-';
    }

    public void addCode(Character c, String code) {
        //TODO: your code here
        char[] codeArray = code.toCharArray();
        BinaryTree<Character> tempRoot = codeTree;
        for(char dashOrDot : codeArray){
            if(dashOrDot == dot){
                if(!tempRoot.hasLeft()){
                    tempRoot.setLeft(new BinaryTree<>(null));
                }
                tempRoot = tempRoot.getLeft();
            }
            if(dashOrDot == dash){
                if(!tempRoot.hasRight()){
                    tempRoot.setRight(new BinaryTree<>(null));
                }
                tempRoot = tempRoot.getRight();
            }
        }
        tempRoot.setData(c);
    }

    public char getCharacter(String code) throws Exception {
        if(code.length() > codeTree.height()) throw new Exception("Too long");
        char[] charArray = code.toCharArray();

        BinaryTree<Character> tempRoot = codeTree;
        for(char c : charArray){
            if(c == dot){
                tempRoot = tempRoot.getLeft();
            }
            if(c == dash){
                tempRoot = tempRoot.getRight();
            }
        }
        if(tempRoot.getData() == null) throw new Exception("Code does not represent a character");
        return tempRoot.getData();
    }

    public Map<Character, String> getCodeMap(BinaryTree<Character> root) throws Exception {
        // huffman
        if(root.isLeaf()) throw new Exception("Binary Tree is Empty");
        Map<Character, String> codeMap = new TreeMap<Character, String>();
        getCodeMapHelper(root, "", codeMap);
        return codeMap;
    }

    public void getCodeMapHelper(BinaryTree<Character> current, String code, Map<Character, String> codeMap){
        if(current.getData() != null) {
            codeMap.put(current.getData(), code);
            if(current.isLeaf()) code = "";
        }

        if(current.hasRight()) {
            getCodeMapHelper(current.getRight(), code+"-", codeMap);
        }

        if(current.hasLeft()) {
            getCodeMapHelper(current.getLeft(), code+".", codeMap);
        }


    }


    public static void main(String[] args) throws Exception {
        MorseCode test = new MorseCode();
        test.addCode('e', ".");
        test.addCode('t', "-");
        test.addCode('m', "--");
        test.addCode('i', "..");
        test.addCode('a', ".-");
        test.addCode('n', "-.");


        System.out.println(test.codeTree);

        System.out.println(test.getCodeMap(test.codeTree));

    }

}
