import java.util.Scanner;

public abstract class Property {
    //data property
    private double listingPrice;
    private double squareFootage;
    private String location;
    private String type;
    private String status;
    private Occupancy occupancy;
    private PropertyDetails details;
    private static int propertyCount=0;
    private String propertyID;
    
    // cons with no arg
    public Property(){
        this(0.00,0.00," "," "," ",null,null);
    }
    
    // cons with arg
    public Property(double listingPrice,double squareFootage,String location, String type, String status, Occupancy occupancy, PropertyDetails details){
        this.listingPrice = listingPrice;
        this.squareFootage = squareFootage;
        this.type = type;
        this.location = location;
        this.status = status;
        this.occupancy = occupancy;
        this.details = details;
        this.propertyID = generatePropertyID();
    }
    
//    public static Property fromScannerDat(Scanner reader){
//        Property p = new Property();
//        p.propertyID = reader.nextLine(); 
//        p.listingPrice = reader.nextDouble();
//        p.squareFootage = reader.nextDouble();
//
//        //clear buffer to next line
//        reader.nextLine();
//        p.setType(reader.nextLine());
//        p.setLocation(reader.nextLine());
//        p.setStatus(reader.nextLine());
//        p.setOccupancy(new Occupancy(reader.nextInt()));
//        
//        //clear buffer to next line
//        reader.nextLine();
//        //change these once photo saving is finalised;
//        reader.next();
//        p.setDetails(new PropertyDetails(null, reader.nextLine()));
//        return p;
//    }
    
    private String generatePropertyID() {
        propertyCount++; 
        return String.format("P%03d", propertyCount);
}
    
    //getters
    public double getListingPrice(){
        return listingPrice;
    }
    public double getSquareFootage(){
        return squareFootage;
    }
    public String getLocation(){
        return location;
    }
    public String getType(){
        return type;
    }
    public String getStatus(){
        return status;
    }
    public Occupancy getOccupancy(){
        return occupancy;
    }
    public PropertyDetails getDetails(){
        return details;
    }
    public static int getPropertyCount(){
        return propertyCount;
    }
    public String getPropertyID(){
        return propertyID;
    }
    //setters
    public void setListingPrice(double listingPrice){
        this.listingPrice = listingPrice;
    }
    public void setSquareFootage(double squareFootage){
        this.squareFootage = squareFootage;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setOccupancy(Occupancy occupancy){
        this.occupancy = occupancy;
    }
    public void setDetails(PropertyDetails details){
        this.details = details;
    }
    public static void setPropertyCount(int propertyCount){
        Property.propertyCount = propertyCount;
    }
    public void setPropertyID(String propertyID){
        this.propertyID = propertyID;
    }
    
    // other method
    public String toString() {
        return  "\n------------------------------------------------" +
                "\nProperty ID  : " + propertyID +
                "\nListingPrice : RM " + String.format("%.2f", listingPrice) +
                "\nSquareFootage: " + squareFootage + "ft" + 
                "\nLocation     : " + location +
                "\nType         : " + type +
                "\nStatus       : " + status +
                "\nOccupancy    : " + occupancy +
                "\nDetails      : " + details;
    }
    
    public String asDat(){
        return propertyID + "\n" + listingPrice + "\n" + squareFootage + "\n" + location + "\n" + type + "\n" + status + "\n" + 
            occupancy.getOccupants() + "\n" + details.asDat();
    }
    
    public abstract String showBrief();
}