package dev.rdcl.pokedex;

public record Pokemon(
        int index,
        String name,
        String description,
        Type primary,
        Type secondary
) {
}
