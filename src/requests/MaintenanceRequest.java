/**
 * @(#)MaintenanceRequest.java
 *
 *
 * @author 
 * @version 1.00 2026/4/5
 */

import java.time.LocalDateTime;

public class MaintenanceRequest extends Request{
    
    private String description;

    public MaintenanceRequest() {
        this(null, null, null, " ");
    }
    
    public MaintenanceRequest(Property property, LocalDateTime time, Tenant tenant, String description){
        super(property, time, tenant);
        this.description = description;
    }
    
    //getters
    
    public String getDescription(){
        return description;
    }
    
    //setters
    
    public void setDescription(String description){
        this.description = description;
    }
    
    //etc
    
    public String toString(){
        return super.toString() + "\nDescription: " + description + "\n";
    }
    
    public String showBrief(){
        return String.format("%-3d %-20s %-9s %s", super.getRequestID(), super.getTime().format(super.getDateFormatter()), super.getStatus().name(), description);
    }
    
    public String asDat(){
        return super.asDat() + "\n" + description;
    }
    
}