package dev.akarah.codeblocks.flow;

import com.google.gson.*;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.arguments.Args;

import java.lang.reflect.Type;
import java.util.List;

public record EntityEvent(
    String action
) implements CodeBlock {
    public static class Serializer implements JsonSerializer<EntityEvent>, JsonDeserializer<EntityEvent> {
        @Override
        public JsonElement serialize(EntityEvent bracket, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("id", "block");
            je.addProperty("block", "entity_event");
            je.addProperty("action", bracket.action);
            je.addProperty("attribute", "LS-CANCEL");
            je.add("args", jsonSerializationContext.serialize(new Args(List.of())));
            return je;
        }

        @Override
        public EntityEvent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new EntityEvent(
                jsonElement.getAsJsonObject().get("action").getAsString()
            );
        }
    }
}
