/**
 * @(#)PropertyRentalSystem.java
 *
 * PropertyRentalSystem application
 *
 * @author 
 * @version 1.00 2026/4/1
 */
 
/**
 * @(#)TestUsers.java
 *
 *
 * @author 
 * @version 1.00 2026/4/4
 */

import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

public class PropertyRentalSystem {

    static Path cwd = Path.of(System.getProperty("user.dir"));
    static Path outputDir = cwd.getParent().resolve("dat");

    static Scanner scanner = new Scanner(System.in);
    static HashMap<String, HashMap<String, Object>> users = new HashMap<>();
    static HashMap<Integer, Person> userdb = new HashMap<>();
    static HashMap<String, Property> propertydb = new HashMap<>();
    static HashMap<Integer, Lease> leasedb = new HashMap<>();
    static HashMap<String, HashMap<String, ArrayList<Request>>> requestdb = new HashMap<>();
    
    static Person sessionUser;
    static final HashMap<String, String> LOCATION_ABBREVIATIONS = new HashMap<>();
    
    static {
        LOCATION_ABBREVIATIONS.put("kl", "Kuala Lumpur");
        LOCATION_ABBREVIATIONS.put("pg", "Penang");
        LOCATION_ABBREVIATIONS.put("jb", "Johor Bahru");
        LOCATION_ABBREVIATIONS.put("sl", "Selangor");
        LOCATION_ABBREVIATIONS.put("ml", "Melaka");
        LOCATION_ABBREVIATIONS.put("malacca", "Melaka");
        LOCATION_ABBREVIATIONS.put("kt", "Kelantan");
        LOCATION_ABBREVIATIONS.put("kd", "Kedah");
        LOCATION_ABBREVIATIONS.put("ns", "Negeri Sembilan");
        LOCATION_ABBREVIATIONS.put("ph", "Pahang");
        LOCATION_ABBREVIATIONS.put("pl", "Perlis");
        LOCATION_ABBREVIATIONS.put("pr", "Perak");
        LOCATION_ABBREVIATIONS.put("sb", "Sabah");
        LOCATION_ABBREVIATIONS.put("sr", "Sarawak");
        LOCATION_ABBREVIATIONS.put("tg", "Terengganu");
    }

    public static void main(String[] args) {
        
        loadData();

        while (true){
            
            if (authenticationMenu() == 3)
                break;
            
            //match session user type
            if (sessionUser != null){
                if (sessionUser instanceof Tenant){
                    sessionUser = (Tenant)sessionUser;
                    tenantSession();
                }
                else if (sessionUser instanceof PropertyHolder){
                    sessionUser = (PropertyHolder)sessionUser;
                    propertyHolderSession();
                }
            }
        }
        saveData();
    }

    //main menu
    public static int authenticationMenu(){
        boolean askAgain = false;
        String authResult;
        int authType;
        
        do{
            DataInputHelper.clearScreen();
            DataInputHelper.printHeader("PROPERTY RENTAL SYSTEM");
            System.out.println("1. Log in\n2. Sign up\n3. Exit program\n");
            authType = DataInputHelper.readRangedInt(scanner, 1, 3);
            authResult = "";
            
            //determine the authentication type
            switch(authType){
                case 1: //Log in
                    authResult = loginMenu();
                    break;
                case 2: //Sign up
                    authResult = signUpMenu();
                    break;
                case 3:
                    System.out.println("Terminated Session\n\n");
                default:
                    askAgain = false;
                    break;
            }

            //if returned from previous page
            if (authResult == "" && authType != 3)
                askAgain = true;
            else
                askAgain = false;

        } while(askAgain);
        return authType;
    }
    
    //log in menu
    public static String loginMenu(){
        DataInputHelper.clearScreen();
        DataInputHelper.printHeader("LOGIN");
        boolean validUsername = false, validPassword = false;
        String username = "", password = "";
        while (!validUsername){
            username = DataInputHelper.readSingleItem(scanner, "Enter username (Or press <Enter> to return to the previous page): ");
            
            //pressed enter to return
            if (username == "")
                return "";
                
            //valid username
            if (users.containsKey(username)){
                while (!validPassword){
                    password = DataInputHelper.readSingleItem(scanner, "Enter password (Or press <Enter> to return to cancel): ");
                    //pressed enter to return
                    if (password == "")
                        break;
                    if (password.equals(users.get(username).get("password"))){
                        sessionUser = userdb.get(users.get(username).get("userID"));
                        validUsername = validPassword = true;
                    }
                    else
                        System.out.println("Wrong password, please try again.\n");
                }
            }
            //username not found in system
            else
                System.out.println("Unable to find username, please try again.\n");
        }
        return username;
    }
    
