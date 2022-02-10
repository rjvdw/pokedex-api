package dev.rdcl.pokedex.converters;

import dev.rdcl.pokedex.Type;
import dev.rdcl.pokedex.entities.TypeEntity;

public class TypeEntityConverter {

    public static Type fromEntity(TypeEntity entity) {
        return Type.valueOf(entity.getName());
    }

    public static TypeEntity asEntity(Type record) {
        return TypeEntity.builder()
                .name(record.name())
                .build();
    }
}
