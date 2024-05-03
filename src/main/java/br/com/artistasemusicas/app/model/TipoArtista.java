package br.com.artistasemusicas.app.model;

public enum TipoArtista {
    // solo, dupla ou banda
    SOLO("solo"),
    DUPLA("dupla"),
    BANDA("banda");

    private String tipo;

    TipoArtista(String tipo) {
        this.tipo = tipo;
    }

    public static TipoArtista fromString(String text) {
        for (TipoArtista tipo : TipoArtista.values()) {
            if (tipo.tipo.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Nenhuma tipo encontrado para a string fornecida: " + text);
    }
}
