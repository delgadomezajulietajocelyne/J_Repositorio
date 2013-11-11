/**
 * @(#)Operador.java
 * Validar de acuerdo al operando el modo de direccionamiento
 *
 * @author Delgado Meza Julieta Jocelyne
 * @version 1.00 2013/9/21
 */

import java.util.*;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileWriter;
import java.io.*;

public class Operador {

    //String operador;

    public Operador () {
    	//operador = op;
    }

    public void VerificarInicioDeOperador(String oper, ArrayList<String> modosdir, FileWriter archErr, int numeroLinea, Linea linea_ens,FileWriter archInst,ArrayList<String> sumab,ContadorDeLocalidades contlocalidades,ArbolEtiquetas arboletq,FileWriter archTABSIM) {
    	int convdecimal;
    	String inherente = "NULL";

    	String sumadebytes="";

    	/*if(oper.matches("^@.*")){
    		System.out.println("Octal @");
    	}
    	else*/
    	try {

    		for(int i = 0;i<modosdir.size();i++){
    			//archInst.write((String)reco.modosdir.get(i));
                if(modosdir.get(i).equals("INH")){
                	inherente = "INH";
                	i=modosdir.size();
              	}
    		}
            if (inherente.equals("INH") && !oper.equals("NULL")) {
				for(int posi = 0;posi<modosdir.size();posi++){
					if(modosdir.get(posi).equalsIgnoreCase("INH")){
						sumadebytes = sumab.get(posi);
              		}
              	}
    			if (oper.matches ("^[a-zA-Z].*") && oper.matches("[a-zA-Z0-9_]+")){
    				if(oper.length()<=8){
    					System.out.println("Oper etiqueta");
    					try{
    						System.out.println("CONLOC: "+contlocalidades.conloc);
    						if (linea_ens.etq.equalsIgnoreCase("NULL")) {
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"INH"+"\r\n");

    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else {
    							boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    							if (existeEtq == false) {
    								archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"INH"+"\r\n");

        							EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        							int sumbytes = Integer.parseInt(sumadebytes,10);
    								int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    								contlocalidades.conloc = Integer.toHexString(i);
    								contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    								contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    							}
    							else
    								linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    						}

    						//calcular conloc sig.

    						//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");

    					}
    					catch (Exception e){ //Catch de excepciones
    						System.err.println("Ocurrio un error: " + e.getMessage());
              			}
    				}
    				else {
    					System.out.println ("ERROR Operando Etiqueta fuera de longitud");
    					linea_ens.guardarError(archErr,"ERROR Operando Etiqueta fuera de longitud",numeroLinea);
    				}
    			}
    			else {
    				StringTokenizer opConvertir = new StringTokenizer(oper,"",false);
    				convdecimal = Conversion (opConvertir,archErr,numeroLinea,linea_ens,"INH",false);

    				if (convdecimal != 65536){
    					if (oper.matches ("^[a-zA-Z].*") && oper.matches("[a-zA-Z0-9_]+")){
    						if(oper.length()<=8){
    							System.out.println("Oper etiqueta");
    							try{
    								System.out.println("CONLOC: "+contlocalidades.conloc);

    								if (linea_ens.etq.equalsIgnoreCase("NULL")) {
    									archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"INH"+"\r\n");
    										//calcular conloc sig.
    									int sumbytes = Integer.parseInt(sumadebytes,10);
    									int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    									contlocalidades.conloc = Integer.toHexString(i);
    									contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    									contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    								}
    								else {
    									boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    									if (existeEtq == false) {
    											archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"INH"+"\r\n");

        									EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        									//calcular conloc sig.
    										int sumbytes = Integer.parseInt(sumadebytes,10);
    										int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    										contlocalidades.conloc = Integer.toHexString(i);
    										contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    										contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    									}
    									else
    										linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    								}


    								//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");
    							}
    							catch (Exception e){ //Catch de excepciones
    								System.err.println("Ocurrio un error: " + e.getMessage());
              					}
    						}
    						else {
    							System.out.println ("ERROR Operando Etiqueta fuera de longitud");
    							linea_ens.guardarError(archErr,"ERROR Operando Etiqueta fuera de longitud",numeroLinea);
    						}
    					}
    					if (convdecimal >= -256 && convdecimal <=255){
      						System.out.println ("Es INH");

      						System.out.println("CONLOC: "+contlocalidades.conloc);

    						if (linea_ens.etq.equalsIgnoreCase("NULL")) {
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"INH"+"\r\n");
    							//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else {
    								boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    								if (existeEtq == false) {
    										archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"INH"+"\r\n");

        								EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        								//calcular conloc sig.
    									int sumbytes = Integer.parseInt(sumadebytes,10);
    									int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    									contlocalidades.conloc = Integer.toHexString(i);
    									contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    									contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    								}
    								else
    									linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    							}



      						//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"INH"+"\r\n");
      					}
      					else {
      							System.out.println ("ERROR en operando para INH");
    							linea_ens.guardarError(archErr,"ERROR en operando para INH",numeroLinea);
    					}

    				}
    			}
            }
            else
    		if (oper.equals("NULL")){
    				for(int i = 0;i<modosdir.size();i++){
                			if (modosdir.get(i).equals("INH")) {
                				for(int posi = 0;posi<modosdir.size();posi++){
									if(modosdir.get(posi).equalsIgnoreCase("INH")){
										sumadebytes = sumab.get(posi);
              						}
              					}
                				System.out.println("INH");
                				System.out.println("CONLOC: "+contlocalidades.conloc);

    							if (linea_ens.etq.equalsIgnoreCase("NULL")) {
    								archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"INH"+"\r\n");
    										//calcular conloc sig.
    								int sumbytes = Integer.parseInt(sumadebytes,10);
    								int ii = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    								contlocalidades.conloc = Integer.toHexString(ii);
    								contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    								contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    							}
    							else {
    									boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    									if (existeEtq == false) {
    											archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"INH"+"\r\n");

        									EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        									//calcular conloc sig.
    										int sumbytes = Integer.parseInt(sumadebytes,10);
    										int ii = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    										contlocalidades.conloc = Integer.toHexString(ii);
    										contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    										contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    									}
    									else
    										linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    								}


                				//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"INH"+"\r\n");
                			}
                			else
                				if (modosdir.get(i).equals("IMM")) {
                					for(int posi = 0;posi<modosdir.size();posi++){
										if(modosdir.get(posi).equalsIgnoreCase("IMM")){
											sumadebytes = sumab.get(posi);
              							}
              						}
                					System.out.println("IMM");
                					System.out.println("CONLOC: "+contlocalidades.conloc);

    								if (linea_ens.etq.equalsIgnoreCase("NULL")) {
    									archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"IMM"+"\r\n");
    										//calcular conloc sig.
    									int sumbytes = Integer.parseInt(sumadebytes,10);
    									int ii = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    									contlocalidades.conloc = Integer.toHexString(ii);
    									contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    									contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    								}
    								else {
    									boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    									if (existeEtq == false) {
    											archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"IMM"+"\r\n");

        									EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        									//calcular conloc sig.
    										int sumbytes = Integer.parseInt(sumadebytes,10);
    										int ii = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    										contlocalidades.conloc = Integer.toHexString(ii);
    										contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    										contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    									}
    									else
    										linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    								}


                					//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"IMM"+"\r\n");
                				}
                				else
                					if (modosdir.get(i).equals("IDX")) {
                						for(int posi = 0;posi<modosdir.size();posi++){
											if(modosdir.get(posi).equalsIgnoreCase("IDX")){
												sumadebytes = sumab.get(posi);
              								}
              							}
                						System.out.println("IDX");

                						System.out.println("CONLOC: "+contlocalidades.conloc);

    									if (linea_ens.etq.equalsIgnoreCase("NULL")) {
    										archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"IDX"+"\r\n");
    										//calcular conloc sig.
    										int sumbytes = Integer.parseInt(sumadebytes,10);
    										int ii = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    										contlocalidades.conloc = Integer.toHexString(ii);
    										contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    										contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    									}
    									else {
    										boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    										if (existeEtq == false) {
    												archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"IDX"+"\r\n");

        										EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        										//calcular conloc sig.
    											int sumbytes = Integer.parseInt(sumadebytes,10);
    											int ii = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    											contlocalidades.conloc = Integer.toHexString(ii);
    											contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    											contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    										}
    										else
    											linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    									}





                						//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"IDX"+"\r\n");
                					}
                					else
                						System.out.println("Hay caramba es otro");

    				}
    		}
    		else{
    			System.out.println("SIIIIIIIIII");
    		String relativo = new String();
    		for(int i = 0;i<modosdir.size();i++){
    			//archInst.write((String)reco.modosdir.get(i));
                if(modosdir.get(i).equals("REL8")){
                	relativo = "REL8";
                	i=modosdir.size();
              	}
    		}

    		if (relativo.equals("REL8")){
    			for(int posi = 0;posi<modosdir.size();posi++){
					if(modosdir.get(posi).equalsIgnoreCase("REL8")){
						sumadebytes = sumab.get(posi);
              		}
              	}

    			if (oper.matches ("^[a-zA-Z].*") && oper.matches("[a-zA-Z0-9_]+")){
    				if(oper.length()<=8){
    					System.out.println("Oper etiqueta");
    					try{
    						System.out.println("CONLOC: "+contlocalidades.conloc);

    						if (linea_ens.etq.equalsIgnoreCase("NULL")) {
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"REL8"+"\r\n");
    										//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else {
    									boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    									if (existeEtq == false) {
    											archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"REL8"+"\r\n");

        									EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        									//calcular conloc sig.
    										int sumbytes = Integer.parseInt(sumadebytes,10);
    										int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    										contlocalidades.conloc = Integer.toHexString(i);
    										contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    										contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    									}
    									else
    										linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    						}


    						//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");
    					}
    					catch (Exception e){ //Catch de excepciones
    						System.err.println("Ocurrio un error: " + e.getMessage());
              			}
    				}
    				else {
    					System.out.println ("ERROR Operando Etiqueta fuera de longitud");
    					linea_ens.guardarError(archErr,"ERROR Operando Etiqueta fuera de longitud",numeroLinea);
    				}
    			}
    			else {
    				StringTokenizer opConvertir = new StringTokenizer(oper,"",false);
    				convdecimal = Conversion (opConvertir,archErr,numeroLinea,linea_ens,relativo,false);

    				if (convdecimal != 65536){
    					VerifRel8(relativo,convdecimal,archErr,numeroLinea,linea_ens,archInst,sumab,contlocalidades,arboletq,archTABSIM,modosdir);
    				}
    			}

    		}
    		else{
    			for(int i = 0;i<modosdir.size();i++){
    				//archInst.write((String)reco.modosdir.get(i));
                	if(modosdir.get(i).equals("REL9")){
                		relativo = "REL9";
                		i=modosdir.size();
              		}
    			}
    			if (relativo.equals("REL9")){
    				for(int posi = 0;posi<modosdir.size();posi++){
						if(modosdir.get(posi).equalsIgnoreCase("REL9")){
							sumadebytes = sumab.get(posi);
              			}
              		}
    				if (oper.matches ("^[a-zA-Z].*") && oper.matches("[a-zA-Z0-9_]+")){
    					if(oper.length()<=8){
    						System.out.println("Oper etiqueta");
    						try{
    							System.out.println("CONLOC: "+contlocalidades.conloc);

    							if (linea_ens.etq.equalsIgnoreCase("NULL")) {
    								archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"REL9"+"\r\n");
    										//calcular conloc sig.
    								int sumbytes = Integer.parseInt(sumadebytes,10);
    								int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    								contlocalidades.conloc = Integer.toHexString(i);
    								contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    								contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    							}
    							else {
    									boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    									if (existeEtq == false) {
    											archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"REL9"+"\r\n");

        									EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        									//calcular conloc sig.
    										int sumbytes = Integer.parseInt(sumadebytes,10);
    										int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    										contlocalidades.conloc = Integer.toHexString(i);
    										contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    										contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    									}
    									else
    										linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    							}


    							//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");
    						}
    						catch (Exception e){ //Catch de excepciones
    							System.err.println("Ocurrio un error: " + e.getMessage());
              				}
    					}
    					else {
    						System.out.println ("ERROR Operando Etiqueta fuera de longitud");
    						linea_ens.guardarError(archErr,"ERROR Operando Etiqueta fuera de longitud",numeroLinea);
    					}
    				}
    				else {
    					StringTokenizer opConvertir = new StringTokenizer(oper,"",false);
    					convdecimal = Conversion (opConvertir,archErr,numeroLinea,linea_ens,relativo,false);

    					if (convdecimal != 65536){
    						VerifRel9(relativo,convdecimal,archErr,numeroLinea,linea_ens,archInst,sumab,contlocalidades,arboletq,archTABSIM,modosdir);
    					}
    				}
    			}
    			else {
    				for(int i = 0;i<modosdir.size();i++){
                		//archInst.write((String)reco.modosdir.get(i));
                		if(modosdir.get(i).equals("REL16")){
                			relativo = "REL16";
                			i=modosdir.size();
              			}
    				}

    				if (relativo.equals("REL16")){
    					for(int posi = 0;posi<modosdir.size();posi++){
							if(modosdir.get(posi).equalsIgnoreCase("REL16")){
								sumadebytes = sumab.get(posi);
              				}
              			}

    					if (oper.matches ("^[a-zA-Z].*") && oper.matches("[a-zA-Z0-9_]+")){
    						if(oper.length()<=8){
    							System.out.println("Oper etiqueta");
    							try{
    								System.out.println("CONLOC: "+contlocalidades.conloc);


    								if (linea_ens.etq.equalsIgnoreCase("NULL")) {
    										archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"REL16"+"\r\n");
    										//calcular conloc sig.
    										int sumbytes = Integer.parseInt(sumadebytes,10);
    										int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    										contlocalidades.conloc = Integer.toHexString(i);
    										contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    										contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    								}
    								else {
    									boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    									if (existeEtq == false) {
    											archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"REL16"+"\r\n");

        									EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        									//calcular conloc sig.
    										int sumbytes = Integer.parseInt(sumadebytes,10);
    										int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    										contlocalidades.conloc = Integer.toHexString(i);
    										contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    										contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    									}
    									else
    										linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    								}


    								//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");
    							}
    							catch (Exception e){ //Catch de excepciones
    								System.err.println("Ocurrio un error: " + e.getMessage());
              					}
    						}
    						else {
    							System.out.println ("ERROR Operando Etiqueta fuera de longitud");
    							linea_ens.guardarError(archErr,"ERROR Operando Etiqueta fuera de longitud",numeroLinea);
    						}
    					}
    					else {
    						StringTokenizer opConvertir = new StringTokenizer(oper,"",false);
    						convdecimal = Conversion (opConvertir,archErr,numeroLinea,linea_ens,relativo,false);

    						if (convdecimal != 65536){
    							VerifRel16(relativo,convdecimal,archErr,numeroLinea,linea_ens,archInst,sumab,contlocalidades,arboletq,archTABSIM,modosdir);
    						}
    						else {
      							System.out.println ("ERROR Operando fuera de rango para modo "+relativo);
    							linea_ens.guardarError(archErr,"ERROR Operando fuera de rango para modo "+relativo,numeroLinea);
      						}
    					}

    				}
    				else{
    				if(oper.matches("^#.*")){
    					System.out.println("MODO INMEDIATO #");
    					StringTokenizer opConvertir = new StringTokenizer(oper,"#",false);
    					String Inmediato = new String();
    					for(int i = 0;i<modosdir.size();i++){
                			//archInst.write((String)reco.modosdir.get(i));
                			if(modosdir.get(i).equals("IMM8")){
                				Inmediato = "IMM8";
                				i=modosdir.size();
              				}
              				else {
              					if(modosdir.get(i).equals("IMM16")){
                					Inmediato = "IMM16";
                					i=modosdir.size();
              					}
              					/*else{
              						System.out.println("COMETI ALGUN ERROR");
              					}*/
              				}
    					}
    					if(Inmediato.equals("IMM8") || Inmediato.equals("IMM16")) {
    						convdecimal = Conversion (opConvertir,archErr,numeroLinea,linea_ens, Inmediato,false);
    						if (convdecimal != 65536)
    							VerifInmediato(Inmediato,convdecimal,archErr,numeroLinea,linea_ens,archInst,sumab,contlocalidades,arboletq,archTABSIM,modosdir);
    					}
    					else {
    								System.out.println ("ERROR El codigo de operacion no contiene direccionamiento Inmediato");
    								linea_ens.guardarError(archErr,"ERROR El codigo de operacion no contiene direccionamiento Inmediato",numeroLinea);
    					}

    				}
    				else{
    					System.out.println();
    					System.out.println();
    					System.out.println();
    					System.out.println(oper);

    					int comas = contarComas(oper,',');
    					System.out.println("COMAS: "+comas);

    					if (comas == 0 && !oper.startsWith("[")){
    						boolean dir = false, ext = false, ambos = false;
    						String directo = new String();
    						String extendido = new String();
    						for(int i = 0;i<modosdir.size();i++){
                				//archInst.write((String)reco.modosdir.get(i));
                				if(modosdir.get(i).equals("DIR")){
                					directo = "DIR";
                					dir = true;
                				//i=modosdir.size();
              					}
              					if(modosdir.get(i).equals("EXT")){
                					extendido = "EXT";
                					ext = true;
                					//i=modosdir.size();
              					}
              					if(dir == true && ext == true) {
              						ambos =true;
              					}
    						}
    						StringTokenizer opConvertir = new StringTokenizer(oper,"",false);
    						StringTokenizer opConvertirrr = new StringTokenizer(oper,"",false);
    						String loquetiene = opConvertirrr.nextToken();
    						boolean hx = loquetiene.startsWith("$");
    						if (ambos == false && dir == true){
    							if(loquetiene.matches("^@{1,}.*") || loquetiene.matches("^%{1,}.*") || (hx==true)  || loquetiene.matches("[0-9_]+"))
    							{
    								System.out.println("HER1");
    								convdecimal = Conversion (opConvertir,archErr,numeroLinea,linea_ens,directo,false);
									if (convdecimal != 65536)
										VerifDirecto(directo,convdecimal,archErr,numeroLinea,linea_ens,archInst,sumab,contlocalidades,arboletq,archTABSIM,modosdir);
    							}
    							else {
    								System.out.println ("ERROR Formato de operando inválido para ningun modo de direccionamiento");
    								linea_ens.guardarError(archErr,"ERROR Formato de operando inválido para ningun modo de direccionamiento",numeroLinea);
    							}
    						}
    						else {
    							if (ambos == false && ext == true){
    								if (oper.matches ("^[a-zA-Z].*") && oper.matches("[a-zA-Z0-9_]+")){
    									if(oper.length()<=8){
    										System.out.println("Oper etiqueta");
    										try{
    											System.out.println("CONLOC: "+contlocalidades.conloc);
    											for(int posi = 0;posi<modosdir.size();posi++){
													if(modosdir.get(posi).equalsIgnoreCase("EXT")){
														sumadebytes = sumab.get(posi);
              										}
              									}

    								if (linea_ens.etq.equalsIgnoreCase("NULL")) {
    									archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");
    									//calcular conloc sig.
    									int sumbytes = Integer.parseInt(sumadebytes,10);
    									int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    									contlocalidades.conloc = Integer.toHexString(i);
    									contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    									contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    								}

    								else {
    									boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    									if (existeEtq == false) {
    											archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");

        									EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        									//calcular conloc sig.
    										int sumbytes = Integer.parseInt(sumadebytes,10);
    										int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    										contlocalidades.conloc = Integer.toHexString(i);
    										contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    										contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    									}
    									else
    										linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    								}


    											//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");
    										}
    										catch (Exception e){ //Catch de excepciones
            									System.err.println("Ocurrio un error: " + e.getMessage());
              								}
    									}
    									else {
    										System.out.println ("ERROR Operando Etiqueta fuera de rango");
    										linea_ens.guardarError(archErr,"ERROR Operando Etiqueta fuera de rango",numeroLinea);
    									}

    								}
    								else {
    									if(loquetiene.matches("^@{1,}.*") || loquetiene.matches("^%{1,}.*") || (hx==true) || loquetiene.matches("[0-9_]+")){
    										System.out.println("HER2");
    										convdecimal = Conversion (opConvertir,archErr,numeroLinea,linea_ens,extendido,false);
    										if (convdecimal != 65536)
												VerifExtendido(extendido,convdecimal,archErr,numeroLinea,linea_ens,archInst,sumab,contlocalidades,arboletq,archTABSIM,modosdir);

    									}
    									else {
    										System.out.println ("ERROR Formato de operando inválido para ningun modo de direccionamiento");
    										linea_ens.guardarError(archErr,"ERROR Formato de operando inválido para ningun modo de direccionamiento",numeroLinea);
    									}
    									//else
											//linea_ens.guardarError(archErr,"ERROR Formato de operador incorrecto para "+"EXT",numeroLinea);
    								}

    							}
    							else {
    								if (ambos == true){
    									if (oper.matches ("^[a-zA-Z].*") && oper.matches("[a-zA-Z0-9_]+")){
    										if(oper.length()<=8){
    											System.out.println("Oper etiqueta");
    											try{
    												System.out.println("CONLOC: "+contlocalidades.conloc);
    												for(int posi = 0;posi<modosdir.size();posi++){
														if(modosdir.get(posi).equalsIgnoreCase("EXT")){
															sumadebytes = sumab.get(posi);
              											}
              										}
    												if (linea_ens.etq.equalsIgnoreCase("NULL")) {
    													archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");
    													//calcular conloc sig.
    													int sumbytes = Integer.parseInt(sumadebytes,10);
    													int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    													contlocalidades.conloc = Integer.toHexString(i);
    													contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    													contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    												}
    												else {
    													boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    													if (existeEtq == false) {
    															archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");

        														EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        														//calcular conloc sig.
    															int sumbytes = Integer.parseInt(sumadebytes,10);
    															int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    															contlocalidades.conloc = Integer.toHexString(i);
    															contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    															contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    													}
    													else
    															linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    												}


    												//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");
    											}
    											catch (Exception e){ //Catch de excepciones
            										System.err.println("Ocurrio un error: " + e.getMessage());
              									}
    										}
    										else {
    											System.out.println ("ERROR Operando Etiqueta fuera de longitud");
    											linea_ens.guardarError(archErr,"ERROR Operando Etiqueta fuera de longitud",numeroLinea);
    										}

    									}
    									else {
    										if(loquetiene.matches("^@{1,}.*") || loquetiene.matches("^%{1,}.*") || (hx==true) || loquetiene.matches("[0-9_]+")){
    											System.out.println("HER3");
    											convdecimal = Conversion (opConvertir,archErr,numeroLinea,linea_ens,extendido,true);
    											if (convdecimal != 65536)
													VerifDIRYEXT(extendido,convdecimal,archErr,numeroLinea,linea_ens,archInst,sumab,contlocalidades,arboletq,archTABSIM,modosdir);
												else
													linea_ens.guardarError(archErr,"ERROR Formato de operador incorrecto para "+"EXT",numeroLinea);
    										}
    										else {
    											System.out.println ("ERROR Formato de operando inválido para ningun modo de direccionamiento");
    											linea_ens.guardarError(archErr,"ERROR Formato de operando inválido para ningun modo de direccionamiento",numeroLinea);
    										}

    									}
    								}
    							}
    						}
    					}
    					else {
    						String Indexado = new String();
    						/*for(int i = 0;i<modosdir.size();i++){
                				//archInst.write((String)reco.modosdir.get(i));
                				if(modosdir.get(i).equals("IDX")){
                					Indexado = "IDX";
                					i=modosdir.size();
              					}
    						}*/
    						convdecimal=0;
    						if (comas >= 1 && !oper.startsWith("[") && !oper.endsWith("]")){
    							for(int i = 0;i<modosdir.size();i++){
                					//archInst.write((String)reco.modosdir.get(i));
                					if(modosdir.get(i).equals("IDX")){
                						Indexado = "IDX";
                						i=modosdir.size();
              						}
    							}
    							System.out.println("Aquí entro");
    							if (comas == 1){
    								StringTokenizer opConvertir = new StringTokenizer(oper,",",false);
    								int canttokens = opConvertir.countTokens();
    								if (canttokens == 1)
    									convdecimal = 0;
    								if (canttokens == 0){
    									linea_ens.guardarError(archErr,"ERROR Formato de operando inválido para ningun modo de direccionamiento",numeroLinea);
    								}

    								System.out.println("CANTIDAD DE TOKENS: "+canttokens);

    								StringTokenizer opConvertir2 = new StringTokenizer(oper,",",false);
    								String porsiempiezaconacum = opConvertir2.nextToken();

    								if (porsiempiezaconacum.equalsIgnoreCase("a") || porsiempiezaconacum.equalsIgnoreCase("b") || porsiempiezaconacum.equalsIgnoreCase("d")){
    									String registro = opConvertir2.nextToken();
    									System.out.println(registro);
    									VerifIDXAcum(Indexado,0,archErr,numeroLinea,linea_ens,archInst,registro,sumab,contlocalidades,arboletq,archTABSIM,modosdir);
    								}

    								else {

    									if (canttokens != 1){
    										convdecimal = Conversion (opConvertir,archErr,numeroLinea,linea_ens,Indexado,false);
    									}

    									String registro = opConvertir.nextToken();
    									System.out.println(registro);
    									if (convdecimal != 65536 && ((convdecimal>= -16 && convdecimal <=15) || ((registro.equalsIgnoreCase("-x") || registro.equalsIgnoreCase("+x") || registro.equalsIgnoreCase("x-") || registro.equalsIgnoreCase("x+") || registro.equalsIgnoreCase("-y") || registro.equalsIgnoreCase("+y") || registro.equalsIgnoreCase("y-") || registro.equalsIgnoreCase("y+") || registro.equalsIgnoreCase("-sp") || registro.equalsIgnoreCase("+sp") || registro.equalsIgnoreCase("sp-") || registro.equalsIgnoreCase("sp+")))) && Indexado.equals("IDX")){
    										//Indexado = "IDX";
    										VerifIDX(Indexado,convdecimal,archErr,numeroLinea,linea_ens,archInst,registro,sumab,contlocalidades,arboletq,archTABSIM,modosdir);
    									}
    									else{
    										for(int i = 0;i<modosdir.size();i++){
                								//archInst.write((String)reco.modosdir.get(i));
                								if(modosdir.get(i).equals("IDX1")){
                									Indexado = "IDX1";
                									i=modosdir.size();
              									}
    										}
    										if (convdecimal != 65536 && (convdecimal>= -256 && convdecimal <=255) && Indexado.equals("IDX1") && !oper.startsWith("[") && !oper.endsWith("[")){
    											//Indexado = "IDX1";
    											VerifIDX1(Indexado,convdecimal,archErr,numeroLinea,linea_ens,archInst,registro,sumab,contlocalidades,arboletq,archTABSIM,modosdir);
    										}
    										else {
    											for(int i = 0;i<modosdir.size();i++){
                									//archInst.write((String)reco.modosdir.get(i));
                									if(modosdir.get(i).equals("IDX2")){
                										Indexado = "IDX2";
                										i=modosdir.size();
              										}
    											}
    											if (convdecimal != 65536 && (convdecimal>= 0 && convdecimal <=65535) && Indexado.equals("IDX2")){
    												//Indexado = "IDX2";
    												VerifIDX2(Indexado,convdecimal,archErr,numeroLinea,linea_ens,archInst,registro,sumab,contlocalidades,arboletq,archTABSIM,modosdir);
    											}
    											/*else {
    												System.out.println ("-----ERROR Operando fuera de rango para "+"IDX");
    												linea_ens.guardarError(archErr,"ERROR Operando fuera de rango para "+"IDX",numeroLinea);
    											}*/
    										}
    									}
    								}

    								/*String cc = opConvertir.nextToken();
    								System.out.println(cc);*/
    							}
    							else {
    								System.out.println ("ERROR Formato de operando inválido para ningun modo de direccionamiento");
    								linea_ens.guardarError(archErr,"ERROR Formato de operando inválido para ningun modo de direccionamiento",numeroLinea);
    							}

    						}
    						else {
    							System.out.println ("CON CONRCHETES");
    							if (comas==1 && oper.startsWith("[") && oper.endsWith("]")){
    									for(int i = 0;i<modosdir.size();i++){
                						//archInst.write((String)reco.modosdir.get(i));
                						if(modosdir.get(i).equals("[IDX2]")){
                							Indexado = "[IDX2]";
                							i=modosdir.size();
              							}
    								}
    								StringTokenizer opConvertirparaversitieneaD = new StringTokenizer(oper,"[,]");
    								String tieneaD = opConvertirparaversitieneaD.nextToken();
    								System.out.println(tieneaD);
    								if(tieneaD.equalsIgnoreCase("D")){
    									for(int i = 0;i<modosdir.size();i++){
                							//archInst.write((String)reco.modosdir.get(i));
                							if(modosdir.get(i).equals("[D,IDX]")){
                								Indexado = "[D,IDX]";
                								i=modosdir.size();
              								}
    									}
    									System.out.println("HII");
    									String registro = opConvertirparaversitieneaD.nextToken();
    									IndizadoDeAcumuladorD(Indexado,convdecimal,archErr,numeroLinea,linea_ens,archInst,registro,sumab,contlocalidades,arboletq,archTABSIM,modosdir);
    								}
    								else {
    									StringTokenizer opConvertir = new StringTokenizer(oper,"[,]");
    									convdecimal = Conversion (opConvertir,archErr,numeroLinea,linea_ens,Indexado,false);
    									System.out.println("CONVERSIONNNN: "+convdecimal);
    									String registro = opConvertir.nextToken();
    									System.out.println(registro);
    									Indirecto16bits(Indexado,convdecimal,archErr,numeroLinea,linea_ens,archInst,registro,sumab,contlocalidades,arboletq,archTABSIM,modosdir);
    								}


    							}
    							else{


    								System.out.println ("ERROR Formato de operando inválido para ningun modo de direccionamiento");
    								linea_ens.guardarError(archErr,"ERROR Formato de operando inválido para ningun modo de direccionamiento",numeroLinea);

    							}

    						}
    					}

    				}


    					/*if(oper.matches("^$.*")){
    						System.out.println("MODO DIRECTO //Hexadecimal $");
    					}
    					else
    						if(oper.matches("^%afdadsfsdf.*")){
    							System.out.println("Binasdfasdfasdfsadfario %");
    						}*/
    			}
    			}
    		}
    		}

    	}
    	catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }

    }

    public int contarComas(String cadena, char caracter){
		int veces=0;
		int i;
		for (i=0;i<cadena.length();i++){
			if (cadena.charAt(i)==caracter){
				veces++;
			}
		}
		return veces;
	}

    public int Conversion (StringTokenizer oper, FileWriter archErr,int numeroLinea, Linea linea_ens, String tipodireccionamiento, boolean tienediryext) {
    	//String porsinohaygato = oper;

    	int resultadoc_2 = 0;
		String contenido = new String (oper.nextToken());
		System.out.println("adsfadsf: "+contenido);

		boolean resultado= contenido.startsWith("$");
		System.out.println(resultado);

		if(resultado == true){ //YA ENTRO
    		StringTokenizer opConvertir = new StringTokenizer(contenido,"$",false);
    		contenido = opConvertir.nextToken();
    		if (contenido.matches("[a-fA-F0-9_]+")){
    			System.out.println("AQUI");
    			System.out.println("HEXADECIMAL: "+contenido);
    			int i = Integer.parseInt(contenido,16);

      			if(contenido.startsWith("F") || contenido.startsWith("f")) {


      				char [] conte = contenido.toCharArray();

      				int j=0;
      				for (j = 0 ; (conte[j] != 'F' && conte[j] != 'f') ; )
      					j++;
 					String cc="";
      				for (j = j ; j<contenido.length() ; j++)
      					cc = cc+(new StringBuffer().append(conte[j])).toString();

      				System.out.println("CC: "+cc);
      				if(cc.length() > 1) {
      					String bin = Integer.toBinaryString(i);
      					resultadoc_2 = (ConversionAComplementoADos (bin)*-1);
      					System.out.println("Complemento a DOSSS: "+resultadoc_2);
      					return resultadoc_2;
      				}
      				else
      					return i;
    			}
    			else {
    					//System.out.println("Binario: "+contenido);
    					//String bin = Integer.toBinaryString(i);
    					//resultadoc_2 = ConversionAComplementoADos (bin);
      					//System.out.println("Complemento a DOSSS: "+resultadoc_2);
      					return i;

    			}
    		}
    		else {
    			if(!tienediryext)
    				linea_ens.guardarError(archErr,"ERROR Formato de operador incorrecto en hexadecimal para "+tipodireccionamiento,numeroLinea);
    		}
    	}
    	else
    		if(contenido.matches("^@.*")){
    			StringTokenizer opConvertir = new StringTokenizer(contenido,"@",false);
    			contenido = opConvertir.nextToken();
    			System.out.println("OCTAL: "+contenido);

       			if (contenido.startsWith("7") || contenido.matches("^0{0,}7.*") && contenido.matches("[0-7_]+")){
       				int i = Integer.parseInt(contenido,8);
       				String bin = Integer.toBinaryString(i);
       				System.out.println("octal a Binario: "+bin);
       				resultadoc_2 = ConversionAComplementoADos (bin)*-1;
      				System.out.println("Complemento a DOSSS: "+resultadoc_2);
      				return resultadoc_2;
       			}
       			else {
       				if (contenido.matches("[0-7_]+")){
       					int i = Integer.parseInt(contenido,8);
       					return i;
       				}

       				else {
       					if(!tienediryext) {
    						linea_ens.guardarError(archErr,"ERROR Formato de operador incorrecto en octal para "+tipodireccionamiento,numeroLinea);
    					}
       				}
       			}
      			//VerifInmediato(tipodireccionamiento,resultadoc_2,archErr,numeroLinea,linea_ens);
    		}
    		else
    			if(contenido.matches("^%.*")){
    				System.out.println(contenido);
    				StringTokenizer opConvertir = new StringTokenizer(contenido,"%",false);
    				contenido = opConvertir.nextToken();
    				if(contenido.matches("[0-1_]+")){
    					if(contenido.startsWith("1") && contenido.length()>8) {
    						System.out.println("Binario: "+contenido);
    						/*int i = Integer.parseInt(contenido,2);
    						String bin = Integer.toBinaryString(i);*/
    						resultadoc_2 = (ConversionAComplementoADos (contenido))*-1;
      						System.out.println("Complemento a DOSSS: "+resultadoc_2);
      						return resultadoc_2;
    					}
    					else {
    						if(contenido.startsWith("0")) {
    							System.out.println("Binario: "+contenido);
    							int i = Integer.parseInt(contenido,2);
    							//String bin = Integer.toBinaryString(i);
    							//resultadoc_2 = ConversionAComplementoADos (bin);
      							//System.out.println("Complemento a DOSSS: "+resultadoc_2);
      							return i;
    						}
    						else {
    							System.out.println("Binario: "+contenido);
    							int i = Integer.parseInt(contenido,2);
    							return i;
    						}
    					}
      				//	VerifInmediato(tipodireccionamiento,resultadoc_2,archErr,numeroLinea,linea_ens);
    				}
    				else {
    					if(!tienediryext) {
    						linea_ens.guardarError(archErr,"ERROR Formato de operador incorrecto en binario para "+tipodireccionamiento,numeroLinea);
    					}
    				}
    			}
    			else {

    				boolean tienesigno= contenido.startsWith("+");
					System.out.println(tienesigno);

					if(tienesigno == true){ //signo positivo
    					StringTokenizer opConvertir = new StringTokenizer(contenido,"+",false);
    					String contenidespudesigno = opConvertir.nextToken();
    					System.out.println("Decimal: "+contenido);
    					if (contenidespudesigno.matches("[0-9_]+")){
    						int i = Integer.parseInt(contenido,10);
    						System.out.println("DECIMAL NUMERO: "+i);
    						return i;
      						//VerifInmediato(tipodireccionamiento,i,archErr,numeroLinea,linea_ens);
    					}
    					else {
    						if(!tienediryext){
    							System.out.println ("ERROR Formato de operando incorrecto para "+tipodireccionamiento);
    							linea_ens.guardarError(archErr,"ERROR Formato de operando incorrecto para "+tipodireccionamiento,numeroLinea);
    						}
    					}
    				}
    				else{
    					tienesigno= contenido.startsWith("-");
						System.out.println(tienesigno);
						if(tienesigno == true){ //signo negativo
    						StringTokenizer opConvertir = new StringTokenizer(contenido,"-",false);
    						String contenidespudesigno = opConvertir.nextToken();
    						System.out.println("Decimal: "+contenido);
    						if (contenidespudesigno.matches("[0-9_]+")){
    							int i = Integer.parseInt(contenido,10);
    							System.out.println("DECIMAL NUMERO: "+i);
    							return i;
      							//VerifInmediato(tipodireccionamiento,i,archErr,numeroLinea,linea_ens);
    						}
    						else {
    							if(!tienediryext) {
    								System.out.println ("ERROR Formato de operando incorrecto para "+tipodireccionamiento);
    								linea_ens.guardarError(archErr,"ERROR Formato de operando incorrecto para "+tipodireccionamiento,numeroLinea);
    							}

    						}
    					}
    					else {
    						if (contenido.matches("[0-9_]+")){
    							int i = Integer.parseInt(contenido,10);
    							System.out.println("DECIMAL NUMERO: "+i);
    							return i;
      							//VerifInmediato(tipodireccionamiento,i,archErr,numeroLinea,linea_ens);
    						}
    						else {
    							if(!tienediryext) {
    								System.out.println ("ERROR Formato de operando incorrecto para "+tipodireccionamiento);
    								linea_ens.guardarError(archErr,"ERROR Formato de operando incorrecto para "+tipodireccionamiento,numeroLinea);
    							}
    						}
    					}
    				}
    			}

    	return 65536;
    }

    public int ConversionAComplementoADos (String binario) {
    	char [] bin = binario.toCharArray();
 		for(int i = 0; i < binario.length(); i++) {
 			if (binario.charAt(i) == '1')
				bin[i] = '0';
			else
				bin[i] = '1';
		}
		binario = new String(bin);
		System.out.println("NEGADO: "+binario);
		int i = Integer.parseInt(binario,2)+1;
		System.out.println("Numero: "+i);
		binario = Integer.toBinaryString(i);
		System.out.println("Complemento a uno en oper: "+binario);
		return i;
 	}

 	public void VerifInmediato(String tipoinmediato,int resultadoc_2,FileWriter archErr,int numeroLinea, Linea linea_ens,FileWriter archInst,ArrayList<String> sumab,ContadorDeLocalidades contlocalidades,ArbolEtiquetas arboletq, FileWriter archTABSIM, ArrayList<String> modosdir){
 		String sumadebytes="";
 		try{
 			System.out.println("ENTERO: "+resultadoc_2);
 			System.out.println("ENTERO: ");
 			if (tipoinmediato.equals("IMM8")) {
      				if (resultadoc_2 >= -256 && resultadoc_2 <=255){
      					System.out.println ("Es inmediato 8");
      					System.out.println("CONLOC: "+contlocalidades.conloc);
    					for(int posi = 0;posi<modosdir.size();posi++){
							if(modosdir.get(posi).equalsIgnoreCase("IMM8")){
								sumadebytes = sumab.get(posi);
              				}
              			}

    					if (linea_ens.etq.equalsIgnoreCase("NULL")){
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+tipoinmediato+"\r\n");
    							//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    					}
    					else {
    						boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    						if (existeEtq == false) {
    								archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+tipoinmediato+"\r\n");

        							EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        							//calcular conloc sig.
    								int sumbytes = Integer.parseInt(sumadebytes,10);
    								int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    								contlocalidades.conloc = Integer.toHexString(i);
    								contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    								contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else
    							linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    					}


      					//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+tipoinmediato+"\r\n");
      				}
      				else {
      						System.out.println ("ERROR Operando fuera de rango para IMM8");
    						linea_ens.guardarError(archErr,"ERROR Operando fuera de rango para IMM8",numeroLinea);
    				}
      		}
      		else
      			if (tipoinmediato.equals("IMM16")) {
      				if (resultadoc_2 >= -32768 && resultadoc_2 <=65535){
      					System.out.println ("Es inmediato 16");
      					System.out.println("CONLOC: "+contlocalidades.conloc);
      					for(int posi = 0;posi<modosdir.size();posi++){
							if(modosdir.get(posi).equalsIgnoreCase("IMM16")){
								sumadebytes = sumab.get(posi);
              				}
              			}
      					if (linea_ens.etq.equalsIgnoreCase("NULL")){
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+tipoinmediato+"\r\n");
    							//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    					}
    					else {
    						boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    						if (existeEtq == false) {
    								archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+tipoinmediato+"\r\n");

        							EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        							//calcular conloc sig.
    								int sumbytes = Integer.parseInt(sumadebytes,10);
    								int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    								contlocalidades.conloc = Integer.toHexString(i);
    								contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    								contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else
    							linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    					}


      					//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+tipoinmediato+"\r\n");
      				}
      				else {
      					System.out.println ("ERROR Operando fuera de rango para IMM16");
    					linea_ens.guardarError(archErr,"ERROR Operando fuera de rango para IMM16",numeroLinea);
    				}
      			}
 		}
 		catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
 	}


 	public void VerifDirecto(String directo,int resultadoc_2,FileWriter archErr,int numeroLinea, Linea linea_ens,FileWriter archInst,ArrayList<String> sumab,ContadorDeLocalidades contlocalidades,ArbolEtiquetas arboletq,FileWriter archTABSIM, ArrayList<String> modosdir){
 		String sumadebytes="";
 		try{
 			System.out.println("ENTERO: "+resultadoc_2);
 			System.out.println("ENTERO: ");
 			if (directo.equals("DIR")) {
      				if (resultadoc_2 >= 0 && resultadoc_2 <=255){
      					System.out.println ("Es Directo");
      					System.out.println("CONLOC: "+contlocalidades.conloc);
      					for(int posi = 0;posi<modosdir.size();posi++){
							if(modosdir.get(posi).equalsIgnoreCase("DIR")){
								sumadebytes = sumab.get(posi);
              				}
              			}
      					if (linea_ens.etq.equalsIgnoreCase("NULL")){
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+directo+"\r\n");
    							//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    					}
    					else {
    						boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    						if (existeEtq == false) {
    								archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+directo+"\r\n");

        							EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        							//calcular conloc sig.
    								int sumbytes = Integer.parseInt(sumadebytes,10);
    								int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    								contlocalidades.conloc = Integer.toHexString(i);
    								contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    								contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else
    							linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    					}


      					//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+directo+"\r\n");
      				}
      				else {
      						System.out.println ("ERROR Operando fuera de rango para modo "+directo);
    						linea_ens.guardarError(archErr,"ERROR Operando fuera de rango para modo "+directo,numeroLinea);
    				}
      		}
 		}
 		catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
 	}

 	public void VerifExtendido(String extendido,int resultadoc_2,FileWriter archErr,int numeroLinea, Linea linea_ens,FileWriter archInst,ArrayList<String> sumab,ContadorDeLocalidades contlocalidades,ArbolEtiquetas arboletq,FileWriter archTABSIM, ArrayList<String> modosdir){
 		String sumadebytes="";
 		try{
 			System.out.println("ENTERO: "+resultadoc_2);
 			System.out.println("ENTERO: ");
 			if (extendido.equals("EXT")) {
      				if (resultadoc_2 >= -32768 && resultadoc_2 <=65535){
      						System.out.println ("Es Extendido");
      						System.out.println("CONLOC: "+contlocalidades.conloc);
      						for(int posi = 0;posi<modosdir.size();posi++){
							if(modosdir.get(posi).equalsIgnoreCase("EXT")){
								sumadebytes = sumab.get(posi);
              				}
              			}
      						if (linea_ens.etq.equalsIgnoreCase("NULL")){
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+extendido+"\r\n");
    							//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else {
    							boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    							if (existeEtq == false) {
    									archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+extendido+"\r\n");

        								EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        								//calcular conloc sig.
    									int sumbytes = Integer.parseInt(sumadebytes,10);
    									int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    									contlocalidades.conloc = Integer.toHexString(i);
    									contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    									contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    							}
    							else
    								linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    						}


      						//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");
      				}
      				else {
      						System.out.println ("ERROR Operando fuera de rango para modo "+extendido);
    						linea_ens.guardarError(archErr,"ERROR Operando fuera de rango para modo "+extendido,numeroLinea);
      				}
      		}
 		}
 		catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
 	}

 	public void VerifDIRYEXT(String extendido,int resultadoc_2,FileWriter archErr,int numeroLinea, Linea linea_ens,FileWriter archInst,ArrayList<String> sumab,ContadorDeLocalidades contlocalidades,ArbolEtiquetas arboletq,FileWriter archTABSIM, ArrayList<String> modosdir){
 		String sumadebytes="";
 		try{
 			System.out.println("ENTERO: "+resultadoc_2);
 			System.out.println("ENTERO: ");
      				if (resultadoc_2 >= 0 && resultadoc_2 <=255){
      					System.out.println ("Es Directo");
      					System.out.println("CONLOC: "+contlocalidades.conloc);
      					for(int posi = 0;posi<modosdir.size();posi++){
							if(modosdir.get(posi).equalsIgnoreCase("DIR")){
								sumadebytes = sumab.get(posi);
              				}
              			}
      					if (linea_ens.etq.equalsIgnoreCase("NULL")){
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"DIR"+"\r\n");
    							//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    					}
    					else {
    						boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    						if (existeEtq == false) {
    								archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"DIR"+"\r\n");

        							EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        							//calcular conloc sig.
    								int sumbytes = Integer.parseInt(sumadebytes,10);
    								int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    								contlocalidades.conloc = Integer.toHexString(i);
    								contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    								contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else
    							linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    					}

      					//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"DIR"+"\r\n");
      				}
      				else {
      					if (resultadoc_2 >= -32768 && resultadoc_2 <=65535){
      						System.out.println ("Es Extendido");
      						System.out.println("CONLOC: "+contlocalidades.conloc);
      						for(int posi = 0;posi<modosdir.size();posi++){
								if(modosdir.get(posi).equalsIgnoreCase("EXT")){
									sumadebytes = sumab.get(posi);
              					}
              				}
      						if (linea_ens.etq.equalsIgnoreCase("NULL")){
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");
    							//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else {
    							boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    							if (existeEtq == false) {
    									archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");

        								EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        								//calcular conloc sig.
    									int sumbytes = Integer.parseInt(sumadebytes,10);
    									int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    									contlocalidades.conloc = Integer.toHexString(i);
    									contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    									contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    							}
    							else
    								linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    						}

      						//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");
      					}
      					else {
      						System.out.println ("ERROR Operando fuera de rango para modo "+extendido);
    						linea_ens.guardarError(archErr,"ERROR Operando fuera de rango para modo "+extendido,numeroLinea);
      					}
      						//
    				}

 		}
 		catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
 	}

 	public void VerifIDX(String idx,int resultadoc_2,FileWriter archErr,int numeroLinea, Linea linea_ens,FileWriter archInst, String registro,ArrayList<String> sumab,ContadorDeLocalidades contlocalidades,ArbolEtiquetas arboletq,FileWriter archTABSIM, ArrayList<String> modosdir){
 		String sumadebytes="";
 		try{
 			System.out.println("ENTERO: "+resultadoc_2);
 			System.out.println("ENTERO: ");
 			if (idx.equals("IDX")) {
 				if ((registro.equalsIgnoreCase("-x") || registro.equalsIgnoreCase("+x") || registro.equalsIgnoreCase("x-") || registro.equalsIgnoreCase("x+") || registro.equalsIgnoreCase("-y") || registro.equalsIgnoreCase("+y") || registro.equalsIgnoreCase("y-") || registro.equalsIgnoreCase("y+") || registro.equalsIgnoreCase("-sp") || registro.equalsIgnoreCase("+sp") || registro.equalsIgnoreCase("sp-") || registro.equalsIgnoreCase("sp+"))){
 					//AUTO PRE/POST DECREMENTO /INCREMENTO
 					if ((resultadoc_2 >= 1 && resultadoc_2 <=8))
 					{
 						System.out.println ("Es IDX");
 						System.out.println("CONLOC: "+contlocalidades.conloc);
 						for(int posi = 0;posi<modosdir.size();posi++){
							if(modosdir.get(posi).equalsIgnoreCase("IDX")){
								sumadebytes = sumab.get(posi);
              				}
              			}
 						if (linea_ens.etq.equalsIgnoreCase("NULL")){
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");
    							//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    					}
    					else {
    						boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    						if (existeEtq == false) {
    								archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");

        							EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        							//calcular conloc sig.
    								int sumbytes = Integer.parseInt(sumadebytes,10);
    								int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    								contlocalidades.conloc = Integer.toHexString(i);
    								contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    								contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else
    							linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    					}


      					//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");
 					}
 					else {
      							System.out.println ("ERROR Operando fuera de rango para modo "+idx+" AUTO PRE/POST DECREMENTO /INCREMENTO");
    						linea_ens.guardarError(archErr,"ERROR Operando fuera de rango para modo "+idx+" AUTO PRE/POST DECREMENTO /INCREMENTO",numeroLinea);
    						}
 				}
 				else
      				if (registro.equalsIgnoreCase("x") || registro.equalsIgnoreCase("y") || registro.equalsIgnoreCase("sp") || registro.equalsIgnoreCase("pc")){
      					//IDX NORMAL
      						if ((resultadoc_2 >= -16 && resultadoc_2 <=15)){
      							System.out.println ("Es IDX");
      							System.out.println("CONLOC: "+contlocalidades.conloc);
      							for(int posi = 0;posi<modosdir.size();posi++){
									if(modosdir.get(posi).equalsIgnoreCase("IDX")){
										sumadebytes = sumab.get(posi);
              						}
              					}
      							if (linea_ens.etq.equalsIgnoreCase("NULL")){
    								archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");
    								//calcular conloc sig.
    								int sumbytes = Integer.parseInt(sumadebytes,10);
    								int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    								contlocalidades.conloc = Integer.toHexString(i);
    								contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    								contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    							}
    							else {
    								boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    								if (existeEtq == false) {
    										archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");

        									EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        									//calcular conloc sig.
    										int sumbytes = Integer.parseInt(sumadebytes,10);
    										int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    										contlocalidades.conloc = Integer.toHexString(i);
    										contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    										contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    								}
    								else
    									linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    							}

      							//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");
      						}
      						else {
      							System.out.println ("ERROR Operando fuera de rango para modo "+idx+" de 5 bits");
    							linea_ens.guardarError(archErr,"ERROR Operando fuera de rango para modo "+idx+" de 5 bits",numeroLinea);
    						}
      				}
      				else {
      						System.out.println ("ERROR Registro incorrecto para modo "+idx+" de 5 bits");
    						linea_ens.guardarError(archErr,"ERROR Registro incorrecto para modo "+idx+" de 5 bits",numeroLinea);
    				}
      		}
 		}
 		catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
 	}

 	public void VerifIDXAcum(String idx,int resultadoc_2,FileWriter archErr,int numeroLinea, Linea linea_ens,FileWriter archInst, String registro,ArrayList<String> sumab,ContadorDeLocalidades contlocalidades,ArbolEtiquetas arboletq,FileWriter archTABSIM, ArrayList<String> modosdir){
 		String sumadebytes="";
 		try{
 			System.out.println("ENTERO: "+resultadoc_2);
 			System.out.println("ENTERO: ");
 			if (idx.equals("IDX")) {
      					//IDX
      						if (registro.equalsIgnoreCase("x") || registro.equalsIgnoreCase("y") || registro.equalsIgnoreCase("sp") || registro.equalsIgnoreCase("pc")){
      							System.out.println ("Es IDX");
      							System.out.println("CONLOC: "+contlocalidades.conloc);
      							for(int posi = 0;posi<modosdir.size();posi++){
									if(modosdir.get(posi).equalsIgnoreCase("IDX")){
										sumadebytes = sumab.get(posi);
              						}
              					}
      							if (linea_ens.etq.equalsIgnoreCase("NULL")){
    									archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");
    									//calcular conloc sig.
    									int sumbytes = Integer.parseInt(sumadebytes,10);
    									int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    									contlocalidades.conloc = Integer.toHexString(i);
    									contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    									contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    							}
    							else {
    								boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    								if (existeEtq == false) {
    										archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");

        									EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        									//calcular conloc sig.
    										int sumbytes = Integer.parseInt(sumadebytes,10);
    										int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    										contlocalidades.conloc = Integer.toHexString(i);
    										contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    										contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    								}
    								else
    									linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    							}

      							//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");
      						}
      						else {
      							System.out.println ("ERROR Registro incorrecto para modo "+idx+" indizado de acumulador");
    							linea_ens.guardarError(archErr,"ERROR Registro incorrecto para modo "+idx+" indizado de acumulador",numeroLinea);
    						}

      		}
 		}
 		catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
        }
 	}

 	public void VerifIDX1(String idx,int resultadoc_2,FileWriter archErr,int numeroLinea, Linea linea_ens,FileWriter archInst, String registro,ArrayList<String> sumab,ContadorDeLocalidades contlocalidades,ArbolEtiquetas arboletq,FileWriter archTABSIM, ArrayList<String> modosdir){
 		String sumadebytes="";
 		try{
 			System.out.println("ENTERO: "+resultadoc_2);
 			System.out.println("ENTERO: ");
 			if (idx.equals("IDX1")) {
      				if (registro.equalsIgnoreCase("x") || registro.equalsIgnoreCase("y") || registro.equalsIgnoreCase("sp") || registro.equalsIgnoreCase("pc")){
      					if ((resultadoc_2 >= -256 && resultadoc_2 <=255)){
      						System.out.println ("Es IDX1");
      						System.out.println("CONLOC: "+contlocalidades.conloc);
      						for(int posi = 0;posi<modosdir.size();posi++){
								if(modosdir.get(posi).equalsIgnoreCase("IDX1")){
									sumadebytes = sumab.get(posi);
              					}
              				}
      						if (linea_ens.etq.equalsIgnoreCase("NULL")){
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");
    							//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else {
    							boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    							if (existeEtq == false) {
    									archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");

        								EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        								//calcular conloc sig.
    									int sumbytes = Integer.parseInt(sumadebytes,10);
    									int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    									contlocalidades.conloc = Integer.toHexString(i);
    									contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    									contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    							}
    							else
    								linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    						}


      						//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");
      					}
      					else {
      						System.out.println ("ERROR Operando fuera de rango para modo "+idx);
    						linea_ens.guardarError(archErr,"ERROR Operando fuera de rango para modo "+idx,numeroLinea);
    					}
      				}
      				else {
      						System.out.println ("ERROR Registro incorrecto para modo "+idx+" de 9 bits");
    						linea_ens.guardarError(archErr,"ERROR Registro incorrecto para modo "+idx+" de 9 bits",numeroLinea);
    				}
      		}
 		}
 		catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
 	}

 	public void VerifIDX2(String idx,int resultadoc_2,FileWriter archErr,int numeroLinea, Linea linea_ens,FileWriter archInst, String registro,ArrayList<String> sumab,ContadorDeLocalidades contlocalidades,ArbolEtiquetas arboletq,FileWriter archTABSIM, ArrayList<String> modosdir){
 		String sumadebytes="";
 		try{
 			System.out.println("ENTERO: "+resultadoc_2);
 			System.out.println("ENTERO: ");
 			if (idx.equals("IDX2")) {
      				if ((resultadoc_2 >= 0 && resultadoc_2 <=65535)){
      					if (registro.equalsIgnoreCase("x") || registro.equalsIgnoreCase("y") || registro.equalsIgnoreCase("sp") || registro.equalsIgnoreCase("pc")){
      						System.out.println ("Es IDX2");
      						System.out.println("CONLOC: "+contlocalidades.conloc);
      						for(int posi = 0;posi<modosdir.size();posi++){
								if(modosdir.get(posi).equalsIgnoreCase("IDX2")){
									sumadebytes = sumab.get(posi);
              					}
              				}
      						if (linea_ens.etq.equalsIgnoreCase("NULL")){
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");
    							//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else {
    							boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    							if (existeEtq == false) {
    									archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");

        								EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        								//calcular conloc sig.
    									int sumbytes = Integer.parseInt(sumadebytes,10);
    									int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    									contlocalidades.conloc = Integer.toHexString(i);
    									contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    									contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    							}
    							else
    								linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    						}

      						//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");
      					}
      					else {
      						System.out.println ("ERROR Registro incorrecto para modo "+idx+" de 16 bits");
    						linea_ens.guardarError(archErr,"ERROR Registro incorrecto para modo "+idx+" de 16 bits",numeroLinea);
    					}
      				}
      				else {
      						System.out.println ("ERROR Operando fuera de rango para modo "+idx);
    						linea_ens.guardarError(archErr,"ERROR Operando fuera de rango para modo "+idx,numeroLinea);
    				}
      		}
 		}
 		catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
 	}

 	public void Indirecto16bits(String idx,int resultadoc_2,FileWriter archErr,int numeroLinea, Linea linea_ens,FileWriter archInst, String registro,ArrayList<String> sumab,ContadorDeLocalidades contlocalidades,ArbolEtiquetas arboletq,FileWriter archTABSIM, ArrayList<String> modosdir){
 		String sumadebytes="";
 		try{
 			System.out.println("ENTERO: "+resultadoc_2);
 			System.out.println("ENTERO: ");
 			if (idx.equals("[IDX2]")) {
      				if ((resultadoc_2 >= 0 && resultadoc_2 <=65535)){
      					if (registro.equalsIgnoreCase("x") || registro.equalsIgnoreCase("y") || registro.equalsIgnoreCase("sp") || registro.equalsIgnoreCase("pc")){
      						System.out.println ("Es [IDX2]");
      						System.out.println("CONLOC: "+contlocalidades.conloc);
      						for(int posi = 0;posi<modosdir.size();posi++){
								if(modosdir.get(posi).equalsIgnoreCase("[IDX2]")){
									sumadebytes = sumab.get(posi);
              					}
              				}
      						if (linea_ens.etq.equalsIgnoreCase("NULL")){
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");
    							//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else {
    							boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    							if (existeEtq == false) {
    									archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");

        								EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        								//calcular conloc sig.
    									int sumbytes = Integer.parseInt(sumadebytes,10);
    									int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    									contlocalidades.conloc = Integer.toHexString(i);
    									contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    									contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    							}
    							else
    								linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    						}


      						//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");
      					}
      					else {
      						System.out.println ("ERROR Registro incorrecto para modo "+idx+"");
    						linea_ens.guardarError(archErr,"ERROR Registro incorrecto para modo "+idx+"",numeroLinea);
    					}
      				}
      				else {
      						System.out.println ("ERROR Operando fuera de rango para modo "+idx);
    						linea_ens.guardarError(archErr,"ERROR Operando fuera de rango para modo "+idx,numeroLinea);
    				}
      		}
 		}
 		catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
 	}

 	//IndizadoDeAcumuladorD
 	public void IndizadoDeAcumuladorD(String idx,int resultadoc_2,FileWriter archErr,int numeroLinea, Linea linea_ens,FileWriter archInst, String registro,ArrayList<String> sumab,ContadorDeLocalidades contlocalidades,ArbolEtiquetas arboletq,FileWriter archTABSIM, ArrayList<String> modosdir){
 		String sumadebytes="";
 		try{
 			if (idx.equals("[D,IDX]")) {
 				if (registro.equalsIgnoreCase("x") || registro.equalsIgnoreCase("y") || registro.equalsIgnoreCase("sp") || registro.equalsIgnoreCase("pc")){
      					System.out.println ("Es IDX");
      					System.out.println("CONLOC: "+contlocalidades.conloc);
      					for(int posi = 0;posi<modosdir.size();posi++){
								if(modosdir.get(posi).equalsIgnoreCase("[D,IDX]")){
									sumadebytes = sumab.get(posi);
              					}
              				}
      					if (linea_ens.etq.equalsIgnoreCase("NULL")){
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");
    							//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    					}
    					else {
    						boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    						if (existeEtq == false) {
    								archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");

        							EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        							//calcular conloc sig.
    								int sumbytes = Integer.parseInt(sumadebytes,10);
    								int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    								contlocalidades.conloc = Integer.toHexString(i);
    								contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    								contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else
    							linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    					}


      					//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+idx+"\r\n");
      			}
      			else {
      				System.out.println ("ERROR Registro incorrecto para modo "+idx);
    				linea_ens.guardarError(archErr,"ERROR Registro incorrecto para modo "+idx,numeroLinea);
    			}

      		}
 		}
 		catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
 	}

 	public void VerifRel8(String relativo,int resultadoc_2,FileWriter archErr,int numeroLinea, Linea linea_ens,FileWriter archInst,ArrayList<String> sumab,ContadorDeLocalidades contlocalidades,ArbolEtiquetas arboletq,FileWriter archTABSIM, ArrayList<String> modosdir){
 		String sumadebytes="";
 		try{
 			System.out.println("ENTERO: "+resultadoc_2);
 			System.out.println("ENTERO: ");
 			if (relativo.equals("REL8")) {
      				if (resultadoc_2 >= -128 && resultadoc_2 <=127){
      						System.out.println ("Es REL8");
      						System.out.println("CONLOC: "+contlocalidades.conloc);
      						for(int posi = 0;posi<modosdir.size();posi++){
								if(modosdir.get(posi).equalsIgnoreCase("REL8")){
									sumadebytes = sumab.get(posi);
              					}
              				}
      						if (linea_ens.etq.equalsIgnoreCase("NULL")){
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+relativo+"\r\n");
    							//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else {
    							boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    							if (existeEtq == false) {
    									archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+relativo+"\r\n");

        								EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        								//calcular conloc sig.
    									int sumbytes = Integer.parseInt(sumadebytes,10);
    									int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    									contlocalidades.conloc = Integer.toHexString(i);
    									contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    									contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    							}
    							else
    								linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    						}

      						//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+relativo+"\r\n");
      				}
      				else {
      						System.out.println ("ERROR Operando fuera de rango para modo "+relativo);
    						linea_ens.guardarError(archErr,"ERROR Operando fuera de rango para modo "+relativo,numeroLinea);
      				}
      		}
 		}
 		catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
 	}

 	public void VerifRel9(String relativo,int resultadoc_2,FileWriter archErr,int numeroLinea, Linea linea_ens,FileWriter archInst,ArrayList<String> sumab,ContadorDeLocalidades contlocalidades,ArbolEtiquetas arboletq,FileWriter archTABSIM, ArrayList<String> modosdir){
 		String sumadebytes="";
 		try{
 			System.out.println("ENTERO: "+resultadoc_2);
 			System.out.println("ENTERO: ");
 			if (relativo.equals("REL9")) {
      				if (resultadoc_2 >= -256 && resultadoc_2 <=255){
      						System.out.println ("Es REL9");
      						System.out.println("CONLOC: "+contlocalidades.conloc);
      						for(int posi = 0;posi<modosdir.size();posi++){
								if(modosdir.get(posi).equalsIgnoreCase("REL9")){
									sumadebytes = sumab.get(posi);
              					}
              				}
      						if (linea_ens.etq.equalsIgnoreCase("NULL")){
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+relativo+"\r\n");
    							//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else {
    							boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    							if (existeEtq == false) {
    									archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+relativo+"\r\n");

        								EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        								//calcular conloc sig.
    									int sumbytes = Integer.parseInt(sumadebytes,10);
    									int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    									contlocalidades.conloc = Integer.toHexString(i);
    									contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    									contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    							}
    							else
    								linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    						}


      						//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+relativo+"\r\n");
      				}
      				else {
      						System.out.println ("ERROR Operando fuera de rango para modo "+relativo);
    						linea_ens.guardarError(archErr,"ERROR Operando fuera de rango para modo "+relativo,numeroLinea);
      				}
      		}
 		}
 		catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
 	}

 	public void VerifRel16(String relativo,int resultadoc_2,FileWriter archErr,int numeroLinea, Linea linea_ens,FileWriter archInst,ArrayList<String> sumab,ContadorDeLocalidades contlocalidades,ArbolEtiquetas arboletq,FileWriter archTABSIM, ArrayList<String> modosdir){
 		String sumadebytes="";
 		try{
 			System.out.println("ENTERO: "+resultadoc_2);
 			System.out.println("ENTERO: ");
 			if (relativo.equals("REL16")) {
      				if (resultadoc_2 >= -32768 && resultadoc_2 <=65535){
      						System.out.println ("Es REL16");
      						System.out.println("CONLOC: "+contlocalidades.conloc);
      						for(int posi = 0;posi<modosdir.size();posi++){
								if(modosdir.get(posi).equalsIgnoreCase("REL16")){
									sumadebytes = sumab.get(posi);
              					}
              				}
      						if (linea_ens.etq.equalsIgnoreCase("NULL")){
    							archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+relativo+"\r\n");
    							//calcular conloc sig.
    							int sumbytes = Integer.parseInt(sumadebytes,10);
    							int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    							contlocalidades.conloc = Integer.toHexString(i);
    							contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    							contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    						}
    						else {
    							boolean existeEtq = arboletq.insertar (linea_ens.etq,contlocalidades.conloc);
    							if (existeEtq == false) {
    									archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+relativo+"\r\n");

        								EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades);
        								//calcular conloc sig.
    									int sumbytes = Integer.parseInt(sumadebytes,10);
    									int i = Integer.parseInt(contlocalidades.conloc,16) + sumbytes;
    									contlocalidades.conloc = Integer.toHexString(i);
    									contlocalidades.conloc = contlocalidades.PonerA4caracteres(contlocalidades.conloc);
    									contlocalidades.conloc = contlocalidades.conloc.toUpperCase();
    							}
    							else
    								linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    						}


      						//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+relativo+"\r\n");
      				}
      				else {
      						System.out.println ("ERROR Operando fuera de rango para modo "+relativo);
    						linea_ens.guardarError(archErr,"ERROR Operando fuera de rango para modo "+relativo,numeroLinea);
      				}
      		}
 		}
 		catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
 	}

 	public void EscribirEnTABSIM (FileWriter archTABSIM,Linea linea_ens, ContadorDeLocalidades contlocalidades) {
 		System.out.println(contlocalidades.conloc+"\t\t"+linea_ens.etq);

 		try{
    		archTABSIM.write(linea_ens.etq+"\t\t"+contlocalidades.conloc+"\r\n");
    	}
    	catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }

 	}
}