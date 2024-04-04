package cs490santschiproject2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Class for process objects
public class Process implements Runnable {
    // Vars to store process information
    private int id;
    private String name;
    private int msToRun;
    private String procClass;
    private int priority;
    
    // Constructor function
    public Process(int id, String name, int msToRun, String procClass, int priority) {
        this.id = id;
        this.name = name;
        this.msToRun = msToRun;
        this.procClass = procClass;
        this.priority = priority;
    }
    
    // Run method
    @Override
    public void run() {
        // Retrieve start time and print to console
        String startTime = getTime();
        System.out.println("BEGIN\t" + startTime + "\t" + id + "\t" + name);
        
        // Wait for the specified amount of time
        try {
            Thread.sleep(msToRun);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Retrieve end time and print to console
        String endTime = getTime();
        System.out.println("END\t" + endTime + "\t" + id + "\t" + name);
    }
    
    // Function to format and retrieve exact time
    private String getTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return now.format(formatter);
    }
    
    // Function to retrieve priority number
    public int getPriority() {
        return priority;
    }

    // Function to retrieve process class
    public String getProcClass() {
        return procClass;
    }
}
