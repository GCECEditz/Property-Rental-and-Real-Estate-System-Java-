/**
 * @(#)LoadHelper.java
 *
 *
 * @author 
 * @version 1.00 2026/4/6
 */
 
 //warning spaghetti code beacuse time constraints

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.nio.charset.*;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoadHelper {

    public static void loadUsers(Path fname, HashMap<String, HashMap<String, Object>> users){
        File file = fname.toFile();

        try (Scanner reader = new Scanner(file)) {
          while (reader.hasNextLine()) {
            String [] lineArr = reader.nextLine().split("[\\s]");
            users.put(lineArr[0], new HashMap(Map.of("password", lineArr[1], "userID", Integer.parseInt(lineArr[2]))));
          }
        } catch (FileNotFoundException e) {
          System.out.println("File " + file.getName() + " not found.");
          //e.printStackTrace();
        }
    }
    
    public static void loadUserdb(Path fname, HashMap<Integer, Person> userdb, HashMap<String, Property> propertydb, HashMap<Integer, Lease> leasedb){
        File file = fname.toFile();

        try (Scanner reader = new Scanner(file)) {
            Person p = null;
            while (reader.hasNextLine()) {
                String lineDat = reader.nextLine();
                
                //start loading users
                if (lineDat.equals("<")){
                    
                    //System.out.println("new user loaded\n");
                    String userType = reader.nextLine();
                    
                    //load user data
                    switch(userType){
                        case "tenant":
                            p = loadTenant(reader, leasedb);
                            break;
                        case "landlord":
                            p = loadLandlord(reader, propertydb);
                            break;
                        case "agent":
                            p = loadAgent(reader, propertydb);
                            break;
                        default:
                            System.out.println("Invalid user type");
                            break;
                    }
                    userdb.put(p.getPersonID(), p);

                }
                else if (lineDat.equals(">")){
                    System.out.println("User " + p.getPersonID() + " " + p.getClass().getSimpleName() + " loaded.");
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
          System.out.println("File " + file.getName() + " not found.");
          //e.printStackTrace();
        }
    }
    
    public static Tenant loadTenant(Scanner reader, HashMap<Integer, Lease> leasedb){
        Tenant p;
        //System.out.println("Tenant");
        Integer userID = Integer.parseInt(reader.nextLine());
        String name = reader.nextLine(), email = reader.nextLine(), telNo = reader.nextLine();
        PerformanceRating perf = new PerformanceRating(reader.nextDouble(), reader.nextLine().strip());
        double budget = reader.nextDouble();
        Lease l = leasedb.get(reader.nextInt());
        p = new Tenant(name, email, telNo, budget, null);
        p.setPersonID(userID);
        p.setPerformanceRating(perf);
        p.setLease(l);
        return p;
    }
    
    public static Landlord loadLandlord(Scanner reader, HashMap<String, Property> propertydb){
        Landlord p;
        //System.out.println("Landlord");
        Integer userID = Integer.parseInt(reader.nextLine());
        String name = reader.nextLine(), email = reader.nextLine(), telNo = reader.nextLine();
        PerformanceRating perf = new PerformanceRating(reader.nextDouble(), reader.nextLine().strip());
        p = new Landlord(name, email, telNo, propertydb.get(reader.nextLine()));
        p.setPersonID(userID);
        p.setPerformanceRating(perf);
        return p;
    }
    
    public static Agent loadAgent(Scanner reader, HashMap<String, Property> propertydb){
        Agent p;
        //System.out.println("Agent");
        Integer userID = Integer.parseInt(reader.nextLine());
        String name = reader.nextLine(), email = reader.nextLine(), telNo = reader.nextLine();
        PerformanceRating perf = new PerformanceRating(reader.nextDouble(), reader.nextLine().strip());
        p = new Agent(name, email, telNo, propertydb.get(reader.nextLine()));
        p.setPersonID(userID);
        p.setPerformanceRating(perf);
        return p;
    }

    public static void loadPropertydb(Path fname, HashMap<String, Property> propertydb){
        File file = fname.toFile();

        try (Scanner reader = new Scanner(file)) {
            Property p = null;
            while (reader.hasNextLine()) {
                String lineDat = reader.nextLine();
                
                //start loading users
                if (lineDat.equals("<")){
                    
                    //System.out.println("new property loaded\n");
                    String userType = reader.nextLine();
                    
                    //load user data
                    switch(userType){
                        case "apartment":
                            p = loadApartment(reader);
                            break;
                        case "villa":
                            p = loadVilla(reader);
                            break;
                        case "office":
                            p = loadOffice(reader);
                            break;
                        case "warehouse":
                            p = loadWarehouse(reader);
                            break;
                        default:
                            System.out.println("Invalid property type");
                            break;
                    }
                    propertydb.put(p.getPropertyID(), p);

                }
                else if (lineDat.equals(">")){
                    System.out.println("Property " + p.getPropertyID() + " " + p.getClass().getSimpleName() + " loaded.");
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
          System.out.println("File " + file.getName() + " not found.");
          //e.printStackTrace();
        }
    }

    public static Apartment loadApartment(Scanner reader){
        Apartment p;
        //System.out.println("Apartment");
        String propertyID = reader.nextLine(); 
        double listingPrice = reader.nextDouble(), squareFootage = reader.nextDouble();
        //clear buffer to next line
        reader.nextLine();
        String type = reader.nextLine(), location = reader.nextLine(), status = reader.nextLine();
        
        Occupancy o = new Occupancy(reader.nextInt());
        //clear buffer to next line
        reader.nextLine();
        PropertyDetails pDetails = new PropertyDetails(reader.nextLine());
        
        int floors = reader.nextInt();
        p = new Apartment(listingPrice, squareFootage, type, location, status, o, pDetails, floors);
        p.setPropertyID(propertyID);
        return p;
    }
    
    public static Villa loadVilla(Scanner reader){
        Villa p;
        //System.out.println("Apartment");
        String propertyID = reader.nextLine(); 
        double listingPrice = reader.nextDouble(), squareFootage = reader.nextDouble();
        //clear buffer to next line
        reader.nextLine();
        String type = reader.nextLine(), location = reader.nextLine(), status = reader.nextLine();
        
        Occupancy o = new Occupancy(reader.nextInt());
        //clear buffer to next line
        reader.nextLine();
        PropertyDetails pDetails = new PropertyDetails(reader.nextLine());
        
        int floors = reader.nextInt();
        boolean hasPool = reader.nextBoolean(), hasGarden = reader.nextBoolean();
        p = new Villa(listingPrice, squareFootage, type, location, status, o, pDetails, floors, hasPool, hasGarden);
        p.setPropertyID(propertyID);
        return p;
    }
    
    public static Office loadOffice(Scanner reader){
        Office p;
        //System.out.println("Apartment");
        String propertyID = reader.nextLine(); 
        double listingPrice = reader.nextDouble(), squareFootage = reader.nextDouble();
        //clear buffer to next line
        reader.nextLine();
        String type = reader.nextLine(), location = reader.nextLine(), status = reader.nextLine();
        
        Occupancy o = new Occupancy(reader.nextInt());
        //clear buffer to next line
        reader.nextLine();
        PropertyDetails pDetails = new PropertyDetails(reader.nextLine());
        
        p = new Office(listingPrice, squareFootage, type, location, status, o, pDetails);
        p.setPropertyID(propertyID);
        return p;
    }
    
    public static Warehouse loadWarehouse(Scanner reader){
        Warehouse p;
        //System.out.println("Apartment");
        String propertyID = reader.nextLine(); 
        double listingPrice = reader.nextDouble(), squareFootage = reader.nextDouble();
        //clear buffer to next line
        reader.nextLine();
        String type = reader.nextLine(), location = reader.nextLine(), status = reader.nextLine();
        
        Occupancy o = new Occupancy(reader.nextInt());
        //clear buffer to next line
        reader.nextLine();
        PropertyDetails pDetails = new PropertyDetails(reader.nextLine());
        
        double height = reader.nextDouble();
        p = new Warehouse(listingPrice, squareFootage, type, location, status, o, pDetails, height);
        p.setPropertyID(propertyID);
        return p;
    }

    public static void loadLeasedb(Path fname, HashMap<Integer, Lease> leasedb, HashMap<String, Property> propertydb){
        File file = fname.toFile();

        try (Scanner reader = new Scanner(file)) {
            Lease l = null;
            while (reader.hasNextLine()) {
                String lineDat = reader.nextLine();
                
                //start loading leases
                if (lineDat.equals("<")){
                    
                    l = loadLease(reader, propertydb);
                    leasedb.put(l.getLeaseID(), l);
                }
                else if (lineDat.equals(">")){
                    System.out.println("Lease " + l.getLeaseID() + " loaded.");
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
          System.out.println("File " + file.getName() + " not found.");
          //e.printStackTrace();
        }
    }
    
    public static Lease loadLease(Scanner reader, HashMap<String, Property> propertydb){
        int leaseID = reader.nextInt();
        reader.nextLine();
        String propertyID = reader.nextLine();
        LocalDate startDate = LocalDate.parse(reader.nextLine()), endDate = LocalDate.parse(reader.nextLine()), lastPaid = LocalDate.parse(reader.nextLine());
        double depositAmount = reader.nextDouble(), monthlyRent = reader.nextDouble();
        Lease l = new Lease(propertydb.get(propertyID), startDate, endDate, depositAmount, monthlyRent, lastPaid);
        l.setLeaseID(leaseID);
        ArrayList<Payment> leasePayments = l.getPayments();
        
        reader.nextLine();
        if (reader.nextLine().equals("[")){
            String payment = "";
            do {
                payment = reader.nextLine();
                if (!payment.equals("]")){
                    String [] paymentDetails = payment.split(" ");
                    leasePayments.add(new Payment(Double.valueOf(paymentDetails[0]), LocalDateTime.parse(paymentDetails[1])));
                }
            } while (!payment.equals("]"));
        }
        
        return l;
    }
    
    public static void loadRequestdb(Path fname, HashMap<String, HashMap<String, ArrayList<Request>>> requestdb, HashMap<Integer, Person> userdb, HashMap<String, Property> propertydb){
        File file = fname.toFile();

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String lineDat = reader.nextLine();
                
                //start loading a single property's request
                if (lineDat.equals("<")){
                    reader.nextLine(); //propertyid
               
                    lineDat = reader.nextLine(); //= {
                
                    //start loading request types
                    while (lineDat.equals("{")){
                        if (lineDat.equals("{")){
                            exhaustRequestType(reader, reader.nextLine(), requestdb, userdb, propertydb);
                        }
                        lineDat = reader.nextLine();
                    }
                }
                else if (lineDat.equals(">")){
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
          System.out.println("File " + file.getName() + " not found.");
          //e.printStackTrace();
        }
    }
    
    public static void exhaustRequestType(Scanner reader, String requestType, HashMap<String, HashMap<String, ArrayList<Request>>> requestdb, HashMap<Integer, Person> userdb, HashMap<String, Property> propertydb){
        Request r = null;
        String next = reader.nextLine();
        while (!next.equals("}")){ //either next is [ or }
            int requestID = Integer.valueOf(reader.nextLine());
            Property property = propertydb.get(reader.nextLine());
            LocalDateTime time = LocalDateTime.parse(reader.nextLine());
            Person tenant = userdb.get(Integer.valueOf(reader.nextLine()));
            RequestStatus status = RequestStatus.valueOf(reader.nextLine());
            switch (requestType){
                case "view":
                    LocalDateTime when = LocalDateTime.parse(reader.nextLine());
                    int attendees = Integer.valueOf(reader.nextLine());
                    String remarks = reader.nextLine();
                    r = new ViewRequest(property, time, (Tenant)tenant, when, attendees, remarks);
                    break;
                case "rent":
                    LocalDate date = LocalDate.parse(reader.nextLine());
                    int months = Integer.valueOf(reader.nextLine());
                    double deposit = Double.valueOf(reader.nextLine());
                    int occupants = Integer.valueOf(reader.nextLine());
                    r = new RentApplication(property, time, (Tenant)tenant, date, months, deposit, occupants);
                    break;
                case "maintenance":
                    String description = reader.nextLine();
                    r = new MaintenanceRequest(property, time, (Tenant)tenant, description);
                    break;
                default:
                    break;
            }
            if (r != null){
                r.setStatus(status);
                r.setRequestID(requestID);
                requestdb.get(r.getProperty().getPropertyID()).get(requestType).add(r);
                System.out.println("Request " + r.getRequestID() + " " + r.getClass().getSimpleName() + " loaded.");
            }
            reader.nextLine(); //] end of request type list
            next = reader.nextLine();
        }
        return; //end of requests of that type
    }
}