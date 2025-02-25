package aed;

import aed.ListaEnlazada.Nodo;

public class SistemaSIU {
    private Trie<Trie<Materia>> _carreras;
    private Trie<Integer> _estudiantes;

    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }
    

    public SistemaSIU(InfoMateria[] materiasEnCarreras, String[] libretasUniversitarias){
        this._carreras = new DiccionarioDigital<>();
        this._estudiantes = new DiccionarioDigital<>();
        
        // Cada posicion del arreglo 'materiasEnCarreras' corresponde a una materia.
        for (InfoMateria info : materiasEnCarreras) {

            // Obtengo la lista de carreras que tienen esa misma materia.
            // Y obtengo la lista de nombres de esa materia. --> O(1).
            String[] infoCarreras = info.carreras;
            String[] infoMaterias = info.nombresEnCarreras;

            // Creo una instancia de Materia que van a compartir todas las carreras
            // que la tengan como materia. --> O(1).
            Materia nueva = new Materia();

            for (int i = 0; i < infoCarreras.length; i++) {

                // Accedo al subtrie de cada carrera que contiene sus materias. --> O(|c|).
                Trie<Materia> subtrieMateria = this._carreras.obtener(infoCarreras[i]);

                // Sino tiene uno lo creo. --> O(1).
                // Y lo defino --> O(|c|)
                if (subtrieMateria == null) {
                    subtrieMateria = new DiccionarioDigital<>();
                    this._carreras.definir(infoCarreras[i], subtrieMateria);
                }
                // Una vez que obtenemos el subtrie , agrego la materia. --> O(|n|).
                // Y guardo en el 'mapa' de la materia como encontrarla con cada carrera. --> O(1).
                subtrieMateria.definir(infoMaterias[i], nueva);
                nueva.guardarDireccion(subtrieMateria, infoMaterias[i]);
            }
        } 
        // Una vez que inscribi todas las materias , inicio a los estudiantes. --> O(E).
        for (String alumnos : libretasUniversitarias) {
            this._estudiantes.definir(alumnos, 0);
        }
    }

// Complejidad sistemaSIU.
/*
    * Obtener el subTrie de una carrera y definirlo tiene complejidad O(|c|) + O(|c|) = O(|c|).
    * Esto lo hacemos tantas veces como materias tenga esa carrera en su conjunto de materias.
    * Si Mc es el conjunto de materias que tiene cada carrera entonces ese paso lo hacemos 
      |Mc| veces.
    * Y esto lo hacemos con cada carrera que hay en el conjunto total de carreras C.
    * En total ese paso al finalizar el metodo tiene complejidad: O(Σ_(c ∈ C) |c|*|Mc|).

    * Definir una instancia de Materia en cada subtrie cuesta O(|n|).
    * Como las materias tienen distintos nombres , ese paso lo tengo que hacer tantas veces 
      como nombres tenga.
    * Y como los nombres aveces difieren ese paso va a costar en cada iteracion :
    * O(|n_1|) + O(|n_2|) + ... + O(|n_n|). Si Nm es el conjunto de nombres de cada materia la 
      complejidad queda: O(Σ_(n ∈ Nm) |n|. 
    * Y esto lo voy hacer con cada materia que hay en el conjunto total de materias M.
    * Por lo tanto en total la complejidad de ese paso queda : O(Σ_(m ∈ M) Σ_(n ∈ Nm) |n|.
    
    * Por ultimo como las claves de los estudiantes estan acotadas ,agregarlas a un Trie tiene 
      costo O(1).
    * Por lo tanto ese paso cuesta O(E).'E' siendo la cantidad de estudiantes en total.

    * Al finalizar el metodo la complejidad queda:
    * O(Σ_(c ∈ C) |c|*|Mc| + Σ_(m ∈ M) Σ_(n ∈ Nm) |n| + E).
 */

    public void inscribir(String estudiante, String carrera, String materia){
        // Accedo a las materias de "carrera". --> O(|c|)
        Trie<Materia> subMateria = this._carreras.obtener(carrera);

        // Accedo a la materia que voy a modificar y agrego ese alumno. --> O(|m|)
        Materia datos = subMateria.obtener(materia);
        datos.inscribirAlumno(estudiante);
        
        // Aumento la cantidad de materias que esta inscripto ese estudiante. --> O(1).
        int cantidad = this._estudiantes.obtener(estudiante);
        cantidad += 1;
        this._estudiantes.definir(estudiante, cantidad);

    }

