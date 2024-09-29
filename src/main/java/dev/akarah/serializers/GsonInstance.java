package dev.akarah.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.akarah.codeblocks.actions.*;
import dev.akarah.codeblocks.arguments.VarItem;
import dev.akarah.codeblocks.arguments.varitems.DFComponent;
import dev.akarah.codeblocks.arguments.varitems.DFNumber;
import dev.akarah.codeblocks.arguments.varitems.DFString;
import dev.akarah.codeblocks.arguments.varitems.DFVariable;
import dev.akarah.codeblocks.flow.*;
import dev.akarah.codeblocks.flow.Process;
import dev.akarah.codeblocks.misc.Bracket;
import dev.akarah.template.Template;

public class GsonInstance {
    public static Gson GSON =
        new GsonBuilder()
            .registerTypeAdapter(Template.class, new Template.Serializer())

            .registerTypeAdapter(Bracket.class, new Bracket.Serializer())
            .registerTypeAdapter(Bracket.Direction.class, new Bracket.Direction.Serializer())
            .registerTypeAdapter(Bracket.Type.class, new Bracket.Type.Serializer())

            .registerTypeAdapter(PlayerAction.class, new PlayerAction.Serializer())
            .registerTypeAdapter(EntityAction.class, new EntityAction.Serializer())
            .registerTypeAdapter(GameAction.class, new GameAction.Serializer())
            .registerTypeAdapter(SetVariableAction.class, new SetVariableAction.Serializer())

            .registerTypeAdapter(PlayerEvent.class, new PlayerEvent.Serializer())
            .registerTypeAdapter(EntityEvent.class, new EntityEvent.Serializer())

            .registerTypeAdapter(Function.class, new Function.Serializer())
            .registerTypeAdapter(Process.class, new Process.Serializer())
            .registerTypeAdapter(CallFunction.class, new CallFunction.Serializer())
            .registerTypeAdapter(StartProcess.class, new StartProcess.Serializer())


            .registerTypeAdapter(DFString.class, new DFString.Serializer())
            .registerTypeAdapter(DFNumber.class, new DFNumber.Serializer())
            .registerTypeAdapter(DFComponent.class, new DFComponent.Serializer())

            .registerTypeAdapter(DFVariable.class, new DFVariable.Serializer())
            .registerTypeAdapter(DFVariable.Scope.class, new DFVariable.Scope.Serializer())
            .create();
}
