public class Student {
    private String studentName;
    private int gradYear;

    public Student(String name, int year){
        this.studentName = name;
        this.gradYear = year;
    }

    public String getName(){
        return studentName;
    }
    public int getGradYear(){
        return gradYear;
    }

    public void setStudentName(String name){
        this.studentName = name;
    }

    public void setGradYear(int year){
        this.gradYear = year;
    }




}