    //sign up menu
    public static String signUpMenu(){
        DataInputHelper.clearScreen();
        int userType = 0;
        boolean validUsername = false;
        String username = "", password = "";
        
        DataInputHelper.printHeader("SIGN UP");
        System.out.println("1. Tenant\n2. Landlord\n3. Agent");
        userType = DataInputHelper.readRangedInt(scanner, 1, 3);
        DataInputHelper.clearScreen();
        
        while (!validUsername){
            username = DataInputHelper.readSingleItem(scanner, "Enter username (Or press <Enter> to return to the previous page): ");
            
            //pressed enter to return
            if (username == "")
                return "";
                
            //free username
            if (!users.containsKey(username)){
                while (true){
                    password = DataInputHelper.readSingleItem(scanner, "Enter password (Or press <Enter> to return to cancel): ");
                    //pressed enter to return
                    if (password == "")
                        break;
                        
                    //create account
                    createAccountMenu(userType, username, password);
                    validUsername = true;
                    break;
                }
            }
            //username already in system
            else
                System.out.println("Username already exists, please try a new username.\n");
        }
        return username;
    }
    
    //create account menu
    public static void createAccountMenu(int userType, String username, String password){
        DataInputHelper.clearScreen();
        Property property = null;
        DataInputHelper.printHeader("ACCOUNT DETAILS");
        String name = DataInputHelper.readNonEmptyString(scanner, "Enter your name: ");
        String email = DataInputHelper.readEmail(scanner, "Enter your email: ");
        String telNo = DataInputHelper.readPhoneNumber(scanner, "Enter your telephone number: ");

        switch (userType){
            case 1: //create Tenant
                double budget = DataInputHelper.readPositiveDouble(scanner, "Enter your budget: ");
                Tenant t = new Tenant(name, email, telNo, budget, null);
                addUser(username, password, t);
                sessionUser = (Tenant)t;
                break;
            case 2: //create Landlord
                DataInputHelper.clearScreen();
                displayPropertiesList();
                property = DataInputHelper.readPropertyFromID(scanner, "Enter governing property ID: ", propertydb);
                Landlord l = new Landlord(name, email, telNo, property);
                addUser(username, password, l);
                sessionUser = (Landlord)l;
                break;
            case 3: //create Agent
                DataInputHelper.clearScreen();
                displayPropertiesList();
                property = DataInputHelper.readPropertyFromID(scanner, "Enter governing property ID: ", propertydb);
                Agent a = new Agent(name, email, telNo, property);
                addUser(username, password, a);
                sessionUser = (Agent)a;
                break;
            default:
                break;
        }
    }
    
    //adds user to the db
    public static void addUser(String username, String password, Person user){
        //create username/account mapping
        HashMap<String, Object> details = new HashMap(Map.of(
                                            "password", password,
                                            "userID", (Integer)user.getPersonID()));
        users.put(username, details);
        userdb.put((Integer)user.getPersonID(), user);
    }

    //tenant session
    public static void tenantSession(){
        boolean askAgain = false;
        int actionType;
        
        do{
            DataInputHelper.clearScreen();
            DataInputHelper.printHeader("TENANT MENU");
            System.out.println("Hello, " + sessionUser.getName() + "!");
            System.out.println("What would you like to do today?");
            DataInputHelper.printSeparator();
            System.out.println("1. Update Account Details");                     //done
            System.out.println("2. Update Budget");                              //done
            System.out.println("3. Search for Properties and Submit a Request"); //done
            System.out.println("4. Submit a Maintenance Request");               //done
            System.out.println("5. Lease Details");                              //done
            System.out.println("6. Find a Property Holder");                     //done
            System.out.println("7. Log Out");                                    //done
            DataInputHelper.printSeparator();
            actionType = DataInputHelper.readRangedInt(scanner, 1, 7);
            
            //determine the authentication type
            switch(actionType){
                case 1: //Update Account Details
                    changeAccountDetails(sessionUser);
                    break;
                case 2: //Update Budget
                    updateBudget(sessionUser);
                    break;
                case 3: //Search for properties and submit a request
                    searchAndSubmitFlow();
                    break;
                case 4: //Submit Maintenance Request)
                    displayMaintenanceSubmissionMenu(sessionUser);
                    break;
                case 5: //View Lease Details and pay
                    displayLeaseMenu(sessionUser);
                    break;
                case 6: //Find a Property Holder
                    searchUserMenu(sessionUser);
                    break;
                case 7: //Exit Program
                default:
                    askAgain = false;
                    break;
            }

            //if returned from previous page
            if (actionType != 7)
                askAgain = true;
            else
                askAgain = false;

        } while(askAgain);
    }

    //property holder session
    public static void propertyHolderSession(){
        boolean askAgain = false;
        int actionType;
        
        do{
            DataInputHelper.clearScreen();
            DataInputHelper.printHeader("PROPERTY HOLDER MENU");
            System.out.println("Hello, " + sessionUser.getName() + "!");
            System.out.println("What would you like to do today?");
            DataInputHelper.printSeparator();
            System.out.println("1. Update Account Details");     //done
            System.out.println("2. Update Property Details");    //done
            System.out.println("3. Reassign Property");          //done
            System.out.println("4. Check View Requests");        //done
            System.out.println("5. Check Rent Applications");    //done
            System.out.println("6. Check Maintenance Requests"); //done
            System.out.println("7. Generate Occupancy Report");  //done
            System.out.println("8. Find a Tenant");              //done
            System.out.println("9. Log Out");                    //done
            DataInputHelper.printSeparator();
            actionType = DataInputHelper.readRangedInt(scanner, 1, 9);
            
            //determine the authentication type
            switch(actionType){
                case 1: //Update Account Details
                    changeAccountDetails(sessionUser);
                    break;
                case 2: //Update Property Details
                    updatePropertyMenu(sessionUser);
                    break;
                case 3: //Reassign Property
                    changeProperty(sessionUser);
                    break;
                case 4: //Check View Request (can approve or reject)
                    checkRequests(sessionUser, "view");
                    break;
                case 5: //Check Rent Application (can approve or reject)
                    checkRequests(sessionUser, "rent");
                    break;
                case 6: //Check Maintenance Requests (can update status)
                    checkRequests(sessionUser, "maintenance");
                    break;
                case 7: //Generate Occupancy Report
                    generateOccupancyReportMenu(sessionUser);
                    break;
                case 8: //Find a Tenant
                    searchUserMenu(sessionUser);
                    break;
                case 9: //Exit Program
                default:
                    askAgain = false;
                    break;
            }

            //if returned from previous page
            if (actionType != 9)
                askAgain = true;
            else
                askAgain = false;

        } while(askAgain);
    }
    
