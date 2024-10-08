package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.*;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFGameValue(String type, String selection) implements VarItem {
    public static class Serializer implements JsonSerializer<DFGameValue>, JsonDeserializer<DFGameValue> {
        @Override
        public JsonElement serialize(DFGameValue stringVarItem, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "g_bal");

            var data = new JsonObject();
            data.addProperty("type", stringVarItem.type);
            data.addProperty("target", stringVarItem.selection);

            base.add("data", data);
            return base;
        }

        @Override
        public DFGameValue deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new DFGameValue(
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("type").getAsString(),
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("target").getAsString()
            );
        }
    }
}
