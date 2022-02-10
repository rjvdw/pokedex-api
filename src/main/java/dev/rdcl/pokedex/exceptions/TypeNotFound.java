package dev.rdcl.pokedex.exceptions;

public class TypeNotFound extends RuntimeException {

    public TypeNotFound(String name) {
        super("Type '%s' not found.".formatted(name));
    }
}