    //find person menu
    public static void searchUserMenu(Person p){
        Person toRate = null;
        //determine who is searching who
        if (p instanceof Tenant){
            toRate = propertyHolderRatingList();
        }
        else if (p instanceof PropertyHolder){
            toRate = tenantRatingList();
        }
        //rating menu if any
        if (toRate != null){
                rateUserMenu(toRate);
            return;
        }
        else
            return;
    }
    
    //rating menu
    public static void rateUserMenu(Person toRate){
        //unfortunately due to implementations of userdb saving, only one value of rating and comment can exist at a time
        DataInputHelper.clearScreen();
        DataInputHelper.printHeader("RATE USER");
        System.out.println(toRate);
        DataInputHelper.printSeparator();
        System.out.println("0. Exit view\n1. Overwrite Rating\n2. Overwrite Comment");
        int option = DataInputHelper.readRangedInt(scanner, 0, 2);
        if (option == 1){ //overwrite rating
            System.out.print("Enter the new rating: ");
            toRate.getPerformanceRating().setRating(DataInputHelper.readRangedDouble(scanner, PerformanceRating.getMinRating(), PerformanceRating.getMaxRating()));
        }
        else if (option == 2){ //overwrite comment
            toRate.getPerformanceRating().setComment(DataInputHelper.readNonEmptyString(scanner, "Enter the new comment: "));
        }
        DataInputHelper.askEnter(scanner);
    }
    
    //get PropertyHolder rating list and the chosen person
    public static Person propertyHolderRatingList(){
        Person p = null;
        ArrayList<Person> propertyHolders = new ArrayList<Person>();
        for (Person person: userdb.values()){
            if (person instanceof PropertyHolder){
                propertyHolders.add(person);
            }
        }
        DataInputHelper.clearScreen();
        DataInputHelper.printHeader("PROPERTY HOLDERS");
        System.out.format("\nNo  Type       %-2s %-20s %-20s\n", "ID", "Name", "Email");
        DataInputHelper.printSeparator();
        //display in list form
        for (int i = 0; i < propertyHolders.size(); i++){
            System.out.println(String.format("%-2d", i + 1) + ": " + propertyHolders.get(i).showBrief());
        }
        System.out.println("\nEnter the No, or enter 0 to return to the previous page");
        int option = DataInputHelper.readRangedInt(scanner, 0, propertyHolders.size());
        if (option != 0){
            p = propertyHolders.get((option - 1));
        }
        return p;
    }
    
    //get Tenant rating list and the chosen person
    public static Person tenantRatingList(){
        Person p = null;
        ArrayList<Person> tenants = new ArrayList<Person>();
        for (Person person: userdb.values()){
            if (person instanceof Tenant){
                tenants.add(person);
            }
        }
        DataInputHelper.clearScreen();
        DataInputHelper.printHeader("TENANTS");
        System.out.format("\nNo  Type       %-2s %-20s %-20s\n", "ID", "Name", "Email");
        DataInputHelper.printSeparator();
        //display in list form
        for (int i = 0; i < tenants.size(); i++){
            System.out.println(String.format("%-2d", i + 1) + ": " + tenants.get(i).showBrief());
        }
        System.out.println("\nEnter the No, or enter 0 to return to the previous page");
        int option = DataInputHelper.readRangedInt(scanner, 0, tenants.size());
        if (option != 0){
            p = tenants.get((option - 1));
        }
        return p;
    }
    
    public static void changeAccountDetails(Person p){
        DataInputHelper.clearScreen();
        DataInputHelper.printHeader("CHANGE ACCOUNT DETAILS");
        p.setName(DataInputHelper.readNonEmptyString(scanner, "Enter your name: "));
        p.setEmail(DataInputHelper.readEmail(scanner, "Enter your email: "));
        p.setTelNo(DataInputHelper.readPhoneNumber(scanner, "Enter your telephone number: "));
        System.out.println("Update successful!");
        DataInputHelper.askEnter(scanner);
    }
    
    //update budget menu
    public static void updateBudget(Person p){
        if (p instanceof Tenant){
            DataInputHelper.clearScreen();
            DataInputHelper.printHeader("UPDATE BUDGET");
            ((Tenant)p).setBudget(DataInputHelper.readPositiveDouble(scanner, "Enter budget: "));
            System.out.println("Update successful!");
            DataInputHelper.askEnter(scanner);
        }
    }
    
