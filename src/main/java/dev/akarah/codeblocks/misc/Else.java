package dev.akarah.codeblocks.misc;

import com.google.gson.*;
import dev.akarah.codeblocks.CodeBlock;

import java.lang.reflect.Type;

public record Else() implements CodeBlock {
    public static class Serializer implements JsonSerializer<Else>, JsonDeserializer<Else> {
        @Override
        public JsonElement serialize(Else bracket, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("id", "block");
            je.addProperty("block", "else");
            return je;
        }

        @Override
        public Else deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new Else();
        }
    }
}
