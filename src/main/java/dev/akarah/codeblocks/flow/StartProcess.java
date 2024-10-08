package dev.akarah.codeblocks.flow;

import com.google.gson.*;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.arguments.Args;

import java.lang.reflect.Type;

public record StartProcess(
    String name,
    Args args
) implements CodeBlock {
    public static class Serializer implements JsonSerializer<StartProcess>, JsonDeserializer<StartProcess> {
        @Override
        public JsonElement serialize(StartProcess bracket, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("id", "block");
            je.addProperty("block", "start_process");
            je.addProperty("data", bracket.name);
            je.add("args", jsonSerializationContext.serialize(bracket.args));
            return je;
        }

        @Override
        public StartProcess deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new StartProcess(
                jsonElement.getAsJsonObject().get("data").getAsString(),
                jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("args"), Args.class)
            );
        }
    }
}
