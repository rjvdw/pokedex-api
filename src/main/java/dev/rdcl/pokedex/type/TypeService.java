package dev.rdcl.pokedex.type;

import lombok.AllArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;

    @Transactional
    public List<String> getAllTypes() {
        return typeRepository
                .findAll()
                .stream()
                .map(TypeEntityConverter::fromEntity)
                .toList();
    }
}
