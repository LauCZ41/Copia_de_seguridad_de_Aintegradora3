package model;

import java.util.ArrayList;

public class Course {
    private String code;
    private String name;
    private String description;
    private int numberOfCredits;



    //relations
    private ArrayList<Project> myProjects = new ArrayList<>();
    private Professor professor;


    public Course(String name, String description, int  numberOfCredits, String code){
        this.name=name;
        this.description= description;
        this.numberOfCredits=numberOfCredits;
        this.code=code;
    }

    public String addProject(String name, ArrayList<String> keyWords,String description ,ArrayList<String>  associatedBusiness, String link, String semester, TypeOfProject typeOfProject, String uniqId) {
        myProjects.add( new Project(name, keyWords, description, associatedBusiness, link, semester, typeOfProject, uniqId));
        return "Project added";
    }

  
    public boolean searchProject(String projectID){
        for(Project p : myProjects){
            if(p.getID().equals(projectID)){
                return true;
            }
        }
        return false;
    }

    public String displayProjectInfo(String projectID){
        boolean verify = searchProject(projectID);
        if(verify){
            for(Project p : myProjects){
                if(p.getID().equals(projectID)){
                    return "This is the project info:\n" + p.toString();
                }
            }
            return "Project ID exists but no matching project was found.";
        } else {
            return "That ID does not exist";
        }
    }

    public String changeInformation(String newX, String projectID, int option){
        for(Project p : myProjects){
            if(p.getID().equals(projectID)){
                switch(option){
                    case 1: p.setName(newX);
                        break;
                    case 2: p.setDescription(newX);
                        break;
                    case 3: p.setLink(newX);
                        break;
                }
                return "The information was successfully changed\n";
            }
        }
        return "That ID does not exist";
    }

    public String changeTypeOfProject(int  newTypeOfProject, String projectID){
        TypeOfProject typeOfProject = TypeOfProject.INTEGRADORA;
        for(Project p : myProjects){
            if(p.getID().equals(projectID)){
                switch(newTypeOfProject){
                    case 1: typeOfProject = TypeOfProject.INTEGRADORA;
                        break;
                    case 2: typeOfProject = TypeOfProject.P_CURSO;
                        break;
                    case 3: typeOfProject = TypeOfProject.P_FINAL;
                }
                p.setTypeOfProject(typeOfProject);
                return "The information was successfully changed\\n";
            }
        }
        return "Error";
    }

    public String changeKeyWordList(String projectID, ArrayList<String> deleteKeyWords, ArrayList<String> newKeyWords) {
        for (Project p : myProjects) {
            if (p.getID().equals(projectID)) {
                for (int i = 0; i < deleteKeyWords.size(); i++) {
                    String wordToDelete = deleteKeyWords.get(i);
                    p.removeKeyword(wordToDelete);
                }

                p.addKeywords(newKeyWords);
    
                return "Key words updated successfully.";
            }
        }
        return "ID NOT FOUND";
    }
    
    public String changeAssosiatedBussinesList(String projectID, ArrayList<String> deletBussines, ArrayList<String> neweBussines) {
        for (Project p : myProjects) {
            if (p.getID().equals(projectID)) {
                for (int i = 0; i< deletBussines.size();i++){
                    String wordToDelete = deletBussines.get(i);
                    p.removeAssociatedBusiness(wordToDelete);
                }

                p.addAssociatedBusinesses(neweBussines);
                return "Assosiated bussines updated successfully.";
            }
        }
        return"ID NOT FOUND";
    }


    public String getName(){
        return name;
    }

    public String getCode(){
        return code;
    }
    public Professor getProfessor() {
        return professor;
    }
    // axxxxxxxxxqwwwww
    public void setProfessor(Professor professor) {
        this.professor = professor;
        if (professor != null && !professor.getCourses().contains(this)) {
            professor.addCourse(this); 
        }
    }

    public String showProjects() {
        StringBuilder sb = new StringBuilder();

        int count = 1;
        for (Project p : myProjects) {
            sb.append(count++)
            .append("- ")
            .append(p.getName())
            .append(" ")
            .append("ID: ")
            .append(p.getID())
            .append(" Description: ")
            .append(p.getDescription())
            .append("\n");
        }

        if (sb.length() == 0) {
            return "No projects registered. \n";
        }

        return sb.toString();
    }

    public String addResult(String link, String description, String uniqIdentificator, String projectID){
        for(Project p:myProjects){
            p.addResult(link, description, uniqIdentificator);
            return "";
        }
        return  "";
    }

    public String maxResult(String iDproject){
        for(Project p: myProjects){
            if(p.getID().equals(iDproject)){
                return p.maxResults();
            }
        }
        return "Error";
    }

    public StringBuilder projectsWithNoResult() {
        StringBuilder message = new StringBuilder();
        boolean found = false;
        for (Project p : myProjects) {
            if (p.isEmpthyR()) {
                message.append("Project: ").append(p.getName())
                    .append(" - ID: ").append(p.getID()).append("\n");
                found = true;
            }
        }

        if (!found) {
            message.append("All projects in this course have at least one result.\n");
        }

        return message;
    }

    
    // public String maxAssignaments(String projectID, String recurseID){
    //     for(Project p:myProjects){
    //         if(p.getID().equals(projectID)){
    //             return p.masAssignament(recurseID);
    //         }
    //     }
    //     return "x";
    // }
}
