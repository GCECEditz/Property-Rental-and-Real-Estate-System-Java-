/**
 * @(#)PropertyHolder.java
 *
 *
 * @author 
 * @version 1.00 2026/4/4
 */


public abstract class PropertyHolder extends Person{
    
    private Property property;

    public PropertyHolder(){
        this(" ", " ", " ", null);
    }
    
    public PropertyHolder(String name, String email, String telNo, Property property){
        super(name, email, telNo);
        this.property = property;
    }
    
//    public static PropertyHolder fromScannerDat(){
//        Person p = Person.
//    }
    
    //getters
    
    public Property getProperty(){
        return property;
    }
    
    //setters
    
    public void setProperty(Property property){
        this.property = property;
    }
    
    //etc
    
    public String toString(){
        return "-<PropertyHolder>-\n" + super.toString() + "\nProperty Assigned: " + property.getPropertyID();
    }
    
    public abstract String showBrief();
    
    public String asDat(){
        return super.asDat() + "\n" + (property != null ? property.getPropertyID() : -1);
    }
}