// Complejidad inscribir:
/*
    * Accedo a la carrera de ese alumno en tiempo O(|c|).
    * Despues accedo a la materia que lo quiero inscribir en tiempo O(|m|).
    * Lo agrego a esa materia en tiempo O(1)
    * Como los nombres de estudiantes estan acotados , accedo a su trie materias en O(1).
    * Aumento la cantidad de materias en la que esta en O(1).
    * En total la complejidad queda O(|c| + |m| + 1 + 1) = O(|c| + |m|). 
*/
    
    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        // Accedo a la materia que quiero agregar el docente en:
        // --> O(|c|) + O(|m|).
        Trie<Materia> subMateria = this._carreras.obtener(carrera);
        Materia datos = subMateria.obtener(materia);
        
        // Lo agrego en --> O(1).
        if (cargo.ordinal() == 0) {
            datos.inscribirDocente("AY2");

        } else if (cargo.ordinal() == 1) {
            datos.inscribirDocente("AY1"); 

        } else if (cargo.ordinal() == 2) {
            datos.inscribirDocente("JTP");

        } else {
            datos.inscribirDocente("PROF");
        }
    } 

// Complejidad agregarDocente:
/*
   * Accedo a un Trie que contiene las materias de una carrera específica O(|c|).
   * Accedo a la materia dentro del Trie de materias de la carrera O(|m|).
   * Los if cuestan O(1) entonces la complejidad queda O(|c| + |m|).
*/

    public int[] plantelDocente(String materia, String carrera){
        // Accedo a la materia del plantel que quiero en --> O(|c|) + O(|m|).
        Trie<Materia> subMateria = this._carreras.obtener(carrera);
        Materia datos = subMateria.obtener(materia);

        // Devuelvo el plantel en O(1).
        return datos.obtenerPlantel();
    }
// Complejidad plantelDocente
/*
  * Accedo a la materia en O(|c|) + O(|m|) y devuelvo el plantel en O(|1|).
  * La complejidad queda O(|c| + |m|). 
 */    

    @SuppressWarnings("rawtypes")
    public void cerrarMateria(String materia,   String carrera){
        // Accedo a las materias de esa carrera --> O(|c|).
        Trie<Materia> subTrieMateria = this._carreras.obtener(carrera);

        // Accedo a los datos de esa materia --> O(|m|).
        Materia datosMateria = subTrieMateria.obtener(materia);

        // Accedo a todas las direcciones en que se encuentra esa materia. --> O(1).
        ListaEnlazada<infoIguales> direcciones = datosMateria.obtenerIguales();

        // Comienzo en la primer direccion y con un ciclo voy cerrando las materia en todas 
        // las carreras que tenga asociadas.
        Nodo actual = direcciones.obtenerPrimero();
        
        // Elimino la materia en cada carrera que la tenga. --> O(Σ_(n ∈ Nm) |n|)
        while (actual != null) {
            infoIguales info = (infoIguales) actual.valor;
            info.otraCarrera.eliminar(info.otraClave);
            actual = actual.siguiente;
        }

        // Idem al paso anterior pero ahora itero con los alumnos de la materia.
        ListaEnlazada<String> alumnes = datosMateria.obtenerAlumnos();
        Nodo alumno = alumnes.obtenerPrimero();
        
        // Disminuyo la cantidad de materias de cada alumno en esa materia. --> O(Em).
        while (alumno != null) {

            // Como la longitud de los nombres de estudiantes estan acotadas ,tanto obtener
            // como definir cuesta O(1).
            String LU = (String) alumno.valor;
            Integer inscripciones = this._estudiantes.obtener(LU);
            inscripciones -= 1;
            this._estudiantes.definir(LU, inscripciones);

            alumno = alumno.siguiente;
        }
    }
