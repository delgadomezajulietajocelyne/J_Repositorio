/**
 * @(#)Arbol.java
 *
 *
 * @author Delgado Meza Julieta Jocelyne
 * @version 1.00 2013/9/13
 */

import java.util.*;
import java.io.*;
import java.util.StringTokenizer;
import java.io.FileWriter;
import java.io.File;

public class ArbolBinarioOrdenado {
    class Nodo {
      	//*INS/tieneonoope\mododedir/codmaqenhex/bytesporlasminus\sumabytes/bytesporlohex
      	String codop;
      	boolean existeoperando;
        ArrayList<String> modosdir = new ArrayList<String>();
        ArrayList<String> codmaqenhex = new ArrayList<String>(); 		// Código maquina en hexadecimal
        ArrayList<String> bytesporlasminus = new ArrayList<String>();  	// Bytes por calcular
        ArrayList<String> sumabytes = new ArrayList<String>(); 			// Suma total de bytes
        ArrayList<String> bytesporlohex = new ArrayList<String>();  		// Bytes calculados
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
          	nuevo.codmaqenhex.add(contenido);		// Código maquina en hexadecimal
          	contenido = st.nextToken();
          	nuevo.bytesporlasminus.add(contenido); 	// Bytes por calcular
          	contenido = st.nextToken();
          	nuevo.sumabytes.add(contenido); 			// Suma total de bytes
          	contenido = st.nextToken();
          	nuevo.bytesporlohex.add(contenido);		// Bytes calculados
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
          		nuevo.codmaqenhex.add(contenido);		// Código maquina en hexadecimal
          		contenido = st.nextToken();
          		nuevo.bytesporlasminus.add(contenido); 	// Bytes por calcular
          		contenido = st.nextToken();
          		nuevo.sumabytes.add(contenido); 			// Suma total de bytes
          		contenido = st.nextToken();
          		nuevo.bytesporlohex.add(contenido); 		// Bytes calculados
              	anterior.izq = nuevo;
              }
              else
              	if (nuevo.codop.compareTo(anterior.codop)>0) {
              		contenido = st.nextToken();
          		  	nuevo.codmaqenhex.add(contenido);		// Código maquina en hexadecimal
          		  	contenido = st.nextToken();
          		  	nuevo.bytesporlasminus.add(contenido); 	// Bytes por calcular
          		  	contenido = st.nextToken();
          		  	nuevo.sumabytes.add(contenido); 			// Suma total de bytes
          		  	contenido = st.nextToken();
          		  	nuevo.bytesporlohex.add(contenido);		// Bytes calculados
                  	anterior.der = nuevo;
              	}
                  else
                  	if(nuevo.codop.compareTo(anterior.codop)==0) {
                  		anterior.modosdir.add(contenido);
                  		contenido = st.nextToken();
          				anterior.codmaqenhex.add(contenido);		// Código maquina en hexadecimal
          				contenido = st.nextToken();
          				anterior.bytesporlasminus.add(contenido); 	// Bytes por calcular
          				contenido = st.nextToken();
          				anterior.sumabytes.add(contenido); 			// Suma total de bytes
          				contenido = st.nextToken();
          				anterior.bytesporlohex.add(contenido);		// Bytes calculados
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
      
      public boolean escribirCodMaq(String codigoOperacion,String mododir,ArbolBinarioOrdenado abo, FileWriter arch,String oper, ArbolEtiquetas arboletq) {
        Nodo reco=raiz;
        String codmaqenhex = "";
        int minusc = 0;
        try{
        	while (reco!=null) {
        		if (codigoOperacion.equalsIgnoreCase(reco.codop)) {
                	//return true;
                	System.out.println("Si esta buscando");
                		for(int posi = 0;posi<reco.modosdir.size();posi++){
							if(reco.modosdir.get(posi).equalsIgnoreCase(mododir)){
								System.out.println("Encontro");
								System.out.println(reco.modosdir.get(posi));
								codmaqenhex = reco.codmaqenhex.get(posi);
								
								System.out.println(reco.codmaqenhex.get(posi));
								minusc = Integer.parseInt(reco.bytesporlasminus.get(posi),10);
              				}
              			}
              			System.out.println("Hasta aqui va bien");
              			if(mododir.equalsIgnoreCase("INH") || mododir.equalsIgnoreCase("IMM")) {
              				arch.write("\t\t"+codmaqenhex+"\r\n");
              			}
              			else
              				if(mododir.equalsIgnoreCase("DIR")) {
              					System.out.println("ENTRO AQUI DIR");
              					System.out.println(oper);
              					System.out.println(oper);
              					String codDir = abo.valorEnHEx(oper,codmaqenhex,minusc);
              					codmaqenhex=codmaqenhex+ codDir;
              					arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");
              				}
              				else {
              					if(mododir.equalsIgnoreCase("EXT")) {
              						if (oper.matches ("^[a-zA-Z].*") && oper.matches("[a-zA-Z0-9_]+")) {
              							if(oper.length()<=8) {
              								System.out.println("ENTRO AQUI EXT CON ETQ");
              								System.out.println(oper);
              								System.out.println(oper);
              								String codExt = arboletq.extraerCLOCTABSIM(oper,arboletq);
              								codmaqenhex=codmaqenhex+ codExt;
              								arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");
              							}
              						}
              						else {
              							System.out.println("ENTRO AQUI EXT");
              							System.out.println(oper);
              							System.out.println(oper);
              							String codExt = abo.valorEnHEx(oper,codmaqenhex,minusc);
              							codmaqenhex=codmaqenhex+ codExt;
              							arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");
              						}
              					}
              					else {
              						if(mododir.equalsIgnoreCase("IMM8")) {
              							System.out.println("ENTRO AQUI IMM8");
	              						System.out.println(oper);
    		          					System.out.println(oper);
    		          					StringTokenizer opConvertir = new StringTokenizer(oper,"#",false);
    									oper = opConvertir.nextToken();
            		  					String codIMM8 = abo.valorEnHEx(oper,codmaqenhex,minusc);
              							codmaqenhex=codmaqenhex+ codIMM8;
              							arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");
              						}
              						else {
              							if(mododir.equalsIgnoreCase("IMM16")) {
              								System.out.println("ENTRO AQUI IMM16");
	    	          						System.out.println(oper);
    			          					System.out.println(minusc);
    		    	      					StringTokenizer opConvertir = new StringTokenizer(oper,"#",false);
    										oper = opConvertir.nextToken();
            		  						String codIMM16 = abo.valorEnHEx(oper,codmaqenhex,minusc);
              								codmaqenhex=codmaqenhex+ codIMM16;
              								arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");	
              							}
              							else {
              								arch.write("\r\n");
              							}
              							
              						}
              							
              					}
              				
              			}
              			return true;
            	}
            	else{
            		if (codigoOperacion.compareTo(reco.codop)>0)
                	    reco=reco.der;
                	else
                    	reco=reco.izq;
            	}

        	}
        	arch.close();
        }catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error al esc codmaq en arb: " + e.getMessage());
              }
        return false;
      }
      
