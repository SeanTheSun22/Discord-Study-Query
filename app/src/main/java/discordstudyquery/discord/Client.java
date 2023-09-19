package discordstudyquery.discord;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Nonnull;

public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        client.init();
    }

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

        JDA jda = JDABuilder.createDefault(token)
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .addEventListeners(new MyListener())
            .build();
    }

    class MyListener extends ListenerAdapter {
        @Override
        public void onMessageReceived(@Nonnull MessageReceivedEvent event)
        {
            if (event.getAuthor().isBot()) return;
            // We don't want to respond to other bot accounts, including ourself
            Message message = event.getMessage();
            String content = message.getContentRaw();
            // getContentRaw() is an atomic getter
            // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
 
            if (content.equals("!ping")) {
                System.out.println("pong!");
                MessageChannel channel = event.getChannel();
                channel.sendMessage("Pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
            }
        }
    }

    public static String readToken() throws FileNotFoundException, IOException {
        /*
         * Token file in the format:
         * {
         *     "token":"<token>"
         * }
         */
        InputStream inputStream = Client.class.getClassLoader().getResourceAsStream("credentials/discord/token.json");
        if (inputStream == null) {throw new FileNotFoundException("File not found: " + "credentials/discord/token.json");}
        Map<String, String> token = new ObjectMapper().readValue(inputStream, new TypeReference<Map<String, String>>() {});
        return token.get("token");
    }
}
