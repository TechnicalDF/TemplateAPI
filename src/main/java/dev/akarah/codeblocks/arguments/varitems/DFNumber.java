package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFNumber(String value) implements VarItem {
    public static class Serializer implements JsonSerializer<DFNumber> {
        @Override
        public JsonElement serialize(DFNumber number, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "num");

            var data = new JsonObject();
            data.addProperty("name", number.value());

            base.add("data", data);
            return base;
        }
    }
}
