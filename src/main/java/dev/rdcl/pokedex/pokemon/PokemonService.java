package dev.rdcl.pokedex.pokemon;

import dev.rdcl.pokedex.type.TypeRepository;
import lombok.AllArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final TypeRepository typeRepository;

    @Transactional
    public List<Pokemon> getAllPokemon() {
        return pokemonRepository
                .findAll()
                .stream()
                .map(PokemonEntityConverter::fromEntity)
                .toList();
    }

    @Transactional
    public Optional<Pokemon> getPokemon(int index) {
        return pokemonRepository
                .findByIndex(index)
                .map(PokemonEntityConverter::fromEntity);
    }

    @Transactional
    public Optional<Pokemon> getPokemon(String name) {
        return pokemonRepository
                .findByName(name)
                .map(PokemonEntityConverter::fromEntity);
    }

    @Transactional
    public void savePokemon(Pokemon pokemon) {
        var entity = pokemonRepository
                .find("index", pokemon.index())
                .firstResultOptional()
                .orElseGet(() -> PokemonEntity.builder()
                        .index(pokemon.index())
                        .build());

        var primaryType = typeRepository.getByName(pokemon.primary());

        var secondaryType = pokemon.secondary() == null
                ? null
                : typeRepository.getByName(pokemon.secondary());

        entity.setName(pokemon.name());
        entity.setDescription(pokemon.description());
        entity.setPrimaryType(primaryType);
        entity.setSecondaryType(secondaryType);

        pokemonRepository.persist(entity);
    }

}
