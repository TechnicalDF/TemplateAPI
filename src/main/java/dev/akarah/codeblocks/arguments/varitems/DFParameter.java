package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.*;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFParameter(
    String name,
    String type,
    boolean plural,
    boolean optional,
    String note,
    String description,
    VarItem _default
) implements VarItem {
    public static class Serializer implements JsonSerializer<DFParameter>, JsonDeserializer<DFParameter> {
        @Override
        public JsonElement serialize(DFParameter stringVarItem, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "pn_el");

            var data = new JsonObject();
            data.addProperty("name", stringVarItem.name);
            data.addProperty("type", stringVarItem.type);
            data.addProperty("plural", stringVarItem.optional);
            data.addProperty("optional", stringVarItem.optional);
            data.addProperty("note", stringVarItem.note);
            data.addProperty("description", stringVarItem.description);
            data.add("default", jsonSerializationContext.serialize(stringVarItem._default));

            base.add("data", data);
            return base;
        }

        @Override
        public DFParameter deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return null;
        }
    }
}
