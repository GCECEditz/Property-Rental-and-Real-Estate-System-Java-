/**
 * @(#)DumpHelper.java
 *
 *
 * @author 
 * @version 1.00 2026/4/5
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.nio.charset.*;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class DumpHelper {

    public static void dumpUsers(Path fname, HashMap<String, HashMap<String, Object>> users){
        createFile(fname);
        try (BufferedWriter writer = Files.newBufferedWriter(fname, StandardCharsets.UTF_8)) {
            for (Map.Entry<String, HashMap<String, Object>> entry : users.entrySet()) {
                HashMap<String, Object> userDat = entry.getValue();
                writer.write(entry.getKey() + " " + userDat.get("password") + " " + userDat.get("userID") + "\n");
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void dumpUserdb(Path fname, HashMap<Integer, Person> userdb){
        createFile(fname);
        try (BufferedWriter writer = Files.newBufferedWriter(fname, StandardCharsets.UTF_8)) {
            for (Map.Entry<Integer, Person> entry : userdb.entrySet()) {
                writer.write("<\n" + entry.getValue().asDat() + "\n>\n");
                writer.flush();
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void dumpPropertydb(Path fname, HashMap<String, Property> propertydb){
        createFile(fname);
        try (BufferedWriter writer = Files.newBufferedWriter(fname, StandardCharsets.UTF_8)) {
            for (Map.Entry<String, Property> entry : propertydb.entrySet()) {
                writer.write("<\n" + entry.getValue().asDat() + "\n>\n");
                writer.flush();
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void dumpLeasedb(Path fname, HashMap<Integer, Lease> leasedb){
        createFile(fname);
        try (BufferedWriter writer = Files.newBufferedWriter(fname, StandardCharsets.UTF_8)) {
            for (Map.Entry<Integer, Lease> entry : leasedb.entrySet()) {
                writer.write("<\n" + entry.getValue().asDat() + "\n>\n");
                writer.flush();
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void dumpRequestdb(Path fname, HashMap<String, HashMap<String, ArrayList<Request>>> requestdb){
        createFile(fname);
        try (BufferedWriter writer = Files.newBufferedWriter(fname, StandardCharsets.UTF_8)) {
            for (Map.Entry<String, HashMap<String, ArrayList<Request>>> entry : requestdb.entrySet()) {
                writer.write("<\n" + entry.getKey() + "\n");
                for (Map.Entry<String, ArrayList<Request>> requestTypes : entry.getValue().entrySet()){
                    writer.write("{\n" + requestTypes.getKey() + "\n");
                    for (Request r: requestTypes.getValue()){
                        writer.write("[\n" + r.asDat() + "\n]\n");
                    }
                    writer.write("}\n");
                }
                writer.write(">\n");
                writer.flush();
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFile(Path path){
        try {
            File file = path.toFile();
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists: " + file.getName());
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}