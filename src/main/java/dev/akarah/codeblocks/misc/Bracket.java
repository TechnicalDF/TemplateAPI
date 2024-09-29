package dev.akarah.codeblocks.misc;

import com.google.gson.*;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.serializers.GsonInstance;

public record Bracket(
    Direction direction,
    Type type
) implements CodeBlock {
    public static class Serializer implements JsonSerializer<Bracket> {
        @Override
        public JsonElement serialize(Bracket bracket, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("id", "bracket");
            je.add("direct", jsonSerializationContext.serialize(bracket.direction));
            je.add("type", jsonSerializationContext.serialize(bracket.type));
            return je;
        }
    }

    public enum Direction {
        OPEN,
        CLOSED;

        public static class Serializer implements JsonSerializer<Direction> {
            @Override
            public JsonElement serialize(Direction direction, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
                return switch (direction) {
                    case OPEN -> new JsonPrimitive("open");
                    case CLOSED -> new JsonPrimitive("closed");
                };
            }
        }
    }

    public enum Type {
        NORMAL,
        REPEAT;

        public static class Serializer implements JsonSerializer<Type> {
            @Override
            public JsonElement serialize(Type type, java.lang.reflect.Type type2, JsonSerializationContext jsonSerializationContext) {
                return switch (type) {
                    case NORMAL -> new JsonPrimitive("norm");
                    case REPEAT -> new JsonPrimitive("repeat");
                };
            }
        }
    }
}
