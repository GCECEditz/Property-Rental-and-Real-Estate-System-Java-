/**
 * @(#)Person.java
 *
 *
 * @author 
 * @version 1.00 2026/4/1
 */


public abstract class Person{

    private int personID;
    private static int personCount = 0;
    private String name;
    private String email;
    private String telNo;
    private PerformanceRating performanceRating;

    public Person(){
        this(" ", " ", " ");
    }
    
    public Person(String name, String email, String telNo){
        this.name = name;
        this.email = email;
        this.telNo = telNo;
        performanceRating = new PerformanceRating();
        personID = personCount;
        personCount++;
    }
    
    //getters
    
    public int getPersonID(){
        return personID;
    }
    
    public static int getPersonCount(){
        return personCount;
    }
    
    public String getName(){
        return name;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getTelNo(){
        return telNo;
    }
    
    public PerformanceRating getPerformanceRating(){
        return performanceRating;
    }
    
    //setters
    
    public void setPersonID(int personID){
        this.personID = personID;
    }
    
    public static void setPersonCount(int personCount){
        Person.personCount = personCount;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setTelNo(String telNo){
        this.telNo = telNo;
    }
    
    public void setPerformanceRating(PerformanceRating performanceRating){
        this.performanceRating = performanceRating;
    }
    
    //etc
    
    public String toString(){
        return "User ID  : " + personID
           + "\nName     : " + name
           + "\nEmail    : " + email
           + "\nTelephone: " + telNo
           + "\nRating   : " + performanceRating.getRating()
           + "\nComment  : " + performanceRating.getComment();
    }
    
    public String asDat(){
        return personID + "\n" + name + "\n" + email + "\n" + telNo + "\n" + performanceRating.getRating() + " " + performanceRating.getComment();
    }
    
    public abstract String showBrief();
    
}