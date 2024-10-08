package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.*;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFString(String value) implements VarItem {
    public static class Serializer implements JsonSerializer<DFString>, JsonDeserializer<DFString> {
        @Override
        public JsonElement serialize(DFString stringVarItem, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "txt");

            var data = new JsonObject();
            data.addProperty("name", stringVarItem.value());

            base.add("data", data);
            return base;
        }

        @Override
        public DFString deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new DFString(
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("name").getAsString()
            );
        }
    }
}
