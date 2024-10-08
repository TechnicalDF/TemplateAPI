package dev.akarah.codeblocks;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import dev.akarah.codeblocks.actions.*;
import dev.akarah.codeblocks.flow.*;
import dev.akarah.codeblocks.flow.Process;
import dev.akarah.codeblocks.ifs.*;
import dev.akarah.codeblocks.misc.Bracket;
import dev.akarah.codeblocks.misc.Else;

import java.lang.reflect.Type;

public interface CodeBlock {
    class Serializer implements JsonDeserializer<CodeBlock> {
        @Override
        public CodeBlock deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            return switch (jsonElement.getAsJsonObject().get("id").getAsString()) {
                case "bracket" -> ctx.deserialize(jsonElement, Bracket.class);
                case "block" -> switch (jsonElement.getAsJsonObject().get("block").getAsString()) {
                    case "event" -> ctx.deserialize(jsonElement, PlayerEvent.class);
                    case "entity_event" -> ctx.deserialize(jsonElement, EntityEvent.class);
                    case "func" -> ctx.deserialize(jsonElement, Function.class);
                    case "player_action" -> ctx.deserialize(jsonElement, PlayerAction.class);
                    case "entity_action" -> ctx.deserialize(jsonElement, EntityAction.class);
                    case "game_action" -> ctx.deserialize(jsonElement, GameAction.class);
                    case "if_player" -> ctx.deserialize(jsonElement, IfPlayer.class);
                    case "if_entity" -> ctx.deserialize(jsonElement, IfEntity.class);
                    case "if_game" -> ctx.deserialize(jsonElement, IfGame.class);
                    case "if_var" -> ctx.deserialize(jsonElement, IfVariable.class);
                    case "set_var" -> ctx.deserialize(jsonElement, SetVariableAction.class);
                    case "control" -> ctx.deserialize(jsonElement, ControlAction.class);
                    case "process" -> ctx.deserialize(jsonElement, Process.class);
                    case "repeat" -> ctx.deserialize(jsonElement, Repeat.class);
                    case "select_obj" -> ctx.deserialize(jsonElement, SelectObject.class);
                    case "start_process" -> ctx.deserialize(jsonElement, StartProcess.class);
                    case "call_func" -> ctx.deserialize(jsonElement, CallFunction.class);
                    case "else" -> ctx.deserialize(jsonElement, Else.class);
                    default ->
                        throw new IllegalStateException("Unexpected value: " + jsonElement.getAsJsonObject().get("block").getAsString());
                };

                default ->
                    throw new IllegalStateException("Unexpected value: " + jsonElement.getAsJsonObject().get("id").getAsString());
            };
        }
    }
}
