package dev.akarah.codeblocks.flow;

import com.google.gson.*;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.arguments.Args;

import java.lang.reflect.Type;
import java.util.List;

public record Function(
    String name,
    Args args
) implements CodeBlock {
    public static class Serializer implements JsonSerializer<Function>, JsonDeserializer<Function> {
        @Override
        public JsonElement serialize(Function bracket, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("id", "block");
            je.addProperty("block", "func");
            je.addProperty("data", bracket.name);
            je.add("args", jsonSerializationContext.serialize(bracket.args));
            return je;
        }

        @Override
        public Function deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new Function(
                jsonElement.getAsJsonObject().get("data").getAsString(),
                jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("args"), Args.class)
            );
        }
    }
}
