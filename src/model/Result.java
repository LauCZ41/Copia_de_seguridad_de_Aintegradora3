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
}
