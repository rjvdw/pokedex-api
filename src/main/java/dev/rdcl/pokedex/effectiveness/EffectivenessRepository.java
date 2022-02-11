package dev.rdcl.pokedex.effectiveness;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EffectivenessRepository implements PanacheRepository<EffectivenessEntity> {
}
