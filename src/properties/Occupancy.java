/**
 * @(#)Occupancy.java
 *
 *
 * @author 
 * @version 1.00 2026/4/2
 */

import java.util.Date;

public class Occupancy {
    private int occupants;

    public Occupancy() {
        this(0);
    }
    
    public Occupancy(int occupants){
        setOccupants(occupants);
    }
    
    //getters
    
    public int getOccupants(){
        return occupants;
    }
    
    //setters
    
    public void setOccupants(int occupants){
        this.occupants = occupants;
        if (occupants < 0){
            this.occupants = 0;
        }
    }
    
    
    
    //etc
    
    public void addOccupants(int count){
        occupants += count;
    }
    
    public void removeOccupants(int count){
        occupants -= count;
        if (occupants < 0){
            this.occupants = 0;
        }
    }
    
    public String generateOccupancyReport(){
        return "Occupants on " + new Date() + "\nTotal Tenants: " + occupants;
    }
    
    public String toString(){
        return String.valueOf(occupants);
    }
}