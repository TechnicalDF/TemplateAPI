package dev.akarah.codeblocks.actions;

import com.google.gson.*;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.arguments.Args;
import dev.akarah.serializers.GsonInstance;

import java.lang.reflect.Type;
import java.util.Optional;

public record PlayerAction(
    String action,
    String target,
    Args args
) implements CodeBlock {
    public static class Serializer implements JsonSerializer<PlayerAction>, JsonDeserializer<PlayerAction> {
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

        @Override
        public PlayerAction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new PlayerAction(
                jsonElement.getAsJsonObject().get("action").getAsString(),
                Optional.ofNullable(jsonElement.getAsJsonObject().get("target")).orElse(new JsonPrimitive("")).getAsString(),
                jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("args"), Args.class)
            );
        }
    }
}
