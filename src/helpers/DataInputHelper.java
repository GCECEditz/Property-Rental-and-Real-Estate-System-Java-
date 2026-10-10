/**
 * @(#)DateInputHelper.java
 *
 *
 * @author 
 * @version 1.00 2026/4/4
 */

//add restrictions to certain symbols

import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DataInputHelper {
    
    // Date formatter for DD-MM-YYYY
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    // Email validation pattern
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    // Phone number pattern
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[+]?[0-9][0-9\\s\\-]{5,20}$");

    private static final String BLACKLIST_CHARS = "<{[]}>";
    private static final int CONSOLE_WIDTH = 70;

    //some display methods
    public static void clearScreen(){
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
    
    public static void printHeader(String title) {
        String line = "=".repeat(CONSOLE_WIDTH);
        System.out.println(line);
        int padding = (CONSOLE_WIDTH - title.length()) / 2;
        System.out.printf("%" + padding + "s%s%" + (CONSOLE_WIDTH - padding - title.length()) + "s%n", "", title, "");
        System.out.println(line);
    }
    
    public static void printSeparator() {
        System.out.println("-".repeat(CONSOLE_WIDTH));
    }

    //input methods
    public static int readRangedInt(Scanner scanner, int min, int max){
        int option = min - 1;
        do{
            try{
                System.out.print("Please enter a number (" + min + "-" + max + "): ");
                option = scanner.nextInt();
                scanner.nextLine();
                if (option < min || option > max)
                    System.out.println(option + " is out of range (" + min + "-" + max + ")\n");
            }
            catch(java.util.InputMismatchException e){
                scanner.nextLine();
                System.out.println("Please enter a valid number\n");
            }
        } while (option < min || option > max);
        return option;
    }
    
    public static double readRangedDouble(Scanner scanner, double min, double max){
        double option = min - 1;
        do{
            try{
                System.out.print("Please enter a number (" + min + "-" + max + "): ");
                option = scanner.nextDouble();
                scanner.nextLine();
                if (option < min || option > max)
                    System.out.println(option + " is out of range (" + min + "-" + max + ")\n");
            }
            catch(java.util.InputMismatchException e){
                scanner.nextLine();
                System.out.println("Please enter a valid number\n");
            }
        } while (option < min || option > max);
        return option;
    }
    
    public static int readInt(Scanner scanner){
        int option = 0;
        while (true){
            try{
                System.out.print("Please enter a number: ");
                option = scanner.nextInt();
                scanner.nextLine();
                break;
            }
            catch(java.util.InputMismatchException e){
                scanner.nextLine();
                System.out.println("Please enter a valid number\n");
            }
        }
        return option;
    }
    
    public static String readSingleItem(Scanner scanner, String inputMsg){
        String option = " ";
        do{
            System.out.print(inputMsg);
            option = scanner.nextLine();
            if (option.contains(" ") || option.contains("\t")){
                System.out.println("Input must be continuous\n");
                option = " ";
            }
            else if (containsCharacters(option, BLACKLIST_CHARS)){
                System.out.println("Input must not contain special characters\n");
                option = " ";
            }
        } while ((option.contains(" ") || option.contains("\t")) && option.length() > 0);
        return option;
    }

    public static Property readPropertyFromID(Scanner scanner, String inputMsg, HashMap<String, Property> propertydb){
        while (true){
            //search for valid assignable property
            String propertyID = readSingleItem(scanner, inputMsg);
            if (propertydb.containsKey(propertyID.toUpperCase())){
                Property property = propertydb.get(propertyID.toUpperCase());
                return property;
            }
            else
                System.out.println("No such Property ID. Please try again\n");
        }
    }

    // Reads a positive double from the user.
    public static double readPositiveDouble(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                if (value >= 0)
                    return value;
                else
                    System.out.println("Value cannot be negative. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a numeric value.");
            }
        }
    }
    
    // Reads a positive integer from the user.
    public static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                if (value > 0)
                    return value;
                else
                    System.out.println("Value must be greater than 0. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Please enter a whole number.");
            }
        }
    }
    
    // Reads a valid email address.
    public static String readEmail(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("Email cannot be empty. Please try again.");
                continue;
            }
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                System.out.println("Invalid email format. Please enter a valid email (e.g., name@domain.com).");
                continue;
            }
            return email;
        }
    }
    
    // Reads a valid telephone number.
    public static String readPhoneNumber(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String phone = scanner.nextLine().trim();
            if (phone.isEmpty()) {
                System.out.println("Telephone number cannot be empty. Please try again.");
                continue;
            }
            if (!PHONE_PATTERN.matcher(phone).matches()) {
                System.out.println("Invalid phone number. Use digits, spaces, hyphens, optional leading '+' only. Minimum 6 digits.");
                continue;
            }
            return phone;
        }
    }
    
    // Reads a LocalDate from the user (format: DD-MM-YYYY).
    public static LocalDate readLocalDate(Scanner scanner, String prompt, boolean requireFuture) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                LocalDate date = LocalDate.parse(input, DATE_FORMATTER);
                if (requireFuture && date.isBefore(LocalDate.now())) {
                    System.out.println("Date must be today or in the future. Today is " + LocalDate.now().format(DATE_FORMATTER));
                    continue;
                }
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use DD-MM-YYYY (e.g., 09-04-2026).");
            }
        }
    }

    // Reads a LocalDateTime from the user (date DD-MM-YYYY and time HH:MM).
    public static LocalDateTime readLocalDateTime(Scanner scanner, String promptDate, String promptTime, boolean requireFuture) {
        LocalDate date = readLocalDate(scanner, promptDate, requireFuture);
        while (true) {
            try {
                System.out.print(promptTime);
                String timeStr = scanner.nextLine();
                String[] parts = timeStr.split(":");
                if (parts.length != 2) throw new DateTimeParseException("Invalid time", timeStr, 0);
                int hour = Integer.parseInt(parts[0]);
                int minute = Integer.parseInt(parts[1]);
                if (hour < 0 || hour > 23 || minute < 0 || minute > 59)
                    throw new DateTimeParseException("Hour 0-23, minute 0-59", timeStr, 0);
                LocalDateTime dateTime = date.atTime(hour, minute);
                if (requireFuture && dateTime.isBefore(LocalDateTime.now())) {
                    System.out.println("Date/time must be in the future. Current time: " + LocalDateTime.now().format(DATE_TIME_FORMATTER));
                    continue;
                }
                return dateTime;
            } catch (DateTimeParseException | NumberFormatException e) {
                System.out.println("Invalid time format. Please use HH:MM (e.g., 14:30).");
            }
        }
    }

    // Reads a non empty string from the user.
    public static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty() && !containsCharacters(input, BLACKLIST_CHARS))
                return input;
            System.out.println("Input cannot be empty and must be alphanumeric only. Please try again.");
        }
    }
    
    public static void askEnter(Scanner scanner){
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public static boolean containsCharacters(String input, String charsToMatch){
        for (char c: charsToMatch.toCharArray()){
            if (input.indexOf(c) >= 0){
                return true;
            }
        }
        return false;
    }
    
    public static boolean readBooleanInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.print("Please answer 'yes'/'no': ");
            }
        }
    }
    
    public static String readValidStatus(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("Available") || input.equalsIgnoreCase("Rented") || input.equalsIgnoreCase("Sold")) {
                return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
            } else {
            System.out.print("Invalid status. Please enter Available, Rented, or Sold: ");
            }
        }
    }
}