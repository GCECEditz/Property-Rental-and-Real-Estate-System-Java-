/**
 * @(#)Agent.java
 *
 *
 * @author 
 * @version 1.00 2026/4/4
 */


public class Agent extends PropertyHolder{

    public Agent(){
        this(" ", " ", " ", null);
    }
    
    public Agent(String name, String email, String telNo, Property property){
        super(name, email, telNo, property);
    }
    
    public String showBrief(){
        return String.format("<Agent>    %-2s %-20s %-20s", super.getPersonID(), super.getName(), super.getEmail());
    }
    
    public String asDat(){
        return "agent\n" + super.asDat();
    }
}