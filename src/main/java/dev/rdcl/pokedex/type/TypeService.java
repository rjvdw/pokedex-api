package dev.rdcl.pokedex.type;

import dev.rdcl.pokedex.effectiveness.EffectivenessEntity;
import dev.rdcl.pokedex.effectiveness.EffectivenessRepository;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;
    private final EffectivenessRepository effectivenessRepository;

    @Transactional
    public List<String> getAllTypes() {
        return typeRepository
                .findAll()
                .stream()
                .map(TypeEntityConverter::fromEntity)
                .toList();
    }

    @Transactional
    public List<TypeWithEffectiveness> getTypeChart() {
        var superEffective = new HashMap<String, ArrayList<String>>();
        var notVeryEffective = new HashMap<String, ArrayList<String>>();
        var noEffect = new HashMap<String, ArrayList<String>>();

        effectivenessRepository
                .findAll(Sort.by("type"))
                .stream()
                .forEach(entity -> {
                    var type = entity.getType().getName();
                    if (!superEffective.containsKey(type)) {
                        superEffective.put(type, new ArrayList<>());
                        notVeryEffective.put(type, new ArrayList<>());
                        noEffect.put(type, new ArrayList<>());
                    }

                    (switch (entity.getEffect()) {
                        case "super effective" -> superEffective;
                        case "not very effective" -> notVeryEffective;
                        case "no effect" -> noEffect;
                        default -> throw new IllegalStateException("Invalid value for effect: " + entity.getEffect());
                    })
                            .get(type)
                            .add(entity.getAgainst().getName());
                });

        return typeRepository
                .findAll(Sort.by("sort_order"))
                .stream()
                .map(entity -> {
                    var type = entity.getName();
                    return new TypeWithEffectiveness(
                            type,
                            asImmutableList(type, superEffective),
                            asImmutableList(type, notVeryEffective),
                            asImmutableList(type, noEffect));
                })
                .toList();
    }

    private List<String> asImmutableList(String key, Map<String, ArrayList<String>> map) {
        if (map.containsKey(key)) {
            return map.get(key).stream().toList();
        } else {
            return Collections.emptyList();
        }
    }

    @Transactional
    public void defineTypes(
            List<String> types,
            List<TypeWithEffectiveness> chart
    ) {

        // Ensure all types in the chart exist.
        var existingTypes = getExistingTypes();
        var typesToKeep = new HashSet<String>();
        for (var i = 0; i < types.size(); i += 1) {
            var type = types.get(i);
            typesToKeep.add(type);
            if (existingTypes.containsKey(type)) {
                var entity = existingTypes.get(type);
                entity.setSortOrder(i);
                typeRepository.persist(entity);
            } else {
                var entity = TypeEntity.builder()
                        .name(type)
                        .sortOrder(i)
                        .build();

                existingTypes.put(type, entity);
                typeRepository.persist(entity);
            }
        }

        // Persist the new types.
        existingTypes
                .keySet()
                .stream()
                .filter(type -> !typesToKeep.contains(type))
                .forEach(type -> {
                    typeRepository.delete("name", type);
                });

        // Translate the chart to a list of entities.
        var entities = chartToEntities(chart, existingTypes);

        // Determine which entities need to be deleted, inserted or updated.
        var entitiesToDelete = new ArrayList<EffectivenessEntity>();
        effectivenessRepository
                .findAll()
                .stream()
                .forEach(entity -> {
                    var key = asKey(entity);
                    if (entities.containsKey(key)) {
                        // replace the entity with the existing one and update it
                        var e = entities.get(key);
                        entity.setEffect(e.getEffect());
                        entities.put(key, entity);
                    } else {
                        entitiesToDelete.add(entity);
                    }
                });

        for (var entity : entities.values()) {
            effectivenessRepository.persist(entity);
        }
        for (var entity : entitiesToDelete) {
            effectivenessRepository.delete(entity);
        }
    }

    private Map<String, TypeEntity> getExistingTypes() {
        return typeRepository
                .findAll()
                .stream()
                .collect(Collectors.toMap(TypeEntity::getName, Function.identity()));
    }

    private Map<String, EffectivenessEntity> chartToEntities(
            List<TypeWithEffectiveness> chart,
            Map<String, TypeEntity> types
    ) {
        var entities = new HashMap<String, EffectivenessEntity>();
        for (var entry : chart) {
            var type = types.get(entry.type());

            for (var against : entry.superEffectiveAgainst()) {
                entities.put(
                        asKey(entry.type(), against),
                        EffectivenessEntity.builder()
                                .type(type)
                                .against(types.get(against))
                                .effect("super effective")
                                .build()
                );
            }

            for (var against : entry.notVeryEffectiveAgainst()) {
                entities.put(
                        asKey(entry.type(), against),
                        EffectivenessEntity.builder()
                                .type(type)
                                .against(types.get(against))
                                .effect("not very effective")
                                .build()
                );
            }

            for (var against : entry.noEffectAgainst()) {
                entities.put(
                        asKey(entry.type(), against),
                        EffectivenessEntity.builder()
                                .type(type)
                                .against(types.get(against))
                                .effect("no effect")
                                .build()
                );
            }
        }
        return entities;
    }

    private String asKey(String type, String against) {
        return "%s:%s".formatted(type, against);
    }

    private String asKey(EffectivenessEntity entity) {
        return asKey(entity.getType().getName(), entity.getAgainst().getName());
    }
}
