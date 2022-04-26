import java.util.Map;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class question4 {
    public Map<String, Set<String>> getAreaCodeToPeople(Map<String, String> personToPhone, Set<String> areaCodeMap){
        Map<String, Set<String>> areaCodeToPeople = new TreeMap<>();
        for(String person : personToPhone.keySet()){
            String areaCode = personToPhone.get(person).substring(0, 3);
            if(!areaCodeToPeople.containsKey(areaCode) && areaCodeMap.contains(areaCode)){
                Set<String> people = new TreeSet<>();
                areaCodeToPeople.put(areaCode, people);
            }
            areaCodeToPeople.get(areaCode).add(person);
        }
        return areaCodeToPeople;
    }

    public static void main(String[] args) {
        Map<String, String> personToPhone = new TreeMap<>();
        personToPhone.put("Alice", "765-555-1234");
        personToPhone.put("Bob", "650-555-9999");
        personToPhone.put("Charlie", "765-555-1111");
        personToPhone.put("Denise", "650-555-6666");
        personToPhone.put("Elvis", "901-555-0000");
        Set<String> areaCodeMap = new TreeSet<>();
        areaCodeMap.add("765"); areaCodeMap.add("650"); areaCodeMap.add("901");
        question4 test = new question4();
        System.out.println(test.getAreaCodeToPeople(personToPhone, areaCodeMap));





    }

}
