package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.*;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFHint() implements VarItem {
    public static class Serializer implements JsonSerializer<DFHint>, JsonDeserializer<DFHint> {
        @Override
        public JsonElement serialize(DFHint stringVarItem, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "hint");

            var data = new JsonObject();
            data.addProperty("id", "function");

            base.add("data", data);
            return base;
        }

        @Override
        public DFHint deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new DFHint();
        }
    }
}
