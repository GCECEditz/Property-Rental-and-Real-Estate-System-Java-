import java.time.LocalDate;
import java.time.LocalDateTime;
public class RentApplication extends Request {
    private LocalDate proposedStartDate;
    private int leaseDurationMonths;
    private double offeredDeposit;
    private int expectedOccupants;

    public RentApplication() {
        this(null, null, null, null, 12, 0.0, 1);
    }
    public RentApplication(Property property, LocalDateTime submissionTime, Tenant tenant, LocalDate proposedStartDate, int leaseDurationMonths, double offeredDeposit, int expectedOccupants) {
        super(property, submissionTime, tenant);
        this.proposedStartDate = proposedStartDate;
        this.leaseDurationMonths = leaseDurationMonths;
        this.offeredDeposit = offeredDeposit;
        this.expectedOccupants = expectedOccupants;
    }

    public LocalDate getProposedStartDate() {
        return proposedStartDate;
    }
    public int getLeaseDurationMonths() {
        return leaseDurationMonths;
    }
    public double getOfferedDeposit() {
        return offeredDeposit;
    }
    public int getExpectedOccupants() {
        return expectedOccupants;
    }

    public void setProposedStartDate(LocalDate proposedStartDate) {
        this.proposedStartDate = proposedStartDate;
    }
    public void setLeaseDurationMonths(int leaseDurationMonths) {
        this.leaseDurationMonths = leaseDurationMonths;
    }
    public void setOfferedDeposit(double offeredDeposit) {
        this.offeredDeposit = offeredDeposit;
    }
    public void setExpectedOccupants(int expectedOccupants) {
        this.expectedOccupants = expectedOccupants;
    }

    public String toString() {
        return super.toString() +
                "\nProposed Start Date  : " + proposedStartDate +
                "\nLease Duration Months: " + leaseDurationMonths +
                "\nOffered Deposit      : " + offeredDeposit +
                "\nExpected Occupants   : " + expectedOccupants + "\n";
    }
    
    public String showBrief(){
        return String.format("%-3d %-20s %-9s", super.getRequestID(), super.getTime().format(super.getDateFormatter()), super.getStatus().name());
    }
    
    public String asDat(){
        return super.asDat() + "\n" + proposedStartDate + "\n" + leaseDurationMonths + "\n" + offeredDeposit + "\n" + expectedOccupants;
    }
}
