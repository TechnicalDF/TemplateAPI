package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.*;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFBlockTag(
    String option,
    String tag,

    String action,
    String block
) implements VarItem {
    public static class Serializer implements JsonSerializer<DFBlockTag>, JsonDeserializer<DFBlockTag> {
        @Override
        public JsonElement serialize(DFBlockTag stringVarItem, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "bl_tag");

            var data = new JsonObject();
            data.addProperty("option", stringVarItem.option());
            data.addProperty("tag", stringVarItem.tag());
            data.addProperty("action", stringVarItem.action());
            data.addProperty("block", stringVarItem.block());

            base.add("data", data);
            return base;
        }

        @Override
        public DFBlockTag deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            System.out.println(jsonElement);
            return new DFBlockTag(
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("option").getAsString(),
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("tag").getAsString(),
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("action").getAsString(),
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("block").getAsString()
            );
        }
    }
}
