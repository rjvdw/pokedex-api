package dev.rdcl.pokedex.pokemon;

import org.eclipse.microprofile.graphql.Id;
import org.eclipse.microprofile.graphql.NonNull;

public record Pokemon(
        @Id
        @NonNull
        int index,

        @NonNull
        String name,

        @NonNull
        String description,

        @NonNull
        String primary,

        String secondary
) {
}
