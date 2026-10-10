
public class Villa extends Residential{
	//data properties
	private int floors;
	private boolean hasPool;
	private boolean hasGarden;
	
	// cons with no args
	public Villa(){
		this(0.00,0.00," "," "," ",null,null,0,false,false);
	}
    
    // cons with args
    public Villa(double listingPrice, double squareFootage, String location,String type, String status,Occupancy occupancy, PropertyDetails details,int floors,boolean hasPool, boolean hasGarden){
    	super(listingPrice, squareFootage, location, type,status,occupancy, details);
    	this.floors = floors;
    	this.hasPool = hasPool;
    	this.hasGarden = hasGarden;
    }
    
    // getters
    public int getFloors(){
    	return floors;
    }
    public boolean getHasPool(){
    	return hasPool;
    }
    public boolean getHasGarden(){
    	return hasGarden;
    }
    
    //setters
    public void setFloors(int floors){
    	this.floors = floors;
    }
    public void setHasPool(boolean hasPool){
    	this.hasPool = hasPool;
    }
    public void setHasGarden(boolean hasGarden){
    	this.hasGarden = hasGarden;
    }
    
    // other method
    public String toString(){
    	return super.toString() +
    			"\nFloors       : " + floors +
    			"\nHas Pool?    : " + (hasPool ? "Yes" : "No") +
                "\nHas Garden?  : " + (hasGarden ? "Yes" : "No");
    }
    
    public String asDat(){
        return "villa\n" + super.asDat() + "\n" + floors + "\n" + hasPool + "\n" + hasGarden;
    }
    
    public String showBrief(){
        return String.format("%-4s (%-10s) in %-20s", super.getPropertyID(), "Villa", super.getLocation());
    }
}