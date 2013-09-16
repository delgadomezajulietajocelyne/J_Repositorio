/**
 * @(#)Arbol.java
 *
 *
 * @author Delgado Meza Julieta Jocelyne
 * @version 1.00 2013/9/13
 */

import java.util.*;
import java.util.StringTokenizer;
import java.io.FileWriter;

public class ArbolBinarioOrdenado {
    class Nodo {
      	//*INS/tieneonoope\mododedir/codmaqenhex/bytesporlasminus\sumabytes/bytesporlohex
      	String codop;
      	boolean existeoperando;
        ArrayList<String> modosdir = new ArrayList<String>();
        String codmaqenhex; 		// Código maquina en hexadecimal
        String bytesporlasminus; 	// Bytes por calcular
        String sumabytes; 			// Suma total de bytes
        String bytesporlohex; 		// Bytes calculados
        Nodo izq, der;
      }
      Nodo raiz;

      public ArbolBinarioOrdenado() {
          raiz=null;
      }
      
      public void insertar (StringTokenizer st) {
          Nodo nuevo;
          nuevo = new Nodo ();
          String contenido = new String (st.nextToken());
          nuevo.codop = contenido;
          contenido = st.nextToken();
          if(contenido.equals("1")) {
          	nuevo.existeoperando = true;
          }
          else{
          	nuevo.existeoperando = false;
          }
          contenido = st.nextToken();
          nuevo.modosdir.add(contenido);
          contenido = st.nextToken();
          nuevo.codmaqenhex = contenido;		// Código maquina en hexadecimal
          contenido = st.nextToken();
          nuevo.bytesporlasminus = contenido; 	// Bytes por calcular
          contenido = st.nextToken();
          nuevo.sumabytes = contenido; 			// Suma total de bytes
          contenido = st.nextToken();
          nuevo.bytesporlohex = contenido; 		// Bytes calculados
          nuevo.izq = null;
          nuevo.der = null;
          
          //System.out.print(nuevo.info + " " +nuevo.codop + " "+nuevo.modosdir);
          if (raiz == null)
              raiz = nuevo;
          else {
              
              Nodo anterior = null, reco;
              reco = raiz;
              while (reco != null) {
                  anterior = reco;
                  if (nuevo.codop.compareTo(reco.codop)<0)
                      reco = reco.izq;
                  else
                  	if (nuevo.codop.compareTo(reco.codop)>0)
                      reco = reco.der;
                    else
                    	if(nuevo.codop.compareTo(reco.codop)==0){
                    		//System.out.println("ENTROOO");
                  		//reco.modosdir.add(contenido);
                  		reco=null;
                  	}
                      
              }
              
              if (nuevo.codop.compareTo(anterior.codop) <0)
                  anterior.izq = nuevo;
              else
              	if (nuevo.codop.compareTo(anterior.codop)>0)
                  anterior.der = nuevo;
                  else
                  	if(nuevo.codop.compareTo(anterior.codop)==0) {
                  		//System.out.println("aqui");
                  		anterior.modosdir.add(contenido);
                  		anterior=nuevo;
                  	}
          }
      }


      private void imprimirPre (Nodo reco) {
          if (reco != null) {
              System.out.print(reco.codop + " ");
              imprimirPre (reco.izq);
              imprimirPre (reco.der);
          }
      }

      public void imprimirPre () {
          imprimirPre (raiz);
          System.out.println();
      }

      private void imprimirEntre (Nodo reco) {
          if (reco != null) {    
              imprimirEntre (reco.izq);
              System.out.print(" " +reco.codop + " ");
              for(int i = 0;i<reco.modosdir.size();i++){
              	System.out.print(reco.modosdir.get(i));
              	if((i+1)!= reco.modosdir.size()){
              		System.out.print(", ");
              	}
			  }
			  System.out.println();
              imprimirEntre (reco.der);
          }
      }

      public void imprimirEntre () {
          imprimirEntre (raiz);
          System.out.println();
      }


      private void imprimirPost (Nodo reco){
          if (reco != null) {
              imprimirPost (reco.izq);
              imprimirPost (reco.der);
              System.out.print(reco.codop + " ");
          }
      }


      public void imprimirPost () {
          imprimirPost (raiz);
          System.out.println();
      }

      public void IMPRIME(ArbolBinarioOrdenado abo) {
          System.out.println ("Impresion preorden: ");
          abo.imprimirPre ();
          System.out.println ("Impresion entreorden: ");
          abo.imprimirEntre ();
          System.out.println ("Impresion postorden: ");
          abo.imprimirPost ();        
      }
      
      public boolean existe(Linea linea_ens,FileWriter archInst, int numeroLinea) {
        Nodo reco=raiz;
        try{
        
        while (reco!=null) {
            if (linea_ens.codop.equalsIgnoreCase(reco.codop)) {
            	
            	System.out.println(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+" ");
                		for(int i = 0;i<reco.modosdir.size();i++){
              	System.out.print(reco.modosdir.get(i));
              	if((i+1)!= reco.modosdir.size()){
              		System.out.print(", ");
              	}
			  }
			  System.out.println();
                		archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t");
                		for(int i = 0;i<reco.modosdir.size();i++){
                			archInst.write((String)reco.modosdir.get(i));
              				if((i+1)!= reco.modosdir.size()){
              					archInst.write(", ");
              				}
              			}
                			archInst.write("\r\n");
            	return true;
            }
            else{
            	
                if (linea_ens.codop.compareTo(reco.codop)>0)
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