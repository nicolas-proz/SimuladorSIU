package aed;

public class Materia {
    private ListaEnlazada<String> _alumnos;
    private int[] _docentes;
    private ListaEnlazada<infoIguales> _direcciones;

    // Constructor de clase Materia --> O(1)
    public Materia(){
        this._alumnos = new ListaEnlazada<>();
        this._docentes = new int[4];
        this._direcciones = new ListaEnlazada<>();
    }
    
    // Devuelve la lista de alumnos de la materia. --> O(1)
    public ListaEnlazada<String> obtenerAlumnos(){
        return this._alumnos;
    }
    
    // Devuelve al plantel docente. --> O(1)
    public int[] obtenerPlantel(){
        return this._docentes;
    }

    // Devuelve el mapa de todas las carreras que contienen a Materia
    // Junto con su respectiva clave para encontrarla. --> O(1)
    public ListaEnlazada<infoIguales> obtenerIguales(){
        return this._direcciones;
    }

    // Agregamos un alumno a la Materia --> O(1)
    public void inscribirAlumno(String LU){
        this._alumnos.agregarAdelante(LU);
    }

    // Agregamos un docente al plantel de la Materia --> O(1)
    public void inscribirDocente(String cargo){
        if (cargo.equals("PROF")) {
            this._docentes[0] += 1;

        } else if (cargo.equals("JTP")) {
            this._docentes[1] += 1;

        } else if (cargo.equals("AY1")) {
            this._docentes[2] += 1;

        } else if (cargo.equals("AY2")) {
            this._docentes[3] += 1;

        } else {
            System.out.println("No corresponde a un cargo docente.");
        }      
        }

    // Agregamos otra carrera que tenga la misma materia
    // y su clave correspondiente --> O(1)
    public void guardarDireccion(Trie<Materia> otraCarrera, String clave){
        
        infoIguales direccion = new infoIguales(otraCarrera, clave);
        this._direcciones.agregarAdelante(direccion);
    }           
    }

// Complejidades:
/** 
    * Como en esta clase solo usamos las operaciones basicas de una version reducida de
      Lista enlazada , todas tienen costo O(1).
*/


/** Invariante de Representacion:
 
     * 'alumnos' es siempre distinto de null.
     * No hay repetidos en 'alumnos'.
     * 'docentes' no es null y siempre tiene 4 elementos.
     * Los valores de 'docentes' son siempre mayores o iguales a 0.
     * 'direcciones' nunca es null.
     
*/

