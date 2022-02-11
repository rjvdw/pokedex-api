package dev.rdcl.pokedex.effectiveness;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Effectiveness {
    SuperEffective("super effective", 2.0),
    Normal("normal", 1.0),
    NotVeryEffective("not very effective", 0.5),
    NoEffect("no effect", 0.0);

    private final String name;
    private final double multiplier;
}
