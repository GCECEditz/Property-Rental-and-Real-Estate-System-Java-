
public class Office extends Commercial{
	//data properties
    
    //cons with no args
    public Office(){
    	this(0.00,0.00, " ", " ", " ", null, null);
    }
    
    //cons with args
    public Office(double listingPrice, double squareFootage, String location, String type, String status,Occupancy occupancy, PropertyDetails details){
    	super(listingPrice, squareFootage, location, type, status,occupancy, details);	
    }
    
    //getter
    
    //setter
    
    //other method
    public String toString() {
        return super.toString();
    }
    
    public String asDat(){
        return "office\n" + super.asDat();
    }
    
    public String showBrief(){
        return String.format("%-4s (%-10s) in %-20s", super.getPropertyID(), "Office", super.getLocation());
    }
}