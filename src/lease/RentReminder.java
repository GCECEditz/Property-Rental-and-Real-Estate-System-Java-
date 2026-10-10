public class RentReminder {
	//data properties
	private double amount;
	private String message;
	
	//no args cons
    public RentReminder(){
    	this(0.0, " ");
    }
    
    //cons with args
    public RentReminder(double amount, String message){
    	this.amount = amount;
    	this.message = message;
    }
    
    //getter
    public double getAmount(){
		return amount;
	}
	
	public String getMessage(){
		return message;
	}
	
	//setter
	public void setMessage(String message){
		this.message = message;
	}
	
	//toString()
	public String toString(){
		return message + "\nAmount: RM" + String.format("%.2f", amount);
	}//end toString()
}//end class