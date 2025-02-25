package aed;

public interface Trie<T> {
    // Inserta una palabra en el trie.
    void definir(String clave, T valor);

    // Obtiene el valor de una clave del trie.
    T obtener(String clave);

    // Elimina la clave y su valor asociado del trie.
    void eliminar(String clave);

    // Devuelve en ordne lexicografico las claves de un trie.
    String[] clavesinOrder();
}