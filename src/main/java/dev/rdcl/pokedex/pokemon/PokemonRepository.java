package dev.rdcl.pokedex.pokemon;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class PokemonRepository implements PanacheRepository<PokemonEntity> {

    public Optional<PokemonEntity> findByIndex(int index) {
        return find("index", index).firstResultOptional();
    }

    public Optional<PokemonEntity> findByName(String name) {
        return find("name", name).firstResultOptional();
    }
}
