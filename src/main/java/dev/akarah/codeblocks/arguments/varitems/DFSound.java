package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.*;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFSound(
    String sound,
    float pitch,
    float volume
) implements VarItem {
    public static class Serializer implements JsonSerializer<DFSound>, JsonDeserializer<DFSound> {
        @Override
        public JsonElement serialize(DFSound stringVarItem, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "snd");

            var data = new JsonObject();
            data.addProperty("sound", stringVarItem.sound);
            data.addProperty("pitch", stringVarItem.pitch);
            data.addProperty("vol", stringVarItem.volume);

            base.add("data", data);
            return base;
        }

        @Override
        public DFSound deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            System.out.println(jsonElement);
            return new DFSound(
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("sound").getAsString(),
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("pitch").getAsFloat(),
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("vol").getAsFloat()
            );
        }
    }
}
