/**
 * @(#)Tenant.java
 *
 *
 * @author 
 * @version 1.00 2026/4/4
 */


public class Tenant extends Person{

    private Lease lease;
    private double budget;

    public Tenant(){
        this(" ", " ", " ", 0, null);
    }
    
    public Tenant(String name, String email, String telNo, double budget, Lease lease){
        super(name, email, telNo);
        setBudget(budget);
        this.lease = lease;
    }
    
    //getters
    
    public double getBudget(){
        return budget;
    }
    
    public Lease getLease(){
        return lease;
    }
    
    //setters
    
    public void setBudget(double budget){
        this.budget = budget;
        if (budget < 0){
            this.budget = 0;
        }
    }
    
    public void setLease(Lease lease){
        this.lease = lease;
    }
    
    //etc
    
    public String toString(){
        return "-<Tenant>-\n" + super.toString() + String.format("\nBudget   : RM%.2f", budget);
    }
    
    public String showBrief(){
        return String.format("<Tenant>   %-2s %-20s %-20s", super.getPersonID(), super.getName(), super.getEmail());
    }
    
    public String asDat(){
        return "tenant\n" + super.asDat() + "\n" + budget + "\n" + (lease != null ? lease.getLeaseID() : -1);
    }
}