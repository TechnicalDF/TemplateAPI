import dev.akarah.codeclient.CodeClientAPI;

import java.io.IOException;
import java.lang.classfile.ClassFile;
import java.nio.file.Path;

@SuppressWarnings("preview")
public class TestClass {
    public static void main(String[] args) throws IOException {
        var cf = ClassFile.of().parse(Path.of("./build/classes/java/test/PlayerEvents.class"));
        var templates = ClassFileToTemplate.transform(cf);

        CodeClientAPI.Builder.create()
            .onOpen(CodeClientAPI::requestAuth)
            .onAuth(api -> {
                api.clearCode();
                for(var template : templates) {
                    api.queue(template);
                }
                api.place();
            })
            .open();
    }
}

