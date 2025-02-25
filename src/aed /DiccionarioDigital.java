package aed;

import java.util.ArrayList;

public class DiccionarioDigital<T> implements Trie<T> {
    private NodoTrie<T> _raiz;
    private int _size;


    public class NodoTrie<N> {
        // Hijos.
        ArrayList<NodoTrie<N>> siguientes;

        // Padre.
        NodoTrie<N> padre;

        // Que hijo es ese nodo.
        int indice;

        // Significado.
        N valor;

        NodoTrie() {
            siguientes = new ArrayList<>();
            for (int i = 0; i < 256; i++) {
                siguientes.add(null);
            }
            padre = null;
            // Un nodo nuevo no es hijo de nadie , inicia con un indice invalido.
            indice = -1;
            valor = null;
        }
    }

    // Constructor --> O(1).
    public DiccionarioDigital(){
        _raiz = new NodoTrie<>();
        _size = 0;
    }

    // Definir una clave --> O(|clave|)
    public void definir(String clave, T valor) {

    // Actual me dice en que nodo me encuentro.
    NodoTrie<T> actual = this._raiz;

    // Voy a ir iterando sobre cada caracter de la clave.
    for (int i = 0; i < clave.length(); i++) {

        // Posicion indica que "hijo" debo mirar.
        int posicion = (int) clave.charAt(i);
        
        // Sino tiene hijo ,lo creo y actualizo los datos.
        if (actual.siguientes.get(posicion) == null) {

            NodoTrie<T> nuevo = new NodoTrie<>();
            nuevo.padre = actual;
            nuevo.indice = posicion;

            actual.siguientes.set(posicion,nuevo);
            actual = nuevo;

        // Si tiene hijo ,me muevo a ese nodo.
        } else {
            actual = actual.siguientes.get(posicion);
        }      
    }
    // Una vez que agregue todos los caracteres actualizo el valor y agrando _size.
    this._size += 1;
    actual.valor = valor;   
    }

// Tiene complejidad O(|clave|) ya que cada paso de recorrer un caracter cuesta O(1).
  

    // Obtener el valor asociado a una clave. --> O(|clave|)
    public T obtener(String clave) {
    NodoTrie<T> actual = this._raiz;
    
    // Me muevo por los caracteres de la clave
    for (int i = 0; i < clave.length(); i++) {
        int posicion = (int) clave.charAt(i);
        
        // Si ese caracter no está, devuelvo null.
        if (actual.siguientes.get(posicion) == null) {
            return null;

        } else {
            actual = actual.siguientes.get(posicion);
        }
    }
    // Si la clave estaba devolvera el valor de su significado.
    return actual.valor;
}

// Tiene complejidad O(|clave|) ya que debo recorrer toda la clave en pasos O(1).
    

    // Eliminar una clave y su valor asociado. --> O(|clave|)
    public void eliminar(String clave) {
    // Comienzo a buscar desde la raiz.
    NodoTrie<T> actual = this._raiz;

    // Itero sobre la longitud de la clave.
    for (int i = 0; i < clave.length(); i++) {
        int posicion = (int) clave.charAt(i);
        
        // Si la clave a eliminar no estaba retorno.
        if (actual.siguientes.get(posicion) == null) {
            return;
        } else {
            actual = actual.siguientes.get(posicion);
        }
    }
    // Actual es el nodo desde el cual quiero eliminar.
    eliminarNodo(actual);
    }


    public void eliminarNodo(NodoTrie<T> nodo) {
    int j = 0;

    // Uso un ciclo para encontrar hijos.
    for (int i = 0; i < 256; i++) {
        if (nodo.siguientes.get(i) == null) {
            j += 1;
        }
    }
    // Si j no es 256 es por que tiene hijos , cambio el valor del nodo a null y retorno.
    if (j != 256) {
        nodo.valor = null;
        this._size -= 1;
        return;

    // Si tiene hijos y nodo padre no es null elimino el nodo actual.
    } else if (nodo.padre != null) {
        nodo.padre.siguientes.set(nodo.indice, null);

        // Si nodo padre tiene valor ,eliminar termina ahi.
        if (nodo.padre.valor != null) {
            this._size -= 1;
            return;

        // Si no tiene valor salto a padre y recursivamente elimino los necesarios.
        } else {
            eliminarNodo(nodo.padre);
        }

    // Si llego aca estoy en raiz puedo retornar ya que abre terminado.
    } else {
        this._size -= 1;
        return;
    }
    }

// En peor caso debere eliminar todos los nodos desde el que quiero eliminar hasta llegar a la raiz.
// Me cuesta O(|clave|) encontrar el nodo con el valor a eliminar.
// Me cuesta O(|clave|) subir y eliminar sus nodos.padre hasta llegar a la raiz.
// Entonces O(|clave| + |clave|) = O(|clave|).
    

