public class question9 {
    public static void printLineForNode(BinaryTree<String> root){
        String s = "" + root.getData();
        if(root.hasLeft()) {
            s += "," + root.getLeft().getData();
        }
        if(root.hasRight()){
            s += "," + root.getRight().getData();
        }
        System.out.println(s);
        if(root.hasLeft()) printLineForNode(root.getLeft());
        if(root.hasRight()) printLineForNode(root.getRight());
    }

    public static void main(String[] args) {
        BinaryTree<String> f = new BinaryTree<String>("f");
        BinaryTree<String> d = new BinaryTree<String>("d", f, null);
        BinaryTree<String> g = new BinaryTree<String>("g");
        BinaryTree<String> e = new BinaryTree<String>("e", null, g);

        BinaryTree<String> b = new BinaryTree<String>("b", d, e);
        BinaryTree<String> c = new BinaryTree<String>("c", null, null);

        BinaryTree<String> root = new BinaryTree<String>("A", b, c);
        printLineForNode(root);


    }
}
