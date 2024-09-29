package dev.akarah.codeblocks.arguments;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record Args(
    @SerializedName("items")
    List<SlotItem> items
) {
}
