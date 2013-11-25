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
        	archInst.close();
        	archErr.close();
        	archTABSIM.close();
        }catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
        return false;
      }
      
      public boolean escribirCodMaq(String codigoOperacion,String mododir,ArbolBinarioOrdenado abo, FileWriter arch,String oper, ArbolEtiquetas arboletq,String conloc, long point, long point2,RandomAccessFile archErr,RandomAccessFile archI,String numeroLinea) {
        Nodo reco=raiz;
        String codmaqenhex = "";
        int minusc = 0;
        int sumb = 0;
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
								sumb = Integer.parseInt(reco.sumabytes.get(posi),10);
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
              					String codDir = abo.valorEnHEx(oper,codmaqenhex,minusc,mododir);
              					codDir = PonerAXcaracteres(codDir, minusc);
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
              								codExt = PonerAXcaracteres(codExt, minusc);
              								codmaqenhex=codmaqenhex+ codExt;
              								arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");
              							}
              						}
              						else {
              							System.out.println("ENTRO AQUI EXT");
              							System.out.println(oper);
              							System.out.println(oper);
              							String codExt = abo.valorEnHEx(oper,codmaqenhex,minusc,mododir);
              							codExt = PonerAXcaracteres(codExt, minusc);
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
            		  					String codIMM8 = abo.valorEnHEx(oper,codmaqenhex,minusc,mododir);
            		  					codIMM8 = PonerAXcaracteres(codIMM8, minusc);
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
            		  						String codIMM16 = abo.valorEnHEx(oper,codmaqenhex,minusc,mododir);
            		  						codIMM16 = PonerAXcaracteres(codIMM16, minusc);
              								codmaqenhex=codmaqenhex+ codIMM16;
              								arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");	
              							}
              							else {
              								if(mododir.equalsIgnoreCase("IDX")) {
              									System.out.println("ENTRO AQUI IDX");
	    	          							System.out.println(oper);
    			          						System.out.println(minusc);
    		    	      						StringTokenizer opConvertir = new StringTokenizer(oper,",",false);
    		    	      						int canttokens = opConvertir.countTokens();
    		    	      						//oper = opConvertir.nextToken();
    		    	      						if  (canttokens!= 1)
    												oper = opConvertir.nextToken();
    												
    											String registro = opConvertir.nextToken();
    											System.out.println("REGISTRO: "+registro);
    											boolean espreopost = false;
    											if (registro.startsWith("+") || registro.startsWith("-") || registro.endsWith("+") || registro.endsWith("-"))
    												espreopost = true;
    											if(oper.equalsIgnoreCase("A") || oper.equalsIgnoreCase("B") || oper.equalsIgnoreCase("D")) {
    												String xb="";
            		  								String unos="111";
            		  								String rr = "";
            		  								String uno="1";
            		  								String aa = "";
            		  								if (registro.equalsIgnoreCase("X")) {
            		  									rr  = "00";
            		  								}
            		  								else {
            		  									if (registro.equalsIgnoreCase("Y")) {
            		  										rr  = "01";
            		  									}
            		  									else
            		  										if (registro.equalsIgnoreCase("SP")) {
            		  											rr  = "10";
            		  										}
            		  										else
            		  											if (registro.equalsIgnoreCase("PC")) {
            		  												rr  = "11";
            		  											}
            		  								}
            		  								if (oper.equalsIgnoreCase("A")) {
            		  									aa = "00";
            		  								}
            		  								else {
            		  									if (oper.equalsIgnoreCase("B")) {
            		  										aa = "01";
            		  									}
            		  									else
            		  										if (oper.equalsIgnoreCase("D")) {
            		  											aa = "10";
            		  										}
            		  								}
            		  								xb = unos+rr+uno+aa;
    												System.out.println("XB: "+xb);
    												int i = Integer.parseInt(xb,2);
    												xb = Integer.toHexString(i);
    												System.out.println("XB en hexa: "+xb);
    												xb = PonerAXcaracteres(xb, minusc);
              										codmaqenhex=codmaqenhex+ xb;
              										arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");
    											}
            		  							else {
            		  								if (espreopost == false) {
            		  									
            		  									String xb="";
            		  									String rr="";
            		  									String cero = "0";
            		  									String nnnnn="";
            		  									if (registro.equalsIgnoreCase("X")) {
            		  										rr  = "00";
            		  									}
            		  									else {
            		  										if (registro.equalsIgnoreCase("Y")) {
	            		  										rr  = "01";
    	        		  									}
        	    		  									else
            			  										if (registro.equalsIgnoreCase("SP")) {
            			  											rr  = "10";
            			  										}
            		  											else
            		  												if (registro.equalsIgnoreCase("PC")) {
            		  													rr  = "11";
            		  												}
            		  									}
            		  									if (canttokens != 1){
            		  										String codIDX = abo.valorEnHEx(oper,codmaqenhex,minusc,mododir);
    														int i = Integer.parseInt(codIDX,16);
       														String bin = Integer.toBinaryString(i);
      	 													if (bin.length() == 5) {
       															nnnnn = bin;
       														}
       														else {
       															if (bin.length() == 4) {
       																nnnnn = "0"+bin;
       															}
       															else
       																if (bin.length() == 3) {
       																	nnnnn = "00"+bin;
       																}
       																else
       																	if (bin.length() == 2) {
       																		nnnnn = "000"+bin;
       																	}
       																	else
       																		if (bin.length() == 1)
       																			nnnnn = "0000"+bin;
      	 																	else
       																			if(bin.length() > 5) {
       																				char [] conte = bin.toCharArray();														
 																					nnnnn=(new StringBuffer().append(conte[bin.length()-5])).toString()+ (new StringBuffer().append(conte[bin.length()-4])).toString() + (new StringBuffer().append(conte[bin.length()-3])).toString()+ (new StringBuffer().append(conte[bin.length()-2])).toString()+ (new StringBuffer().append(conte[bin.length()-1])).toString();
       																			}
       														}
       														
    													}
    													else {
    														nnnnn = "00000";
    													}
  		  												xb = rr+cero+nnnnn;
    													System.out.println("XB: "+xb);
    													int i = Integer.parseInt(xb,2);
    													xb = Integer.toHexString(i);
    													System.out.println("XB en hexa: "+xb);
    													xb = PonerAXcaracteres(xb, minusc);
              											codmaqenhex=codmaqenhex+ xb;
              											arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");			
            		  								}
            		  								else {
            		  									System.out.println("ENTRO A PRE POST");
            		  									String xb="";
            		  									String rr="";
            		  									String uno = "1";
            		  									String p = "";
            		  									String nnnn="";
            		  									if (registro.equalsIgnoreCase("+X") || registro.equalsIgnoreCase("-X") ||registro.equalsIgnoreCase("X+") || registro.equalsIgnoreCase("X-")) {
            		  									rr  = "00";
            		  									}
            		  									else {
            		  										if (registro.equalsIgnoreCase("+Y") || registro.equalsIgnoreCase("-Y") ||registro.equalsIgnoreCase("Y+") || registro.equalsIgnoreCase("Y-")) {
	            		  										rr  = "01";
    	        		  									}
        	    		  									else
            			  										if (registro.equalsIgnoreCase("+SP") || registro.equalsIgnoreCase("-SP") ||registro.equalsIgnoreCase("SP+") || registro.equalsIgnoreCase("SP-")) {
            			  											rr  = "10";
            			  										}
            		  											else
            		  												if (registro.equalsIgnoreCase("+PC") || registro.equalsIgnoreCase("-PC") ||registro.equalsIgnoreCase("PC+") || registro.equalsIgnoreCase("PC-")) {
            		  													rr  = "11";
            		  												}
            		  									}
            		  									if (registro.startsWith("+") || registro.startsWith("-"))
            		  										p = "0";
            		  									else {
            		  										if (registro.endsWith("+") || registro.endsWith("-"))
            		  											p = "1";
            		  									}			
            		  									if (registro.startsWith("+") || registro.endsWith("+")) {
            		  										String val = abo.valorEnHEx(oper,codmaqenhex,minusc,mododir);
            		  										int i = Integer.parseInt(val,16);
            		  										if(i==8)
            		  											nnnn="0111";
            		  										if(i==7)
            		  											nnnn="0110";
            		  										if(i==6)
            		  											nnnn="0101";
            		  										if(i==5)
            		  											nnnn="0100";
            		  										if(i==4)
            		  											nnnn="0011";
            		  										if(i==3)
            		  											nnnn="0010";
            		  										if(i==2)
            		  											nnnn="0001";
            		  										if(i==1)
            		  											nnnn="0000";					
            		  											/*int valorOperando = ValorReal(oper);
            		  											System.out.println("OPER: "+oper+"  VALOR REAL: "+valorOperando);
            		  											int val = Integer.parseInt(valorOperando,10);
            		  											if (val >0)*/
            		  									}
            		  									else {
            		  										if (registro.startsWith("-") || registro.endsWith("-")) {
            		  											String val = abo.valorEnHEx(oper,codmaqenhex,minusc,mododir);
            		  											System.out.println("VALOR PARA PREPOST hexa: "+val);
            		  											int i = Integer.parseInt(val,16);
            		  											System.out.println("VALOR PARA PREPOST: "+i);
            		  											if(i==8)
            		  												nnnn="1000";
            		  											if(i==7)
            		  												nnnn="1001";
            		  											if(i==6)
            		  												nnnn="1010";
            		  											if(i==5)
            		  												nnnn="1011";
            		 	 										if(i==4)
            		  												nnnn="1100";
            		  											if(i==3)
            		  												nnnn="1101";
            		  											if(i==2)
            		  												nnnn="1110";
            		  											if(i==1)
            		  												nnnn="1111";
            		  										}
            		  									}
            		  									xb = rr+uno+p+nnnn;
    													System.out.println("XB: "+xb);
    													int i = Integer.parseInt(xb,2);
    													xb = Integer.toHexString(i);
    													System.out.println("XB en hexa: "+xb);
    													xb = PonerAXcaracteres(xb, minusc);
              											codmaqenhex=codmaqenhex+ xb;
              											arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");           		  									
            		  								}
            		  							}
              								}
              								else{
              									if(mododir.equalsIgnoreCase("IDX1")) {
              										System.out.println("ENTRO AQUI IDX1");
	    	          								System.out.println(oper);
    			          							System.out.println(minusc);
    		    	      							StringTokenizer opConvertir = new StringTokenizer(oper,",",false);
    		    	      							int canttokens = opConvertir.countTokens();
    		    	      							//oper = opConvertir.nextToken();
    		    	      							if  (canttokens!= 1)
    													oper = opConvertir.nextToken();
    													
    												if (canttokens == 1)
    													oper = "0";
    												
    												String registro = opConvertir.nextToken();
    												System.out.println("REGISTRO: "+registro);
    												
              										String xb="";
            		  								String unos="111";
            		  								String rr = "";
            		  								String cero = "0";
            		  								String z = "0";
            		  								String s = "";
            		  								if (registro.equalsIgnoreCase("X")) {
            		  										rr  = "00";
            		  									}
            		  								else {
            		  									if (registro.equalsIgnoreCase("Y")) {
	            		  									rr  = "01";
    	        		  								}
        	    		  								else
            			  									if (registro.equalsIgnoreCase("SP")) {
            			  										rr  = "10";
            			  									}
            		  										else
            		  											if (registro.equalsIgnoreCase("PC")) {
            		  												rr  = "11";
            		  											}
            		  								}
            		  								int valor = ValorReal(oper,mododir);
            		  								System.out.println("VAL: "+valor);
            		  								//int val = Integer.parseInt(valor,16);
            		  								if (valor <0)
            		  									s = "1";
            		  								else
            		  									s = "0";
            		  								System.out.println("unos: "+unos);
            		  								System.out.println("rr: "+rr);
            		  								System.out.println("cero: "+cero);
            		  								System.out.println("z: "+z);
            		  								System.out.println("s: "+s);
            		  								xb = unos+rr+cero+z+s;
    												System.out.println("XB: "+xb);
    												int i = Integer.parseInt(xb,2);
    												xb = Integer.toHexString(i);
    												System.out.println("XB en hexa: "+xb);
    												int paraff = soloComplementoADos(oper,mododir);
    												String ff = Integer.toHexString(paraff);
    												System.out.println("ff: "+ff);
    												ff = PonerAXcaracteres(ff, 1);
              										codmaqenhex=codmaqenhex+ xb + ff;
              										arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");     
              									}
              									else {
              										if(mododir.equalsIgnoreCase("IDX2")) {
              											System.out.println("ENTRO AQUI IDX2");
	    	       		   								System.out.println(oper);
    			        	  							System.out.println(minusc);
    		    	      								StringTokenizer opConvertir = new StringTokenizer(oper,",",false);
    		    	      								int canttokens = opConvertir.countTokens();
    		    	      								//oper = opConvertir.nextToken();
    		    	      								if  (canttokens!= 1)
    														oper = opConvertir.nextToken();
    													
    													if (canttokens == 1)
    														oper = "0";
    												
    													String registro = opConvertir.nextToken();
    													System.out.println("REGISTRO: "+registro);
              											String xb ="";
              											String eeff = "";
              											String unos = "111";
              											String rr ="";
              											String cero ="0";
              											String z = "1";
              											String s = "";
              											if (registro.equalsIgnoreCase("X")) {
            		  										rr  = "00";
            		  									}
            		  									else {
            		  										if (registro.equalsIgnoreCase("Y")) {
	            		  										rr  = "01";
    	       		 		  								}
        	    			  								else
            				  									if (registro.equalsIgnoreCase("SP")) {
            			  											rr  = "10";
            			  										}
            		  											else
            		  												if (registro.equalsIgnoreCase("PC")) {
            		  													rr  = "11";
            		  												}
            		  									}
            		  									int valor = ValorReal(oper,mododir);
            		 	 								System.out.println("VAL: "+valor);
            		  									//int val = Integer.parseInt(valor,16);
            		  									if (valor <0)
            		  										s = "1";
            		  									else
            		  										s = "0";
            		  									System.out.println("unos: "+unos);
            		  									System.out.println("rr: "+rr);
            		  									System.out.println("cero: "+cero);
            			  								System.out.println("z: "+z);
            		  									System.out.println("s: "+s);
            		  									xb = unos+rr+cero+z+s;	
            		  									
            		  									System.out.println("XB: "+xb);
    													int i = Integer.parseInt(xb,2);
    													xb = Integer.toHexString(i);
    													System.out.println("XB en hexa: "+xb);
    													int paraeeff = soloComplementoADos(oper,mododir);
    													eeff = Integer.toHexString(paraeeff);
    													System.out.println("eeff: "+eeff);
    													eeff = PonerAXcaracteres(eeff, 2);
              											codmaqenhex=codmaqenhex+ xb + eeff;
              											System.out.println(codmaqenhex);
              											arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n"); 				
              										}
              										else {
              											if(mododir.equalsIgnoreCase("[IDX2]")) {
              												System.out.println("ENTRO AQUI [IDX2]");
		   	 		          								System.out.println(oper);
    						       							System.out.println(minusc);
    						       							StringTokenizer opConvertir = new StringTokenizer(oper,"[,]");
    														//String oper = opConvertir.nextToken();
    			    		   								//StringTokenizer opConvertir = new StringTokenizer(oper,",",false);
    		    		    	  							int canttokens = opConvertir.countTokens();
    		    		      								//oper = opConvertir.nextToken();
    		    	    	  								if  (canttokens!= 1)
    															oper = opConvertir.nextToken();
    															
    														if (canttokens == 1)
    															oper = "0";
    														
    														String registro = opConvertir.nextToken();
    														System.out.println("REGISTRO: "+registro);
    														
  	            											String xb="";
    	        		  									String unos="111";
        	    		  									String rr = "";
            				  									String ceroyunos = "011";
            		  										if (registro.equalsIgnoreCase("X")) {
            		  											rr  = "00";
            		  										}
            		 	 									else {
            		  											if (registro.equalsIgnoreCase("Y")) {
	            		  											rr  = "01";
    	        		  										}
        	    		  										else
            			  											if (registro.equalsIgnoreCase("SP")) {
            			  												rr  = "10";
            			  											}
            		  												else
            		  													if (registro.equalsIgnoreCase("PC")) {
            		  														rr  = "11";
            		  													}
            		  										}
            		  										xb = unos+rr+ceroyunos;	
            		  									
          	  			  									System.out.println("XB: "+xb);
    														int i = Integer.parseInt(xb,2);
    														xb = Integer.toHexString(i);
    														System.out.println("XB en hexa: "+xb);
    														int paraeeff = soloComplementoADos(oper,mododir);
    														String eeff = Integer.toHexString(paraeeff);
    														System.out.println("eeff: "+eeff);
    														eeff = PonerAXcaracteres(eeff, 2);
    	          											codmaqenhex=codmaqenhex+ xb + eeff;
        	      											System.out.println(codmaqenhex);
            	  											arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n"); 
              												
              											}
              											else {
              												if(mododir.equalsIgnoreCase("[D,IDX]")) {
              													System.out.println("ENTRO AQUI [D,IDX]");
		   	 		          									System.out.println(oper);
    						       								System.out.println(minusc);
    						       								StringTokenizer opConvertir = new StringTokenizer(oper,"[,]");
    						       								String operD = opConvertir.nextToken();
    						       								String registro = opConvertir.nextToken();
    															System.out.println("REGISTRO: "+registro);
    														
  	            												String xb="";
    	        		  										String unos="111";
        	    		  										String rr = "";
            		  											if (registro.equalsIgnoreCase("X")) {
           	 			  											rr  = "00";
            			  										}
            			 	 									else {
            		  												if (registro.equalsIgnoreCase("Y")) {
	            		  												rr  = "01";
    	        		  											}
        	    		  											else
            			  												if (registro.equalsIgnoreCase("SP")) {
            			  													rr  = "10";
            			  												}
            		  													else
            		  														if (registro.equalsIgnoreCase("PC")) {
            		  															rr  = "11";
            		  														}
            		  											}
            		  											xb = unos+rr+unos;	
          	  			  										System.out.println("XB: "+xb);
	    														int i = Integer.parseInt(xb,2);
    															xb = Integer.toHexString(i);
    															System.out.println("XB en hexa: "+xb);
    															xb = PonerAXcaracteres(xb, minusc);
              													codmaqenhex=codmaqenhex+ xb;
              													arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");    
              													
              												}
              												else {
              													if(mododir.equalsIgnoreCase("REL8")) {
              														System.out.println("COD MAQ REL8: "+codmaqenhex);
              														int desplazamiento;
              														if(oper.matches("^[a-zA-Z].*")) {
              															String dir;
              															dir= arboletq.extraerCLOCTABSIM(oper, arboletq);
              															int representacionNumerica = ValorReal("$"+dir,mododir);
              															System.out.println("representacionNumerica: "+representacionNumerica);
              															int valCONLOCSIG = Integer.parseInt(conloc,16) + sumb;
              															//int valCONLOCSIG = Integer.parseInt(cl,10);
              															//System.out.println("cl siguiente: "+cl);
              															System.out.println("valor cl siguiente: "+valCONLOCSIG);
              															desplazamiento = representacionNumerica - valCONLOCSIG;
              															System.out.println("desplazamiento: "+desplazamiento);
              															if (desplazamiento >= -128 && desplazamiento <= 127) {
              																String despEnHexa;
              																
              																//int i = Integer.parseInt(desplazamiento,10);
       																		despEnHexa = Integer.toHexString(desplazamiento);
       																		System.out.println("desplazamiento en HEXA: "+despEnHexa);
              												
       																		despEnHexa = PonerAXcaracteres(despEnHexa, minusc);
       																		codmaqenhex = codmaqenhex + despEnHexa;
       																		arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");
              																
              															}
              															else {
              																System.out.println("ERROR");
              																archI.seek(point);
    																		archI.writeBytes("*");
    																		archErr.writeBytes("Linea "+numeroLinea+": "+"ERROR Desplazamiento fuera de rango en REL8\r\n");
																			archI.seek(point2);
              															}
              														}
              														else {
              															/*String valenHexa;
              															valenHexa =abo.valorEnHEx(oper,codmaqenhex,minusc,mododir);
              															System.out.println("valor que tiene REL8 que no es etiqueta: "+valenHexa);*/
              															int representacionNumerica = ValorReal(oper,mododir);
              															System.out.println("representacionNumerica: "+representacionNumerica);
              															int valCONLOCSIG = Integer.parseInt(conloc,16) + sumb;
              															//int valCONLOCSIG = Integer.parseInt(cl,10);
              															//System.out.println("cl siguiente: "+cl);
              															System.out.println("valor cl siguiente: "+valCONLOCSIG);
              															desplazamiento = representacionNumerica - valCONLOCSIG;
              															System.out.println("desplazamiento: "+desplazamiento);
              															if (desplazamiento >= -128 && desplazamiento <= 127) {
              																String despEnHexa;
              																
              																//int i = Integer.parseInt(desplazamiento,10);
       																		despEnHexa = Integer.toHexString(desplazamiento);
       																		System.out.println("desplazamiento en HEXA: "+despEnHexa);
              															
       																		despEnHexa = PonerAXcaracteres(despEnHexa, minusc);
       																		codmaqenhex = codmaqenhex + despEnHexa;
       																		arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");
              																
              															}
              															else {
              																System.out.println("ERROR");
              																archI.seek(point);
    																		archI.writeBytes("*");
    																		archErr.writeBytes("Linea "+numeroLinea+": "+"ERROR Desplazamiento fuera de rango en REL8\r\n");
																			archI.seek(point2);
              															}
              														}
              															
              														
              													}
              													else {
              														if(mododir.equalsIgnoreCase("REL16")) {
              															System.out.println("COD MAQ REL16: "+codmaqenhex);
              															int desplazamiento;
              															if(oper.matches("^[a-zA-Z].*")) {
              																String dir;
              																dir= arboletq.extraerCLOCTABSIM(oper, arboletq);
              																int representacionNumerica = ValorReal("$"+dir,mododir);
              																System.out.println("representacionNumerica: "+representacionNumerica);
              																int valCONLOCSIG = Integer.parseInt(conloc,16) + sumb;
              																//int valCONLOCSIG = Integer.parseInt(cl,10);
              																//System.out.println("cl siguiente: "+cl);
              																System.out.println("valor cl siguiente: "+valCONLOCSIG);
              																desplazamiento = representacionNumerica - valCONLOCSIG;
              																System.out.println("desplazamiento: "+desplazamiento);
              																
              																
              																if (desplazamiento >= -32768 && desplazamiento <= 32767) {
              																	String despEnHexa;
              																	
              																	//int i = Integer.parseInt(desplazamiento,10);
       																			despEnHexa = Integer.toHexString(desplazamiento);
       																			System.out.println("desplazamiento en HEXA: "+despEnHexa);
              																
       																			despEnHexa = PonerAXcaracteres(despEnHexa, minusc);
       																			codmaqenhex = codmaqenhex + despEnHexa;
       																			arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");
              																	
              																}
              																else {
              																	System.out.println("ERROR");
              																	archI.seek(point);
    																			archI.writeBytes("*");
    																			archErr.writeBytes("Linea "+numeroLinea+": "+"ERROR Desplazamiento fuera de rango en REL16\r\n");
																				archI.seek(point2);
              																}
              															}
              															else {
              																int representacionNumerica = ValorReal(oper,mododir);
              																System.out.println("representacionNumerica: "+representacionNumerica);
              																int valCONLOCSIG = Integer.parseInt(conloc,16) + sumb;
              																//int valCONLOCSIG = Integer.parseInt(cl,10);
              																//System.out.println("cl siguiente: "+cl);
              																System.out.println("valor cl siguiente: "+valCONLOCSIG);
              																desplazamiento = representacionNumerica - valCONLOCSIG;
              																System.out.println("desplazamiento: "+desplazamiento);
              																
              																
              																if (desplazamiento >= -32768 && desplazamiento <= 32767) {
              																	String despEnHexa;
              																	
              																	//int i = Integer.parseInt(desplazamiento,10);
       																			despEnHexa = Integer.toHexString(desplazamiento);
       																			System.out.println("desplazamiento en HEXA: "+despEnHexa);
              																	
       																			despEnHexa = PonerAXcaracteres(despEnHexa, minusc);
       																			codmaqenhex = codmaqenhex + despEnHexa;
       																			arch.write("\t\t"+codmaqenhex.toUpperCase()+"\r\n");
              																	
              																}
              																else {
              																	System.out.println("ERROR");
              																	archI.seek(point);
    																			archI.writeBytes("*");
    																			archErr.writeBytes("Linea "+numeroLinea+": "+"ERROR Desplazamiento fuera de rango en REL16\r\n");
																				archI.seek(point2);
              																}
              															}
              															
              															
              														}
              														else
              															arch.write("\r\n");
              													}
              													
              												}
              											}
              										}
              									}
              									
              								}
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
        	archErr.close();
        	archI.close();
        }catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error al esc codmaq en arb: " + e.getMessage());
              }
        return false;
      }
      
      public String valorEnHEx(String oper, String codmaqenhex, int minus, String mododir) {
      	System.out.println("ENTRO AQUI JAJAJ");
      	boolean resultado= oper.startsWith("$");
      	System.out.println("ENTRO AQUI JAJAJ");
      	
		System.out.println(resultado);
		if(resultado == true){ //YA ENTRO
    		StringTokenizer opConvertir = new StringTokenizer(oper,"$",false);
    		oper = opConvertir.nextToken();
    		//oper = PonerAXcaracteres(oper, minus);
    	}
    	else {
    				resultado= oper.startsWith("%");
    				StringTokenizer opConvertir = new StringTokenizer(oper,"%",false);
    				if (resultado) {
    					oper = opConvertir.nextToken();
    					int i = Integer.parseInt(oper,2);
       					oper = Integer.toHexString(i);
						//oper = PonerAXcaracteres(oper, minus);
    				}
    				else {
    					resultado = oper.startsWith("@");
    					opConvertir = new StringTokenizer(oper,"@",false);
    					if (resultado) {
    						System.out.println("ENTRO AQUI OCTAL");
    						oper = opConvertir.nextToken();
    						System.out.println("ENTRO AQUI OCTAL");
    						int i = Integer.parseInt(oper,8);
    						System.out.println("ENTRO AQUI OCTAL");
       						oper = Integer.toHexString(i);
       						System.out.println("ENTRO AQUI OCTAL");
       						//oper = PonerAXcaracteres(oper, minus);
    					}
    					else{
    						resultado = oper.startsWith("+");
    						opConvertir = new StringTokenizer(oper,"+",false);
    						if (resultado){
    							oper = opConvertir.nextToken();
    							int i = Integer.parseInt(oper);
       							oper = Integer.toHexString(i);
       							//oper = PonerAXcaracteres(oper, minus);
    						}
    						else {
    							resultado = oper.startsWith("-");
    							opConvertir = new StringTokenizer(oper,"-",false);
    							if (resultado) {
    								oper = opConvertir.nextToken();
    								int i = Integer.parseInt(oper,10);
       								String bin = Integer.toBinaryString(i);
       								System.out.println("decimal a Binario: "+bin);
       								if (bin.length() == 5) {
       									bin = bin;
       								}
       								else {
       									if (bin.length() == 4) {
       										bin = "0"+bin;
       									}
       									else
       										if (bin.length() == 3) {
       											bin = "00"+bin;
       										}
       										else
       											if (bin.length() == 2) {
       												bin = "000"+bin;
       											}
       											else
       												if (bin.length() == 1)
       													bin = "0000"+bin;
       								}							       								
       								int encomplaDOS = ConversionAComplementoADos (bin, mododir);
       								oper = Integer.toHexString(encomplaDOS);
       								//oper = PonerAXcaracteres(oper, minus);
    							}
    							else {
    								oper = oper;
    								int i = Integer.parseInt(oper);
       								oper = Integer.toHexString(i);
       								//oper = PonerAXcaracteres(oper, minus);
    							}
    							
    						}
    						
    					}

    				}
    			}
    	return oper;
      }
      
     // public
      
      public int ConversionAComplementoADos (String binario, String mododir) {
		if (mododir.equalsIgnoreCase("IDX1")) {
    		if (binario.length() == 1)
    			binario = "0000000"+binario;
    		if (binario.length() == 2)
    			binario = "000000"+binario;
    		if (binario.length() == 3)
    			binario = "00000"+binario;
    		if (binario.length() == 4)
    			binario = "0000"+binario;
    		if (binario.length() == 5)
    			binario = "000"+binario;
    		if (binario.length() == 6)
    			binario = "00"+binario;
    		if (binario.length() == 7)
    			binario = "0"+binario;
    		if (binario.length() == 8)
    			binario = binario;
    	}
    	
    	System.out.println("binariooooo: "+binario);
    	char [] bin = binario.toCharArray();
 		for(int i = 0; i < binario.length(); i++) {
 			if (binario.charAt(i) == '1')
				bin[i] = '0';
			else
				bin[i] = '1';
		}
		System.out.println("binariooooo: "+binario);
		binario = new String(bin);
		System.out.println("NEGADO: "+binario);
		int i = Integer.parseInt(binario,2);
		i = i+1;
		System.out.println("Numero: "+i);
		binario = Integer.toBinaryString(i);
		System.out.println("Complemento a uno: "+binario);
		return i;
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
 	
 	public int ValorReal (String oper, String mododir) {
    	//String porsinohaygato = oper;

    	int resultadoc_2 = 0;
		//String contenido = new String (oper.nextToken());
		System.out.println("adsfadsf: "+oper);

		boolean resultado= oper.startsWith("$");
		System.out.println(resultado);

		if(resultado == true){ //YA ENTRO
    		StringTokenizer opConvertir = new StringTokenizer(oper,"$",false);
    		oper = opConvertir.nextToken();
    		if (oper.matches("[a-fA-F0-9_]+")){
    			System.out.println("AQUI");
    			System.out.println("HEXADECIMAL: "+oper);
    			int i = Integer.parseInt(oper,16);

      			if(oper.startsWith("F") || oper.matches("^0{0,}F.*") || oper.startsWith("f") || oper.matches("^0{0,}f.*")) {
      				
      				
      				char [] conte = oper.toCharArray();
      				
      				int j=0;
      				for (j = 0 ; (conte[j] != 'F' && conte[j] != 'f') ; )
      					j++;
 					String cc="";
      				for (j = j ; j<oper.length() ; j++)
      					cc = cc+(new StringBuffer().append(conte[j])).toString();
      					
      				System.out.println("CC: "+cc);
      				if(cc.length() > 2) {
      					String bin = Integer.toBinaryString(i);
      					resultadoc_2 = (ConversionAComplementoADos (bin,mododir)*-1);
      					System.out.println("Complemento a DOSSS: "+resultadoc_2);
      					return resultadoc_2;
      				}
      				else 
      					return i;
    			}
    			else {
      					return i;
    			}
    		}
    	}
    	else
    		if(oper.matches("^@.*")){
    			StringTokenizer opConvertir = new StringTokenizer(oper,"@",false);
    			oper = opConvertir.nextToken();
    			System.out.println("OCTAL: "+oper);

       			if (oper.startsWith("7") || oper.matches("^0{0,}7.*") && oper.matches("[0-7_]+")){
       				int i = Integer.parseInt(oper,8);
       				String bin = Integer.toBinaryString(i);
       				System.out.println("octal a Binario: "+bin);
       				resultadoc_2 = ConversionAComplementoADos (bin,mododir)*-1;
      				System.out.println("Complemento a DOSSS: "+resultadoc_2);
      				return resultadoc_2;
       			}
       			else {
       				if (oper.matches("[0-7_]+")){
       					int i = Integer.parseInt(oper,8);
       					return i;
       				}
       			}
      			//VerifInmediato(tipodireccionamiento,resultadoc_2,archErr,numeroLinea,linea_ens);
    		}
    		else
    			if(oper.matches("^%.*")){
    				System.out.println(oper);
    				StringTokenizer opConvertir = new StringTokenizer(oper,"%",false);
    				oper = opConvertir.nextToken();
    				if(oper.matches("[0-1_]+")){
    					if(oper.startsWith("1") && oper.length()>8) {
    						System.out.println("Binario: "+oper);
    						/*int i = Integer.parseInt(contenido,2);
    						String bin = Integer.toBinaryString(i);*/
    						resultadoc_2 = (ConversionAComplementoADos (oper,mododir))*-1;
      						System.out.println("Complemento a DOSSS: "+resultadoc_2);
      						return resultadoc_2;
    					}
    					else {
    						if(oper.startsWith("0")) {
    							System.out.println("Binario: "+oper);
    							int i = Integer.parseInt(oper,2);
    							//String bin = Integer.toBinaryString(i);
    							//resultadoc_2 = ConversionAComplementoADos (bin);
      							//System.out.println("Complemento a DOSSS: "+resultadoc_2);
      							return i;
    						}
    						else {
    							System.out.println("Binario: "+oper);
    							int i = Integer.parseInt(oper,2);
    							return i;
    						}
    					}
    				}
    			}
    			else {

    				boolean tienesigno= oper.startsWith("+");
					System.out.println(tienesigno);

					if(tienesigno == true){ //signo positivo
    					StringTokenizer opConvertir = new StringTokenizer(oper,"+",false);
    					String contenidespudesigno = opConvertir.nextToken();
    					System.out.println("Decimal: "+oper);
    					if (contenidespudesigno.matches("[0-9_]+")){
    						int i = Integer.parseInt(oper,10);
    						System.out.println("DECIMAL NUMERO: "+i);
    						return i;
      						//VerifInmediato(tipodireccionamiento,i,archErr,numeroLinea,linea_ens);
    					}
    				}
    				else{
    					tienesigno= oper.startsWith("-");
						System.out.println(tienesigno);
						if(tienesigno == true){ //signo negativo
    						StringTokenizer opConvertir = new StringTokenizer(oper,"-",false);
    						String contenidespudesigno = opConvertir.nextToken();
    						System.out.println("Decimal: "+oper);
    						if (contenidespudesigno.matches("[0-9_]+")){
    							int i = Integer.parseInt(oper,10);
    							System.out.println("DECIMAL NUMERO: "+i);
    							return i;
      							//VerifInmediato(tipodireccionamiento,i,archErr,numeroLinea,linea_ens);
    						}
    					}
    					else {
    						if (oper.matches("[0-9_]+")){
    							int i = Integer.parseInt(oper,10);
    							System.out.println("DECIMAL NUMERO: "+i);
    							return i;
      							//VerifInmediato(tipodireccionamiento,i,archErr,numeroLinea,linea_ens);
    						}
    					}
    				}
    			}

    	return 65536;
    }
 	
 	
 	
 	public int soloComplementoADos (String oper,String mododir) {
    	//String porsinohaygato = oper;

    	int resultadoc_2 = 0;
		//String contenido = new String (oper.nextToken());
		System.out.println("adsfadsf: "+oper);

		boolean resultado= oper.startsWith("$");
		System.out.println(resultado);

		if(resultado == true){ //YA ENTRO
    		StringTokenizer opConvertir = new StringTokenizer(oper,"$",false);
    		oper = opConvertir.nextToken();
    		if (oper.matches("[a-fA-F0-9_]+")){
    			System.out.println("AQUI");
    			System.out.println("HEXADECIMAL: "+oper);
    			int i = Integer.parseInt(oper,16);

      			if(oper.startsWith("F") || oper.matches("^0{0,}F.*") || oper.startsWith("f") || oper.matches("^0{0,}f.*")) {
      				
      				
      				char [] conte = oper.toCharArray();
      				
      				int j=0;
      				for (j = 0 ; (conte[j] != 'F' && conte[j] != 'f') ; )
      					j++;
 					String cc="";
      				for (j = j ; j<oper.length() ; j++)
      					cc = cc+(new StringBuffer().append(conte[j])).toString();
      					
      				System.out.println("CC: "+cc);
      				if(cc.length() > 2 && !mododir.equalsIgnoreCase("[IDX2]")) {
      					String bin = Integer.toBinaryString(i);
      					resultadoc_2 = (ConversionAComplementoADos (bin,mododir));
      					System.out.println("Complemento a DOSSS: "+resultadoc_2);
      					return resultadoc_2;
      				}
      				if(cc.length() > 4 && mododir.equalsIgnoreCase("[IDX2]")) {
      					String bin = Integer.toBinaryString(i);
      					resultadoc_2 = (ConversionAComplementoADos (bin,mododir));
      					System.out.println("Complemento a DOSSS: "+resultadoc_2);
      					return resultadoc_2;
      				}
      				else 
      					return i;
    			}
    			else {
      					return i;
    			}
    		}
    	}
    	else
    		if(oper.matches("^@.*")){
    			StringTokenizer opConvertir = new StringTokenizer(oper,"@",false);
    			oper = opConvertir.nextToken();
    			System.out.println("OCTAL: "+oper);

       			if (oper.startsWith("7") || oper.matches("^0{0,}7.*") && oper.matches("[0-7_]+")){
       				int i = Integer.parseInt(oper,8);
       				String bin = Integer.toBinaryString(i);
       				System.out.println("octal a Binario: "+bin);
       				resultadoc_2 = ConversionAComplementoADos (bin,mododir);
      				System.out.println("Complemento a DOSSS: "+resultadoc_2);
      				return resultadoc_2;
       			}
       			else {
       				if (oper.matches("[0-7_]+")){
       					int i = Integer.parseInt(oper,8);
       					return i;
       				}
       			}
      			//VerifInmediato(tipodireccionamiento,resultadoc_2,archErr,numeroLinea,linea_ens);
    		}
    		else
    			if(oper.matches("^%.*")){
    				System.out.println(oper);
    				StringTokenizer opConvertir = new StringTokenizer(oper,"%",false);
    				oper = opConvertir.nextToken();
    				if(oper.matches("[0-1_]+")){
    					if(oper.startsWith("1") && oper.length()>8) {
    						System.out.println("Binario: "+oper);
    						/*int i = Integer.parseInt(contenido,2);
    						String bin = Integer.toBinaryString(i);*/
    						resultadoc_2 = (ConversionAComplementoADos (oper,mododir));
      						System.out.println("Complemento a DOSSS: "+resultadoc_2);
      						return resultadoc_2;
    					}
    					else {
    						if(oper.startsWith("0")) {
    							System.out.println("Binario: "+oper);
    							int i = Integer.parseInt(oper,2);
    							//String bin = Integer.toBinaryString(i);
    							//resultadoc_2 = ConversionAComplementoADos (bin);
      							//System.out.println("Complemento a DOSSS: "+resultadoc_2);
      							return i;
    						}
    						else {
    							System.out.println("Binario: "+oper);
    							int i = Integer.parseInt(oper,2);
    							return i;
    						}
    					}
    				}
    			}
    			else {

    				boolean tienesigno= oper.startsWith("+");
					System.out.println(tienesigno);

					if(tienesigno == true){ //signo positivo
    					StringTokenizer opConvertir = new StringTokenizer(oper,"+",false);
    					String contenidespudesigno = opConvertir.nextToken();
    					System.out.println("Decimal: "+oper);
    					if (contenidespudesigno.matches("[0-9_]+")){
    						int i = Integer.parseInt(oper,10);
    						System.out.println("DECIMAL NUMERO: "+i);
    						return i;
      						//VerifInmediato(tipodireccionamiento,i,archErr,numeroLinea,linea_ens);
    					}
    				}
    				else{
    					tienesigno= oper.startsWith("-");
						System.out.println(tienesigno);
						if(tienesigno == true){ //signo negativo
    						StringTokenizer opConvertir = new StringTokenizer(oper,"-",false);
    						String contenidespudesigno = opConvertir.nextToken();
    						System.out.println("Decimal: "+oper);
    						if (contenidespudesigno.matches("[0-9_]+")){
    							int i = Integer.parseInt(contenidespudesigno,10);
       							String bin = Integer.toBinaryString(i);
       							//char [] conte = bin.toCharArray();														
 								//bin=(new StringBuffer().append(conte[bin.length()-9])).toString()+(new StringBuffer().append(conte[bin.length()-8])).toString()+(new StringBuffer().append(conte[bin.length()-7])).toString()+(new StringBuffer().append(conte[bin.length()-6])).toString()+(new StringBuffer().append(conte[bin.length()-5])).toString()+ (new StringBuffer().append(conte[bin.length()-4])).toString() + (new StringBuffer().append(conte[bin.length()-3])).toString()+ (new StringBuffer().append(conte[bin.length()-2])).toString()+ (new StringBuffer().append(conte[bin.length()-1])).toString();
       							System.out.println("decimal a Binario: "+bin);
       							resultadoc_2 = ConversionAComplementoADos (bin,mododir);
    							System.out.println("DECIMAL NUMERO comp a 2: "+i);
    							return resultadoc_2;
      							//VerifInmediato(tipodireccionamiento,i,archErr,numeroLinea,linea_ens);
    						}
    					}
    					else {
    						if (oper.matches("[0-9_]+")){
    							int i = Integer.parseInt(oper,10);
    							System.out.println("DECIMAL NUMERO: "+i);
    							return i;
      							//VerifInmediato(tipodireccionamiento,i,archErr,numeroLinea,linea_ens);
    						}
    					}
    				}
    			}

    	return 65536;
    }
 	
}