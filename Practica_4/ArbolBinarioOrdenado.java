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
import java.io.File;

public class ArbolBinarioOrdenado {
    class Nodo {
      	//*INS/tieneonoope\mododedir/codmaqenhex/bytesporlasminus\sumabytes/bytesporlohex
      	String codop;
      	boolean existeoperando;
        ArrayList<String> modosdir = new ArrayList<String>();
        String codmaqenhex; 		// C�digo maquina en hexadecimal
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

          nuevo.izq = null;
          nuevo.der = null;

          if (raiz == null){
          	contenido = st.nextToken();
          	nuevo.codmaqenhex = contenido;		// C�digo maquina en hexadecimal
          	contenido = st.nextToken();
          	nuevo.bytesporlasminus = contenido; 	// Bytes por calcular
          	contenido = st.nextToken();
          	nuevo.sumabytes = contenido; 			// Suma total de bytes
          	contenido = st.nextToken();
          	nuevo.bytesporlohex = contenido; 		// Bytes calculados
          	raiz = nuevo;
          }

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
                    		reco=null;
                  	}

              }

              if (nuevo.codop.compareTo(anterior.codop) <0){
              	contenido = st.nextToken();
          		nuevo.codmaqenhex = contenido;		// C�digo maquina en hexadecimal
          		contenido = st.nextToken();
          		nuevo.bytesporlasminus = contenido; 	// Bytes por calcular
          		contenido = st.nextToken();
          		nuevo.sumabytes = contenido; 			// Suma total de bytes
          		contenido = st.nextToken();
          		nuevo.bytesporlohex = contenido; 		// Bytes calculados
              	anterior.izq = nuevo;
              }
              else
              	if (nuevo.codop.compareTo(anterior.codop)>0) {
              		contenido = st.nextToken();
          		  	nuevo.codmaqenhex = contenido;		// C�digo maquina en hexadecimal
          		  	contenido = st.nextToken();
          		  	nuevo.bytesporlasminus = contenido; 	// Bytes por calcular
          		  	contenido = st.nextToken();
          		  	nuevo.sumabytes = contenido; 			// Suma total de bytes
          		  	contenido = st.nextToken();
          		  	nuevo.bytesporlohex = contenido; 		// Bytes calculados
                  	anterior.der = nuevo;
              	}
                  else
                  	if(nuevo.codop.compareTo(anterior.codop)==0) {
                  		anterior.modosdir.add(contenido);
                  		contenido = st.nextToken();
          				nuevo.codmaqenhex = contenido;		// C�digo maquina en hexadecimal
          				contenido = st.nextToken();
          				nuevo.bytesporlasminus = contenido; 	// Bytes por calcular
          				contenido = st.nextToken();
          				nuevo.sumabytes = contenido; 			// Suma total de bytes
          				contenido = st.nextToken();
          				nuevo.bytesporlohex = contenido; 		// Bytes calculados
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

      public boolean existe(Linea linea_ens,FileWriter archInst, int numeroLinea, FileWriter archErr, ContadorDeLocalidades contlocalidades,ArbolEtiquetas arboletq,FileWriter archTABSIM) {
        Nodo reco=raiz;
        try{
        	while (reco!=null) {
        		if (linea_ens.codop.equalsIgnoreCase(reco.codop)) {
                	if (reco.existeoperando == true && !linea_ens.oper.equals("NULL")) {
                		System.out.println(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t");

                		//AQUI ME VOY A CHECAR OPERADOR
                		Operador oper = new Operador ();
                		oper.VerificarInicioDeOperador(linea_ens.oper,reco.modosdir,archErr,numeroLinea,linea_ens, archInst,reco.sumabytes,contlocalidades,arboletq,archTABSIM);
                		/*for(int i = 0;i<reco.modosdir.size();i++){
                			archInst.write((String)reco.modosdir.get(i));
                			if((i+1)!= reco.modosdir.size()){
                				archInst.write(", ");
              				}
              			}*/
                		//archInst.write("\r\n");
                	}
                	else
                		if (reco.existeoperando == true && linea_ens.oper.equals("NULL"))
                			linea_ens.guardarError(archErr,"ERROR la linea deberia de contener operando",numeroLinea);
                		else
                			if (reco.existeoperando == false && !linea_ens.oper.equals("NULL"))
                				linea_ens.guardarError(archErr,"ERROR El codigo de operacion no debe contener operando",numeroLinea);
                				else
                					if (reco.existeoperando == false && linea_ens.oper.equals("NULL")){
                						System.out.println(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t");
                						//AQUI ME VOY A CHECAR OPERADOR
                						Operador oper = new Operador ();
                						oper.VerificarInicioDeOperador(linea_ens.oper,reco.modosdir,archErr,numeroLinea,linea_ens, archInst,reco.sumabytes,contlocalidades,arboletq,archTABSIM);
                						//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t");
                						/*for(int i = 0;i<reco.modosdir.size();i++){
                							archInst.write((String)reco.modosdir.get(i));
                							if((i+1)!= reco.modosdir.size()){
                								archInst.write(", ");
              								}
              							}*/
                						//archInst.write("\r\n");
                					}
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