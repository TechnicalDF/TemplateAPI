package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.*;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFNumber(String value) implements VarItem {
    public static class Serializer implements JsonSerializer<DFNumber>, JsonDeserializer<DFNumber> {
        @Override
        public JsonElement serialize(DFNumber number, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "num");

            var data = new JsonObject();
            data.addProperty("name", number.value());

            base.add("data", data);
            return base;
        }

        @Override
        public DFNumber deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new DFNumber(jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("name").getAsString());
        }
    }
}
