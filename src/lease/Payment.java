import java.time.LocalDateTime;
public class Payment {
	//data properties
	private double amount;
	private LocalDateTime time;
	
	//no cons arg
	public Payment(){
		this(0.0, null);
	}
	
	//arg with cons
	public Payment(double amount, LocalDateTime time){
		this.amount = amount;
		this.time = time;
	}
	
	//getter
	public double getAmount(){
		return amount;
	}
	
	public LocalDateTime getTime(){
		return time;
	}

	//-----------toString() Method------------
	public String toString(){
		return "\nAmount: " + amount
		     + "  Time: " + time;
	}//end toString()
	
	public String asDat(){
	   return amount + " " + time;
	}
}//end class