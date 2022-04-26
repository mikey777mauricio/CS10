import java.util.*;

public class question10 {
    public static Map<String, Integer> getFreqMap(List<String> words){
        Map<String, Integer> freqMap = new TreeMap<String, Integer>();
        for(String s : words){
            if(!freqMap.containsKey(s)) freqMap.put(s, 0);
            freqMap.put(s, freqMap.get(s) +1);
        }
        return freqMap;
    }

    public static Map<String, Integer> getLoosyMap(Map<String, Integer> freqMap, int min){
        Map<String, Integer> loosMap = new TreeMap<>();
        for(String s : freqMap.keySet()){
            if(freqMap.get(s) < min){
                if(!loosMap.containsKey("foos")) loosMap.put("foos", 0);
                loosMap.put("foos", loosMap.get("foos") +1);
            }
            else{
                loosMap.put(s, freqMap.get(s));
            }
        }
        return loosMap;
    }



    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("Hey");words.add("Hey");words.add("Hi");words.add("Hi");words.add("Hi");
        words.add("ey");words.add("y");
        Map<String, Integer> test = getFreqMap(words);
        System.out.println(getLoosyMap(test, 2));
    }
}
