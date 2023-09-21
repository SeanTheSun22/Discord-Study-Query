package discordstudyquery.eventdispatch;

import java.util.*;
import discordstudyquery.guild.DiscordModel;

public class DispatchTimer extends Timer {
    DiscordModel model;
        public DispatchTimer(DiscordModel model) {
        super();
        this.model = model;
        init();
    }

    private void init() {
        TimerTask cleanUpTask = new CleanUp(model);
        this.schedule(cleanUpTask, 300 * 1000, 300 * 1000);
    }
}

class CleanUp extends TimerTask {
    DiscordModel model;
    public CleanUp(DiscordModel model) {
        super();
        this.model = model;
    }
    public void run() {
        new Thread(){
            public void run() {
                model.cleanUp();
                System.out.println("Cleaned up!");
            }
        }.start();
    }
}