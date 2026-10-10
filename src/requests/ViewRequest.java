import java.time.LocalDateTime;
public class ViewRequest extends Request {
    private LocalDateTime preferredViewingDate;
    private int numberOfAttendees;
    private String remarks;

    public ViewRequest() {
        this(null, null, null, null, 1, " ");
    }
    public ViewRequest(Property property, LocalDateTime submissionTime, Tenant tenant, LocalDateTime preferredViewingDate, int numberOfAttendees, String remarks) {
        super(property, submissionTime, tenant);
        this.preferredViewingDate = preferredViewingDate;
        this.numberOfAttendees = numberOfAttendees;
        this.remarks = remarks;
    }
    
    //getters
    public LocalDateTime getPreferredViewingDate() {
        return preferredViewingDate;
    }
    public int getNumberOfAttendees() {
        return numberOfAttendees;
    }
    public String getRemarks() {
        return remarks;
    }

    //setters
    public void setPreferredViewingDate(LocalDateTime preferredViewingDate) {
        this.preferredViewingDate = preferredViewingDate;
    }
    public void setNumberOfAttendees(int numberOfAttendees) {
        this.numberOfAttendees = numberOfAttendees;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    //etc
    public String toString() {
        return super.toString() + 
                "\nPreferred Viewing Date: " + preferredViewingDate +
                "\nNumber of Attendees   : " + numberOfAttendees +
                "\nRemarks               : " + remarks + "\n";
    }
    
    public String showBrief(){
        return String.format("%-3d %-20s %-9s %s", super.getRequestID(), super.getTime().format(super.getDateFormatter()), super.getStatus().name(), remarks);
    }
    
    public String asDat(){
        return super.asDat() + "\n" + preferredViewingDate + "\n" + numberOfAttendees + "\n" + remarks;
    }
}
