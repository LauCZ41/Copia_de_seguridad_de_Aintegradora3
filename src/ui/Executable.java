package ui;

import java.util.ArrayList;
import java.util.Scanner;
import model.Controller;


public class Executable{

    public static Controller myController;
    public static Scanner reader = new Scanner(System.in);
    public Executable(){
        myController = new Controller();
    }
    public static void main(String[] args) {
        Executable exe = new Executable();
        exe.menu();
    }

    public void menu(){
        int option = 3549;
        do{
            System.out.println("*** Welcome to the admin software of Facultad Barberi de Ingeniería, Diseño y Ciencias Aplicadas ***");
            System.out.println("Please select one number ");
            System.out.println("1 - Register Course");
            System.out.println("2 - Register Professor");
            System.out.println("3 - Register Poyect");
            System.out.println("4 - Search a project ");
            System.out.println("5 - Edit project information ");
            System.out.println("6 - Link Course or professor");
            System.out.println("7 - Add results ");
            System.out.println("8 - Search projects with no results ");
            System.out.println("0 - Exit \n");
            try {
                option = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("That is not a possible option, try again");
            }

            switch(option){
                case 1: registerCourse();
                    break;
                case 2: registerProfesor();
                    break;
                case 3: registerProject();
                    break;
                case 4: searchProject();
                    break;
                case 5: editProjectInfo();
                    break;
                case 6: linkCoursAndProfessor();
                    break;
                case 7: addResults();
                    break;
                case 8: projectsWithNoResults();
            }
        } while (option != 0);
    }

