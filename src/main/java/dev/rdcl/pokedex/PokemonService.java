package dev.rdcl.pokedex;

import dev.rdcl.pokedex.converters.PokemonEntityConverter;
import dev.rdcl.pokedex.entities.PokemonEntity;
import lombok.AllArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final TypeRepository typeRepository;

    public Optional<Pokemon> getPokemon(int index) {
        return pokemonRepository
                .findByIndex(index)
                .map(PokemonEntityConverter::fromEntity);
    }

    public Optional<Pokemon> getPokemon(String name) {
        return pokemonRepository
                .findByName(name)
                .map(PokemonEntityConverter::fromEntity);
    }

    public void savePokemon(Pokemon pokemon) {
        var entity = pokemonRepository
                .find("index", pokemon.index())
                .firstResultOptional()
                .orElseGet(() -> PokemonEntity.builder()
                        .index(pokemon.index())
                        .build());

        var primaryType = typeRepository.getByName(pokemon.primary().name());

        var secondaryType = pokemon.secondary() == null
                ? null
                : typeRepository.getByName(pokemon.secondary().name());

        entity.setName(pokemon.name());
        entity.setDescription(pokemon.description());
        entity.setPrimaryType(primaryType);
        entity.setSecondaryType(secondaryType);

        pokemonRepository.persist(entity);
    }

}
