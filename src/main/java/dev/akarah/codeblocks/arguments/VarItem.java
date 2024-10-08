package dev.akarah.codeblocks.arguments;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import dev.akarah.codeblocks.arguments.varitems.*;

import java.lang.reflect.Type;

public interface VarItem {
    public static class Serializer implements JsonDeserializer<VarItem> {

        @Override
        public VarItem deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            System.out.println(jsonElement);
            return switch (jsonElement.getAsJsonObject().get("id").getAsString()) {
                case "comp" -> ctx.deserialize(jsonElement, DFComponent.class);
                case "loc" -> ctx.deserialize(jsonElement, DFLocation.class);
                case "num" -> ctx.deserialize(jsonElement, DFNumber.class);
                case "pn_el" -> ctx.deserialize(jsonElement, DFParameter.class);
                case "txt" -> ctx.deserialize(jsonElement, DFString.class);
                case "var" -> ctx.deserialize(jsonElement, DFVariable.class);
                case "vec" -> ctx.deserialize(jsonElement, DFVector.class);
                case "bl_tag" -> ctx.deserialize(jsonElement, DFBlockTag.class);
                case "hint" -> ctx.deserialize(jsonElement, DFHint.class);
                case "item" -> ctx.deserialize(jsonElement, DFItem.class);
                case "g_val" -> ctx.deserialize(jsonElement, DFGameValue.class);
                case "snd" -> ctx.deserialize(jsonElement, DFSound.class);
                case "pot" -> ctx.deserialize(jsonElement, DFPotion.class);
                case "part" -> ctx.deserialize(jsonElement, DFParticle.class);
                default ->
                    throw new IllegalStateException("Unexpected value: " + jsonElement.getAsJsonObject().get("id").getAsString());
            };
        }
    }
}
