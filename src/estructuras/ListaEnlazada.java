package aed;

public class ListaEnlazada<T> {
    private Nodo _primero;
    private int _size;

    public class Nodo {
        T valor;
        Nodo siguiente;

        Nodo(T valor) {
            this.valor = valor;
            this.siguiente = null;
        }
    }

    // Constructor de la clase --> O(1).
    public ListaEnlazada() {
        this._primero = null;
        this._size = 0;
    }
    

    // Agrego un elemento --> O(1).
    public void agregarAdelante(T elemento) {
        Nodo nuevo = new Nodo(elemento);
        if (this._primero == null) {
            this._primero = nuevo;
        } else {
            nuevo.siguiente = this._primero;
            this._primero = nuevo;
        }
        this._size++;
    }
    

    // Devuelve la longitud de la lista --> O(1).
    public int obtenerSize() {
        return this._size;
    }
    

    // Devuelve el primer elemento de la lista --> O(1).
    public Nodo obtenerPrimero() {
        return this._primero;
    } 
} 


 // Invariante de representacion
    /** 
       * _size es siempre mayor o igual a 0.
       
       * _size es 0 si y solo si _primero es null.

       * _size es igual a la cantidad de elementos siguientes != null, comenzando con 
         _primero. 
         
       * No hay ciclos.Si comienzo con _primero , y recorro siguientes llegare al nodo null.
    */




   

    
