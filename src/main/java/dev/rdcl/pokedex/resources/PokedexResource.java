package dev.rdcl.pokedex.resources;

import dev.rdcl.pokedex.pokemon.Pokemon;
import dev.rdcl.pokedex.pokemon.PokemonService;
import dev.rdcl.pokedex.type.TypeService;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

@GraphQLApi
@AllArgsConstructor
public class PokedexResource {

    private final TypeService typeService;
    private final PokemonService pokemonService;

    @Query
    @Description("All Pokémon in the Pokédex")
    public List<Pokemon> getAllPokemon() {
        return pokemonService.getAllPokemon();
    }

    @Query
    @Description("All types supported by this Pokédex")
    public List<String> getAllTypes() {
        return typeService.getAllTypes();
    }

    @Mutation
    @Description("Register a Pokémon")
    public Pokemon registerPokemon(Pokemon pokemon) {
        pokemonService.savePokemon(pokemon);
        return pokemon;
    }

}
