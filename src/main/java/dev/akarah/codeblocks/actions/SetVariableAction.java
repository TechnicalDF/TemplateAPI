package dev.akarah.codeblocks.actions;

import com.google.gson.*;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.arguments.Args;

import java.lang.reflect.Type;

public record SetVariableAction(
    String action,
    Args args
) implements CodeBlock {
    public static class Serializer implements JsonSerializer<SetVariableAction>, JsonDeserializer<SetVariableAction> {
        @Override
        public JsonElement serialize(SetVariableAction bracket, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("id", "block");
            je.addProperty("block", "set_var");
            je.addProperty("action", bracket.action);
            je.addProperty("target", "");
            je.addProperty("attribute", "");
            je.add("args", jsonSerializationContext.serialize(bracket.args));
            return je;
        }

        @Override
        public SetVariableAction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new SetVariableAction(
                jsonElement.getAsJsonObject().get("action").getAsString(),
                jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("args"), Args.class)
            );
        }
    }
}
