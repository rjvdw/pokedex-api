package dev.rdcl.pokedex.effectiveness;

import dev.rdcl.pokedex.type.TypeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "effectiveness",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"type", "against"}),
        }
)
public class EffectivenessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "type", nullable = false)
    private TypeEntity type;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "against", nullable = false)
    private TypeEntity against;

    @Column(name = "effect", nullable = false)
    private String effect;

}
