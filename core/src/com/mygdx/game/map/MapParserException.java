package com.mygdx.game.map;

public class MapParserException extends RuntimeException {

    public MapParserException() {
    }

    public MapParserException(String message) {
        super(message);
    }

    public MapParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapParserException(Throwable cause) {
        super(cause);
    }
}
