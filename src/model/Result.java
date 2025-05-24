package model;
import java.util.ArrayList;

public class Result {
    private String date;
    private String studentGroup;
    private String id;

    //relations
    private ArrayList<Assignament> myAssignaments = new ArrayList<>();

    public Result(String date, String studentGroup, String id ){
        this.date = date;
        this.studentGroup=studentGroup;
        this.id = id;
    }

    // public String maxAssignaments() {
    //     if(myAssignaments.size() == 1){
    //         return "1";
    //     } else if (myAssignaments.size() == 2){
    //         return "2";
    //     } else if (myAssignaments.size() ==3){
    //         return "3";
    //     } else if(myAssignaments.size() >3){
    //         return "s";
    //     }
    //     return "0";
    // }

    public String getID(){
        return id;
    }
}
