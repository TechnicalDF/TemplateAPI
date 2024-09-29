package dev.akarah.codeblocks.ifs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.arguments.Args;

public record Repeat(
    String action,
    String subAction,
    String attribute,
    Args args
) implements CodeBlock {
    public static class Serializer implements JsonSerializer<Repeat> {
        @Override
        public JsonElement serialize(Repeat bracket, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("id", "block");
            je.addProperty("block", "repeat");
            je.addProperty("action", bracket.action());
            je.addProperty("sub_action", bracket.subAction);
            je.addProperty("target", "");
            je.addProperty("attribute", bracket.attribute());
            je.add("args", jsonSerializationContext.serialize(bracket.args));
            return je;
        }
    }
}
