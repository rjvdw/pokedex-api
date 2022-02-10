package dev.rdcl.pokedex.type;

public class TypeEntityConverter {

    public static String fromEntity(TypeEntity entity) {
        return entity.getName();
    }

    public static TypeEntity asEntity(String record) {
        return TypeEntity.builder()
                .name(record)
                .build();
    }
}
