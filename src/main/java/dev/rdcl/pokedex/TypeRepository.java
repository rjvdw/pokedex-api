package dev.rdcl.pokedex;

import dev.rdcl.pokedex.entities.TypeEntity;
import dev.rdcl.pokedex.exceptions.TypeNotFound;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TypeRepository implements PanacheRepository<TypeEntity> {

    public TypeEntity getByName(String name) {
        return find("name", name)
                .firstResultOptional()
                .orElseThrow(() -> new TypeNotFound(name));
    }
}
