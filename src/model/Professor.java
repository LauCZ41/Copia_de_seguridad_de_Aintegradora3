package model;

import java.util.ArrayList;

public class Professor{
    private String name;
    private String id;
    private String email;
    private  iDType idType;
    private String uniqIdentifier;


    //relations
    private ArrayList<Course> courses;

    /**
     * Constructor of the Professor class
     * @param uniqIdentifier String
     * @param name String, the name of the pofressor
     * @param id String, the id of the professor
     * @param email String, the email of the professor 
     * @param idType iDType, is the type of id the Professor has
     */
    public Professor(String name, String id, String email, iDType idType, String uniqIdentifier){
        this.name = name;
        this.id=id;
        this.email=email;
        this.idType=idType;
        this.uniqIdentifier=uniqIdentifier;
        this.courses = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    //AXXXXXXXX
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);

            if (course.getProfessor() != this) {
                course.setProfessor(this);
            }
        }
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public String getID(){
        return uniqIdentifier;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}