      public String valorEnHEx(String oper, String codmaqenhex, int minus) {
      	System.out.println("ENTRO AQUI JAJAJ");
      	boolean resultado= oper.startsWith("$");
      	
		System.out.println(resultado);
		if(resultado == true){ //YA ENTRO
    		StringTokenizer opConvertir = new StringTokenizer(oper,"$",false);
    		oper = opConvertir.nextToken();
    		oper = PonerAXcaracteres(oper, minus);
    	}
    	else {
    				resultado= oper.startsWith("%");
    				StringTokenizer opConvertir = new StringTokenizer(oper,"%",false);
    				if (resultado) {
    					oper = opConvertir.nextToken();
    					int i = Integer.parseInt(oper,2);
       					oper = Integer.toHexString(i);
						oper = PonerAXcaracteres(oper, minus);
    				}
    				else {
    					resultado = oper.startsWith("@");
    					opConvertir = new StringTokenizer(oper,"@",false);
    					if (resultado) {
    						oper = opConvertir.nextToken();
    						int i = Integer.parseInt(oper,8);
       						oper = Integer.toHexString(i);
       						oper = PonerAXcaracteres(oper, minus);
    					}
    					else{
    						oper = oper;
    						int i = Integer.parseInt(oper);
       						oper = Integer.toHexString(i);
       						oper = PonerAXcaracteres(oper, minus);
    					}

    				}
    			}
    	return oper;
      }
      
      public String PonerAXcaracteres(String codmaqenhex, int cantminus) {
 		char [] cmaq = codmaqenhex.toCharArray();
 		System.out.println("ssssss"+codmaqenhex);
 		System.out.println("cantmins "+cantminus);
 		if (codmaqenhex.length()<(cantminus*2))
 			while (codmaqenhex.length()!= cantminus*2)
 				codmaqenhex="0"+codmaqenhex;
 		else {
 			System.out.println("en sino"+codmaqenhex);
 			int longi = codmaqenhex.length();
 			codmaqenhex="";
 			
 			System.out.println("longi "+longi);
 			for (int i = longi-1 ; i>longi-1-(cantminus*2) ; i--)
 				codmaqenhex = (new StringBuffer().append(cmaq[i])).toString()+codmaqenhex;
 			
 			if (codmaqenhex.length() == cantminus)
 				codmaqenhex=codmaqenhex;
 		}
 			
 		System.out.println("lo que regreso "+codmaqenhex);
 		return codmaqenhex;
 	}
}