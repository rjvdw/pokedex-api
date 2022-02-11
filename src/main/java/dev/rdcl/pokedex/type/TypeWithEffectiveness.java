package dev.rdcl.pokedex.type;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.NonNull;

import java.util.List;

public record TypeWithEffectiveness(
        @NonNull
        String type,

        @NonNull
        @Description("This type is super effective against the following types, dealing double damage.")
        List<@NonNull String> superEffectiveAgainst,

        @NonNull
        @Description("This type is not very effective against the following types, dealing half damage.")
        List<@NonNull String> notVeryEffectiveAgainst,

        @NonNull
        @Description("This type has no effect against the following types, dealing no damage.")
        List<@NonNull String> noEffectAgainst
) {
}
