public class ExamClass {
    private String name;
    private double estimatedValue;

    public ExamClass(String name, double value){
        this.name = name;
        this.estimatedValue = value;
    }

    public double getEstimatedValue(){
        return estimatedValue;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEstimatedValue(double value){
        this.estimatedValue = value;
    }

    public String toString(){
        return name + " valued at $" + estimatedValue + "M";


    }

    public static void main(String[] args) {
        ExamClass monaLisa = new ExamClass("Mona Lisa", 100.5);
       // monaLisa.setEstimatedValue(100.5); monaLisa.setName("Mona Lisa");
        //System.out.println(monaLisa.getEstimatedValue());
        //System.out.println(monaLisa.getName());
        System.out.println(monaLisa.toString());
    }
}
