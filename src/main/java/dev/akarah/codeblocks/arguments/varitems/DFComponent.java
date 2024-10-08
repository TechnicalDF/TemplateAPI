package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.*;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFComponent(String value) implements VarItem {
    public static class Serializer implements JsonSerializer<DFComponent>, JsonDeserializer<DFComponent> {
        @Override
        public JsonElement serialize(DFComponent stringVarItem, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "comp");

            var data = new JsonObject();
            data.addProperty("name", stringVarItem.value());

            base.add("data", data);
            return base;
        }

        @Override
        public DFComponent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

            return new DFComponent(
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("name").getAsString()
            );
        }
    }
}
