package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFVector(
    double x,
    double y,
    double z
) implements VarItem {
    public static class Serializer implements JsonSerializer<DFVector> {
        @Override
        public JsonElement serialize(DFVector number, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "vec");

            var loc = new JsonObject();
            loc.addProperty("x", number.x());
            loc.addProperty("y", number.y());
            loc.addProperty("z", number.z());

            var data = new JsonObject();
            data.addProperty("isBlock", false);
            data.add("loc", loc);
            
            base.add("data", data);
            return base;
        }
    }
}
