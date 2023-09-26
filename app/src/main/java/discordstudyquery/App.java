/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package discordstudyquery;

import discordstudyquery.database.DatabaseEditor;
import discordstudyquery.discord.DiscordClient;
import discordstudyquery.guild.DiscordModel;
import discordstudyquery.eventdispatch.DispatchTimer;

public class App {
    private static App instance = new App();
    private DiscordModel model;
    private DiscordClient client;
    private DispatchTimer timer;
    public static void main(String[] args) throws Exception {
        instance.init();
    }

    public void init() {
        LogToFileAndConsole.set();
        model = new DiscordModel();
        client = new DiscordClient(model);
        timer = new DispatchTimer(model);
        DatabaseEditor.setJDA(client.getJDA());
    }

    public static void stop() {
        instance.client.shutdown();
        instance.model.unloadAll();
        instance.timer.cancel();
        LogToFileAndConsole.close();
        System.exit(0);
    }

    public static void restart() {
        instance.client.shutdown();
        instance.model.unloadAll();
        instance.timer.cancel();
        LogToFileAndConsole.close();
        System.exit(1);
    }
}