    public void registerCourse(){
        boolean registered = false;
        System.out.println("*** Adding a Course ***");
        System.out.println("Enter the name of the course");
        String name = reader.nextLine();
        String description;
        do{
            System.out.println("Enter the description of the course");
            System.out.println("It must have less than 250 characters");
            description = reader.nextLine();
            if(description.length()>250){
                System.out.println("This description is too long, try again \n");
            } else {
                registered = true;
            }
        }while(!registered);
        registered = false;
        int numberOfCredits = 0;
        boolean valid = false;
        do { 
            try {
                System.out.println("Enter the amount of credits of the course"); 
                numberOfCredits = Integer.parseInt(reader.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("this is not a number \n ");
            }
        } while (!valid);
        String courseID = myController.registerCourse(name, description, numberOfCredits);
        System.out.println("added course ID: " +courseID+"\n");

        boolean flag = false;
        System.out.println("Link "+ name +" Course to a professor");
        System.out.println("Enter the professor ID you want to link to this course ");
        System.out.println("Type exit if you want to skip this step for now, you can do it latter ;)" );
        System.out.println("This are the existant Professors: ");
        System.out.println(myController.showAllProfessors());
        while (!flag) {
            String professorID = reader.nextLine().trim();
            if (professorID.equalsIgnoreCase("exit")) {
                flag = true;
            } else {
                System.out.println(myController.linkCourseToProfessor(professorID, courseID));
                flag = true;
            }

        }
    }

    public void registerProfesor(){
        System.out.println("*** Adding a Professor ***");
        boolean newP = false;
        String name;
        String id;
        String email;
        int idType = 0;
        do { 
            System.out.println("Enter the name of the professor");
            name = reader.nextLine();
            do { 
                System.out.println("1 - Cedula");
                System.out.println("2 - Cedula de Extranjeria ");
                System.out.println("3 - Passaporte");
                try {
                    idType = Integer.parseInt(reader.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("That is not a possible option, try again");
                }
            } while (idType<0 || idType>4);
            System.out.println("Enter the id");
            id= reader.nextLine();
            boolean key = false;
            do { 
                System.out.println("Enter the email ");
                email= reader.nextLine();
                key = validEmail(email);
                if(!key){
                    System.out.println("That is not a valid email, try again");
                }
            } while (!key);
            newP = myController.searchSameProfessor(id);
            if (!newP) {
                System.out.println("A professor with that ID already exists. Please try again.");
            }
        } while (!newP);
        System.out.println(myController.registerProfesor(name, id, email, idType));
    }

    public void registerProject(){
        int projectType = 0;
        String courseCode, name, description, link, semester;
        ArrayList<String> keyWords = new ArrayList<>();
        ArrayList<String> associatedBusiness = new ArrayList<>();
        boolean end = false;
        do { 
            System.out.println("These are the existant Courses: ");
            System.out.println(myController.showAllCourses());
            System.out.println("Enter the code of the coures you want to link the project to");
            courseCode = reader.nextLine();
            System.out.println("Enter the name of the project");
            name = reader.nextLine();
            System.out.println("Enter the description of the project");
            description = reader.nextLine();
            System.out.println("Enter the link that contains the statement");
            link = reader.nextLine();
            System.out.println("Enter the semster and year");
            System.out.println("Example: 2025-1 or 2025-2");  
            semester = reader.nextLine().trim();  
            System.out.println("Enter key words (one by one, to exit write down exit ):");
            while (true) {
                String keyword = reader.nextLine().trim();
                if (keyword.equalsIgnoreCase("exit")) break;
                keyWords.add(keyword);
            }
            System.out.println("Enter the assosiated bussines (one by one, to exit write down exit):");
            while (true) {
                String company = reader.nextLine().trim();
                if (company.equalsIgnoreCase("exit")) break;
                associatedBusiness.add(company);
            }
            do { 
                System.out.println("Enter the type of project");
                System.out.println("1 - Integradora");
                System.out.println("2 - Proyecto de curso");
                System.out.println("3 - Proyecto final");
                try {
                    projectType = Integer.parseInt(reader.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("That is not a possible option, try again");
                }
                if(projectType<1 || projectType>3){
                    System.out.println("That is not a possible option ");
                }
            } while (projectType<0 || projectType>4);
            end = myController.searchCourse(courseCode);
            if(!end){
                System.out.println("The course id does not exist, try again \n");
            } else{
                System.out.println(myController.registerProject(courseCode, name, keyWords, description, associatedBusiness, link, semester, projectType)+"\n");
            }
        } while (!end);
    }

    public void searchProject(){
        System.out.println("Enter the project id: ");
        String projectID = reader.nextLine();
        System.out.println(myController.displayProjectInfoGlobal(projectID));
    }

    public void editProjectInfo(){
        System.out.println("***Editing***");
        System.out.println("Enter the ID of the project you want to add");
        String projectID = reader.nextLine();
        int option = 0;
        System.out.println(myController.displayProjectInfoGlobal(projectID)+ "\n");
        do { 
            System.out.println("Enter the number of what you want to edit");
            System.out.println("1 - Name");
            System.out.println("2 - Description");
            System.out.println("3 - Link");
            System.out.println("4 - Semester");
            System.out.println("5 - Type of project");
            System.out.println("6 - Key Words");
            System.out.println("7 - Assosiated Bussines");
            try {
                option = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("this is not a number \n ");
            }
            if(option<0 || option>7){
                System.out.println("That is not a possible option, try again");
            }
        } while (option<0 || option>7);
        int projectType = 0;
        switch(option){
            case 1: System.out.println("Enter the new name");
                String newName = reader.nextLine();
                myController.changeInformation(newName, projectID, option);
                break;
            case 2: System.out.println("Enter the new description");
                String newDescription = reader.nextLine();
                myController.changeInformation(newDescription, projectID, option);
                break;
            case 3: System.out.println("Enter the new link");
                String newLink = reader.nextLine();
                myController.changeInformation(newLink, projectID, option);
                break;
            case 4: System.out.println("Enter the new link");
                String newSemester = reader.nextLine();
                myController.changeInformation(newSemester, projectID, option);
                break;
            case 5: System.out.println("Enter the number of the new type of project");
                do { 
                    System.out.println("Enter the type of project");
                    System.out.println("1 - Integradora");
                    System.out.println("2 - Proyecto de curso");
                    System.out.println("3 - Proyecto final");
                    try {
                        projectType = Integer.parseInt(reader.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("That is not a possible option, try again");
                    }
                    if(projectType<1 || projectType>3){
                        System.out.println("That is not a possible option ");
                    }
                } while (projectType<0 || projectType>4);
                myController.changeTypeOfProject(projectID, projectType);
                break;
            case 6: System.out.println("Enter the word you want to REMOVE from the Key Word list");
                ArrayList<String> deletKeyWords = new ArrayList<>();
                ArrayList<String> newKeyWords = new ArrayList<>();
                System.out.println("when you are finish type exit ");
                do { 
                    String keyword = reader.nextLine().trim();
                    if (keyword.equalsIgnoreCase("exit")) break;
                    deletKeyWords.add(keyword);
                } while (true);
                System.out.println("Enter the word you want to ADD to the Key Word list");
                System.out.println("when you are finish type exit ");
                do { 
                    String keyword = reader.nextLine().trim();
                    if (keyword.equalsIgnoreCase("exit")) break;
                    newKeyWords.add(keyword);
                } while (true);
                myController.changeKeyWordList(projectID, deletKeyWords, newKeyWords);
                break;
            case 7: System.out.println("Enter the word you want to REMOVE from the Assosiated Bussines list");
                ArrayList<String> deletBussines = new ArrayList<>();
                ArrayList<String> newBussines = new ArrayList<>();
                System.out.println("when you are finish type exit ");
                do { 
                    String keyword = reader.nextLine().trim();
                    if (keyword.equalsIgnoreCase("exit")) break;
                    deletBussines.add(keyword);
                } while (true);
                System.out.println("Enter the word you want to ADD to the Assosiated Bussines list");
                System.out.println("when you are finish type exit ");
                do { 
                    String keyword = reader.nextLine().trim();
                    if (keyword.equalsIgnoreCase("exit")) break;
                    newBussines.add(keyword);
                } while (true);
                myController.changeAssosiatedBussinesList(projectID, deletBussines, newBussines);
                break;
        }
    }

    public void linkCoursAndProfessor(){
        int option = 0;
        do { 
            System.out.println("Enter the number og the option you want to slect");
            System.out.println("1 - Link a PROFESSOR to a course");
            System.out.println("2 - link a COURSE to a Profesor");
            System.out.println("--A professor can have more than one course -- ");
            System.out.println("*These two options make the same process the difference is the order you put the information* ");
            try {
                option = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("this is not a number \n ");
            }
            if(option<0 || option>7){
                System.out.println("That is not a possible option, try again");
            }
        } while (option<0 || option>2);
        switch(option){
            case 1: System.out.println("These are the existant professors: ");
                System.out.println(myController.showAllProfessors());
                System.out.println("Enter the professor's ID");
                String professorID = reader.nextLine();
                System.out.println("These are the existant Courses: ");
                System.out.println(myController.showAllCourses());
                System.out.println("Enter the course ID");
                String courseID = reader.nextLine();
                System.out.println(myController.linkProfessorToCourse(courseID, professorID));myController.linkProfessorToCourse(courseID, professorID);
                break;
            case 2: System.out.println("These are the existant Courses: ");
                System.out.println(myController.showAllCourses());
                System.out.println("Enter the course ID");
                courseID = reader.nextLine();
                System.out.println("These are the existant professors: ");
                System.out.println(myController.showAllProfessors());
                System.out.println("Enter the Professor's ID");
                professorID = reader.nextLine();
                System.out.println(myController.linkCourseToProfessor(professorID, courseID));
                break;

        }
    }

    public boolean validEmail(String email){
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public boolean validLink(String link) {
        if (link == null) return false;
        return (link.startsWith("http://") || link.startsWith("https://")) && link.contains(".");
    }

    public void addResults(){
        System.out.println("These are the existing projects:");
        System.out.println(myController.showAllProjects());

        System.out.println("Type the ID of the project you want to add results: ");
        String projectID = reader.nextLine();

        boolean founded = myController.searchProjectt(projectID);

        if (!founded) {
            System.out.println("There is no project with that ID. Process aborted.");
            return; 
        }

        System.out.println("Enter the date of the result. (day/month/year)");
        String date = reader.nextLine();

        System.out.println("Enter the student group (G1, G2, G3...)");
        String studentGroup = reader.nextLine();

        String number = myController.maxResult(projectID);
        boolean maxResult = true;
        if(number.equals("3")){
            maxResult= false;
            System.out.println("This project already has 3 Results");
            return;
        } else{
            maxResult =true;
        }

        String recurseID = myController.registerResult(date, studentGroup, projectID, number);
        System.out.println("The result has been successfully created. Its ID is: " + recurseID + "\n");

        System.out.println("Now let's add assignments (max 3). Type 0 to skip the process.");
        //String quantity = myController.maxAssignaments(projectID, recurseID);
        int option = 99;

        do {
            do {
                System.out.println("Enter the type of assignment you want to add or 0 to stop:");
                System.out.println("1- Repository");
                System.out.println("2- Document");
                System.out.println("3- Artefact");

                try {
                    option = Integer.parseInt(reader.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("That is not a valid option, try again.");
                }

            } while (option < 0 || option > 3);

            switch (option) {
                case 1: addRepo(); 
                    break;
                case 2: addDocument(); 
                    break;
                case 3: addArtefact(); 
                    break;
                case 0: System.out.println("Assignment process skipped."); break;
            }
        } while (option != 0);
    }

    public void addRepo(){
        int numberOfDocuments = 0;
        boolean valid = false;
        do { 
            try {
                System.out.println("Enter the amount of documents the repository has"); 
                numberOfDocuments = Integer.parseInt(reader.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("this is not a number \n ");
            }
        } while (!valid);
        String link = "";
        boolean key = false;
        do { 
            System.out.println("Enter the link of the repository");
            link= reader.nextLine();
            key = validLink(link);
            if(!key){
                System.out.println("That is not a valid link, try again");
            }
        } while (!key);
        int developmentPhase = selectDevelopmentPhase();
    }

    public void addDocument(){
        String link = "";
        boolean key = false;
        do { 
            System.out.println("Enter the link of the document");
            link= reader.nextLine();
            key = validLink(link);
            if(!key){
                System.out.println("That is not a valid link, try again");
            }
        } while (!key);
        int developmentPhase = selectDevelopmentPhase();
    }

    public void projectsWithNoResults() {
        System.out.println("These are the existing professors: ");
        System.out.println(myController.showAllProfessors());

        System.out.println("Enter the professor ID:");
        String id = reader.nextLine();

        boolean foundedProfessor = myController.searchProfessor(id);
        if (!foundedProfessor) {
            System.out.println("There is no professor with that ID.\n");
            return; 
        }
        StringBuilder message = myController.projectsWithNoResults(id);

        System.out.println("Projects without results:");
        System.out.println(message.toString());
    }

    public int addArtefact(){
        System.out.println("Select the artefact type");
        int option = 0;
        do { 
            System.out.println("1 -Requirements specification");
            System.out.println("2 -Class diagram");
            System.out.println("3 -Infographic");
            System.out.println("4 -Data model");
            System.out.println("5 -Test plan");
            System.out.println("6 -Deployment diagram");
            try {
                    option= Integer.parseInt(reader.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("That is not a possible option, try again");
                }
        } while (true);
    }

    public int selectDevelopmentPhase(){
        System.out.println("Now select the phase of software develpmente: ");
        int option = 0;
        do { 
            System.out.println("1 -Requirements analysis");
            System.out.println("2 -Design");
            System.out.println("3 -Construction");
            System.out.println("4 -Test/evidence");
            System.out.println("5 -Deployment");
            try {
                option = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("That is not a number, try again");
            }
        } while (option<1 || option>5);
        return option;
    }

}


