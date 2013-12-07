/**
 * @(#)ArbolEtiquetas.java
 *
 *
 * @author 
 * @version 1.00 2013/10/20
 */
 
 
//http://www.youtube.com/watch?v=5OImsT0rLs4
//http://aci710-2013.blogspot.mx/2013/04/tarea-3-algoritmos-de-busqueda-en-java_22.html



public class ArbolEtiquetas {
        
    class Nodo {
        Nodo izq, der;
        String etiqueta;
        String dir;
      }
      Nodo raiz;
      
    public ArbolEtiquetas() {
          raiz=null;
    }
    
    public void Inicializar() {
          raiz=null;
    }
   
    public boolean insertar (String etq, String dir) {
          Nodo nuevo;
          nuevo = new Nodo ();
          String contenido = etq;
          String contenido2 = dir;
          nuevo.etiqueta = contenido;
          nuevo.dir = contenido2;
          nuevo.izq = null;
          nuevo.der = null;
          boolean existeEtiqueta = false;

          if (raiz == null){
          	nuevo.etiqueta = contenido;
          	nuevo.dir = contenido2;
          	raiz = nuevo;
          }

          else {

              Nodo anterior = null, reco;
              reco = raiz;
              while (reco != null) {
                  anterior = reco;
                  if (nuevo.etiqueta.compareTo(reco.etiqueta)<0)
                      reco = reco.izq;
                  else
                  	if (nuevo.etiqueta.compareTo(reco.etiqueta)>0)
                      reco = reco.der;
                    else
                    	if(nuevo.etiqueta.compareTo(reco.etiqueta)==0){
                    		reco=null;
                  	}

              }

              if (nuevo.etiqueta.compareTo(anterior.etiqueta) <0){
              	nuevo.etiqueta=contenido;
              	nuevo.dir = contenido2;
              	anterior.izq = nuevo;
              	existeEtiqueta = false;
              }
              else
              	if (nuevo.etiqueta.compareTo(anterior.etiqueta)>0) {
              		nuevo.etiqueta = contenido;
              		nuevo.dir = contenido2;
                  	anterior.der = nuevo;
                  	existeEtiqueta = false;
              	}
                  else
                  	if(nuevo.etiqueta.compareTo(anterior.etiqueta)==0) {
                  		
                  		//nuevo.etiqueta = contenido;
                  		existeEtiqueta = true;
                  		anterior=nuevo;
                  	}
          }
          return existeEtiqueta;
      }
      
      private void imprimirEntre (Nodo reco) {
          if (reco != null) {
              imprimirEntre (reco.izq);
              System.out.print("........" +reco.etiqueta + " ");
			  System.out.println();
              imprimirEntre (reco.der);
          }
      }

      public void imprimirEntre () {
          imprimirEntre (raiz);
          System.out.println();
      }
      
      
      public boolean existe(String contenido,ArbolEtiquetas arboletq) {
        Nodo reco=raiz;
        try{
        	while (reco!=null) {
        		if (contenido.equalsIgnoreCase(reco.etiqueta)) {
                	return true;
            	}
            	else{
            		if (contenido.compareTo(reco.etiqueta)>0)
                	    reco=reco.der;
                	else
                    	reco=reco.izq;
            	}

        	}
        }catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
        return false;
      }
      
      public String extraerCLOCTABSIM(String contenido,ArbolEtiquetas arboletq) {
        Nodo reco=raiz;
        try{
        	while (reco!=null) {
        		if (contenido.equals(reco.etiqueta)) {
                	return reco.dir;
            	}
            	else{
            		if (contenido.compareTo(reco.etiqueta)>0)
                	    reco=reco.der;
                	else
                    	reco=reco.izq;
            	}

        	}
        }catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
        return "NULL";
      }
      
      
      public boolean buscar(Nodo n, String etq) {
      	if(n!=null){
      		if(n.etiqueta.equalsIgnoreCase(etq)) return true;
      		boolean bus1 = buscar (n.izq,etq);
      		boolean bus2 = buscar (n.der,etq);
      		if((bus1 == true) || (bus2 == true)) return true;
      	}
      	return false;
      }
      
      
      /*public void borrar (String elemento) {
      	raiz = borrar(this.raiz,elemento);
      }*/
      
      /*private Nodo borrar (Nodo r, String elemento) {
      	if(r.etiqueta.equalsIgnoreCase(elemento)) {
      			if(r.der == null && r.izq == null) {
      				r = null;
      				return r;
      			}
      			if(r.der == null) {
      				r = r.izq;
      				return r;
      			}
      			if(r.izq == null) {
      				r = r.der;
      				return r;
      			}
      			r.etiqueta =
      	}
      }*/
      
      
      
       public void borrar (String elemento)//aqui se recibe el elemento a eliminar
    {
        raiz = borrar(this.raiz, elemento);
    }
   
    private Nodo borrar(Nodo r, String elemento)
    {
        if (r.etiqueta.equalsIgnoreCase(elemento))//este es un caso en que el nodo r se requiere borrar del arbol
        {
            if(r.der == null && r.izq == null)
            {
                r = null;
                return r;
            }
            if(r.der == null)// caso en el que r solo tiene hijo izquierdo
            {
                r = r.izq;
                return r;    //aqui el hijo ocupa el lugar del padre
            }
            if(r.izq ==null)
            {
                r = r.der;
                return r;
            }
            //este es el caso en el que el nodo tiene dos hijos
            r.etiqueta = encontrarMaximo(r.izq); //sera igual que el nodo de mayor valor
            r = ajuste(r, r.izq, r);
            return r;//el nodo igualado de mayor valor se debe eliminar
        }
   
        if(r.etiqueta.compareTo(elemento)<0)//si el elemento buscado es mayor al del nodo actual
        {
            r.der = borrar(r.der, elemento);
            return r;
        }
   
        r.izq = borrar(r.izq, elemento);
        return r;
    }
    //este metodo sirve para econtrar al nodo maximo
    //nos ayudara para la eliminacion del nodo dado en el metodo borrar
    private String encontrarMaximo(Nodo r)
    {
        if(r.der == null)//si no hay un nodo mayor retorna el valor del nodo
            return r.etiqueta;
        return encontrarMaximo(r.der);
    }
    //metodo que elimina el nodo repetido y ajusta el arbol
    private Nodo ajuste(Nodo padre, Nodo hijo, Nodo ances)
    {
        if(hijo.der == null)
        {
            if(padre.equals(ances))
            {
                padre.izq = hijo.izq;
                return padre;
            }
            padre.der = hijo.izq;
            return padre;
        }
        return padre;
    }    
      
      
}
