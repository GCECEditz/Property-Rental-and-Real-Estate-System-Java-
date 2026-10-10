/**
 * @(#)Request.java
 *
 *
 * @author 
 * @version 1.00 2026/4/2
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Request {
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
    private int requestID;
    private static int requestCount;
    private Property property;
    private LocalDateTime time;
    private Tenant tenant;
    private RequestStatus status = RequestStatus.PENDING;

    public Request() {
        this(null, null, null);
    }
    
    public Request(Property property, LocalDateTime time, Tenant tenant){
        this.property = property;
        this.time = time;
        this.tenant = tenant;
        requestID = requestCount;
        requestCount++;
    }
    
    //getters
    
    public int getRequestID(){
        return requestID;
    }
    
    public static int getRequestCount(){
        return requestCount;
    }
    
    public Property getProperty(){
        return property;
    }
    
    public LocalDateTime getTime(){
        return time;
    }
    
    public RequestStatus getStatus(){
        return status;
    }
    
    public Tenant getTenant() {
        return tenant;
    }
    
    public DateTimeFormatter getDateFormatter(){
        return dateFormatter;
    }
    
    //setters
    
    public void setRequestID(int requestID){
        this.requestID = requestID;
    }
    
    public static void setRequestCount(int requestCount){
        Request.requestCount = requestCount;
    }
    
    public void setProperty(Property property){
        this.property = property;
    }
    
    public void setTime(LocalDateTime time){
        this.time = time;
    }
    
    public void setStatus(RequestStatus status){
        this.status = status;
    }
    
    public void setStatus(String status){
        this.status = RequestStatus.valueOf(status.toUpperCase());
    }
    
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
    
    //etc
    
    public String toString(){
        return "\nRequest ID : " + requestID + 
               "\nProperty   : " + property.getPropertyID() + 
               "\nDate       : " + time.format(dateFormatter) + 
               "\nTenant     : " + tenant.getName() + 
               "\nStatus     : " + status.name();
    }
    
    public abstract String showBrief();
    
    public String asDat(){
        return requestID + "\n" + (property != null ? property.getPropertyID() : -1) + "\n" + time + "\n" + (tenant != null ? tenant.getPersonID() : -1) + "\n" + status.name();
    }
}