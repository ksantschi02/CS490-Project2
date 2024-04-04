package cs490santschiproject2;

// Class for processor thread objects
public class ProcessorThread extends Thread {
    // Vars to link to scheduler queue and check whether the thread is currently
    // processing a thread to make sure it can only process one at a time
    private final ProcScheduler scheduler;
    private boolean isProcessing = false;

    // Constructor function
    public ProcessorThread(ProcScheduler scheduler) {
        this.scheduler = scheduler;
    }

    // Thread running function
    @Override
    public void run() {
        // While the main loop is active,
        while (CS490SantschiProject2.getActive()) {
            // And if the thread isn't currently processing anything,
            if (!isProcessing) {
                // And if the scheduler queue is not empty,
                if (!(scheduler.isListEmpty())) {
                    // Try to retrieve the first process in the list, lock the
                    // processor thread and run the process
                    try {
                        Process process = scheduler.getProcess();
                        isProcessing = true;
                        process.run();
                        isProcessing = false;
                    } catch (IndexOutOfBoundsException e) {
                        // This catch just makes sure that if this thread tries
                        // to accept a thread that has already been claimed, and
                        // tries to pull from an empty queue, it does not throw
                        // breaking errors.
                    }
                }
            }
        }
    }
}
