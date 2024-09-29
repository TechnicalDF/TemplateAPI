package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFLocation(
    double x,
    double y,
    double z,
    double pitch,
    double yaw
) implements VarItem {
    public static class Serializer implements JsonSerializer<DFLocation> {
        @Override
        public JsonElement serialize(DFLocation number, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "loc");

            var loc = new JsonObject();
            loc.addProperty("x", number.x());
            loc.addProperty("y", number.y());
            loc.addProperty("z", number.z());
            loc.addProperty("pitch", number.pitch());
            loc.addProperty("yaw", number.yaw());

            var data = new JsonObject();
            data.addProperty("isBlock", false);
            data.add("loc", loc);

            base.add("data", data);
            return base;
        }
    }
}
