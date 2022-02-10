package dev.rdcl.pokedex.type;

import lombok.AllArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;

    public List<String> getAllTypes() {
        return typeRepository
                .findAll()
                .stream()
                .map(TypeEntityConverter::fromEntity)
                .toList();
    }
}
