package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.*;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFVector(
    double x,
    double y,
    double z
) implements VarItem {
    public static class Serializer implements JsonSerializer<DFVector>, JsonDeserializer<DFVector> {
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

        @Override
        public DFVector deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new DFVector(
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("x").getAsDouble(),
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("y").getAsDouble(),
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("z").getAsDouble()
            );
        }
    }
}
