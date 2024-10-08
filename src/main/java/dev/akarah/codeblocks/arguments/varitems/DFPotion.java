package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.*;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFPotion(
    String effect,
    float duration,
    float amplifier
) implements VarItem {
    public static class Serializer implements JsonSerializer<DFPotion>, JsonDeserializer<DFPotion> {
        @Override
        public JsonElement serialize(DFPotion stringVarItem, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "pot");

            var data = new JsonObject();
            data.addProperty("pot", stringVarItem.effect);
            data.addProperty("dur", stringVarItem.duration);
            data.addProperty("amp", stringVarItem.amplifier);

            base.add("data", data);
            return base;
        }

        @Override
        public DFPotion deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            System.out.println(jsonElement);
            return new DFPotion(
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("pot").getAsString(),
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("dur").getAsFloat(),
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("amp").getAsFloat()
            );
        }
    }
}
