package dev.akarah.codeblocks.flow;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.arguments.Args;

public record StartProcess(
    String name,
    Args args
) implements CodeBlock {
    public static class Serializer implements JsonSerializer<StartProcess> {
        @Override
        public JsonElement serialize(StartProcess bracket, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("id", "block");
            je.addProperty("block", "start_process");
            je.addProperty("data", bracket.name);
            je.add("args", jsonSerializationContext.serialize(bracket.args));
            return je;
        }
    }
}
