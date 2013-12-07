/**
 * @(#)s19.java
 *
 *
 * @author
 * @version 1.00 2013/11/27
 */

import java.util.*;
import java.io.*;


public class s19 {
	String s0;
	String s1="";
	String s9 = "S9030000FC";
	

	public void crears19 (String ruta, String name) {
		try {
			File archis19=new File(name+".s19");
			if(archis19.exists()) {
				archis19.delete();
			}
			FileWriter archs19 = new FileWriter(archis19,true);
			//RandomAccessFile archs19 = new RandomAccessFile(name+".s19","rw");
			
			RandomAccessFile archINST = new RandomAccessFile(name+".INST","rw");
    		archINST.seek(0);
    		String cantidad;
			s0 = "0000";
			String sobrante = "";
			String sob = "";
    		int cant=4;
    		int suma = 10;
    		for (int i = 0; i < ruta.length(); i++) {
    			int ascii = ruta.codePointAt(i);
    			suma = suma + ascii;
       			String hexa = (Integer.toHexString(ascii)).toUpperCase();
    			System.out.println(hexa);
    			s0 = s0 + hexa;
    			cant ++;
    		}
			suma = suma+cant;
			
			int k = 0;
			String bin = Integer.toBinaryString(suma);
			int complemento = ConversionAComplementoADos (bin) - 1;
    		String hexasuma = (Integer.toHexString(complemento)).toUpperCase();
    		System.out.println("HEXASUMA: "+ hexasuma);
    		s0 = "S0"+(Integer.toHexString(cant)).toUpperCase() + s0 + "0A" + hexasuma.charAt(hexasuma.length()-2) + hexasuma.charAt(hexasuma.length()-1);
    		System.out.println("s0: " + s0);
    		archs19.write(s0+"\r\n");
    		
    		int cuenta = 0;
    		int bandera = 0;
    		String strLinea;
    		String codigos = "";
    		String direccion = "";
    		String d= "";
    		while (archINST.getFilePointer()!=archINST.length())   {
            	//long point = arch.getFilePointer();
                strLinea=archINST.readLine();
                StringTokenizer st = new StringTokenizer(strLinea," \t",false); //con false omiti tomar como token el espacio y el tabulador
                String contenido = st.nextToken();
                int l = 0;
                int numerolinea = 0;
                if (!contenido.equals("LINEA") && !contenido.equals("------------------------------------------------------------------------------------------------------")) {
                	//if(st.hasMoreTokens()){
                	//	if (l == 0) {
                			String nlinea = contenido;
                			numerolinea = Integer.parseInt(nlinea,10);
                			l = 1;
                			if (k == 0) {
                				direccion = st.nextToken();
                				k++;
                			}
                			else {
                				d = st.nextToken();
                			}
                				
                				
                				
        					String etiqueta = st.nextToken();
        					String codop = st.nextToken();
        					String oper = st.nextToken();
        					int entero = 34;
		    				char enteroChar = (char) entero;
  		  					System.out.println(enteroChar);
 		       				if(oper.charAt(0) == enteroChar && codop.equalsIgnoreCase("FCC")) {
        						System.out.println("Aqui para FCC");

				        		//linea_ens.oper = contenido;
    				    		//System.out.println(linea_ens.oper);
        						while(st.hasMoreTokens()){
        						//	oper = st.nextToken();
        							oper = oper + " "+st.nextToken();
        							System.out.println("OPER FCC:"+ oper);
        						}
        						System.out.println("OPER FCC:"+ oper);

	        				}
	        				
	        				String codmaq="";
	        				
	        				if (codop.equalsIgnoreCase("DB") || codop.equalsIgnoreCase("DC.B") || codop.equalsIgnoreCase("FCB") ||codop.equalsIgnoreCase("DW") || codop.equalsIgnoreCase("DC.W") || codop.equalsIgnoreCase("FDB") || codop.equalsIgnoreCase("FCC")) {
	        					if (codop.equalsIgnoreCase("FCC")) {
	        						int posi=0;
	        						System.out.println("oper.length():"+ oper.length());
	        						for (int i = oper.length()-1; i>0; i--){
	        							if(oper.charAt(i) == ' ') {
	        								posi = i;
	        								i=0;
	        							}
	        								

	        						}
	        						for ( posi = posi+1 ;posi<oper.length(); posi++) {
	        							codmaq = codmaq + oper.charAt(posi);
	        						}
	        						/*for (int i = oper.length()-1; i>0; i--){
	        							System.out.println("i:"+ i);
	        							System.out.println("char en i:"+ oper.charAt(i));
	        							if(oper.charAt(i) == ' ')
	        								i = 0;
	        							else {
	        								codmaq = codmaq + oper.charAt(i);
	        								System.out.println("OPER FCC:"+ codmaq);
	        							
	        							}
	        						}*/
	        						System.out.println("codmaq:"+ codmaq);
	        						
	        					}
	        					else {
	        						codmaq = st.nextToken();
	        					System.out.println("codmaq:"+ codmaq);
	        					}
	        					
	        				}
	        				else {
	        					System.out.println ("----------"+numerolinea+oper);
        						String mododir;
        						if (st.hasMoreTokens())
        							mododir = st.nextToken();
        						if (st.hasMoreTokens())
        							codmaq = st.nextToken();
        						else
        							codmaq = "NULL";
	        				}
	        				
	        				System.out.println("codigos:"+ codigos+ "   codmaq:"+ codmaq);
	        				//s1 = direccion;
	        				if (!codmaq.equalsIgnoreCase("NULL"))
	        					codigos = codigos + codmaq;
	        					
	        					
	        				/*if (codigos.length()==1)
	        					direccion = d;*/	
	        						
	        						if(codmaq.equalsIgnoreCase("NULL")) {
	        							bandera = 1;
	        						}
	        						
	        						if(!codmaq.equalsIgnoreCase("NULL") && bandera == 1) {
	        							bandera = 0;
	        							direccion = d;
	        						}
	        					
	        					
	        					
	        				if (codigos.length()>16*2) {
	        					//String cod = "";
	        					s1 = "";
	        					System.out.println("codigos: "+codigos);
	        					for (int i = 0; i<32; i++) {
	        						s1 = s1 + codigos.charAt(i);
	        					}
	        					//s1 = s1 +codigos;
	        					System.out.println("solo s1: "+s1);
	        					
	        					for (int i = 32; i<codigos.length(); i++) {
	        						sobrante = sobrante + codigos.charAt(i);
	        					}
	        						
	        					codigos = s1;
	        					System.out.println("codigos = s1: "+codigos);
	        					
	        					if (direccion.length() == 1)
	        						direccion = "000"+direccion;
	        					if (direccion.length() == 2)
	        						direccion = "00"+direccion;
	        					if (direccion.length() == 3)
	        						direccion = "0"+direccion;
	        					
	        					s1 = (direccion+codigos).toUpperCase();
	        					int canti=1;
	        					int sumaa=0;
	        					
	        					String part = "" ;
	        					
	        					for (int i = 0; i<s1.length(); i++) {
	        						part = part + s1.charAt(i) + s1.charAt(i+1);
	        						System.out.println("part: "+part);
	        						i++;
	        						int  hex = Integer.parseInt(part,16);
	        						System.out.println("hex: "+ hex);
	        						sumaa = sumaa + hex;
	        						System.out.println("suma: "+Integer.toHexString(sumaa));
	        						canti++;
	        						part = "";
	        					}
	        					
	        					sumaa = sumaa + canti;
	        					int abin = Integer.parseInt(Integer.toHexString(sumaa),16);
       							String binn = Integer.toBinaryString(abin);
       							//System.out.println("octal a Binario: "+bin);
       							int resultadoc_2 = ConversionAComplementoADos (binn) -1 ;
       							String checksum = Integer.toHexString(resultadoc_2);
       							
	        					
	        					s1 = "s1"+ (Integer.toHexString(canti)).toUpperCase() + s1 + (checksum.charAt(checksum.length()-2)) + (checksum.charAt(checksum.length()-1));
	        					s1 = s1.toUpperCase();
	        					/*for (int p = 0; p<s1.length(); p++) {
	        					}*/
	        					
	        					archs19.write(s1+"\r\n");
	        					System.out.println("s1 parte dir y cod: "+s1);
	        					System.out.println("codigos length: "+codigos.length());
	        					s1 = "";
	        					//s1 = 
	        					//if(!codop.equalsIgnoreCase("RMB") && !codop.equalsIgnoreCase("RMW"))
	        						direccion = Integer.toHexString(Integer.parseInt(direccion,16) + (codigos.length()/2));
	        					codigos = ""+ sobrante;
	        					if(sobrante.length() >0)
            						System.out.println("QUE ONDA");
            						sob = sobrante;
	        					sobrante = "";
	        					System.out.println("direccion: "+direccion);
	        				}
	        				else {
	        					if(codmaq.equalsIgnoreCase("NULL") && (codigos.length() > 0)) {
	        						System.out.println("direccion: "+direccion);
	        						if (direccion.length() == 1)
	        							direccion = "000"+direccion;
	        						if (direccion.length() == 2)
	        							direccion = "00"+direccion;
	        						if (direccion.length() == 3)
	        							direccion = "0"+direccion;
	        						
	        						//if (codop.equalsIgnoreCase("EQU"))
	        							s1 = (direccion+ codigos).toUpperCase();
	        						int canti=1;
	        						int sumaa=0;
	        					
	        						String part = "" ;
	        					
	        						for (int i = 0; i<s1.length(); i++) {
	        							part = part + s1.charAt(i) + s1.charAt(i+1);
	  		      						System.out.println("part: "+part);
	        							i++;
	        							int  hex = Integer.parseInt(part,16);
	        							System.out.println("hex: "+ hex);
	        							sumaa = sumaa + hex;
	        							System.out.println("suma: "+Integer.toHexString(sumaa));
	        							canti++;
	        							part = "";
	        						}
	        					
	 		       					sumaa = sumaa + canti;
	        						int abin = Integer.parseInt(Integer.toHexString(sumaa),16);
       								String binn = "0" +Integer.toBinaryString(abin);
       								System.out.println("abin: "+abin);
       								System.out.println("bin: "+binn);
       								//System.out.println("octal a Binario: "+bin);
       								int resultadoc_2 = ConversionAComplementoADos (binn) -1;
       								String checksum = Integer.toHexString(resultadoc_2);
       							
	        						if (checksum.length() == 1)
	        							checksum = "0"+checksum;
	        						String cc = (Integer.toHexString(canti)).toUpperCase();
	        						if (cc.length() == 1)
	        							cc = "0"+cc;
	        					
	        						s1 = "s1"+ cc + s1 + (checksum.charAt(checksum.length()-2)) + (checksum.charAt(checksum.length()-1));
	        						s1 = s1.toUpperCase();
	        						archs19.write(s1+"\r\n");
	        						
	        						//if (codop.equalsIgnoreCase("EQU"))
	        							direccion = Integer.toHexString(Integer.parseInt(direccion,16) + (codigos.length()/2));
	        						
	        						System.out.println("aqui codmaq null: "+s1);
	        						s1 = "";
	        						//if (codop.equalsIgnoreCase("EQU"))
	        							//direccion = Integer.toHexString(Integer.parseInt(direccion,16) + (codigos.length()/2));
	        						codigos = "";
	        						
	        						/*if (codop.equalsIgnoreCase("DS") || codop.equalsIgnoreCase("DS.B") || codop.equalsIgnoreCase("RMB")) {
	        							//direccion = Integer.toHexString(Integer.parseInt(direccion,16) + (codigos.length()/2));
	        							int valornumerico = Valnumerico(oper);
	        							direccion = Integer.toHexString(Integer.parseInt(d,16) + valornumerico);
	        							System.out.println("direccion: "+direccion);
	        						}*/
	        						/*else {
	        							if (codop.equalsIgnoreCase("DS.W") || codop.equalsIgnoreCase("RMW")) {
	        								//direccion = Integer.toHexString(Integer.parseInt(direccion,16) + (codigos.length()/2));
	        								int valornumerico = Valnumerico(oper);
	        								direccion = Integer.toHexString(Integer.parseInt(d,16) + valornumerico*2);
	        								if (direccion.length() == 1)
	        									direccion = "000"+direccion;
	        								if (direccion.length() == 2)
	        									direccion = "00"+direccion;
	        								if (direccion.length() == 3)
	        									direccion = "0"+direccion;
	        								System.out.println("direccion: "+direccion);
	        							}
	        							else
	        								if (!codop.equalsIgnoreCase("EQU"))
	        									
	        								
	        						}*/
	        						
	        						
	        					}
	        					else {
	        						if(codop.equalsIgnoreCase("END")) {
	        							//s1 = direccion + codigos;
	        							if (direccion.length() == 1)
	        								direccion = "000"+direccion;
	        							if (direccion.length() == 2)
	        								direccion = "00"+direccion;
	        							if (direccion.length() == 3)
	        								direccion = "0"+direccion;
	        							s1 = (direccion + codigos).toUpperCase();
	        							int canti=1;
	        							int sumaa=0;
	        					
		        						String part = "" ;
	        					
		        						for (int i = 0; i<s1.length(); i++) {
	    	    							part = part + s1.charAt(i) + s1.charAt(i+1);
	  			      						System.out.println("part: "+part);
	        								i++;
	        								int  hex = Integer.parseInt(part,16);
	        								System.out.println("hex: "+ hex);
	        								sumaa = sumaa + hex;
	        								System.out.println("suma: "+Integer.toHexString(sumaa));
	        								canti++;
	        								part = "";
	   		     						}
	        					
	 			       					sumaa = sumaa + canti;
	        							int abin = Integer.parseInt(Integer.toHexString(sumaa),16);
       									String binn = "0" +Integer.toBinaryString(abin);
       									System.out.println("abin: "+abin);
       									System.out.println("bin: "+binn);
     	  								//System.out.println("octal a Binario: "+bin);
       									int resultadoc_2 = ConversionAComplementoADos (binn) -1;
       									String checksum = Integer.toHexString(resultadoc_2);
       							
	        							if (checksum.length() == 1)
	        								checksum = "0"+checksum;
	        							String cc = (Integer.toHexString(canti)).toUpperCase();
	        							if (cc.length() == 1)
	        								cc = "0"+cc;
	        					
	        							s1 = "s1"+ cc + s1 + (checksum.charAt(checksum.length()-2)) + (checksum.charAt(checksum.length()-1));
	        							s1 = s1.toUpperCase();
	        							archs19.write(s1+"\r\n");
	        							//direccion = Integer.toHexString(Integer.parseInt(direccion,16) + codigos.length());
	        							System.out.println("no 16: "+s1);
	        						}
	        					}
	        				}
	        				
	        					
	        				
                }

            }
            if (sob.length() > 0) {
            	System.out.println("SOB: "+sob);
            	s1 = (direccion + codigos).toUpperCase();
	        							int canti=1;
	        							int sumaa=0;
	        					
		        						String part = "" ;
	        					
		        						for (int i = 0; i<s1.length(); i++) {
	    	    							part = part + s1.charAt(i) + s1.charAt(i+1);
	  			      						System.out.println("part: "+part);
	        								i++;
	        								int  hex = Integer.parseInt(part,16);
	        								System.out.println("hex: "+ hex);
	        								sumaa = sumaa + hex;
	        								System.out.println("suma: "+Integer.toHexString(sumaa));
	        								canti++;
	        								part = "";
	   		     						}
	        					
	 			       					sumaa = sumaa + canti;
	        							int abin = Integer.parseInt(Integer.toHexString(sumaa),16);
       									String binn = "0" +Integer.toBinaryString(abin);
       									System.out.println("abin: "+abin);
       									System.out.println("bin: "+binn);
     	  								//System.out.println("octal a Binario: "+bin);
       									int resultadoc_2 = ConversionAComplementoADos (binn) -1;
       									String checksum = Integer.toHexString(resultadoc_2);
       							
	        							if (checksum.length() == 1)
	        								checksum = "0"+checksum;
	        							String cc = (Integer.toHexString(canti)).toUpperCase();
	        							if (cc.length() == 1)
	        								cc = "0"+cc;
	        					
	        							s1 = "s1"+ cc + s1 + (checksum.charAt(checksum.length()-2)) + (checksum.charAt(checksum.length()-1));
	        							s1 = s1.toUpperCase();
	        							archs19.write(s1+"\r\n"); 
            }
            
            archs19.write(s9+"\r\n");
            // Cerramos el archivo
            archINST.close();
            archs19.close();
    		
    		


		}catch (Exception e){ //Catch de excepciones
            System.err.println("Ocurrio un errorsazo: " + e.getMessage());
        }

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
		System.out.println("Complemento a uno: "+binario);
		return i;
 	}
 	
 	public int Valnumerico(String oper){
 				boolean empieza= oper.startsWith("$");
 				String valString;
 				int i = 0;
    			StringTokenizer opConvertir = new StringTokenizer(oper,"$",false);

    			if (empieza) {
    				valString = opConvertir.nextToken();
    				i = Integer.parseInt(valString,16);
    			}
    			else {
    				empieza= oper.startsWith("%");
    				opConvertir = new StringTokenizer(oper,"%",false);
    				if (empieza) {
    					valString = opConvertir.nextToken();
    					i = Integer.parseInt(valString,2);
    				}
    				else {
    					empieza= oper.startsWith("@");
    					opConvertir = new StringTokenizer(oper,"@",false);
    					if (empieza) {
    						valString = opConvertir.nextToken();
    						i = Integer.parseInt(valString,8);    					
    					}
    					else{
    						valString = oper;
    						i = Integer.parseInt(valString);
       						//conlocdeequ = Integer.toHexString(i);
    					}

    				}
    			}
    			System.out.println("VALOR: "+i);

    			return i;
 	}
 	
 	

}
