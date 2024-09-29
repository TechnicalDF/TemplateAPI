package dev.akarah.codeblocks.ifs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.arguments.Args;

public record IfGame(
    String action,
    String attribute,
    Args args
) implements CodeBlock {
    public static class Serializer implements JsonSerializer<IfGame> {
        @Override
        public JsonElement serialize(IfGame bracket, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("id", "block");
            je.addProperty("block", "if_game");
            je.addProperty("action", bracket.action());
            je.addProperty("target", "");
            je.addProperty("attribute", bracket.attribute());
            je.add("args", jsonSerializationContext.serialize(bracket.args));
            return je;
        }
    }
}
