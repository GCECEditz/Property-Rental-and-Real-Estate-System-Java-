public abstract class Commercial extends Property{
	// data properties
	
	// cons with no args
	public Commercial(){
		this(0.00, 0.00, " ", " "," ", null,null);
	}
    
    public Commercial(double listingPrice, double squareFootage, String location, String type, String status, Occupancy occupancy,PropertyDetails details){
    	super(listingPrice, squareFootage, location, type,status, occupancy, details);	
    }
    //getter
    
    //setter
    
    // other method
    public String toString() {
        return super.toString() +
        		"\nCategory     : Commercial";
    }
    
    public abstract String showBrief();
}