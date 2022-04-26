import java.util.*;

public class DatabaseTable {
    public Map<Integer, Map<String, String>> dataTable;
    public Map<String, String> indMap;
    public static int readCount;
    public static int writeCount;

    public DatabaseTable(){
        dataTable = new HashMap<>();
        indMap = new TreeMap<>();
        indMap.put("name", null);
        indMap.put("Year", null);
        indMap.put("hometown", null);
        indMap.put("sport", null);
        readCount = 0;
        writeCount = 0;

    }

    private String getData(int key, String field){
        // return null if null
        if(dataTable.get(key) == null) return null;
        // else return field
        return dataTable.get(key).get(field);
    }

    private void updateData(int key, String rowData){
        // split row data
        String[] fields = rowData.split(",");
        // add key if not in
        if(!dataTable.containsKey(key)) dataTable.put(key, indMap);
        // iterate through each field
        for(String x : fields){
            String[] splitFields = x.split(";");
            String field = splitFields[0];
            String value = splitFields[1];
            // add to table
            dataTable.get(key).put(field, value);
        }
    }

    public synchronized void takeReadLock() throws InterruptedException {
        // while readCount is more than 0
        try {while(readCount > 0) wait();} // wait
        catch (Exception e){
            System.out.println("Error");
            throw e;
        }
        readCount += 1; // increment

    }

    public synchronized void releaseReadLock() {
        readCount -= 1; // decrement 1
        notifyAll(); // notify all
    }

    public synchronized void takeWriteLock() throws InterruptedException {
        writeCount += 1; // decrement by 1
        try {
            while (readCount > 0 || writeCount > 0) wait(); // wait if no key
        }
        catch (Exception e){
            System.out.println("Error");
            throw e;
        }
    }

    public synchronized void releaseWriteLock(){
        writeCount -= 1; // decrement by 1
        notifyAll(); // notify al
    }

    public synchronized void read(int key, String field) throws InterruptedException {
        while(readCount > 0) wait(); // wait while no key
        takeReadLock(); // take key
        getData(key, field); // get data
        releaseReadLock(); // return key
    }

    public synchronized void write(int key, String rowData) throws InterruptedException {
        while(writeCount > 0) wait(); // wait while no write key
        takeWriteLock(); // take key
        write(key, rowData); // write to data table
        releaseWriteLock(); // release key for others
    }

}