    //lease menu
    public static void displayLeaseMenu(Person p){
        if (p instanceof Tenant){
            DataInputHelper.clearScreen();
            if (((Tenant)p).getLease() != null){
                Lease l = ((Tenant)p).getLease();
                DataInputHelper.printHeader("LEASE DETAILS");
                System.out.println(l + "\n");
                
                //check if has paid
                if (!l.hasPaidThisMonth(LocalDate.now())){
                    RentReminder r = new RentReminder(l.getMonthlyRent(), "You have not paid rent this month.");
                    System.out.println(r + "\n");
                    System.out.println("Would you like to pay now?\n0. Yes\n1. No");
                    if (DataInputHelper.readRangedInt(scanner, 0, 1) == 0){
                        paymentMenu(l);
                    }
                }
                else
                    System.out.println("You have already paid rent this month.");
            }
            else {
                System.out.println("You have no existing Lease.");
            }
            DataInputHelper.askEnter(scanner);
        }
    }
    
    public static void displayMaintenanceSubmissionMenu(Person p){
        if (p instanceof Tenant){
            DataInputHelper.clearScreen();
            if (((Tenant)p).getLease() != null){
                DataInputHelper.printHeader("MAINTENANCE REQUEST");
                Lease l = ((Tenant)p).getLease();
                while (true){
                    String desc = DataInputHelper.readNonEmptyString(scanner, "\nDescribe the issue: ");
                    System.out.println("\nRequest Description:\n" + desc + "\n\n0. Confirm submission\n1. Amend issue details\n2. Cancel submission\n");
                    int requestOption = DataInputHelper.readRangedInt(scanner, 0, 2);
                    if (requestOption == 0){
                        MaintenanceRequest request = new MaintenanceRequest(l.getProperty(), LocalDateTime.now(), (Tenant)p, desc);
                        requestdb.get(l.getProperty().getPropertyID()).get("maintenance").add(request);
                        System.out.println("\nRequest submitted successfully.");
                        break;
                    }
                    else if (requestOption == 2){
                        break;
                    }
                }
            
            }
            else {
                System.out.println("You cannot submit a maintenance request without a lease.");
                
            }
            DataInputHelper.askEnter(scanner);
        }
    }
    
    //payment menu
    public static void paymentMenu(Lease l){
        double monthlyRent = l.getMonthlyRent();
        while (true){
            double tenantPayAmount = DataInputHelper.readPositiveDouble(scanner, "\n(Required: RM" + String.format("%.2f", monthlyRent)+ ") Enter Pay Amount: RM");
            if (l.payMonthlyRent(new Payment(tenantPayAmount, LocalDateTime.now())))
                break;
        }
    }

    //reassign property for propertyholders
    public static void changeProperty(Person p){
        if (p instanceof PropertyHolder){
            DataInputHelper.clearScreen();
            DataInputHelper.printHeader("REASSIGN PROPERTY");
            displayPropertiesList();
            System.out.println();
            ((PropertyHolder)p).setProperty(DataInputHelper.readPropertyFromID(scanner, "Enter new property ID: ", propertydb));
        System.out.println("Update successful!");
        DataInputHelper.askEnter(scanner);
        }
    }
    
    //request list menu depending on type
    public static void checkRequests(Person p, String type){
        if (p instanceof PropertyHolder){
            Property property = ((PropertyHolder)p).getProperty();
            ArrayList<Request> requests = requestdb.get(property.getPropertyID()).get(type);
            if (requests.size() > 0){
                while (true){
                    DataInputHelper.clearScreen();
                    //assuming type wont be enpty
                    DataInputHelper.printHeader(type.toUpperCase() + " REQUESTS OF PROPERTY " + property.getPropertyID());
                    System.out.format("%3s %-3s %-20s %-9s %s\n", "Opt", "ID", "Date", "Status", "Description");
                    DataInputHelper.printSeparator();
                    for (int i = 0; i < requests.size(); i++){
                        System.out.format("%3d %s \n", (i + 1), requests.get(i).showBrief());
                    }
                    DataInputHelper.printSeparator();
                    System.out.println("Choose an opt (1 - " + requests.size() + ") to view/update the requests, or enter 0 to exit.");
                    int option = DataInputHelper.readRangedInt(scanner, 0, requests.size());
                    if (option > 0){
                        switch (type){
                            case "maintenance":
                                updateMaintenanceRequestMenu((MaintenanceRequest)requests.get(option - 1));
                                break;
                            case "view":
                                updateViewRequestMenu((ViewRequest)requests.get(option - 1));
                                break;
                            case "rent":
                                updateRentApplicationMenu((RentApplication)requests.get(option - 1));
                                break;
                            default:
                                break;
                        }
                    }
                    else
                        break;
                }
            }
            else {
                DataInputHelper.clearScreen();
                System.out.println("No " + type + " requests yet!");
                DataInputHelper.askEnter(scanner);
            }
        }
    }
    
