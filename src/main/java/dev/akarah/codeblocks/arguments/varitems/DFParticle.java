package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.*;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFParticle(
    String particle,
    Cluster cluster,
    Data data
) implements VarItem {
    record Cluster(
        int amount,
        double horizontal,
        double vertical
    ) {}

    record Data(
        double motionVariation,
        double x,
        double y,
        double z,
        double colorVariation,
        int rgb,
        double sizeVariation,
        double size,
        String material
    ) {

    }
    public static class Serializer implements JsonSerializer<DFParticle>, JsonDeserializer<DFParticle> {
        @Override
        public JsonElement serialize(DFParticle stringVarItem, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "part");

            var data = new JsonObject();
            data.add("cluster", jsonSerializationContext.serialize(stringVarItem.cluster));
            data.add("data", jsonSerializationContext.serialize(stringVarItem.data));
            data.addProperty("particle", stringVarItem.particle);

            base.add("data", data);
            return base;
        }

        @Override
        public DFParticle deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new DFParticle(
                jsonElement.getAsJsonObject().get("particle").getAsString(),
                jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("cluster").getAsJsonObject(), Cluster.class),
                jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("data").getAsJsonObject(), Data.class)
            );
        }
    }
}
