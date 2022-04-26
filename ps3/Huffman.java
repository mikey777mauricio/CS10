/**
 * Name: Mikey Mauricio
 * Date: 10/14/2021
 * Purpose: Compress files into binary code and decompress binary code to characters
 * PS3
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Huffman {
    public String filename; // name of file to compress
    public ArrayList<Character> charList; // list of characters in file
    public Map<Character, Integer> freqMap; // map where key is character and value is frequency
    public PriorityQueue<BinaryTree<CharAndFreq>> charTreePQ; // priority queue based on frequency
    public BinaryTree<CharAndFreq> treeFrequencies; // binary tree which holds char as leaves
    public Map<Character, String> codeWordMap; // map where a key = character and value = codeword
    public ArrayList<Boolean> bitList; // list of bits to be turned into char


    /**
     * constructor, for initializing instance variables
     * @param filename      file for compression
     * @throws Exception        handle exceptions
     */
    public Huffman(String filename) throws Exception {
        this.filename = filename; // set filename to file
        getChars(filename); // get charList
        getFrequencyMap();  // get frequency
        getInitialTrees();  // get initial trees
        getTreeCreation();  // create large tree
        getCodeWordMap();  // create code word map
    }

    /**
     * main
     * compresses and decompresses files
     * @param args
     * @throws Exception        handle exceptions
     */
    public static void main(String[] args) throws Exception {
        // compress US Constitution file
        try {
            Huffman USConsitution2 = new Huffman("inputs/USConstitution.txt");
            USConsitution2.compression();
            USConsitution2.decompress();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // compress War and Peace file
        try {
            Huffman USConsitution = new Huffman("inputs/WarAndPeace.txt");
            USConsitution.compression();
            USConsitution.decompress();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // compress single letter file
        try {
            Huffman USConsitution = new Huffman("inputs/boundarySingleLetter.txt");
            USConsitution.compression();
            USConsitution.decompress();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // compress repeated letter file
        try {
            Huffman USConsitution = new Huffman("inputs/repeatedLetter.txt");
            USConsitution.compression();
            USConsitution.decompress();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // compress single word file
        try {
            Huffman USConsitution = new Huffman("inputs/boundarySingleWord.txt");
            USConsitution.compression();
            USConsitution.decompress();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * getChars
     * reads over a text file and adds each character to charList
     * @param filename      file to compress
     * @throws Exception        handle exceptions
     */
    private void getChars(String filename) throws Exception {
        // list of every character
        charList = new ArrayList<Character>();
        // new reader to read each char
        BufferedReader input = new BufferedReader(new FileReader(filename));
        try {

            int cInt = input.read(); // read next character's int rep
            while (cInt != -1) { // while there is another char to read
                charList.add((char) (cInt)); // cast to char and add to list
                cInt = input.read(); // read next character
            }
        } catch (Exception e) {
            throw e; // handle IOException
        } finally {
            if (input != null) input.close(); // close file no matter what
        }

    }

    /**
     * getFrequencyMap
     * uses charList and creates a map that holds characters and their frequency
     */
    private void getFrequencyMap() {
        // map to assign each individual character to its frequency
        freqMap = new TreeMap<Character, Integer>();

        for (Character c : charList) {
            // put char in map if it has not yet added
            if (!freqMap.containsKey(c)) freqMap.put(c, 0);
            // increment frequency value for char
            freqMap.put(c, freqMap.get(c) + 1);
        }
    }

    /**
     * getInitialTrees
     * using frequency map, adds subtrees to priority queue based on frequency
     */
    private void getInitialTrees() {
        // initialize PQ to use compareTo method
        charTreePQ = new PriorityQueue<BinaryTree<CharAndFreq>>((BinaryTree<CharAndFreq> s1, BinaryTree<CharAndFreq> s2)
                -> (s1.data.getFrequency() - s2.data.getFrequency()));
        // iterate through each character in freqMap
        for (Character c : freqMap.keySet()) {
            // create a new single node tree for each char
            BinaryTree<CharAndFreq> subTree = new BinaryTree(new CharAndFreq(c, freqMap.get(c)));
            // add to priority queue
            charTreePQ.add(subTree);
        }
    }

    /**
     * getTreeCreation
     * creates one large binary tree for creating codewords for characters from priority queue
     */
    private void getTreeCreation() {
        // handle single letter case
        if (charTreePQ.size() == 1){
            BinaryTree<CharAndFreq> t1 = charTreePQ.remove(); // step 1, remove min frequency char
            CharAndFreq r = new CharAndFreq(null, (t1.getData().getFrequency()));
            // new tree with node as root and right/left as t1 and t2
            BinaryTree<CharAndFreq> t = new BinaryTree<CharAndFreq>(r, t1, null);
            charTreePQ.add(t); // add to treePQ
        }
        while (charTreePQ.size() > 1) {
            BinaryTree<CharAndFreq> t1 = charTreePQ.remove(); // step 1, remove min frequency char
            BinaryTree<CharAndFreq> t2 = charTreePQ.remove(); // step 2, remove next min frequency char
            // new node with no char key and sum of frequency
            CharAndFreq r = new CharAndFreq(null, (t1.getData().getFrequency()) + t2.getData().getFrequency());
            // new tree with node as root and right/left as t1 and t2
            BinaryTree<CharAndFreq> t = new BinaryTree<CharAndFreq>(r, t1, t2);
            charTreePQ.add(t); // add to priority queue
        }
        // return only remaining tree
        treeFrequencies = charTreePQ.remove();
    }

    /**
     * getCodeWordMap
     * uses binary tree and codeWordHelper to create codeWordMap
     */
    private void getCodeWordMap() {
        // initialize codeWord Map
        codeWordMap = new TreeMap<Character, String>();
        String pathSoFar = "";
        // set equal to result of codeWordHelper
        codeWordHelper(treeFrequencies, pathSoFar);
    }

    /**
     * codeWordHelper
     * creates codeword for each character and puts character into codeWordMap
     * this is a helper method for getCodeWordMap
     * @param treeFrequencies       keeps track of tree traversal
     * @param pathSoFar     keeps track of codeword
     */
    private void codeWordHelper(BinaryTree<CharAndFreq> treeFrequencies, String pathSoFar) {
        if (treeFrequencies.isLeaf()) {
            codeWordMap.put(treeFrequencies.data.getCharacter(), pathSoFar);
        } else {
            if (treeFrequencies.hasLeft()) codeWordHelper(treeFrequencies.getLeft(), pathSoFar + '0');
            if (treeFrequencies.hasRight()) codeWordHelper(treeFrequencies.getRight(), pathSoFar + '1');
        }
    }

    /**
     * compressCharacter
     * uses charList and codeWordMap to create an arrayList of bits
     * this is a helper method for compression
     */
    private void compressCharacter() {
        // new bitList to store bits
        bitList = new ArrayList<>();
        // iterate over each character
        for (Character c : charList) {
            // get code word
            String codeWord = codeWordMap.get(c);
            // cast code word to array for iteration
            char[] charList = codeWord.toCharArray();
            // iterate over code word
            for (char bit : charList) {
                // add boolean to corresponding bit
                if (bit == '1') {
                    bitList.add(true);
                } else if (bit == '0') bitList.add(false);
            }
        }
    }

    /**
     * compression
     * uses the bit list to write compressed version of file
     * @throws IOException      handle exceptions
     */
    public void compression() throws IOException {
        BufferedBitWriter bitOutPut = new BufferedBitWriter(filename.substring(0,filename.length() -4) + "_compressed.txt");
        compressCharacter(); // get bitList
        try {
            // iterate over bit list
            for (boolean bit : bitList) {
                bitOutPut.writeBit(bit); // write bit to compressed file
            }
        } catch (Exception e) {
            throw e; // handle Exception
        } finally {
            bitOutPut.close(); // close file
        }
    }

    /**
     * decompress
     * iterates over bitList and writes corresponding character into decompressed file
     * @throws Exception        handles exception
     */
    public void decompress() throws Exception {
        if (bitList == null) throw new Exception("Have not compressed yet.");
        BufferedWriter output = new BufferedWriter(new FileWriter(filename.substring(0,filename.length() -4)+"_decompressed.txt"));
        // get bitList
        // new current path to keep track of paths
        BinaryTree<CharAndFreq> currentPathTree = treeFrequencies;
        // iterate through bit list
        try {
            for (Boolean bit : bitList) {
                // traverse tree until leaf
                if (bit) {
                    currentPathTree = currentPathTree.getRight(); // go to right child
                } else if (!bit) {
                    currentPathTree = currentPathTree.getLeft(); // go to left child
                }
                // when leaf is hit
                if (currentPathTree.isLeaf()) {
                    // add the character that corresponds leaf
                    output.write(currentPathTree.data.getCharacter());
                    currentPathTree = treeFrequencies; // reset current path
                }
            }
        } catch (Exception e) {
            throw e;
        }
        output.close();
    }


}