    //update maintenance requests menu
    public static void updateMaintenanceRequestMenu(MaintenanceRequest r){
        DataInputHelper.clearScreen();
        DataInputHelper.printHeader("MAINTENANCE REQUEST DETAILS");
        System.out.println(r);
        DataInputHelper.printSeparator();
        System.out.println("0. Exit View\n1. Update Status");
        int option = DataInputHelper.readRangedInt(scanner, 0, 1);
        if (option == 1){
            System.out.println("\n\nUpdate Maintenance Request Status:");
            System.out.println("0. Pending\n1. Completed");
            int statusOption = DataInputHelper.readRangedInt(scanner, 0, 1);
            if (statusOption == 0)
                r.setStatus(RequestStatus.PENDING);
            else
                r.setStatus(RequestStatus.COMPLETE);
            System.out.println("Status updated successfully.");
            DataInputHelper.askEnter(scanner);
        }
        else
            return;
    }
    
    //update view requests menu
    public static void updateViewRequestMenu(ViewRequest r){
        DataInputHelper.clearScreen();
        DataInputHelper.printHeader("VIEW REQUEST DETAILS");
        System.out.println(r);
        DataInputHelper.printSeparator();
        if (r.getStatus() == RequestStatus.PENDING){ //only can update if pending
            System.out.println("0. Exit View\n1. Update Status");
            int option = DataInputHelper.readRangedInt(scanner, 0, 1);
            if (option == 1){
                System.out.println("\n\nUpdate View Request Status:");
                System.out.println("0. Pending\n1. Rejected\n2. Approved");
                int statusOption = DataInputHelper.readRangedInt(scanner, 0, 2);
                if (statusOption == 0)
                    r.setStatus(RequestStatus.PENDING);
                else if (statusOption == 1)
                    r.setStatus(RequestStatus.REJECTED);
                else if (statusOption == 2)
                    r.setStatus(RequestStatus.APPROVED);
                System.out.println("Status updated successfully.");
                DataInputHelper.askEnter(scanner);
                return;
            }
            else
                return;
        }
        else
            System.out.println("View Request is view only.");
            DataInputHelper.askEnter(scanner);
            return;
    }
    
    //update rent requests menu
    public static void updateRentApplicationMenu(RentApplication r){
        DataInputHelper.clearScreen();
        DataInputHelper.printHeader("RENT APPLICATION DETAILS");
        System.out.println(r);
        DataInputHelper.printSeparator();
        if (r.getStatus() == RequestStatus.PENDING){ //only can update if pending
            System.out.println("0. Exit View\n1. Update Status");
            int option = DataInputHelper.readRangedInt(scanner, 0, 1);
            if (option == 1){
                System.out.println("\n\nUpdate Maintenance Request Status:");
                System.out.println("0. Pending\n1. Rejected\n2. Approved");
                int statusOption = DataInputHelper.readRangedInt(scanner, 0, 2);
                if (statusOption == 0)
                    r.setStatus(RequestStatus.PENDING);
                else if (statusOption == 1)
                    r.setStatus(RequestStatus.REJECTED);
                else if (statusOption == 2){
                    r.setStatus(RequestStatus.APPROVED);
                    
                    LocalDate now = r.getProposedStartDate();
                    Property property = r.getProperty();
                    //approved so we assign a lease and update occupancy
                    Lease l = new Lease(
                        property,
                        now,
                        now.plusMonths(r.getLeaseDurationMonths()),
                        r.getOfferedDeposit(),
                        property.getListingPrice(),
                        now
                    );
                    leasedb.put(l.getLeaseID(), l);
                    r.getTenant().setLease(l);
                    property.getOccupancy().addOccupants(r.getExpectedOccupants());
                }
                System.out.println("Status updated successfully.");
                DataInputHelper.askEnter(scanner);
                return;
            }
            else
                return;
        }
        else
            System.out.println("Rent Request is view only.");
            DataInputHelper.askEnter(scanner);
            return;
    }

    //displays all properties in a list used by changeProperty()
    public static void displayPropertiesList(){
        ArrayList<String> propertyKeys = new ArrayList<>(propertydb.keySet());
        Collections.sort(propertyKeys);
        System.out.format("%-4s  %-10s     %-20s\n", "P-ID", "Type", "Location");
        DataInputHelper.printSeparator();
        for (String pID: propertyKeys){
            System.out.println(propertydb.get(pID).showBrief());
        }
        System.out.println();
    }
    
