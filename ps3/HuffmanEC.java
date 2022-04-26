import java.io.*;
import java.util.*;

public class HuffmanEC {

    // get list of all characters (handle exception)
    public static ArrayList<Character> getChars(String filename) throws IOException {
        // list of every character
        ArrayList<Character> charList = new ArrayList<Character>();
        // new reader to read each char
        BufferedReader input = new BufferedReader(new FileReader(filename));

        try {
            int cInt = input.read(); // read next character's int rep
            while (cInt != -1) { // while there is another char to read
                charList.add((char)(cInt)); // cast to char and add to list
                cInt = input.read(); // read next character

            }
        }
        catch(IOException e){
            System.out.println(e.getMessage()); // handle IOException
        }

        finally {
            input.close(); // close file no matter what
        }
        return charList; // return character list

    }

    // get frequency maps using character list
    public static Map<Character, Integer> getFrequencyMap(ArrayList<Character> charList){
        // map to assign each individual character to its frequency
        Map<Character, Integer> freqMap = new TreeMap<Character, Integer>();

        for(Character c : charList){
            // put char in map if it has not yet added
            if(!freqMap.containsKey(c)) freqMap.put(c, 0);
            // increment frequency value for char
            freqMap.put(c, freqMap.get(c) + 1);
        }
        // return frequency map
        return freqMap;
    }

    // get initial character trees and add to priority queue
    public static PriorityQueue<BinaryTree<CharAndFreq>> getInitialTrees(Map<Character, Integer> freqMap){
        // create new class for frequency comparator
        class FreqComp implements Comparator<BinaryTree<CharAndFreq>>{
            // override compare with CharAndFreq compareTo function
            public int compare(BinaryTree<CharAndFreq> t1, BinaryTree<CharAndFreq> t2){
                return t1.data.compareTo(t2.data);
            }
        }
        // initialize comp as new comparator
        Comparator<BinaryTree<CharAndFreq>> treeComparator = new FreqComp();
        // initialize PQ to use treeComparator
        PriorityQueue<BinaryTree<CharAndFreq>> charTreePQ = new PriorityQueue<>(treeComparator);
        // iterate through each character in freqMap
        for(Character c : freqMap.keySet()){
            // create a new single node tree for each char
           BinaryTree<CharAndFreq> subTree = new BinaryTree(new CharAndFreq(c, freqMap.get(c)));
           // add to priority queue
           charTreePQ.add(subTree);
        }
        return charTreePQ;
    }


    public static BinaryTree<CharAndFreq> getTreeCreation(PriorityQueue<BinaryTree<CharAndFreq>> charPQ){
        while(charPQ.size() > 1) {
            BinaryTree<CharAndFreq> t1 = charPQ.remove(); // step 1, remove min frequency char
            BinaryTree<CharAndFreq> t2 = charPQ.remove(); // step 2, remove next min frequency char
            // new node with no char key and sum of frequency
            CharAndFreq r = new CharAndFreq(null, (t1.getData().getFrequency()) + t2.getData().getFrequency());
            // new tree with node as root and right/left as t1 and t2
            BinaryTree<CharAndFreq> t = new BinaryTree<CharAndFreq>(r, t1, t2);
            charPQ.add(t); // add to priority queue
        }
        // return only remaining tree
        return charPQ.remove();
    }



    public static Map<Character, String> getCodeWordMap(BinaryTree<CharAndFreq> treeFrequencies){
        // initialize codeWord Map
        Map<Character, String> codeWordMap = new TreeMap<Character, String>();
        String pathSoFar = "";
        // set equal to result of codeWordHelper
        codeWordHelper(treeFrequencies, pathSoFar, codeWordMap);
        // return codeWordMap
        return codeWordMap;
    }

    public static void codeWordHelper(BinaryTree<CharAndFreq> treeFrequencies, String pathSoFar, Map<Character, String> codeMap){
        if(treeFrequencies.isLeaf()){
            codeMap.put(treeFrequencies.data.getCharacter(), pathSoFar);

        }
        else {
            if (treeFrequencies.hasLeft()) codeWordHelper(treeFrequencies.getLeft(), pathSoFar + '0', codeMap);
            if (treeFrequencies.hasRight()) codeWordHelper(treeFrequencies.getRight(), pathSoFar + '1', codeMap);
        }


    }
    // get BT(Char, Freq) for compression
    public static BinaryTree<CharAndFreq> getCharFreqBT(String filename) throws IOException {
        // get array list of characters
        ArrayList<Character> charArrayList = getChars(filename);
        // get code map
        Map<Character, Integer> frequencyMap = getFrequencyMap(charArrayList);
        // get priority queue of initial trees
        PriorityQueue<BinaryTree<CharAndFreq>> initialTreePQ = getInitialTrees(frequencyMap);
        // get binary tree
        BinaryTree<CharAndFreq> freqBinaryTree = getTreeCreation(initialTreePQ);
        // return tree
        return freqBinaryTree;
    }

