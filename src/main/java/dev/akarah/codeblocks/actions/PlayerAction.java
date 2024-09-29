package dev.akarah.codeblocks.actions;

import com.google.gson.*;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.arguments.Args;
import dev.akarah.serializers.GsonInstance;

public record PlayerAction(
    String action,
    String target,
    Args args
) implements CodeBlock {
    public static class Serializer implements JsonSerializer<PlayerAction> {
        @Override
        public JsonElement serialize(PlayerAction bracket, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("id", "block");
            je.addProperty("block", "player_action");
            je.addProperty("action", bracket.action);
            je.addProperty("target", bracket.target);
            je.addProperty("attribute", "");
            je.add("args", jsonSerializationContext.serialize(bracket.args));
            return je;
        }
    }
}
