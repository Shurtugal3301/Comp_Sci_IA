import java.util.Timer;
import java.util.TimerTask;

class UpdateTimer {

    /**
     * Constructs a default timer that checks for updates every 20 milliseconds
     */
    UpdateTimer() {

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Task(), 500L, 20L);

    }

    /**
     * Class that the timer will run every update
     */
    class Task extends TimerTask {

        /**
         * Checks the window class for any updates to graphics or other methods
         */
        public void run() {

            Window.DoWindowUpdates();

        }

    }

}