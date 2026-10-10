import java.awt.image.BufferedImage;

public class PropertyDetails {
   //data properties
	private String description;
	
	//cons with no arg
	public PropertyDetails(){
		this(" ");
	}
	
	//cons with args
	public PropertyDetails(String description){
		this.description = description;
	}
	
	//getters
	public String getDescription(){
		return description;
	}
	
	// setters
	public void setDescription(String description){
		this.description = description;
	}
	
	// other method
	public String toString() {
//        return "\nPropertyDetails" +
//        		"\n----------------" +
//                "\ndescription :" + description;
        return description;
    }
    
    public String asDat(){
        return description;
    }
}