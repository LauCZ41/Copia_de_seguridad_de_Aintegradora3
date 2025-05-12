package model;
import java.util.ArrayList;


public class Project {
    private String uniqIdentificator;
    private String name;
    private ArrayList<String> keyWords;
    private String description;
    private ArrayList<String> associatedBusiness;
    private String link;
    private String semester;
    private TypeOfProject typeOfProject;

    //relations
    private ArrayList<Result> myResults = new ArrayList<>();
    //max 3

    public Project(String name, ArrayList<String> keyWords,String description ,ArrayList<String>  associatedBusiness, String link, String semester,TypeOfProject typeOfProject, String uniqIdentificator){
        this.name = name;
        this.keyWords=keyWords;
        this.associatedBusiness=associatedBusiness;
        this.link=link;
        this.semester=semester;
        this.description=description;
        this.typeOfProject=typeOfProject;
        this.uniqIdentificator=uniqIdentificator;
    }

    public void addKeywords(ArrayList<String> newKeywords) {
        keyWords.addAll(newKeywords);
    }
    
    public void addAssociatedBusinesses(ArrayList<String> newBusinesses) {
        associatedBusiness.addAll(newBusinesses);
    }
    
    public boolean removeKeyword(String keywordToRemove) {
        keywordToRemove = keywordToRemove.trim();
        for (int i = 0; i < keyWords.size(); i++) {
            if (keyWords.get(i).equalsIgnoreCase(keywordToRemove)) {
                keyWords.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public boolean removeAssociatedBusiness(String businessToRemove) {
        businessToRemove = businessToRemove.trim();
        for (int i = 0; i < associatedBusiness.size(); i++) {
            if (associatedBusiness.get(i).equalsIgnoreCase(businessToRemove)) {
                associatedBusiness.remove(i);
                return true;
            }
        }
        return false;
    }

    public String addResult(String link, String description, String uniqIdentificator){
        myResults.add(new Result(link, description, uniqIdentificator));
        return "Added result ";
    }
    public String masAssignament(String iD){
        for(Result r: myResults){
            if(r.getID().equals(iD)){
                return r.maxAssignaments();
            }
        }
        return "x";
    }

    @Override
    public String toString() {
        return "Project ID: " + uniqIdentificator + "\n" +
            "Name: " + name + "\n" +
            "Description: " + description + "\n" +
            "Link: " + link + "\n" +
            "Semester: " + semester + "\n" +
            "Type of Project: " + typeOfProject + "\n" +
            "Keywords: " + String.join(", ", keyWords) + "\n" +
            "Associated Businesses: " + String.join(", ", associatedBusiness)+ "\n";
    }

    public String getID(){
        return uniqIdentificator;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public ArrayList<String> getKeyWords(){
        return keyWords;
    }

    public ArrayList<String> getAssosiatedBusiness(){
        return associatedBusiness;
    }

    public void setName(String newName){
        name=newName;
    }

    public void setDescription(String newDescription){
        description=newDescription;
    }

    public void setLink(String newLink){
        link=newLink;
    }

    public void setSemester(String newSemester){
        semester=newSemester;
    }

    public void setTypeOfProject(TypeOfProject newTypeOfProject){
        typeOfProject= newTypeOfProject;
    }

}
