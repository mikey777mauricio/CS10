public class QFour {
    int min; int size;
    public QFour(){
        size = 0;
    }
    public void insert(int e){
        if(size==0) {min = e; size +=1;}
        else if( e < min){
            min = e;
            size += 1;
        }
    }
    public int minimum()
    {
        return min;
    }

}
