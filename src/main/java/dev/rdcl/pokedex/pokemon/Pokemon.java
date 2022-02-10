package dev.rdcl.pokedex.pokemon;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Id;
import org.eclipse.microprofile.graphql.NonNull;

@Description("The Pokédex entry for a Pokémon.")
public record Pokemon(
        @Id
        @NonNull
        @Description("The number of this Pokémon within the Pokédex.")
        int index,

        @NonNull
        @Description("The name of the Pokémon.")
        String name,

        @NonNull
        @Description("Some background information about this Pokémon.")
        String description,

        @NonNull
        @Description("The primary type of this Pokémon")
        String primary,

        @Description("The secondary type of this Pokémon")
        String secondary
) {
}
