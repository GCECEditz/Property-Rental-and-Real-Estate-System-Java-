/**
 * @(#)PerformanceRating.java
 *
 *
 * @author 
 * @version 1.00 2026/4/1
 */

import java.lang.Math;

public class PerformanceRating{

    private static double minRating = 0;
    private static double maxRating = 5;
    private double rating;
    private String comment;

    public PerformanceRating(){
        this(0, " ");
    }
    
    public PerformanceRating(double rating, String comment){
        setRating(rating);
        this.comment = comment;
    }
    
    //getters
    
    public double getRating(){
        return rating;
    }
    
    public String getComment(){
        return comment;
    }
    
    public static double getMaxRating(){
        return maxRating;
    }
    
    public static double getMinRating(){
        return minRating;
    }
    
    //setters
    
    public void setRating(double rating){
        this.rating = Math.max(Math.min(maxRating, rating), minRating);
    }
    
    public void setComment(String comment){
        this.comment = comment;
    }
    
    //etc
    
    public String toString(){
        return String.format("Rating: %.1f\nComment: %s", rating, comment);
    }
}