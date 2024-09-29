package dev.akarah.codeclient;

import dev.akarah.serializers.GsonInstance;
import dev.akarah.template.Template;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CodeClientAPI extends WebSocketClient {


    List<BiConsumer<String, CodeClientAPI>> messageConsumers = new ArrayList<>();
    List<Consumer<CodeClientAPI>> openConsumers = new ArrayList<>();
    List<Consumer<CodeClientAPI>> closeConsumers = new ArrayList<>();

    @Override
    public void send(String text) {
        System.out.println("sending: `" + text + "`");
        super.send(text);
    }

    private CodeClientAPI(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        for(var consumer : this.openConsumers)
            consumer.accept(this);
    }

    @Override
    public void onMessage(String s) {
        for(var consumer : this.messageConsumers)
            consumer.accept(s, this);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        for(var consumer : this.closeConsumers)
            consumer.accept(this);
    }

    @Override
    public void onError(Exception e) {

    }

    public CodeClientAPI requestAuth() {
        this.send("scopes default inventory movement read_plot write_code clear_plot");
        return this;
    }

    public CodeClientAPI clearCode() {
        this.send("clear");
        return this;
    }

    public CodeClientAPI queue(Template template) {
        this.send("place " + template.codeData());
        return this;
    }

    public CodeClientAPI place() {
        this.send("place go");
        return this;
    }

    public static class Builder {
        List<BiConsumer<String, CodeClientAPI>> messageConsumers = new ArrayList<>();
        List<Consumer<CodeClientAPI>> openConsumers = new ArrayList<>();
        List<Consumer<CodeClientAPI>> closeConsumers = new ArrayList<>();

        public static Builder create() {
            return new Builder();
        }

        public Builder onAuth(Consumer<CodeClientAPI> consumer) {
            this.messageConsumers.add((msg, api) -> {
                if(msg.equals("auth")) {
                    consumer.accept(api);
                }
            });
            return this;
        }

        public Builder onOpen(Consumer<CodeClientAPI> consumer) {
            this.openConsumers.add(consumer);
            return this;
        }

        public Builder onClose(Consumer<CodeClientAPI> consumer) {
            this.closeConsumers.add(consumer);
            return this;
        }

        public void open() {
            try {
                var api = new CodeClientAPI(new URI("ws://localhost:31375"));
                api.messageConsumers = this.messageConsumers;
                api.openConsumers = this.openConsumers;
                api.closeConsumers = closeConsumers;
                try {
                    api.connectBlocking();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
