package dev.akarah.codeblocks.arguments.varitems;

import com.google.gson.*;
import dev.akarah.codeblocks.arguments.VarItem;

import java.lang.reflect.Type;

public record DFVariable(String name, Scope scope) implements VarItem {
    public enum Scope {
        LINE,
        LOCAL,
        GAME,
        SAVED;

        public static class Serializer implements JsonSerializer<Scope> {

            @Override
            public JsonElement serialize(Scope scope, Type type, JsonSerializationContext jsonSerializationContext) {
                return switch (scope) {
                    case LINE -> new JsonPrimitive("line");
                    case LOCAL -> new JsonPrimitive("local");
                    case GAME -> new JsonPrimitive("unsaved");
                    case SAVED -> new JsonPrimitive("saved");
                };
            }
        }
    }
    public static class Serializer implements JsonSerializer<DFVariable> {
        @Override
        public JsonElement serialize(DFVariable variable, Type type, JsonSerializationContext jsonSerializationContext) {
            var base = new JsonObject();
            base.addProperty("id", "var");

            var data = new JsonObject();
            data.addProperty("name", variable.name);
            data.add("scope", jsonSerializationContext.serialize(variable.scope));

            base.add("data", data);
            return base;
        }
    }
}
