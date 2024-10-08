package dev.akarah.codeblocks.ifs;

import com.google.gson.*;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.arguments.Args;

import java.lang.reflect.Type;
import java.util.Optional;

public record Repeat(
    String action,
    String subAction,
    String attribute,
    Args args
) implements CodeBlock {
    public static class Serializer implements JsonSerializer<Repeat>, JsonDeserializer<Repeat> {
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

        @Override
        public Repeat deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new Repeat(
                jsonElement.getAsJsonObject().get("action").getAsString(),
                Optional.ofNullable(jsonElement.getAsJsonObject().get("subAction")).orElse(new JsonPrimitive("")).getAsString(),
                Optional.ofNullable(jsonElement.getAsJsonObject().get("attribute")).orElse(new JsonPrimitive("")).getAsString(),
                jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("args"), Args.class)
            );
        }
    }
}
