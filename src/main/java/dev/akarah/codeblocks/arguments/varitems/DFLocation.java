package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.*;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFLocation(
    double x,
    double y,
    double z,
    double pitch,
    double yaw
) implements VarItem {
    public static class Serializer implements JsonSerializer<DFLocation>, JsonDeserializer<DFLocation> {
        @Override
        public JsonElement serialize(DFLocation number, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "loc");

            var loc = new JsonObject();
            loc.addProperty("x", number.x());
            loc.addProperty("y", number.y());
            loc.addProperty("z", number.z());
            loc.addProperty("duration", number.pitch());
            loc.addProperty("yaw", number.yaw());

            var data = new JsonObject();
            data.addProperty("isBlock", false);
            data.add("loc", loc);

            base.add("data", data);
            return base;
        }

        @Override
        public DFLocation deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new DFLocation(
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("x").getAsDouble(),
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("y").getAsDouble(),
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("z").getAsDouble(),
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("duration").getAsDouble(),
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("yaw").getAsDouble()
            );
        }
    }
}
