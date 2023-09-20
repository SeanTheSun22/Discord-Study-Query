package discordstudyquery.discord;

import com.fasterxml.jackson.databind.ObjectMapper;

import discordstudyquery.discord.listeners.CreateThreadListener;
import discordstudyquery.discord.listeners.LoadComponentListener;
import discordstudyquery.guild.DiscordModel;

import com.fasterxml.jackson.core.type.TypeReference;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class DiscordClient {
    public void init() {
        String token;
        try {
            token = readToken();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        DiscordModel model = new DiscordModel();

        @SuppressWarnings("unused")
        JDA jda = JDABuilder.createDefault(token)
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .addEventListeners(
                new CreateThreadListener(model),
                new LoadComponentListener(model))
            .build();
    }

    public static String readToken() throws FileNotFoundException, IOException {
        /*
         * Token file in the format:
         * {
         *     "token":"<token>"
         * }
         */
        InputStream inputStream = DiscordClient.class.getClassLoader().getResourceAsStream("credentials/discord/token.json");
        if (inputStream == null) {throw new FileNotFoundException("File not found: " + "credentials/discord/token.json");}
        Map<String, String> token = new ObjectMapper().readValue(inputStream, new TypeReference<Map<String, String>>() {});
        return token.get("token");
    }
}
