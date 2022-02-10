package dev.rdcl.pokedex.pokemon;

import dev.rdcl.pokedex.type.TypeEntityConverter;

public class PokemonEntityConverter {

    public static Pokemon fromEntity(PokemonEntity entity) {
        return new Pokemon(
                entity.getIndex(),
                entity.getName(),
                entity.getDescription(),
                TypeEntityConverter.fromEntity(entity.getPrimaryType()),
                entity.getSecondaryType() == null
                        ? null
                        : TypeEntityConverter.fromEntity(entity.getSecondaryType())
        );
    }

    public static PokemonEntity asEntity(Pokemon record) {
        return PokemonEntity.builder()
                .index(record.index())
                .name(record.name())
                .description(record.description())
                .primaryType(TypeEntityConverter.asEntity(record.primary()))
                .secondaryType(record.secondary() == null
                        ? null
                        : TypeEntityConverter.asEntity(record.secondary())
                )
                .build();
    }
}
