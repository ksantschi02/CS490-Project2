/*
AUTHOR: Kevin Santschi
ClASS:  CS490-02 Spring 2024
PROJECT 2

The purpose of this program is to simulate a multithread processor in order to
demonstrate priority queue process scheduling.
*/

package cs490santschiproject2;

import java.util.Scanner;

public class CS490SantschiProject2 {
    
    // Bool to determine whether to continue starting processes
    // Int to track process id numbers
    private static boolean procActive = true;
    private static int counter = 0;
    
    public static void main(String[] args) {
        // Create a new process scheduler queue to link all threads to
        ProcScheduler scheduler = new ProcScheduler();

        // Start 5 processor threads
        for (int i = 0; i < 5; i++) {
            ProcessorThread processor = new ProcessorThread(scheduler);
            Thread thread = new Thread(processor);
            thread.start();
        }
        
        // Initialize scanner
        Scanner scanner = new Scanner(System.in);
        
        // While the processor is active,
        while (procActive) {
            // Accept the next input line;
            String inputLine = scanner.nextLine();
            
            // If it is the word shutdown, tell the processor to stop accepting
            // new processes and break from the while loop
            if ((inputLine.replaceAll("\\s+", "")).equalsIgnoreCase("shutdown")) {
                procActive = false;
                continue;
            }
            
            // Otherwise, split input by whitespace and store in array
            String[] inputArray = inputLine.split("\\s+");
            
            // If the first word is process,
            if (inputArray[0].equalsIgnoreCase("process")) {
                try {
                    // Try to accept split strings as process elements:
                    int msToRun = Integer.parseInt(inputArray[1]);
                    String procClass = inputArray[2];
                    
                    // If the input processor class is not one of the specified
                    // values, throw an error and restart the loop
                    if (!"RT".equals(procClass) && !"NM".equals(procClass)) {
                        System.out.println("Invalid input format. Please enter as 'PROCESS [ms] [RT|NM] [priority] [name]'.");
                        continue;
                    }
                    
                    // More process elements
                    int priority = Integer.parseInt(inputArray[3]);
                    
                    // After the first 3 elements, take all remaining words in
                    // the array and concatenate into one name
                    String name = "";
                    for (int i = 4; i < inputArray.length; i++) {
                        name += inputArray[i];
                        if (i < inputArray.length - 1) {
                            name += " "; // Add whitespace except for the last word
                        }
                    }
                    
                    // Form elements into a new process. add it to the scheduler,
                    // and increment the counter
                    scheduler.addProcess(new Process(counter, name, msToRun, procClass, priority));
                    counter += 1;
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    // Handle invalid input format
                    System.out.println("Invalid input format. Please enter as 'PROCESS [ms] [RT|NM] [priority] [name]'.");
                }
            } else {
                // Handle invalid input format
                System.out.println("Invalid input format. Please enter as 'PROCESS [ms] [RT|NM] [priority] [name]'.");
            }
        }
        
        // Close scanner
        scanner.close();
    }
    
    // Function to return the processor activity state
    public static synchronized boolean getActive() {
        return procActive;
    }
}