    // helper for compression, get charArrayList
    public static ArrayList<Boolean> compressCharacter(String filename) throws IOException {
        // get charList
        ArrayList<Character> charArrayList = getChars(filename);
        // get BT(Character, Frequency)
        BinaryTree<CharAndFreq> freqBinaryTree = getCharFreqBT(filename);
        // get code word map
        Map<Character, String> codeWordMap = getCodeWordMap(freqBinaryTree);
        // initialize a new bitList
        ArrayList<Boolean> bitList = new ArrayList<>();
        // iterate over each character
        for (Character c : charArrayList) {
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
        return bitList; // return bitList for compression
    }

    public static ArrayList<Character> compressBinaryTree(BinaryTree<CharAndFreq> binaryTreeUnComp){
        ArrayList<Character> compressedBinaryTree= new ArrayList<>();
        compressBinaryTreeHelper(binaryTreeUnComp, compressedBinaryTree);
        return compressedBinaryTree;
    }

    public static void compressBinaryTreeHelper(BinaryTree<CharAndFreq> pathSofar, ArrayList<Character> compBT){
        if (pathSofar.isLeaf()) compBT.add(pathSofar.data.getCharacter());
        else {
            if (pathSofar.hasLeft()) {
                compBT.add('<');
                compressBinaryTreeHelper(pathSofar.getLeft(), compBT);
            }
            if (pathSofar.hasRight()) {
                compBT.add('>');
                compressBinaryTreeHelper(pathSofar.getRight(), compBT);
            }
        }
    }


    // using the helper methods, convert file to a new compressed file
    public static BinaryTree<CharAndFreq> compression(String filename) throws IOException {
        BufferedBitWriter bitOutPut = new BufferedBitWriter("ps3/CompressedFile.txt");
        // get binary tree to return for decompression
        BinaryTree<CharAndFreq> freqBinaryTree = getCharFreqBT(filename);
        // get bitList
        ArrayList<Boolean> compressedCharList = compressCharacter(filename);

        try {
            // iterate over bit list
           for (boolean bit : compressedCharList) {
               bitOutPut.writeBit(bit); // write bit to compressed file
           }
       }
        catch(Exception e){
           System.out.println(e.getMessage());
       }
        finally {
           bitOutPut.close(); // close file
       }

           return freqBinaryTree; // return tree for decompression
    }

    // get Bit array for decompression
    public static ArrayList<Boolean> getBitArray(String filename) throws IOException {
        // bit array
        ArrayList<Boolean> bitList = new ArrayList<>();
        // new bit reader
        BufferedBitReader bitInput = new BufferedBitReader(filename);
        //go through each bit
        try {
            while (bitInput.hasNext()) {
                boolean bit = bitInput.readBit();
                // do something with bit
                bitList.add(bit);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            bitInput.close();
        }
        return bitList;
    }

    // get decompressed character list for decompression output
    public static ArrayList<Character>  getDecompressedChar(ArrayList<Boolean> bitArray, BinaryTree<CharAndFreq> freqBinaryTree){
        // new array list
        ArrayList<Character> charList = new ArrayList<>();
        // new current path to keep track of paths
        BinaryTree<CharAndFreq> currentPathTree = freqBinaryTree;
        // iterate through bit list
        for(Boolean bit : bitArray) {
            // traverse tree until leaf
            if (bit) {currentPathTree = currentPathTree.getRight();}
            else if(!bit) {currentPathTree = currentPathTree.getLeft();}
            // when leaf is hit
            if (currentPathTree.isLeaf()) {
                // add the character that corresponds leaf
                charList.add(currentPathTree.data.getCharacter());
                currentPathTree = freqBinaryTree; // reset current path
            }
        }

        return charList; // return character list

    }

    // using helper methods, decompress file and write to new file
    public static void decompress(String filename, BinaryTree<CharAndFreq> freqBinaryTree) throws IOException {
        // get bitList
        ArrayList<Boolean> bitArray = getBitArray(filename);
        // new writer
        BufferedWriter output = new BufferedWriter(new FileWriter("ps3/DecompressedFile"));
        // get char list using bit array and freq tree
        ArrayList<Character> charList = getDecompressedChar(bitArray, freqBinaryTree);
        try {
            for (char c : charList) output.write(c); // write char to new file
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            {
                output.close(); // close file
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BinaryTree<CharAndFreq> binTree= compression("ps3/USConstitution.txt");
        System.out.println(binTree);
        System.out.println(compressBinaryTree(binTree));
//        System.out.println(binTree);
//        decompress("ps3/CompressedFile.txt", binTree);



    }


}
