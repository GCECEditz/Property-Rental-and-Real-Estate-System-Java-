/**
 * @(#)Landlord.java
 *
 *
 * @author 
 * @version 1.00 2026/4/4
 */


public class Landlord extends PropertyHolder{

    public Landlord(){
        this(" ", " ", " ", null);
    }
    
    public Landlord(String name, String email, String telNo, Property property){
        super(name, email, telNo, property);
    }
    
    public String showBrief(){
        return String.format("<Landlord> %-2s %-20s %-20s", super.getPersonID(), super.getName(), super.getEmail());
    }
    
    public String asDat(){
        return "landlord\n" + super.asDat();
    }
}