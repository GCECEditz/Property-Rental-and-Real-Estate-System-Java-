import java.time.LocalDate;
import java.util.ArrayList;
public class Lease {
	//data properties
	
    private int leaseID;
    private static int leaseCount = 0;
	private Property property;
	private LocalDate startDate;
	private LocalDate endDate;
	private double depositAmount;
	private double monthlyRent;
	private ArrayList<Payment> payments;
	private LocalDate lastPaid;
	
	//no args cons
    public Lease(){
    	this(null, null, null, 0.0, 0.0, null);
    }
    
    //cons with args
    public Lease(Property property, LocalDate startDate, LocalDate endDate, double depositAmount, double monthlyRent, LocalDate lastPaid){
    	this.property = property;
    	this.startDate = startDate;
    	this.endDate = endDate;
    	this.depositAmount = depositAmount;
    	this.monthlyRent = monthlyRent;
        leaseID = leaseCount;
        leaseCount++;
        this.lastPaid = lastPaid;
        payments = new ArrayList<>();
    }
    
    //-----------------Methods--------------------
    
    public boolean hasPaidThisMonth(LocalDate now){
        return now.getMonth() == lastPaid.getMonth();
    }
    
    public boolean payMonthlyRent(Payment payment){
        LocalDate paidTime = payment.getTime().toLocalDate();
    	if (payment.getAmount() < monthlyRent){
    		System.out.println("Insufficient payment. The monthly rent is RM " + monthlyRent);
    		return false;
    	}
        else if(paidTime.getMonth() == lastPaid.getMonth()){
    		System.out.println("You already paid for this month.");
    		return false;
    	}
    	else{
    		payments.add(payment);
    		lastPaid = paidTime;
            System.out.format("\nPayment successful!\nReceipt:\n\n==========Receipt==========\nAmount      : RM %.2f\nMonthly Rent: RM %.2f\nChange      : RM %.2f\n", payment.getAmount(), monthlyRent, (payment.getAmount() - monthlyRent));
            return true;
    	}//end if
    }//end payMonthlyRent
    
    //getter
    
    public int getLeaseID(){
        return leaseID;
    }
    
    public static int getLeaseCount(){
        return leaseCount;
    }
    
    public Property getProperty(){
    	return property;
    }
    
    public LocalDate getStartDate(){
    	return startDate;
    }
    
    public LocalDate getEndDate(){
    	return endDate;
    }
    
    public double getDepositAmount(){
    	return depositAmount;
    }
    
    public double getMonthlyRent(){
    	return monthlyRent;
    }
    
    public ArrayList<Payment> getPayments(){
    	return payments;
    }
    
    public LocalDate getLastPaid(){
    	return lastPaid;
    }
    
    //setter
    
    public void setLeaseID(int leaseID){
        this.leaseID = leaseID;
    }
    
    public static void setLeaseCount(int leaseCount){
        Lease.leaseCount = leaseCount;
    }
    
    public void setEndDate(LocalDate endDate){
    	this.endDate = endDate;
    }
    public void setDepositAmount(double depositAmount){
    	this.depositAmount = depositAmount;
    }
    
    public void setMonthlyRent(){
    	this.monthlyRent = monthlyRent;
    }
    
    public void setLastPaid(){
    	this.lastPaid = lastPaid;
    }
    
    //toString()
    public String toString(){
    	return   "Lease ID      : " + leaseID
    	     + "\nProperty      : " + property.showBrief()
    		 + "\nStart Date    : " + startDate
    		 + "\nEnd Date      : " + endDate
    		 + "\nDeposit Amount: RM" + depositAmount
    		 + "\nMonthly Rent  : RM" + monthlyRent;
    		 //+ "\nPayments:\n" + payments + "\n";
    }//end toString()
    
    public String asDat(){
        String res =  leaseID + "\n" + property.getPropertyID() + "\n" + startDate + "\n" + endDate + "\n" + lastPaid + "\n" + depositAmount + "\n" + monthlyRent + "\n[\n";
        for (Payment p: payments){
            res += p.asDat() + "\n";
        }
        res += "]";
        return res;
    }
}//end class