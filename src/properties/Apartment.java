import java.util.Scanner;

public class Apartment extends Residential{
	// data propeties
	private int floors;
	
	public Apartment(){
		this(0.00,0.00," "," "," ",null,null,0);
	}
	
	public Apartment(double listingPrice, double squareFootage, String location,String type, String status,Occupancy occupancy, PropertyDetails details,int floors){
    	super(listingPrice, squareFootage, location, type,status,occupancy, details);
		this.floors = floors;
	}
	
//	public static Property fromScannerDat(){
//	    return;
//	}
    
    //getters
    public int getFloors(){
    	return floors;
    }
    
    //setters
    public void setFloors(int floors){
    	this.floors = floors;
    }
    
    //Other methods
    public String toString(){
    	return super.toString() +
    			"\nFloors       : " + floors;
    }
    
    public String asDat(){
        return "apartment\n" + super.asDat() + "\n" + floors;
    }
    
    public String showBrief(){
        return String.format("%-4s (%-10s) in %-20s", super.getPropertyID(), "Apartment", super.getLocation());
    }
}