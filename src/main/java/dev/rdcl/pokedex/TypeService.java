package dev.rdcl.pokedex;

import dev.rdcl.pokedex.converters.TypeEntityConverter;
import dev.rdcl.pokedex.entities.TypeEntity;
import lombok.AllArgsConstructor;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@AllArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;

    public Type getType(String name) {
        return TypeEntityConverter.fromEntity(typeRepository.getByName(name));
    }

    public void saveType(Type type) {
        var optional = typeRepository
                .find("name", type.name())
                .firstResultOptional();

        if (optional.isEmpty()) {
            var entity = TypeEntity.builder()
                    .name(type.name())
                    .build();

            typeRepository.persist(entity);
        }
    }
}