    // Version de 'toString' para trie --> O(Σ_(c ∈ C) |c|).
    // 'C' es el conjunto de todas las claves en el diccionario.
    public String[] clavesinOrder() {

        // claves = res.
        String[] claves = new String[_size];

        // palabra ira 'apilando' los caracteres de las claves presentes.
        String palabra = "";

        // Me va a indicar que posicion de 'claves' puedo ocupar.
        Integer index = 0;

        // Sera el indice que me va a indicar que hijo debo ir viendo en las llamadas recursivas.
        int actual = 0;
        
        coleccionar(claves,palabra,index,actual,this._raiz);

        return claves;
    }

    public void coleccionar(String[] claves, String palabra, Integer index, int actual , NodoTrie<T> nodo){
    // Actual indica el siguiente hijo en orden lexicografico que debo analizar.
    while (actual < 256 && nodo.siguientes.get(actual) == null) {
          actual += 1;
    }

    // Si nodo tiene padre null y actual es 256 ,entonces estoy en raiz y ya abre recorrido
    // todos sus hijos , puedo retornar.
    if (actual == 256 && nodo.padre == null) {
        return;
    
    // Si nodo tiene hijo y el valor de ese hijo es null ,'apilo' su letra en palabra
    // y recursivamente vuelvo a recorrer el diccionario desde ese hijo.
    } else if (actual != 256 && nodo.siguientes.get(actual).valor == null) {

        int letra = actual;
        palabra += (char) letra;
        actual = 0;
        coleccionar(claves, palabra, index, actual, nodo.siguientes.get(letra));

    // Si recorri todos los hijos de ese nodo pero su padre no es null entonces
    // retrocedo a nodo padre y vuelvo a buscar desde ahi.
    // Cada vez que retrocedo a un nodo padre 'desapilo' su letra de palabra
    // para ir colectando las claves correctamente.
    } else if (actual == 256 && nodo.padre != null) {

        // Me aseguro de no volver a recorrer un nodo que ya vi.
        actual = nodo.indice + 1;

        // "Desapilo" la letra de palabra.
        if (!palabra.isEmpty()) {
            palabra = palabra.substring(0, palabra.length() - 1);
        }

        // Vuelvo a empezar.
        coleccionar(claves, palabra, index, actual, nodo.padre);
  

    // Si nodo tiene hijo y tiene valor lo apilo en 'palabra' y lo agrego a 'claves'.
    // Y sigo las llamadas recursivas.
    } else {
        int letra = actual;
        palabra += (char) letra;
        claves[index] = palabra;
        index += 1;

        // Cada vez que me traslado a un hijo ,reinicio actual ya que significa que debo
        // empezar a buscar desde 0.
        actual = 0;
        coleccionar(claves, palabra, index, actual, nodo.siguientes.get(letra));
    }
}
}

// La complejidad sera la suma de las longitudes de las claves presentes en el diccionario
// --> O(Σ_(c ∈ C) |c|).
// Todos los pasos en las llamadas recursivas cuestan O(1).
// Buscar el hijo correspondiente cuesta O(256) pero como es un valor acotado ,es O(1).
// Entonces repetire estos pasos por cada caracter de cada clave presente en el diccionario.


/**  Invariante de Representacion:
     * 
     *   _raiz != null.
     *   _raiz no tiene 'valor'.
     *   _raiz no tiene 'padre'.
     *   _size es igual a la cantidad de nodos con valor != null.
     *   Si nodo.valor es null entonces existe un i entero , 0 <= i < 256 tal que:
         nodo.siguientes[i] != null.
     *   Cada NodoTrie tiene un array de exactamente 256 elementos en 'siguientes'.
     *   Cada elemento en 'siguientes' es o null o una instancia de NodoTrie.
     *   _raiz.indice es siempre -1.
     *   Para todos los nodos distintos de _raiz, indice se mueve en el rango: 0 <= indice < 256.
     *   Para todo NodoTrie n y m , si n.padre = m, entonces m.siguientes[n.indice] = n.
     */
