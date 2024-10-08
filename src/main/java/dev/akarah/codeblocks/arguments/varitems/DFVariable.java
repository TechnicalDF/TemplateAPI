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

        public static class Serializer implements JsonSerializer<Scope>, JsonDeserializer<Scope> {

            @Override
            public JsonElement serialize(Scope scope, Type type, JsonSerializationContext jsonSerializationContext) {
                return switch (scope) {
                    case LINE -> new JsonPrimitive("line");
                    case LOCAL -> new JsonPrimitive("local");
                    case GAME -> new JsonPrimitive("unsaved");
                    case SAVED -> new JsonPrimitive("saved");
                };
            }

            @Override
            public Scope deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return switch (jsonElement.getAsString()) {
                    case "line" -> Scope.LINE;
                    case "local" -> Scope.LOCAL;
                    case "unsaved" -> Scope.GAME;
                    case "saved" -> Scope.SAVED;
                    default -> throw new IllegalStateException("Unexpected value: " + jsonElement.getAsString());
                };
            }
        }
    }
    public static class Serializer implements JsonSerializer<DFVariable>, JsonDeserializer<DFVariable> {
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

        @Override
        public DFVariable deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new DFVariable(
                jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("name").getAsString(),
                jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("scope"), Scope.class)
            );
        }
    }
}
