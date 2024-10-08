import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.flow.PlayerEvent;
import dev.akarah.codeclient.CodeClientAPI;
import dev.akarah.serializers.GsonInstance;
import dev.akarah.template.Template;

public class Main {
    public static void main(String[] args) {
        CodeClientAPI.Builder.create()
            .onOpen(CodeClientAPI::requestAuth)
            .onAuth(CodeClientAPI::scan)
            .onMessage(((string, codeClientAPI) -> {
                var templates = string.split("\n");
                for(var template : templates) {
                    var o =
                        "{\"author\":\"Endistic\",\"name\":\"§b§lFunction §3» §bItemDatabase\",\"version\":1,\"code\":\"" +
                        template +
                            "\"}";
                    System.out.println(
                        GsonInstance.GSON.fromJson(o, Template.class)
                    );
                }

            }))
            .open();
    }
}
