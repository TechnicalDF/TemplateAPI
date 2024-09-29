package dev.akarah.codeblocks.actions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.arguments.Args;

public record EntityAction(
    String action,
    String target,
    Args args
) implements CodeBlock {
    public static class Serializer implements JsonSerializer<EntityAction> {
        @Override
        public JsonElement serialize(EntityAction bracket, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("id", "block");
            je.addProperty("block", "entity_action");
            je.addProperty("action", bracket.action);
            je.addProperty("target", bracket.target);
            je.addProperty("attribute", "");
            je.add("args", jsonSerializationContext.serialize(bracket.args));
            return je;
        }
    }
}