    // Update property
    public static void updatePropertyMenu(Person p) {
        if (p instanceof PropertyHolder){
            Property property = ((PropertyHolder)p).getProperty();
            DataInputHelper.clearScreen();
            DataInputHelper.printHeader("UPDATE PROPERTY");
            DataInputHelper.printSeparator();
            System.out.println(property + "\n");
            DataInputHelper.printSeparator();
            System.out.println("What would you like to update?");
            System.out.println("1. Listing price");
            System.out.println("2. Square footage");
            System.out.println("3. Location");
            System.out.println("4. Status");
            System.out.println("5. Occupancy (number of occupants)");
            System.out.println("6. Description");
            System.out.println("7. Subtype specific attributes (floors, pool, garden, height)");
            System.out.println("8. Cancel");
            System.out.print("Select the option:");
            int choice = DataInputHelper.readRangedInt(scanner, 1, 8);
    
            switch (choice) {
                case 1:
                    double newPrice = DataInputHelper.readPositiveDouble(scanner, "New listing price: RM");
                    property.setListingPrice(newPrice);
                    System.out.println("Listing price updated.");
                    DataInputHelper.askEnter(scanner);
                    break;
                case 2:
                    double newSqft = DataInputHelper.readPositiveDouble(scanner, "New square footage: ");
                    property.setSquareFootage(newSqft);
                    System.out.println("Square footage updated.");
                    DataInputHelper.askEnter(scanner);
                    break;
                case 3:
                    String newLoc = DataInputHelper.readNonEmptyString(scanner, "New location: ");
                    property.setLocation(newLoc);
                    System.out.println("Location updated.");
                    DataInputHelper.askEnter(scanner);
                    break;
                case 4:
                    String newStatus = DataInputHelper.readValidStatus(scanner, "New status (Available/Rented/Sold): ");
                    property.setStatus(newStatus);
                    System.out.println("Status updated.");
                    DataInputHelper.askEnter(scanner);
                    break;
                case 5:
                    int newOccupants = DataInputHelper.readPositiveInt(scanner, "New number of occupants: ");
                    property.getOccupancy().setOccupants(newOccupants);
                    System.out.println("Occupancy updated.");
                    DataInputHelper.askEnter(scanner);
                    break;
                case 6:
                    String newDesc = DataInputHelper.readNonEmptyString(scanner, "New description: ");
                    property.getDetails().setDescription(newDesc);
                    System.out.println("Description updated.");
                    DataInputHelper.askEnter(scanner);
                    break;
                case 7:
                    updateSubtypeAttributes(property);
                    DataInputHelper.askEnter(scanner);
                    break;
                case 8:
                    System.out.println("Update cancelled.");
                    break;
            }
        }
    }

    public static void updateSubtypeAttributes(Property p) {
        if (p instanceof Apartment) {
            Apartment apt = (Apartment) p;
            int newFloors = DataInputHelper.readPositiveInt(scanner, "New number of floors (current: " + apt.getFloors() + "): ");
            apt.setFloors(newFloors);
            System.out.println("Apartment floors updated.");
        } else if (p instanceof Villa) {
            Villa villa = (Villa) p;
            int newFloors = DataInputHelper.readPositiveInt(scanner, "New number of floors (current: " + villa.getFloors() + "): ");
            boolean newPool = DataInputHelper.readBooleanInput(scanner, "Has pool? (current: " + villa.getHasPool() + "): ");
            boolean newGarden = DataInputHelper.readBooleanInput(scanner, "Has garden? (current: " + villa.getHasGarden() + "): ");
            villa.setFloors(newFloors);
            villa.setHasPool(newPool);
            villa.setHasGarden(newGarden);
            System.out.println("Villa attributes updated.");
        } else if (p instanceof Warehouse) {
            Warehouse wh = (Warehouse) p;
            double newHeight = DataInputHelper.readPositiveDouble(scanner, "New height (current: " + wh.getHeight() + "): ");
            wh.setHeight(newHeight);
            System.out.println("Warehouse height updated.");
        } else if (p instanceof Office) {
            System.out.println("Office has no subtype specific attributes.");
        } else {
            System.out.println("there is no subtype attributes to update.");
        }
    }

    // Generate occupancy report
    public static void generateOccupancyReportMenu(Person p) {
        if (p instanceof PropertyHolder){
            Property property = ((PropertyHolder)p).getProperty();
            DataInputHelper.clearScreen();
            DataInputHelper.printHeader("OCCUPANCY REPORT");
            System.out.println(property.getOccupancy().generateOccupancyReport());
            System.out.println();
            DataInputHelper.askEnter(scanner);
        }
    }
    
    // Displays a list of properties with indices.
    public static void displayFoundProperties(ArrayList<Property> properties) {
        if (properties.isEmpty()) {
            System.out.println("\nNo properties match your search criteria.");
            return;
        }
        System.out.println("\nMatching Properties:");
        DataInputHelper.printSeparator();
        for (int i = 0; i < properties.size(); i++) {
            Property p = properties.get(i);
            System.out.format("%-2d. %-4s | %-9s | RM%-7.2f | %-7.2f sq ft | %-30s%n",
                    (i + 1), p.getPropertyID(), p.getType(), p.getListingPrice(),
                    p.getSquareFootage(), p.getLocation());
        }
        DataInputHelper.printSeparator();
    }

