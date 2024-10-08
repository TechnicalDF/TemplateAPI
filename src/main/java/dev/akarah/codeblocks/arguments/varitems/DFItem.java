package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.*;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFItem(
    String nbt
) implements VarItem {
    public static class Serializer implements JsonSerializer<DFItem>, JsonDeserializer<DFItem> {
        @Override
        public JsonElement serialize(DFItem stringVarItem, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "bl_tag");

            var data = new JsonObject();
            data.addProperty("item", stringVarItem.nbt());

            base.add("data", data);
            return base;
        }

        @Override
        public DFItem deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            System.out.println(jsonElement);
            return new DFItem(
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("nbt").getAsString()
            );
        }
    }
}
