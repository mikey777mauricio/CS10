import java.util.List;

public class BinarySearch {
    public static boolean binarySearch(List<Integer> data, int target, int low, int high){
        if(low>high) return false;
        else{
            int mid = (low+high)/2;
            if(target == data.get(mid))
            {
                return true;

            }
            else if(target < data.get(mid))
            {
                System.out.println(mid);
                return binarySearch(data, target, low, mid -1);

            }
            else  {
                System.out.println(mid);
                return binarySearch(data, target, mid + 1, high);
            }
        }
    }

    public static void main(String[] args) {
        List<Integer> numList = List.of(0,1,2,3,4,5,6,7,8,0,10);
        System.out.println(binarySearch(numList, 2, 1, 9));


    }
}
