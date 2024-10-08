package dev.akarah.codeblocks.ifs;

import com.google.gson.*;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.actions.SetVariableAction;
import dev.akarah.codeblocks.arguments.Args;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public record IfPlayer(
    String action,
    String target,
    String attribute,
    Args args
) implements CodeBlock {
    public static class Serializer implements JsonSerializer<IfPlayer>, JsonDeserializer<IfPlayer> {
        @Override
        public JsonElement serialize(IfPlayer bracket, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("id", "block");
            je.addProperty("block", "if_player");
            je.addProperty("action", bracket.action());
            je.addProperty("target", bracket.target());
            je.addProperty("attribute", bracket.attribute());
            je.add("args", jsonSerializationContext.serialize(bracket.args));
            return je;
        }

        @Override
        public IfPlayer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new IfPlayer(
                jsonElement.getAsJsonObject().get("action").getAsString(),
                Optional.ofNullable(jsonElement.getAsJsonObject().get("target")).orElse(new JsonPrimitive("")).getAsString(),
                Optional.ofNullable(jsonElement.getAsJsonObject().get("attribute")).orElse(new JsonPrimitive("")).getAsString(),
                jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("args"), Args.class)
            );
        }
    }
}
