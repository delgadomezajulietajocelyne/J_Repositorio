/**
 * @(#)ArbolEtiquetas.java
 *
 *
 * @author 
 * @version 1.00 2013/10/20
 */

public class ArbolEtiquetas {
        
    class Nodo {
        Nodo izq, der;
        String etiqueta;
      }
      Nodo raiz;
      
    public ArbolEtiquetas() {
          raiz=null;
    }
   
    public boolean insertar (String etq) {
          Nodo nuevo;
          nuevo = new Nodo ();
          String contenido = etq;
          nuevo.etiqueta = contenido;
          nuevo.izq = null;
          nuevo.der = null;
          boolean existeEtiqueta = false;

          if (raiz == null){
          	nuevo.etiqueta = contenido;
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
              	anterior.izq = nuevo;
              	existeEtiqueta = false;
              }
              else
              	if (nuevo.etiqueta.compareTo(anterior.etiqueta)>0) {
              		nuevo.etiqueta = contenido;
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
      
}
