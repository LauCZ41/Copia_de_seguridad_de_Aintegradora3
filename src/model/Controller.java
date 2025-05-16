package model;

import java.util.ArrayList;
import java.util.UUID;

public class Controller {

    public Controller(){
    }

    //relations
    private ArrayList<Professor> myProfessors = new ArrayList<>();
    private ArrayList<Course> myCourses = new ArrayList<>();

    public String registerCourse(String name, String description, int  numberOfCredits){
        String id = generateCourseID();
        myCourses.add(new Course(name, description, numberOfCredits, id));
        return id;
    }

    public String registerProfesor(String name, String id, String email, int iDtypeNumber) {
        String uId = generateProfessorID();
        iDType iDtype;
        switch (iDtypeNumber) {
            case 1:
                iDtype = iDType.CEDULA;
                break;
            case 2:
                iDtype = iDType.PASSAPORTE;
                break;
            case 3:
                iDtype = iDType.CC_EXTRANJERIA;
                break;
            default:
                return "Tipo de identificación inválido.";
        }
        myProfessors.add(new Professor(name, id, email, iDtype, uId));
        return "added professor ID: " + uId + "\n" ;
    }


    public boolean searchSameProfessor(String id) {
        for (Professor p : myProfessors) {
            if (p.getId().equalsIgnoreCase(id)) {
                return false;
            }
        }
        return true;
    }

    public String generateCourseID() {
        return "C-" + UUID.randomUUID().toString().substring(0, 8);
    }
    
    public String generateProfessorID() {
        return "P-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public String generateProject() {
        return "Pro-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public String generateResultID(String projectID, String number, String studentGroup){
        String x = UUID.randomUUID().toString().substring(0, 8);
        return ""+projectID+"_"+ x +"_"+number+"_"+studentGroup;
    }

    public String registerProject(String courseId, String name, ArrayList<String> keyWords,String description ,ArrayList<String>  associatedBusiness, String link, String semester, int typeOfProjectN){
        String uniqId = generateProject();
        TypeOfProject typeOfProject = TypeOfProject.INTEGRADORA;
        switch(typeOfProjectN){
            case 1: typeOfProject = TypeOfProject.INTEGRADORA;
                break;
            case 2: typeOfProject = TypeOfProject.P_CURSO;
                break;
            case 3: typeOfProject = TypeOfProject.P_FINAL;
                break;
        }
        for (Course c : myCourses) {
            if (c.getCode().equals(courseId)) {
                c.addProject(name, keyWords, description, associatedBusiness, link, semester, typeOfProject, uniqId);
                return "The project has been successfully added, its ID: " + uniqId ;
            }
        }
        return "There is no course with a matching ID. \n";
    } 

    public boolean searchCourse(String courseId){
        for(Course c: myCourses){
            if(c.getCode().equals(courseId)){
                return true; 
            }
        }
        return false;
    }

    // public String searchProject(String projectID){
    //     return projectID;
    // }

    public boolean searchProjectt(String porjectID){
        boolean a = false;
        for(Course c: myCourses){
          a = c.searchProject(porjectID);
          return a;
        }
        return a;
    }

    public String displayProjectInfoGlobal(String projectID) {
        for (Course c : myCourses) {
            if (c.searchProject(projectID)) {
                return c.displayProjectInfo(projectID);
            }
        }
        return "That project ID does not exist in any course.\n";
    }

    public String changeInformation(String newX, String projectID, int option){
        for (Course c : myCourses) {
            if (c.searchProject(projectID)) {
                return c.changeInformation(newX, projectID, option);
            }        
        }
        return "That project ID does not exist in any course.\n";
    }
    
    public String changeTypeOfProject(String projectID, int typeOfProject){
        for (Course c : myCourses) {
            if (c.searchProject(projectID)) {
                return c.changeTypeOfProject(typeOfProject, projectID);
            }
        }
        return "That project ID does not exist in any course.\n";
    }

    public String changeKeyWordList(String projectID, ArrayList<String> deletKeyWords, ArrayList<String> neweyWords){
        for (Course c : myCourses) {
            if (c.searchProject(projectID)) {
                return c.changeKeyWordList(projectID, deletKeyWords, neweyWords);
            }
        }
        return "Course not found\n";
    }

    public String changeAssosiatedBussinesList(String projectID, ArrayList<String> deletBussines, ArrayList<String> neweyBussines){
        for (Course c : myCourses) {
            if (c.searchProject(projectID)) {
                return c.changeAssosiatedBussinesList(projectID, deletBussines, neweyBussines);
            }
        }
        return "Course not found\n";
    }

    public String linkCourseToProfessor(String professorID, String courseID){
        Professor foundProfessor = null;
        Course foundCourse = null;
    
        for (Professor p : myProfessors) {
            if (p.getID().equals(professorID)) {
                foundProfessor = p;
                break;
            }
        }
    
        for (Course c : myCourses) {
            if (c.getCode().equals(courseID)) {
                foundCourse = c;
                break;
            }
        }

        if (foundProfessor != null && foundCourse != null) {
            foundProfessor.addCourse(foundCourse); 
            return "Course assigned to professor successfully.\n";
        } else {
            return "Professor or course not found. \n";
        }
    }

    public String linkProfessorToCourse(String courseID, String professorID) {
        Course foundCourse = null;
        Professor foundProfessor = null;
    
        for (Course c : myCourses) {
            if (c.getCode().equals(courseID)) {
                foundCourse = c;
                break;
            }
        }
    
        for (Professor p : myProfessors) {
            if (p.getID().equals(professorID)) {
                foundProfessor = p;
                break;
            }
        }
    
        if (foundCourse != null && foundProfessor != null) {
            foundCourse.setProfessor(foundProfessor); 
            return "Professor assigned to course successfully. \n";
        } else {
            return "Course or professor not found. \n";
        }
    }

    public String searchProfessor(String id){
        for(Professor p: myProfessors){
            if(p.getID().equals(id)){
                return "Searching...";
            }
        }
        return "This id does not match with any Professor";
    }

    public String registerResult(String date, String studentGroup, String projectID, String number){
        String uniqIdentificator = generateResultID(projectID, number, studentGroup);
        for(Course c: myCourses){
            if(c.searchProject(projectID)){
                c.addResult(date, studentGroup, uniqIdentificator,  projectID);
                return uniqIdentificator;
            }
        }

        return "ERROR";
    }


    public String showAllProjects(){
        StringBuilder sb = new StringBuilder();

        for (Course c : myCourses) {
            sb.append("Course: ").append(c.getName()).append("\n");
            sb.append(c.showProjects()).append("\n");
        }

        if (sb.length() == 0) {
            return "There are no projects.";
        }

        return sb.toString();
    }

    public String maxAssignaments(String projectID, String recurseID){
        for(Course c: myCourses){
            if(c.searchProject(projectID)){
                return c.maxAssignaments(projectID, recurseID);
            }
        }
        return "x";
    }

}
