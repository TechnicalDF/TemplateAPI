package dev.akarah.template;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.serializers.GsonInstance;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public record Template(
    String name,
    String author,
    int version,
    List<CodeBlock> codeblocks
) {
    public static class Serializer implements JsonSerializer<Template> {

        @Override
        public JsonElement serialize(Template template, Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("name", template.name());
            je.addProperty("author", template.author);
            je.addProperty("version", template.version);
            je.addProperty("code", template.codeData());
            return je;
        }
    }

    public String codeData() {
        try {
            var jsonBytes =
                (
                    "{\"blocks\":"
                    + GsonInstance.GSON
                        .toJson(this.codeblocks)
                    + "}")
                    .getBytes(StandardCharsets.UTF_8);

            var gzipOutput = new ByteArrayOutputStream();
            var gzipStream = new GZIPOutputStream(gzipOutput);
            gzipStream.write(jsonBytes);
            gzipStream.finish();

            return new String(
                Base64.getEncoder().encode(gzipOutput.toByteArray()),
                StandardCharsets.UTF_8
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String json() {
        return GsonInstance.GSON.toJson(this);
    }
}
