import java.util.Scanner;

public abstract class Residential extends Property{
	//data properties
	
	//cons with no args
    public Residential(){
    	this(0.00,0.00, " ", " ", " ",null, null);
    }
    
    //cons with args
    public Residential(double listingPrice, double squareFootage, String location, String type, String status, Occupancy occupancy,PropertyDetails details){
    	super(listingPrice, squareFootage, location, type,status, occupancy, details);	
    }

    //getter
    
    //setter
    
    // other method
    public String toString() {
        return super.toString() +
        		"\nCategory     : Residential";
    }
    
    
    public abstract String showBrief();
}