/**
 * @(#)ContadorDeLocalidades.java
 *
 *
 * @author Delgado Meza Julieta Jocelyne
 * @version 1.00 2013/10/17
 */
 
import java.util.*;
import java.util.StringTokenizer;
import java.io.*;
import java.io.File;
import java.io.FileWriter;

public class ContadorDeLocalidades {
	
	 //atributos
	 String dirinicial = "0000";
	 String conloc;
	 String conlocdeequ;
	 String etqvalida;
	
    /*public ContadorDeLocalidades() {
    }*/
    
    public boolean EmpiezaConDirectivaOConOrg(Linea linea_ens,FileWriter archErr,int numeroLinea,FileWriter archInst, ArbolEtiquetas arboletq,FileWriter archTABSIM){
    	
    	try {
    		//FileWriter archTABSIM = new FileWriter(archTbs,true);
    		if(linea_ens.codop.equalsIgnoreCase("ORG")) {
    			int conver = ConversionCLoc(linea_ens.oper,archErr,numeroLinea,linea_ens);
    			if (conver >=0 && conver<=65535){
    				    				
    				if (linea_ens.etq.equalsIgnoreCase("NULL")){
    					conloc = ConlocAHexa(conver,linea_ens); 
    					conloc = conloc.toUpperCase();
    					System.out.println("CONLOC: "+conloc); 
    					archInst.write(numeroLinea+"\t\t"+conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n"); 
    				}
    				else {
    					boolean existeEtq = arboletq.insertar (linea_ens.etq);
    					if (existeEtq == false) {
    							conloc = ConlocAHexa(conver,linea_ens); 
    							conloc = conloc.toUpperCase();
    							System.out.println("CONLOC: "+conloc); 
    							archInst.write(numeroLinea+"\t\t"+conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n"); 
    								
        						EscribirEnTABSIM(archTABSIM,linea_ens,conloc);
    					}
    					else
    						linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    				}
    					
    					
    					
    			}
    			else {
    				if (conver== 65536){
    					System.out.println("MAAAAAAAAAAAL "+ "ERROR Operando fuera de rango en directiva EQU");
    					linea_ens.guardarError(archErr,"ERROR Operando fuera de rango en directiva "+linea_ens.codop,numeroLinea);
    				}
    			}
    		
    			return true;
    		}
    		else {
    			//Directivas constantes
    			//DW, DB, DC.W, DC.B, FCB, FDB, FCC
    			if(linea_ens.codop.equalsIgnoreCase("DW") || linea_ens.codop.equalsIgnoreCase("DB") || linea_ens.codop.equalsIgnoreCase("DC.W") || linea_ens.codop.equalsIgnoreCase("DC.B") || linea_ens.codop.equalsIgnoreCase("FCB") || linea_ens.codop.equalsIgnoreCase("FDB") || linea_ens.codop.equalsIgnoreCase("FCC")) {
    				int converoper =0;
    				if (!linea_ens.codop.equalsIgnoreCase("FCC"))
    					converoper = ConversionCLoc(linea_ens.oper,archErr,numeroLinea,linea_ens);

    				if(linea_ens.codop.equalsIgnoreCase("DB") || linea_ens.codop.equalsIgnoreCase("DC.B") || linea_ens.codop.equalsIgnoreCase("FCB")){
    					//ver si esta entre el rango
    					if (converoper >=0 && converoper<=255){	
    						//conloc = ConlocAHexa(conver,linea_ens); 
    						//si esta escribir la conloc obtenida anteriormente
    						
    						System.out.println("CONLOC: "+conloc); 	
    						
    						if (linea_ens.etq.equalsIgnoreCase("NULL")){
    							archInst.write(numeroLinea+"\t\t"+conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n"); 
    							//calcular conloc sig.
    							int i = Integer.parseInt(conloc,16)+1;
    							conloc = Integer.toHexString(i);
    							conloc = PonerA4caracteres(conloc); 
    							conloc = conloc.toUpperCase();
    						}
    						else {
    							boolean existeEtq = arboletq.insertar (linea_ens.etq);
    							if (existeEtq == false) {
    									archInst.write(numeroLinea+"\t\t"+conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n"); 
    								
        								EscribirEnTABSIM(archTABSIM,linea_ens,conloc);
        								//calcular conloc sig.
    									int i = Integer.parseInt(conloc,16)+1;
    									conloc = Integer.toHexString(i);
    									conloc = PonerA4caracteres(conloc); 
    									conloc = conloc.toUpperCase();
    							}
    							else
    								linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    						}
		
    					}
    					else {
    						linea_ens.guardarError(archErr,"ERROR Operando fuera de rango en directiva "+linea_ens.codop,numeroLinea);
    					}
    			
    					return true;
    				}
    				else {
    					if(linea_ens.codop.equalsIgnoreCase("DW") || linea_ens.codop.equalsIgnoreCase("DC.W") || linea_ens.codop.equalsIgnoreCase("FDB")){
    						//ver si esta entre el rango
    						if (converoper >=0 && converoper<=65535){	
    							//conloc = ConlocAHexa(conver,linea_ens); 
    							//si esta escribir la conloc obtenida anteriormente
    							
    							System.out.println("CONLOC: "+conloc); 
    							
    							if (linea_ens.etq.equalsIgnoreCase("NULL")){
    								archInst.write(numeroLinea+"\t\t"+conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n"); 
    								//calcular conloc sig.
    								int i = Integer.parseInt(conloc,16)+2;
    								conloc = Integer.toHexString(i);
    								conloc = PonerA4caracteres(conloc); 
    								conloc = conloc.toUpperCase();	
    							}
    							else {
    								boolean existeEtq = arboletq.insertar (linea_ens.etq);
    								if (existeEtq == false) {
    										archInst.write(numeroLinea+"\t\t"+conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n"); 
    								
        									EscribirEnTABSIM(archTABSIM,linea_ens,conloc);
        									//calcular conloc sig.
    										int i = Integer.parseInt(conloc,16)+2;
    										conloc = Integer.toHexString(i);
    										conloc = PonerA4caracteres(conloc); 
    										conloc = conloc.toUpperCase();	
    								}
    								else
    									linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    							}
    							
    							
    								
    						}
    						else {
    							linea_ens.guardarError(archErr,"ERROR Operando fuera de rango en directiva "+linea_ens.codop,numeroLinea);
    						}
    			
    						return true;
    					}
    					else {
    						if(linea_ens.codop.equalsIgnoreCase("FCC")){
    						//PARA FCC
    						int entero = 34;
    						char enteroString = (char) entero;
    						System.out.println(enteroString);
    						if (((linea_ens.oper.charAt(0) == enteroString) && (linea_ens.oper.charAt(linea_ens.oper.length()-1) == enteroString)) || (linea_ens.oper.startsWith("“") && linea_ens.oper.endsWith("”"))){
    							System.out.println(linea_ens.oper);
    							int cantletras = 0;
    							for (int i = 1; i <= linea_ens.oper.length()-2; i++) {
    								cantletras++;
    							}
    							System.out.println(cantletras);
    							
    							System.out.println("CONLOC: "+conloc); 
    							
    							if (linea_ens.etq.equalsIgnoreCase("NULL")){
    								archInst.write(numeroLinea+"\t\t"+conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n"); 
    								//calcular conloc sig.
    								int i = Integer.parseInt(conloc,16)+cantletras;
    								conloc = Integer.toHexString(i);
    								conloc = PonerA4caracteres(conloc); 
    								conloc = conloc.toUpperCase();
    							}
    							else {
    								boolean existeEtq = arboletq.insertar (linea_ens.etq);
    								if (existeEtq == false) {
    										archInst.write(numeroLinea+"\t\t"+conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n"); 
    								
        									EscribirEnTABSIM(archTABSIM,linea_ens,conloc);
        									//calcular conloc sig.
    										int i = Integer.parseInt(conloc,16)+cantletras;
    										conloc = Integer.toHexString(i);
    										conloc = PonerA4caracteres(conloc); 
    										conloc = conloc.toUpperCase();
    								}
    								else
    									linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    							}
    							
    							
    							
    							
    						}
    						else {
    							System.out.println("ERROR Formato de operando en directiva FCC");
    							linea_ens.guardarError(archErr,"ERROR Formato de operando en directiva "+linea_ens.codop,numeroLinea);
    						}
    							
    						
    						}
    						return true;
    					}
    				}
    			
    			//contloc = contloc
    			}
    			else {
    				//Directivas de reserva de espacio de memoria
    				//DS, DS.B, DS.W, RMB, RMW
    				if(linea_ens.codop.equalsIgnoreCase("DS") || linea_ens.codop.equalsIgnoreCase("DS.B") || linea_ens.codop.equalsIgnoreCase("DS.W") || linea_ens.codop.equalsIgnoreCase("RMB") || linea_ens.codop.equalsIgnoreCase("RMW")) {
    				
    					int converoper = ConversionCLoc(linea_ens.oper,archErr,numeroLinea,linea_ens);
    				
    			
    					if(linea_ens.codop.equalsIgnoreCase("DS") || linea_ens.codop.equalsIgnoreCase("DS.B") || linea_ens.codop.equalsIgnoreCase("RMB")){
    						//ver si esta entre el rango
    						if (converoper >=0 && converoper<=65535){	
    							//conloc = ConlocAHexa(conver,linea_ens); 
    							//si esta escribir la conloc obtenida anteriormente
    							int valornumerico = Valnumerico(linea_ens);
    							System.out.println("CONLOC: "+conloc); 
    								
    							if (linea_ens.etq.equalsIgnoreCase("NULL")){
    								archInst.write(numeroLinea+"\t\t"+conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n"); 
    								//calcular conloc sig.
    								int i = Integer.parseInt(conloc,16)+valornumerico;
    								conloc = Integer.toHexString(i);
    								conloc = PonerA4caracteres(conloc);
    								conloc = conloc.toUpperCase();
    							}
    							else {
    								boolean existeEtq = arboletq.insertar (linea_ens.etq);
    								if (existeEtq == false) {
    										archInst.write(numeroLinea+"\t\t"+conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n"); 
    								
        									EscribirEnTABSIM(archTABSIM,linea_ens,conloc);
        									//calcular conloc sig.
    										int i = Integer.parseInt(conloc,16)+valornumerico;
    										conloc = Integer.toHexString(i);
    										conloc = PonerA4caracteres(conloc);
    										conloc = conloc.toUpperCase();
    								}
    								else
    									linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    							}
    							 			
    						}
    						else {
    							linea_ens.guardarError(archErr,"ERROR Operando fuera de rango en directiva "+linea_ens.codop,numeroLinea);
    						}
    			
    						return true;
    					}
    					else {
    						if(linea_ens.codop.equalsIgnoreCase("DS.W") || linea_ens.codop.equalsIgnoreCase("RMW")){
    							//ver si esta entre el rango
    							if (converoper >=0 && converoper<=65535){	
    								//conloc = ConlocAHexa(conver,linea_ens); 
    								//si esta escribir la conloc obtenida anteriormente
   
    								if (linea_ens.etq.equalsIgnoreCase("NULL")){
    									archInst.write(numeroLinea+"\t\t"+conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n"); 
    									//calcular conloc sig.
    									int i = Integer.parseInt(conloc,16)+(2*converoper);
    									conloc = Integer.toHexString(i);
    									conloc = PonerA4caracteres(conloc);
    									conloc = conloc.toUpperCase(); 	
    								}
    								else {
    									boolean existeEtq = arboletq.insertar (linea_ens.etq);
    									if (existeEtq == false) {
    											archInst.write(numeroLinea+"\t\t"+conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n"); 
    								
        										EscribirEnTABSIM(archTABSIM,linea_ens,conloc);
        										//calcular conloc sig.
    											int i = Integer.parseInt(conloc,16)+(2*converoper);
    											conloc = Integer.toHexString(i);
    											conloc = PonerA4caracteres(conloc);
    											conloc = conloc.toUpperCase(); 	
    									}
    									else
    										linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    								}
    	
    										
    							}
    							else {
    								linea_ens.guardarError(archErr,"ERROR Operando fuera de rango en directiva "+linea_ens.codop,numeroLinea);
    							}
    			
    							return true;
    						}
    						else {
    							System.out.println("ALGUN ERROR QUE PUDIESE OCURRIR");
    							return false;
    						}
    				}
    			
    			//contloc = contloc
    			}
    			else {
    				if(linea_ens.codop.equalsIgnoreCase("EQU")) {
    					int conver = ConversionCLoc(linea_ens.oper,archErr,numeroLinea,linea_ens);
    					if (conver >=0 && conver<=65535){
    							
    						conlocdeequ = ConlocAHexaDeEqu(conver,linea_ens); 
    						conlocdeequ = conlocdeequ.toUpperCase();
    						System.out.println("CONLOCDEEQU: "+conlocdeequ); 
    						System.out.println("CONLOCnormal: "+conloc); 

							if (linea_ens.etq.equalsIgnoreCase("NULL")){
    								archInst.write(numeroLinea+"\t\t"+conlocdeequ+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n"); 
    						}
    						else {
    							boolean existeEtq = arboletq.insertar (linea_ens.etq);
    							if (existeEtq == false) {
    							archInst.write(numeroLinea+"\t\t"+conlocdeequ+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n"); 
    										
        								EscribirEnTABSIM(archTABSIM,linea_ens,conlocdeequ);
    							}
    							else
    								linea_ens.guardarError(archErr,"ERROR La etiqueta ya existe",numeroLinea);
    						}
							
							
							
							
							
    					}
    					else {
    						if (conver== 65536){
    							System.out.println("MAAAAAAAAAAAL "+ "ERROR Operando fuera de rango en directiva EQU");
    							linea_ens.guardarError(archErr,"ERROR Operando fuera de rango en directiva "+linea_ens.codop,numeroLinea);
    						}
    					}
    		
    					return true;
    				}	
    				else
    					//PARA LOS QUE NO SON DIRECTIVAS
    					return false;
    			}
    				
    				
    			}
    		
    		}
    	}
    	catch (Exception e) {
    		System.err.println("Ocurrio un error: " + e.getMessage());
    		return false;
    	}
    	
    }
    
    public int ConversionCLoc (String contenido, FileWriter archErr,int numeroLinea, Linea linea_ens) {
    	//String porsinohaygato = oper;
		
		int resultadoc_2 = 0;
		boolean resultado= contenido.startsWith("$");
		System.out.println(resultado);

		if(resultado == true){ //YA ENTRO
    		StringTokenizer opConvertir = new StringTokenizer(contenido,"$",false);
    		contenido = opConvertir.nextToken();
    		if (contenido.matches("[a-fA-F0-9_]+")){
    			System.out.println("AQUI");
    			System.out.println("HEXADECIMAL: "+contenido);
    			int i = Integer.parseInt(contenido,16);

      			if(contenido.startsWith("F") || contenido.matches("^0{0,}F.*")) {
      				String bin = Integer.toBinaryString(i);
      				resultadoc_2 = (ConversionAComplementoADos (bin));
      				System.out.println("Complemento a DOSSS: "+resultadoc_2);
      				return resultadoc_2;
    			}
    			else {
      					return i;
    			}
    		}
    		else {
    				linea_ens.guardarError(archErr,"ERROR Formato de operador incorrecto en hexadecimal en directiva "+linea_ens.codop,numeroLinea);
    				return 65536;
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
       				resultadoc_2 = ConversionAComplementoADos (bin);
      				System.out.println("Complemento a DOSSS: "+resultadoc_2);
      				return resultadoc_2;
       			}
       			else {
       				if (contenido.matches("[0-7_]+")){
       					int i = Integer.parseInt(contenido,8);
       					return i;
       				}

       				else {
       					linea_ens.guardarError(archErr,"ERROR Formato de operador incorrecto en octal en directiva "+linea_ens.codop,numeroLinea);
       					return 65536;
       				}
       			}
    		}
    		else
    			if(contenido.matches("^%.*")){
    				System.out.println(contenido);
    				StringTokenizer opConvertir = new StringTokenizer(contenido,"%",false);
    				contenido = opConvertir.nextToken();
    				if(contenido.matches("[0-1_]+")){
    					if(contenido.startsWith("1")) {
    						System.out.println("Binario: "+contenido);
    						resultadoc_2 = (ConversionAComplementoADos (contenido));
      						System.out.println("Complemento a DOSSS: "+resultadoc_2);
      						return resultadoc_2;
    					}
    					else {
    						if(contenido.startsWith("0")) {
    							System.out.println("Binario: "+contenido);
    							int i = Integer.parseInt(contenido,2);
      							return i;
    						}
    					}
    				}
    				else {
    					linea_ens.guardarError(archErr,"ERROR Formato de operador incorrecto en binario en directiva "+linea_ens.codop,numeroLinea);
    					return 65536;
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
    					}
    					else {
    						System.out.println ("ERROR Formato de operando incorrecto para directiva "+linea_ens.codop);
    						linea_ens.guardarError(archErr,"ERROR Formato de operando incorrecto para directiva "+linea_ens.codop,numeroLinea);
    						return 65536;
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
    						}
    						else {
    							System.out.println ("ERROR Formato de operando incorrecto para directiva "+linea_ens.codop);
    							linea_ens.guardarError(archErr,"ERROR Formato de operando incorrecto para directiva "+linea_ens.codop,numeroLinea);
    							return 65536;
    						}
    					}
    					else {
    						if (contenido.matches("[0-9_]+")){
    							int i = Integer.parseInt(contenido,10);
    							System.out.println("DECIMAL NUMERO: "+i);
    							return i;
    						}
    						else {
    							System.out.println ("ERROR Formato de operando incorrecto para directiva "+linea_ens.codop);
    							linea_ens.guardarError(archErr,"ERROR Formato de operando incorrecto para directiva "+linea_ens.codop,numeroLinea);
    							return 65536;
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
		System.out.println("Complemento a dos: "+binario);
		return i;
 	}
 	
 	public String ConlocAHexa(int conver, Linea linea_ens){
 				boolean empieza= linea_ens.oper.startsWith("$");
    			StringTokenizer opConvertir = new StringTokenizer(linea_ens.oper,"$",false);
    			
    			if (empieza) {
    				conloc = opConvertir.nextToken();
    			}
    			else {
    				empieza= linea_ens.oper.startsWith("%");
    				opConvertir = new StringTokenizer(linea_ens.oper,"%",false);
    				if (empieza) {
    					conloc = opConvertir.nextToken();
    					int i = Integer.parseInt(conloc,2);
       					conloc = Integer.toHexString(i);
    					
    				}
    				else {
    					empieza= linea_ens.oper.startsWith("@");
    					opConvertir = new StringTokenizer(linea_ens.oper,"@",false);
    					if (empieza) {
    						conloc = opConvertir.nextToken();
    						int i = Integer.parseInt(conloc,8);
       						conloc = Integer.toHexString(i);
    					}
    					else{
    						conloc = linea_ens.oper;
    						int i = Integer.parseInt(conloc);
       						conloc = Integer.toHexString(i);
    					}
    						
    				}
    			}
    			System.out.println("CONLOC: "+conloc);
    			
    			conloc = PonerA4caracteres(conloc);
    			return conloc;
 	}
 	
 	public String PonerA4caracteres(String conloc) {
 		if (conloc.length()== 1) {
 			conloc="000"+conloc;
 		}
 		else {
 			if (conloc.length()== 2) {
 				conloc="00"+conloc;
 			}
 			else {
 				if (conloc.length()== 3) {
 					conloc="0"+conloc;
 				}
 				else {
 					if (conloc.length()== 4) {
 						conloc = conloc;
 					}
 					else
 						System.out.println("En algo estoy mallllllllllllllllll");
 				}
 			}
 		}
 		return conloc;
 	}
 	
 	
 		public String ConlocAHexaDeEqu(int conver, Linea linea_ens){
 				boolean empieza= linea_ens.oper.startsWith("$");
    			StringTokenizer opConvertir = new StringTokenizer(linea_ens.oper,"$",false);
    			
    			if (empieza) {
    				conlocdeequ = opConvertir.nextToken();
    			}
    			else {
    				empieza= linea_ens.oper.startsWith("%");
    				opConvertir = new StringTokenizer(linea_ens.oper,"%",false);
    				if (empieza) {
    					conlocdeequ = opConvertir.nextToken();
    					int i = Integer.parseInt(conlocdeequ,2);
       					conlocdeequ = Integer.toHexString(i);
    					
    				}
    				else {
    					empieza= linea_ens.oper.startsWith("@");
    					opConvertir = new StringTokenizer(linea_ens.oper,"@",false);
    					if (empieza) {
    						conlocdeequ = opConvertir.nextToken();
    						int i = Integer.parseInt(conlocdeequ,8);
       						conlocdeequ = Integer.toHexString(i);
    					}
    					else{
    						conlocdeequ = linea_ens.oper;
    						int i = Integer.parseInt(conlocdeequ);
       						conlocdeequ = Integer.toHexString(i);
    					}
    						
    				}
    			}
    			System.out.println("CONLOCDEEQU: "+conlocdeequ);
    			
    			conlocdeequ = PonerA4caracteres(conlocdeequ);
    			return conlocdeequ;
 	}
 	
 	public int Valnumerico(Linea linea_ens){
 				boolean empieza= linea_ens.oper.startsWith("$");
 				String valString;
 				int i = 0;
    			StringTokenizer opConvertir = new StringTokenizer(linea_ens.oper,"$",false);
    			
    			if (empieza) {
    				valString = opConvertir.nextToken();
    				i = Integer.parseInt(valString,16);
    			}
    			else {
    				empieza= linea_ens.oper.startsWith("%");
    				opConvertir = new StringTokenizer(linea_ens.oper,"%",false);
    				if (empieza) {
    					valString = opConvertir.nextToken();
    					i = Integer.parseInt(valString,2);    					
    				}
    				else {
    					empieza= linea_ens.oper.startsWith("@");
    					opConvertir = new StringTokenizer(linea_ens.oper,"@",false);
    					if (empieza) {
    						valString = opConvertir.nextToken();
    						i = Integer.parseInt(valString,8);    					}
    					else{
    						valString = linea_ens.oper;
    						i = Integer.parseInt(valString);
       						//conlocdeequ = Integer.toHexString(i);
    					}
    						
    				}
    			}
    			System.out.println("VALOR: "+i);
    			
    			return i;
 	}
 	
 	public void EscribirEnTABSIM (FileWriter archTABSIM,Linea linea_ens,String condeloc) {
 		System.out.println(condeloc+"\t\t"+linea_ens.etq);
 		System.out.println("ENTRO A TABSIM");
 		
 		try{
    		archTABSIM.write(linea_ens.etq+"\t\t"+condeloc+"\r\n");
    	}
    	catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
 		
 	}
 
}