// Complejidad cerrarMateria:
/*
  * Acceder a la carrera y la materia que quiero cerrar me cuesta O(|c| + |m|).
  * Eliminar una materia me cuesta O(|n|) y lo voy hacer tantas veces como nombres tenga.
  * Entonces en total me cuesta O(Σ_(n ∈ Nm) |n|).
  * Disminuir la cantidad de materias inscriptas de cada estudiante me cuesta O(1)
    por cada estudiante que la tenga como materia.En total es O(Em).
  * La complejidad queda :  O(|c| + |m| + Σ_(n ∈ Nm) |n| + Em).
*/    

    public int inscriptos(String materia, String carrera){
        // Accedo a la carrera y materia que quiero ver en --> O(|c|) + O(|m|).
        Trie<Materia> subMateria = this._carreras.obtener(carrera);
        Materia datos = subMateria.obtener(materia);

        // Devuelvo la cantidad de inscriptos en O(1).
        return datos.obtenerAlumnos().obtenerSize();
    }

// Complejidad inscriptos:   
/*
  * Voy a la materia que quiero ver sus estudiantes asociados en O(|c|) + O(|m|).
  * Devuelvo su cantidad en O(1) dejando la complejidad en : O(|c| + |m|).
*/ 

    public boolean excedeCupo(String materia, String carrera){
        // Accedo al plantel de esa materia en O(|c| + |m|)
        Trie<Materia> subMateria = this._carreras.obtener(carrera);
        Materia datosMateria = subMateria.obtener(materia);
        
        // Obtengo el plantel en O(1).
        int[] plantel = datosMateria.obtenerPlantel();
        
        // El cupo lo va a indicar el minimo numero asociado a cada cargo docente.
        // Cada if cuesta O(1).
        int cupo = plantel[0]*250;

        if (cupo > plantel[1]*100) {
            cupo = plantel[1]*100;
        } 
        if (cupo > plantel[2]*20) {
            cupo = plantel[2]*20;
        }
        if (cupo > plantel[3]*30) {
            cupo = plantel[3]*30;
        }

        // Retorno true si cupo es menor a la cantidad de alumnos y false en caso contrario.
        return cupo < datosMateria.obtenerAlumnos().obtenerSize();
    }

// Complejidad excedeCupo:
/*
  * Para calcular el cupo tengo que acceder al plantel de esa materia en O(|c| + |m|).
  * Luego calcular el cupo me cuesta O(4) --> O(1).
  * La complejidad queda O(|c| + |m|).
 */

    public String[] carreras(){
        return this._carreras.clavesinOrder();
    }
// Complejidad carreras:
/*
  * Utilizo el metodo clavesinOrder() de la clase DiccionarioDigital que tiene un costo
   O(Σ_(c ∈ C) |c|).
*/

    public String[] materias(String carrera){
        Trie<Materia> subTrieMaterias = this._carreras.obtener(carrera);
        return subTrieMaterias.clavesinOrder();
    }
// Complejidad materias:
/*
  * Idem al anterior solo que ahora sumo el costo de acceder a las materias.
  * En total la complejidad queda : O(|c| + Σ_(mc ∈ Mc) |mc|)
*/

    public int materiasInscriptas(String estudiante){
        return this._estudiantes.obtener(estudiante);
    }
// Complejidad materiasInscriptas:
/*
  * Como los nombres de estudiantes estan acotados , obtener la cantidad de materias
    que se encuentra inscipto me cuesta O(1).
 */


}

/*  Invariante de Representacion:

    * Cada clave de 'carreras' debe tener asociado como valor una instancia valida de Trie.

    * Cada valor del subtrie de cada carrera debe ser una instancia valida de Materia.

    * El subtrie de carreras con mayor cantidad de claves definidas indica la cantidad
      maxima de materias. 

    * Todos los valores asociados a las claves de 'estudiantes' deben ser mas grandes o iguales
      que 0 y no mas grandes que la cantidad maxima de materias.

    * Las claves de 'estudiantes' tienen la misma longitud.

    * Para todo 'm' instancia de materia asociada a los valores de las claves de cada subtrie de 
      'carreras':
      * |m.alumnos| no puede superar la cantidad de claves de 'estudiantes'.
      * Todo elemento de m.alumnos debe ser una clave valida de 'estudiantes'.
      * 0 <= |m.direcciones| < cantidad de claves definidas en 'carreras'.
      * Todo subtrie 'materia' asociado a un elemento de m.direcciones debe ser un subtrie valido 
        de 'carreras'.
*/
