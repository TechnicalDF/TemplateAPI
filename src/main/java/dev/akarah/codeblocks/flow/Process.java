package dev.akarah.codeblocks.flow;

import com.google.gson.*;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.arguments.Args;

import java.lang.reflect.Type;

public record Process(
    String name,
    Args args
) implements CodeBlock {
    public static class Serializer implements JsonSerializer<Process>, JsonDeserializer<Process> {
        @Override
        public JsonElement serialize(Process bracket, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("id", "block");
            je.addProperty("block", "process");
            je.addProperty("data", bracket.name);
            je.add("args", jsonSerializationContext.serialize(bracket.args));
            return je;
        }

        @Override
        public Process deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new Process(
                jsonElement.getAsJsonObject().get("data").getAsString(),
                jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("args"), Args.class)
            );
        }
    }
}
