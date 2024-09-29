package dev.akarah.codeblocks.actions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.arguments.Args;

public record ControlAction(
    String action,
    Args args
) implements CodeBlock {
    public static class Serializer implements JsonSerializer<ControlAction> {
        @Override
        public JsonElement serialize(ControlAction bracket, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("id", "block");
            je.addProperty("block", "control");
            je.addProperty("action", bracket.action);
            je.addProperty("target", "");
            je.addProperty("attribute", "");
            je.add("args", jsonSerializationContext.serialize(bracket.args));
            return je;
        }
    }
}