    // Returns the selected property or null if the user cancels.
    public static Property selectProperty(ArrayList<Property> properties) {
        if (properties.isEmpty())
            return null;

        while (true) {
            System.out.print("\nEnter the number of the property (or 0 to cancel): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 0)
                    return null;
                if (choice >= 1 && choice <= properties.size())
                    return properties.get(choice - 1);
                System.out.println("Invalid selection. Please choose a number between 1 and " + properties.size());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Submits a ViewRequest for the given property and tenant.
    public static void submitViewRequest(Property property, Tenant tenant) {
        DataInputHelper.clearScreen();
        DataInputHelper.printHeader("REQUEST TO VIEW");
        System.out.println("Property: " + property.getPropertyID() + " (" + property.getType() + ")");

        LocalDateTime preferredDate = DataInputHelper.readLocalDateTime(scanner, 
                "Preferred viewing date (DD-MM-YYYY): ",
                "Preferred viewing time (HH:MM): ",
                true);
        int attendees = DataInputHelper.readPositiveInt(scanner, "Number of attendees: ");
        String remarks = DataInputHelper.readNonEmptyString(scanner, "Remarks (e.g., special requests): ");

        ViewRequest request = new ViewRequest(property, LocalDateTime.now(), tenant,
                preferredDate, attendees, remarks);
        requestdb.get(property.getPropertyID()).get("view").add(request);

        System.out.println("View request submitted successfully!");
        System.out.println("Request ID: " + request.getRequestID());
        System.out.println("Status: " + request.getStatus());
        DataInputHelper.askEnter(scanner);
    }

    // Submits a RentApplication for the given property and tenant.
    // Checks if tenant's budget is sufficient; if not, declines the application.
    public static void submitRentApplication(Property property, Tenant tenant) {
        DataInputHelper.clearScreen();
        DataInputHelper.printHeader("APPLICATION TO RENT");
        System.out.println("Property: " + property.getPropertyID() + " (" + property.getType() + ")");
        System.out.printf("Listing price: RM%.2f%n", property.getListingPrice());
        System.out.printf("Your budget: RM%.2f%n", tenant.getBudget());

        // Budget check: tenant must have enough budget to afford the property
        if (tenant.getBudget() < property.getListingPrice()) {
            System.out.println("Application declined: Your budget (RM" + String.format("%.2f", tenant.getBudget()) +
                    ") is lower than the property's listing price (RM" + String.format("%.2f", property.getListingPrice()) + ").");
            System.out.println("Please consider increasing your budget or choosing a more affordable property.");
            DataInputHelper.askEnter(scanner);
            return;
        }

        LocalDate startDate = DataInputHelper.readLocalDate(scanner, "Proposed start date (DD-MM-YYYY): ", true);
        int leaseDuration = DataInputHelper.readPositiveInt(scanner, "Lease duration (months): ");
        double offeredDeposit = DataInputHelper.readPositiveDouble(scanner, "Offered deposit amount (RM): ");
        int expectedOccupants = DataInputHelper.readPositiveInt(scanner, "Expected number of occupants: ");

        RentApplication application = new RentApplication(property, LocalDateTime.now(), tenant,
                startDate, leaseDuration, offeredDeposit, expectedOccupants);
        requestdb.get(property.getPropertyID()).get("rent").add(application);

        System.out.println("Rent application submitted successfully!");
        System.out.println("Request ID: " + application.getRequestID());
        System.out.println("Status: " + application.getStatus());
        DataInputHelper.askEnter(scanner);
    }

    // Searches available properties matching the given criteria.
    public static ArrayList<Property> searchProperties(double maxBudget, String locationKeyword, String propertyType) {
        ArrayList<Property> results = new ArrayList<>();
        for (Property p : propertydb.values()) {
            // Only available properties
            if (!p.getStatus().equalsIgnoreCase("Available"))
                continue;

            // Budget filter
            if (p.getListingPrice() > maxBudget)
                continue;

            // Property type filter
            if (propertyType != null && !propertyType.trim().isEmpty()) {
                if (!p.getType().equalsIgnoreCase(propertyType))
                    continue;
            }

            // Location filter
            if (!locationMatches(p, locationKeyword))
                continue;

            results.add(p);
        }
        return results;
    }

    // Expands a location keyword if it matches a known abbreviation
    // Returns the expanded name, or the original keyword if no match
    public static String expandLocationAbbreviation(String keyword) {
        if (keyword == null || keyword.trim().isEmpty())
            return keyword;
        String lowerKeyword = keyword.toLowerCase().trim();
        if (LOCATION_ABBREVIATIONS.containsKey(lowerKeyword)) {
            return LOCATION_ABBREVIATIONS.get(lowerKeyword);
        }
        return keyword;
    }

    // Checks whether a property's location matches the given search keyword.
    public static boolean locationMatches(Property property, String locationKeyword) {
        if (locationKeyword == null || locationKeyword.trim().isEmpty())
            return true;
        String propLocation = property.getLocation().toLowerCase();
        String keywordLower = locationKeyword.toLowerCase().trim();

        // Direct substring match
        if (propLocation.contains(keywordLower))
            return true;

        // Try expanding abbreviation and check again
        String expanded = expandLocationAbbreviation(keywordLower);
        if (!expanded.equals(keywordLower) && propLocation.contains(expanded.toLowerCase()))
            return true;

        return false;
    }

    // Flow: input search criteria, display results, select property, choose request type.
    public static void searchAndSubmitFlow() {
        DataInputHelper.clearScreen();
        DataInputHelper.printHeader("SEARCH PROPERTIES");
        System.out.println("(Location supports abbreviations: KL, PG, JB, SL, ML, etc.)");
        System.out.println("(Property types available: Apartment, Villa, Office, Warehouse)");

        double budget = DataInputHelper.readPositiveDouble(scanner, "Maximum budget (RM): ");
        String location = DataInputHelper.readNonEmptyString(scanner, "Location keyword (e.g., Penang, KL): ");
        System.out.print("Property type (leave empty to skip): ");
        String type = scanner.nextLine().trim();
        if (type.isEmpty()) type = null;

        ArrayList<Property> results = searchProperties(budget, location, type);
        displayFoundProperties(results);

        if (results.isEmpty()) {
            System.out.println("\nNo properties found. Please adjust your criteria. Returning to main menu.");
            DataInputHelper.askEnter(scanner);
            return;
        }

        Property selected = selectProperty(results);
        if (selected == null) {
            System.out.println("Property selection cancelled.");
            return;
        }

        DataInputHelper.clearScreen();
        DataInputHelper.printHeader("SELECTED PROPERTY");
        System.out.println("Selected property: " + selected.getPropertyID() + " - " + selected.getType());
        System.out.println("Property Details:\n" + selected + "\n");
        System.out.println("1. Submit Request to View");
        System.out.println("2. Submit Application to Rent");
        System.out.println("3. Cancel");
        int reqType = DataInputHelper.readRangedInt(scanner, 1, 3);

        switch (reqType) {
            case 1:
                submitViewRequest(selected, (Tenant)sessionUser);
                break;
            case 2:
                submitRentApplication(selected, (Tenant)sessionUser);
                break;
            case 3:
                System.out.println("Request cancelled.");
                break;
        }
    }
    
    //inserts empty hashmap templates for each property
    public static void setupRequestdbTemplate(HashMap<String, HashMap<String, ArrayList<Request>>> requestdb, HashMap<String, Property> propertydb){
        for (Property p: propertydb.values()) {
            HashMap<String, ArrayList<Request>> propertyRequests = new HashMap<>();
            propertyRequests.put("maintenance", new ArrayList<Request>());
            propertyRequests.put("rent", new ArrayList<Request>());
            propertyRequests.put("view", new ArrayList<Request>());
            requestdb.put(p.getPropertyID(), propertyRequests);
        }
    }
    
    //loads all data
    public static void loadData(){
        LoadHelper.loadUsers(outputDir.resolve("users.txt"), users);
        LoadHelper.loadPropertydb(outputDir.resolve("propertydb.txt"), propertydb);
        LoadHelper.loadLeasedb(outputDir.resolve("leasedb.txt"), leasedb, propertydb);
        LoadHelper.loadUserdb(outputDir.resolve("userdb.txt"), userdb, propertydb, leasedb);
        setupRequestdbTemplate(requestdb, propertydb);
        LoadHelper.loadRequestdb(outputDir.resolve("requestdb.txt"), requestdb, userdb, propertydb);
        leaseCheck();
        
        checkIfLoadFail();
    }
    
    //if loading fails, repopulate with preset data
    public static void checkIfLoadFail(){
        if (propertydb.isEmpty()){
            Populatedb.populatePropertydb(propertydb);
            System.out.println("Creating dummy property data...");
        }
        if (users.isEmpty()){
            Populatedb.populateUsers(users);
            System.out.println("Creating dummy users data...");
        }
        if (userdb.isEmpty()){
            Populatedb.populateUserdb(userdb);
            ((PropertyHolder)userdb.get(1)).setProperty(propertydb.get("P001"));
            ((PropertyHolder)userdb.get(2)).setProperty(propertydb.get("P007"));
            System.out.println("Creating dummy userdb data...");
        }
        if (requestdb.isEmpty()){
            setupRequestdbTemplate(requestdb, propertydb);
        }
    }
    
    //runtime lease check
    public static void leaseCheck(){
        for (Person p: userdb.values()){
            if (p instanceof Tenant && ((Tenant)p).getLease() != null){
                Lease l = ((Tenant)p).getLease();
                if (LocalDate.now().isAfter(l.getEndDate())){
                    //lease has expired
                    Property property = l.getProperty();

                    //brute force search for requests the lease is related to
                    for (Request r: requestdb.get(property.getPropertyID()).get("rent")){
                        LocalDate endDate = ((RentApplication)r).getProposedStartDate().plusMonths(((RentApplication)r).getLeaseDurationMonths());
                        System.out.println(((RentApplication)r).getProposedStartDate() + " " +  l.getStartDate());
                        System.out.println(endDate + " " + l.getEndDate());
                        
                        //compare the dates and the tenant sender
                        if (((RentApplication)r).getProposedStartDate().equals(l.getStartDate()) && endDate.equals(l.getEndDate()) && (p == ((RentApplication)r).getTenant())){
                            //found the request
                            ((Tenant)p).setLease(null);
                            property.getOccupancy().removeOccupants(((RentApplication)r).getExpectedOccupants());
                            break;
                        }
                    }
                }
            }
        }
    }

    //saves all data
    public static void saveData(){
        DumpHelper.dumpUsers(outputDir.resolve("users.txt"), users);
        DumpHelper.dumpUserdb(outputDir.resolve("userdb.txt"), userdb);
        DumpHelper.dumpPropertydb(outputDir.resolve("propertydb.txt"), propertydb);
        DumpHelper.dumpRequestdb(outputDir.resolve("requestdb.txt"), requestdb);
        DumpHelper.dumpLeasedb(outputDir.resolve("leasedb.txt"), leasedb);
    }
}
