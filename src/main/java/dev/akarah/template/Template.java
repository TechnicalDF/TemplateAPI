package dev.akarah.template;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.serializers.GsonInstance;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public record Template(
    String name,
    String author,
    int version,
    List<CodeBlock> codeblocks
) {
    public static class Serializer implements JsonSerializer<Template>, JsonDeserializer<Template> {

        @Override
        public JsonElement serialize(Template template, Type type, JsonSerializationContext jsonSerializationContext) {
            var je = new JsonObject();
            je.addProperty("name", template.name());
            je.addProperty("author", template.author);
            je.addProperty("version", template.version);
            je.addProperty("code", template.codeData());
            return je;
        }

        @Override
        public Template deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new Template(
                jsonElement.getAsJsonObject().get("name").getAsString(),
                jsonElement.getAsJsonObject().get("author").getAsString(),
                jsonElement.getAsJsonObject().get("version").getAsInt(),
                fromData(jsonElement.getAsJsonObject().get("code").getAsString())
            );
        }
    }

    public static List<CodeBlock> fromData(String dataString) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(dataString);

            var byteArrayInputStream = new ByteArrayInputStream(decodedBytes);
            var gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }

            var element = JsonParser.parseString(byteArrayOutputStream.toString(StandardCharsets.UTF_8));
            return GsonInstance.GSON.fromJson(
                element.getAsJsonObject().get("blocks").getAsJsonArray(),
                new TypeToken<>() {
                }
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
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
