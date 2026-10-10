
public class Warehouse extends Commercial{
	// data properties
	private double height;
    
    // cons with no args
    public Warehouse(){
    	this(0.00,0.00, " ", " ", " ", null,null, 0.00);
    }
    
    //cons with args
    public Warehouse(double listingPrice, double squareFootage, String location, String type, String status,Occupancy occupancy, PropertyDetails details, double height){
    	super(listingPrice, squareFootage, location, type, status,occupancy,details);
    	this.height = height;
    }
    
    //getters
    public double getHeight(){
    	return height;
    }
    
    //setters
    public void setHeight(double height){
    	this.height = height;
    }
    
    //other methods
    public String toString() {
        return super.toString() +
        		"\nHeight       : " + height + "m";
    }
    
    public String asDat(){
        return "warehouse\n" + super.asDat() + "\n" + height;
    }
    
    public String showBrief(){
        return String.format("%-4s (%-10s) in %-20s", super.getPropertyID(), "Warehouse", super.getLocation());
    }
}