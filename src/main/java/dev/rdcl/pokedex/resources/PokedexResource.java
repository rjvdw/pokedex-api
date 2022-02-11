package dev.rdcl.pokedex.resources;

import dev.rdcl.pokedex.pokemon.Pokemon;
import dev.rdcl.pokedex.pokemon.PokemonService;
import dev.rdcl.pokedex.type.TypeService;
import dev.rdcl.pokedex.type.TypeWithEffectiveness;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.NonNull;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

@GraphQLApi
@AllArgsConstructor
public class PokedexResource {

    private final TypeService typeService;
    private final PokemonService pokemonService;

    @Query
    @Description("All Pokémon in the Pokédex")
    public List<@NonNull Pokemon> getAllPokemon() {
        return pokemonService.getAllPokemon();
    }

    @Query
    @Description("All types supported by this Pokédex")
    public List<@NonNull String> getAllTypes() {
        return typeService.getAllTypes();
    }

    @Query
    @Description("The full type chart with the effectiveness of the different types against other types.")
    public List<@NonNull TypeWithEffectiveness> getTypeChart() {
        return typeService.getTypeChart();
    }

    @Mutation
    @Description("Saves a Pokémon.")
    public Pokemon savePokemon(@NonNull Pokemon pokemon) {
        pokemonService.savePokemon(pokemon);
        return pokemon;
    }

    @Mutation
    @Description("Define the types and their relative effectiveness towards each other.")
    public List<@NonNull TypeWithEffectiveness> defineTypes(
            @NonNull List<@NonNull String> types,
            @NonNull List<@NonNull TypeWithEffectiveness> chart
    ) {
        typeService.defineTypes(types, chart);
        return chart;
    }

}
