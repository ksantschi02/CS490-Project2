package cs490santschiproject2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Class for process scheduler queue object
public class ProcScheduler {
    // Var to store ArrayList of processes
    private List<Process> processes;
    
    // Constructor function
    public ProcScheduler() {
        processes = new ArrayList<>();
    }
    
    // Function to add a process to the queue and sort it
    public synchronized void addProcess(Process process) {
        // Add the process to the ArrayList
        processes.add(process);

        // Sort the list using comparator/collection functionality
        Collections.sort(processes, new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                if (p1.getProcClass().equals("RT") && p2.getProcClass().equals("NM")) {
                    return -1; // RT comes before NM
                } else if (p1.getProcClass().equals("NM") && p2.getProcClass().equals("RT")) {
                    return 1; // RT comes before NM
                } else {
                    // Within the same class, sort by priority
                    return Integer.compare(p1.getPriority(), p2.getPriority());
                }
            }
        });
    }
    
    // Function to retrieve, remove and return the first process in the queue
    public synchronized Process getProcess() {
        return processes.remove(0);
    }
    
    // Function to check and return whether the queue is empty
    public boolean isListEmpty() {
        return processes.isEmpty();
    }
}
