/**
 * @(#)Populatedb.java
 *
 *
 * @author 
 * @version 1.00 2026/4/8
 */

//this main file is only to manually prepopulate the db in the case a corruption or unintended file change has occured

import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Populatedb {

    public static void populatePropertydb(HashMap<String, Property> propertydb){
        
        PropertyDetails details = new PropertyDetails("Well maintained property.");
        Occupancy occ2 = new Occupancy(2);
        Occupancy occ4 = new Occupancy(4);
        Occupancy occ6 = new Occupancy(6);
        Occupancy occ10 = new Occupancy(10);

        // 1. Apartment in Penang
        Apartment a1 = new Apartment(1500, 800, "Penang, George Town", "Apartment",
                "Available", occ2, details, 5);
        propertydb.put(a1.getPropertyID(), a1);

        // 2. Apartment in Kuala Lumpur
        Apartment a2 = new Apartment(2500, 1000, "Kuala Lumpur, Bangsar", "Apartment",
                "Available", occ4, details, 12);
        propertydb.put(a2.getPropertyID(), a2);

        // 3. Villa in Penang
        Villa villa1 = new Villa(5000, 3000, "Penang, Batu Ferringhi", "Villa",
                "Available", occ4, details, 2, true, true);
        propertydb.put(villa1.getPropertyID(), villa1);

        // 4. Office in Kuala Lumpur
        Office office1 = new Office(3000, 1500, "Kuala Lumpur, City Centre", "Office",
                "Available", occ10, details);
        propertydb.put(office1.getPropertyID(), office1);

        // 5. Warehouse in Penang
        Warehouse wh1 = new Warehouse(2000, 5000, "Penang, Bayan Lepas", "Warehouse",
                "Available", occ10, details, 8.5);
        propertydb.put(wh1.getPropertyID(), wh1);

        // 6. Apartment in Johor Bahru
        Apartment a3 = new Apartment(1800, 900, "Johor Bahru, Taman Molek", "Apartment",
                "Available", occ4, details, 8);
        propertydb.put(a3.getPropertyID(), a3);

        // 7. Villa in Selangor (Petaling Jaya)
        Villa villa2 = new Villa(6000, 3500, "Selangor, Petaling Jaya", "Villa",
                "Available", occ6, details, 3, true, false);
        propertydb.put(villa2.getPropertyID(), villa2);

        // 8. Office in Penang
        Office office2 = new Office(2800, 1200, "Penang, Bayan Baru", "Office",
                "Available", occ6, details);
        propertydb.put(office2.getPropertyID(), office2);

        // 9. Warehouse in Kuala Lumpur
        Warehouse wh2 = new Warehouse(2200, 6000, "Kuala Lumpur, Cheras", "Warehouse",
                "Available", occ10, details, 9.0);
        propertydb.put(wh2.getPropertyID(), wh2);

        // 10. Apartment in Melaka
        Apartment a4 = new Apartment(1200, 700, "Melaka, Bandar Hilir", "Apartment",
                "Available", occ2, details, 4);
        propertydb.put(a4.getPropertyID(), a4);
    }
    
    public static void populateUsers(HashMap<String, HashMap<String, Object>> users){
        users.put("mary", new HashMap(Map.of("password", "mary2born2be", "userID", (Integer)0)));
        users.put("john", new HashMap(Map.of("password", "johndaman", "userID", (Integer)1)));
        users.put("sue", new HashMap(Map.of("password", "sue", "userID", (Integer)2)));
        users.put("polly", new HashMap(Map.of("password", "pollyparrot", "userID", (Integer)3)));
    }
    
    public static void populateUserdb(HashMap<Integer, Person> userdb){
        userdb.put((Integer)0, new Tenant("Mary", "mary@gmail.com", "012-3456789", 2500, null));
        userdb.put((Integer)1, new Landlord("John Doe", "johnd@gmail.com", "014-1120359", null));
        userdb.put((Integer)2, new Agent("Sue Williams", "suew@gmail.com", "016-2411525", null));
        userdb.put((Integer)3, new Tenant("Polly Poh", "pollypoh@gmail.com", "013-6162210", 4000, null));
    }
}
