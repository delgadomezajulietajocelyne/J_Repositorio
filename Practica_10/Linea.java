/**
 * @(#)Linea.java
 * Validacion sintaxis ensamblador
 *
 * @author Delgado Meza Julieta Jocelyne
 * @version 1.00 2013/8/27
 */

import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.io.File;
import java.io.FileWriter;
//import java.io.FilenameFilter;
import java.util.regex.*; // Para expresiones regulares
import java.util.StringTokenizer; //para separar los elementos de la linea

public class Linea {

    //atributos
	String etq;
	String codop;
	String oper;
	boolean errorLinea = false;
	
	String n;

    //constructor
    public Linea (String e, String c, String o) {
    	//Inicialice atributos
    	etq = e;
    	codop = c;
    	oper = o;
    }

    public boolean validarEtiqueta(StringTokenizer str, Linea linea_ens, boolean edoERROR, FileWriter archErr,int numeroLinea){
    	String contenido = new String (str.nextToken());
    	Pattern pat = Pattern.compile("[a-zA-Z0-9_]+");
     	Matcher mat = pat.matcher(contenido);
    	if(contenido.length()<=8){
    		if(mat.matches()){
    			linea_ens.etq = contenido;
    			while (str.hasMoreTokens() && edoERROR == false) {
                				contenido = str.nextToken();
                				if(contenido.matches("^[a-zA-Z].*")){
                					System.out.println("PASO A VER codop");
                					edoERROR = linea_ens.validarCodigo(str,contenido,linea_ens,edoERROR,archErr,numeroLinea);
                				}
                				else{
                					edoERROR = true;
                					linea_ens.errorLinea = true;
    								linea_ens.guardarError(archErr,"ERROR El codigo de operacion debe empezar con letra",numeroLinea);
                				}
                			}
    		}
    		else{
    			edoERROR = true;
    			linea_ens.errorLinea = true;
    			linea_ens.guardarError(archErr,"ERROR la Etiqueta contiene caracteres no alfanumericos",numeroLinea);
    		}

    	}
    	else{
    		edoERROR = true;
    		linea_ens.errorLinea = true;
    		linea_ens.guardarError(archErr,"ERROR la Etiqueta contiene mas de 8 caracteres",numeroLinea);
    	}
    	return edoERROR;
    }

    public boolean validarCodigo(StringTokenizer str, String contenido, Linea linea_ens, boolean edoERROR, FileWriter archErr,int numeroLinea){
    	Pattern pat = Pattern.compile("^[a-zA-Z].*");
     	Matcher mat = pat.matcher(contenido);
    	if(contenido.length()<=5 && edoERROR == false){
    		if(mat.matches()){
    			StringTokenizer palCODOP = new StringTokenizer(contenido,".",false); //con false omiti tomar como token el punto
 				int vecespunto;
 				vecespunto = contarPuntos(contenido, '.');
 				if(vecespunto == 1 || vecespunto == 0)
 				if((palCODOP.countTokens() == 1) || (palCODOP.countTokens() == 2)){
 					switch (palCODOP.countTokens()){
 						case 1: if((palCODOP.nextToken()).matches("[a-zA-Z]+")){
 							System.out.println("CODOP NO DIRECTIVA CON PUNTO: "+contenido);
 									linea_ens.codop = contenido;
 								}
 								else{
 									edoERROR = true;
 									linea_ens.errorLinea = true;
 									linea_ens.guardarError(archErr,"ERROR el codigo de operacion solo puede contener caracteres alfanumericos y/o un punto",numeroLinea);
 								}

 							break;

 						case 2: if((palCODOP.nextToken()).matches("[a-zA-Z]+")){
 									if((palCODOP.nextToken()).matches("[a-zA-Z]+")){
 										linea_ens.codop = contenido;
 									}
 								}
 								else{
 									edoERROR = true;
 									linea_ens.errorLinea = true;
 									linea_ens.guardarError(archErr,"ERROR el codigo de operacion solo puede contener caracteres alfanumericos y/o un punto",numeroLinea);
 								}

 							break;
 					}

 				}
 				else{
 					edoERROR = true;
 					linea_ens.errorLinea = true;
 					linea_ens.guardarError(archErr,"ERROR el codigo de operacion solo puede contener letras y/o un punto",numeroLinea);
 				}
 				else {// este es de si hay mas puntos en vecespuntos
 					edoERROR = true;
 					linea_ens.errorLinea = true;
 					linea_ens.guardarError(archErr,"ERROR el codigo de operacion solo puede contener letras y/o un punto",numeroLinea);
 				}
    		}
    		else{
    			edoERROR = true;
    			linea_ens.errorLinea = true;
    			linea_ens.guardarError(archErr,"ERROR el codigo de operacion solo puede empezar con letras mayusculas o minusculas",numeroLinea);
    		}
    		while (str.hasMoreTokens() && edoERROR == false) {
    			contenido = str.nextToken();
    			System.out.println("PASO A VER operando");
    			edoERROR = linea_ens.validarOperando(str,contenido,linea_ens,edoERROR,archErr,numeroLinea);
       		}
    	}
    	else{
    		edoERROR = true;
    		linea_ens.errorLinea = true;
    		linea_ens.guardarError(archErr,"ERROR Codigo de Operacion invalido",numeroLinea);
    	}
    	return edoERROR;
    }

    public int contarPuntos(String cadena, char caracter){
		int veces=0;
		int i;
		for (i=0;i<cadena.length();i++){
			if (cadena.charAt(i)==caracter){
				veces++;
			}
		}
		return veces;
	}

    public boolean validarOperando(StringTokenizer str,String contenido,Linea linea_ens,boolean edoERROR,FileWriter archErr,int numeroLinea ){
    	if(contenido.matches("^;.*")){
    		linea_ens.validarComentario(linea_ens);
        }
        else{
        	int entero = 34;
    		char enteroChar = (char) entero;
    		System.out.println(enteroChar);
        	if(contenido.charAt(0) == enteroChar && linea_ens.codop.equalsIgnoreCase("FCC")) {
        		System.out.println("Aqui para FCC");

        		linea_ens.oper = contenido;
        		System.out.println(linea_ens.oper);
        		while(str.hasMoreTokens()){
        			contenido = str.nextToken();
        			linea_ens.oper = linea_ens.oper + " "+contenido;
        			System.out.println(linea_ens.oper);
        		}

        	}
        	else {
        		if(contenido.matches(".+")){
        			System.out.println("oper");
        			StringTokenizer str2 = new StringTokenizer(contenido,";",false);
        			String contenido2 = str2.nextToken();
        			linea_ens.oper = contenido2;
        		}
        		if(str.hasMoreTokens()){
        			contenido = str.nextToken();
        			if(contenido.matches("^;.*"))
        				validarComentario(linea_ens);
        			else{
        				edoERROR = true;
        				linea_ens.errorLinea = true;
        				System.out.println("ERROR los operandos no deben de ir separados por espacios");
    					linea_ens.guardarError(archErr,"ERROR los operandos no deben de ir separados por espacios",numeroLinea);
        			}
        		}
        	}
        	System.out.println("oper");
        }
        return edoERROR;
    }

    public void validarComentario(Linea linea_ens){
    	linea_ens = new Linea("NULL","NULL","NULL");
    }

    public void guardarError(FileWriter archErr, String error, int numeroLinea){
    /*	try{
    		archErr.write("Linea "+numeroLinea+": "+error+"\r\n");
    	}
    	catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }*/
            try {
              			RandomAccessFile archErrr = new RandomAccessFile(n+".ERR","rw");
    					archErrr.seek(0);
    					while (archErrr.getFilePointer()!=archErrr.length()){
    	 					String strLinea=archErrr.readLine();
    	 				}
    	 				archErrr.writeBytes("\r\n");
    					archErrr.writeBytes("Linea "+ numeroLinea+": "+error);
              		}
              		catch (Exception e){ //Catch de excepciones
            			System.err.println("Ocurrio un error: " + e.getMessage());
             		}
    }

    public void errorEnd(FileWriter archErr, String error){
    	try{
    		archErr.write(error+"\r\n");
    	}
    	catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
    }

    public boolean AbrirArchivo(File archivo){

		String nombre_fichero = archivo.getName(); //obtengo el nombre del fichero
		int pos = nombre_fichero.lastIndexOf('.'); //me posiciono en el punto y obtengo la posicion
		String extension = nombre_fichero.substring(pos+1); //me posiciono despuÃ©s del punto

    	if(archivo.exists() && extension.equalsIgnoreCase("ASM")){
        	return true;
   		}
   		else
   			return false;
    }

    public boolean abrirTabop(ArbolBinarioOrdenado abo,Scanner teclado){
    	String path;
    	System.out.print("Escribe la ruta del TABOP: ");
        path = teclado.next();
        String strLinea2;
		File fich = new File(path);
        try{

        		RandomAccessFile fichero = new RandomAccessFile(path,"rw");

    			fichero.seek(0);
    			while (fichero.getFilePointer()!=fichero.length()){
            		strLinea2=fichero.readLine();
            		//System.out.println(strLinea2);
                	StringTokenizer st = new StringTokenizer(strLinea2,"|",false); //con false omiti tomar como token el espacio y el tabulador
    				abo.insertar (st);
    			}
    			fichero.close();
    		}catch (Exception e){  //Catch de excepciones
    			System.err.println("Ocurrio un error: " + e.getMessage());
    		 }

    	if(fich.exists()){
        	return true;
   		}
   		else
   			return false;

    }

    public boolean VerifEtisOpers(ArbolEtiquetas arboletq,String name,Linea linea_ens){
    	String strLinea="";
    	File fichero=new File(name+".INST");
    	File ficheroErrores=new File(name+".ERR");
    	boolean error = false;
    	if(fichero.exists()){
        	System.out.println("Nombre del archivo "+fichero.getName());
        	System.out.println("Camino             "+fichero.getPath());
        	System.out.println("Camino absoluto    "+fichero.getAbsolutePath());
        	System.out.println("Se puede escribir  "+fichero.canRead());
        	System.out.println("Se puede leer      "+fichero.canWrite());
        	System.out.println("Tamaño             "+fichero.length());
   		}
    	 try{
    	 	RandomAccessFile archErr = new RandomAccessFile(name+".ERR","rw");
    		archErr.seek(0);
    	 	while (archErr.getFilePointer()!=archErr.length()){
    	 		strLinea=archErr.readLine();
    	 	}
    	 	archErr.writeBytes("\r\n");

    	 	RandomAccessFile arch = new RandomAccessFile(name+".INST","rw");
    		arch.seek(0);
            /*// Abrimos el archivo
            FileInputStream fstream = new FileInputStream(name+".INST");
            // Creamos el objeto de entrada
            DataInputStream entrada = new DataInputStream(fstream);
            // Creamos el Buffer de Lectura
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            //String strLinea;
            // Leer el archivo linea por linea*/
            System.out.println ("SILOHIZO");
            while (arch.getFilePointer()!=arch.length())   {
            	long point = arch.getFilePointer();
                // Imprimimos la línea por pantalla
                //System.out.println ("----------"+strLinea);
                System.out.println ("SILOHIZO");
                strLinea=arch.readLine();
                StringTokenizer st = new StringTokenizer(strLinea," \t",false); //con false omiti tomar como token el espacio y el tabulador
                String contenido = st.nextToken();
                //int a = entrada.seek();
                //System.out.println ("VALOR a:"+ a);

                int l = 0;
                int numerolinea = 0;
                if (!contenido.equals("LINEA") && !contenido.equals("------------------------------------------------------------------------------------------------------")) {
                	//if(st.hasMoreTokens()){
                		if (l == 0) {
                			String nlinea = contenido;
                			numerolinea = Integer.parseInt(nlinea,10);
                			l = 1;
                		}
        				String conloc = st.nextToken();
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

	        			}

        				System.out.println ("----------"+numerolinea+oper);
        				String mododir;
        				if (st.hasMoreTokens())
        					mododir = st.nextToken();

        				long point2 = arch.getFilePointer();


        				if (oper.matches ("^[a-zA-Z].*") && oper.matches("[a-zA-Z0-9_]+") && !oper.equalsIgnoreCase("NULL")){
    						if(contenido.length()<=8){
    							System.out.println("Oper etiqueta");
    							boolean existeEtq = arboletq.existe (oper,arboletq);
    							if (existeEtq == false) {
    								arch.seek(point);
    								arch.writeBytes("*");
    								error = true;
    								linea_ens.errorLinea = true;
    								archErr.writeBytes("Linea "+numerolinea+": "+"ERROR El operando como etiqueta no existe\r\n");
									arch.seek(point2);
									if(!etiqueta.equalsIgnoreCase("NULL")) { //eliminar de arbol de etiquetas
										arboletq.borrar(etiqueta);
									}

    							}
    							else {
    								//PERFECTO SI EXISTE
    							}
    						}
    					}
        			//}
                }

            }

            //REALIZARLO DOBLE VEZ POR AQUELLAS QUE SE ELIMINARON Y SE TENGA QUE VOLVER A ELIMINAR

            arch.seek(0);

            System.out.println ("aqui entro doble");
            while (arch.getFilePointer()!=arch.length())   {
            	long point = arch.getFilePointer();
                System.out.println ("SILOHIZO");
                strLinea=arch.readLine();
                StringTokenizer st = new StringTokenizer(strLinea," \t",false); //con false omiti tomar como token el espacio y el tabulador
                String contenido = st.nextToken();
                int l = 0;
                int numerolinea = 0;
                if (!contenido.equals("LINEA") && !contenido.equals("------------------------------------------------------------------------------------------------------") && !contenido.startsWith("*")) {
                		if (l == 0) {
                			String nlinea = contenido;
                			numerolinea = Integer.parseInt(nlinea,10);
                			l = 1;
                		}
        				String conloc = st.nextToken();
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

	        			}

        				System.out.println ("----------"+numerolinea+oper);
        				String mododir;
        				if (st.hasMoreTokens())
        					mododir = st.nextToken();

        				long point2 = arch.getFilePointer();


        				if (oper.matches ("^[a-zA-Z].*") && oper.matches("[a-zA-Z0-9_]+") && !oper.equalsIgnoreCase("NULL")){
    						if(contenido.length()<=8){
    							System.out.println("Oper etiqueta");
    							boolean existeEtq = arboletq.existe (oper,arboletq);
    							if (existeEtq == false) {
    								arch.seek(point);
    								System.out.println("ERROR AL DOBLE");
    								arch.writeBytes("*");
    								error = true;
    								archErr.writeBytes("Linea "+numerolinea+": "+"ERROR El operando como etiqueta no existe\r\n");
									arch.seek(point2);


    							}
    							else {
    								//PERFECTO SI EXISTE
    							}
    						}
    					}

                }

            }

            // Cerramos el archivo
            arch.close();
            archErr.close();
        }catch (Exception e){ //Catch de excepciones
            System.err.println("Ocurrio un errorsazo: " + e.getMessage());
        }

        return error;
    }


    public void reEscribirInst (File fichero, String name) {
    	File aux=new File((fichero.getName()).substring(0,(fichero.getName()).lastIndexOf('.'))+".txt");
    	if (aux.exists()) {
        	aux.delete();
        }
        try{
    	 	RandomAccessFile arch = new RandomAccessFile(name+".INST","rw");
    		arch.seek(0);
			FileWriter auxInst = new FileWriter(aux,true);


            System.out.println ("ENTRO A REESCRIBIR INST");
            while (arch.getFilePointer()!=arch.length()) {

                String strLinea=arch.readLine();

                if(!strLinea.startsWith("*") ){
                	auxInst.write(strLinea+"\r\n");
                		System.out.println (strLinea);
                }

            }

            auxInst.close();
            arch.close();
            //aux.close();
        }catch (Exception e){ //Catch de excepciones
            System.err.println("Ocurrio un errorsazo: " + e.getMessage());
        }
    }
    
    public void reCalcularCONLOC(File fichero, String name,ArbolEtiquetas arboletq,	ArbolBinarioOrdenado abo,String nombrearch) {
    	String strLinea= "";
    	Linea linea_ens_paraReEscribir=new Linea("NULL","NULL","NULL");;
    	boolean tieneORG = false;
       	boolean tieneEQU = false;
    	ContadorDeLocalidades contlocalidades = new ContadorDeLocalidades();
    	arboletq.Inicializar();
    	File archivoInst=new File(nombrearch+".INST");
        if (archivoInst.exists()) {
        	if (archivoInst.delete())
  				 System.out.println("El fichero ha sido borrado satisfactoriamente");
			else
   				System.out.println("El fichero no puede ser borrado");
        	
        	archivoInst.delete();
        }

        File archivoTABSIM=new File((fichero.getName()).substring(0,(fichero.getName()).lastIndexOf('.'))+".TDS");
        if (archivoTABSIM.exists()) {
        	archivoTABSIM.delete();
        }



    	try{
    		File archivoErrores=new File((fichero.getName()).substring(0,(fichero.getName()).lastIndexOf('.'))+".ERR");
    		FileWriter archErr = new FileWriter(archivoErrores,true);

    	 	RandomAccessFile arch = new RandomAccessFile(name+".txt","rw");
    		arch.seek(0);
			//Para archivo INST
            FileWriter archInst = new FileWriter(archivoInst,true);
            RandomAccessFile arcHI = new RandomAccessFile(name+".INST","rw");
    				arcHI.seek(0);
    	 			while (arcHI.getFilePointer()!=arcHI.length()){
    	 				strLinea=arcHI.readLine();
    	 				System.out.println(strLinea);
    	 			}
    	 			arcHI.writeBytes("LINEA\t\tCONTLOC\t\tETQ\t\tCODOP\t\tOPER\t\tMODOS DE DIRECCIONAMIENTO\t\tCODMAQ\t\t\r\n");
     		   		arcHI.writeBytes("------------------------------------------------------------------------------------------------------\r\n");
        	//archInst.write("LINEA\t\tCONTLOC\t\tETQ\t\tCODOP\t\tOPER\t\tMODOS DE DIRECCIONAMIENTO\t\tCODMAQ\t\t\r\n");
        	//archInst.write("------------------------------------------------------------------------------------------------------\r\n");

			FileWriter archTABSIM = new FileWriter(archivoTABSIM,true);
				//archivo.seek(0);
				archTABSIM.write("TABSIM---------------\r\n");


            System.out.println ("ENTRO A RECALCULAR\n\n");
            while (arch.getFilePointer()!=arch.length())   {
            	long point = arch.getFilePointer();
                strLinea=arch.readLine();
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
                			String conloc = st.nextToken();
        					String etiqueta = st.nextToken();
        					linea_ens_paraReEscribir.etq = etiqueta;
        					String codop = st.nextToken();
        					linea_ens_paraReEscribir.codop = codop;
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

	        				}
        					linea_ens_paraReEscribir.oper = oper;
        					System.out.println ("----------"+numerolinea+oper);
        					String mododir;
        					if (st.hasMoreTokens())
        						mododir = st.nextToken();
                		//}

                		if((linea_ens_paraReEscribir.codop).equalsIgnoreCase("END")){
                			if ((linea_ens_paraReEscribir.oper).equalsIgnoreCase("NULL"))
                			{
                				//banderaEnd = true;
                				try {
              							RandomAccessFile archInstt = new RandomAccessFile(nombrearch+".INST","rw");
    									archInstt.seek(0);
    									while (archInstt.getFilePointer()!=archInstt.length()){
    	 									String strlinea=archInstt.readLine();
    	 								}
    	 								//archInst.writeBytes("\r\n");
    									archInstt.writeBytes(numerolinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens_paraReEscribir.etq+"\t\t"+linea_ens_paraReEscribir.codop+"\t\t"+linea_ens_paraReEscribir.oper+"\r\n");
    									archInstt.close();
              						}
              						catch (Exception e){ //Catch de excepciones
            							System.err.println("Ocurrio un error: " + e.getMessage());
             						}
                				//archInst.write(numerolinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens_paraReEscribir.etq+"\t\t"+linea_ens_paraReEscribir.codop+"\t\t"+linea_ens_paraReEscribir.oper+"\r\n");
                				if (!linea_ens_paraReEscribir.etq.equalsIgnoreCase("NULL"))
                					contlocalidades.EscribirEnTABSIM(archTABSIM,linea_ens_paraReEscribir,contlocalidades.conloc);

                			}
               			}
                		else {
                			if (!linea_ens_paraReEscribir.codop.equalsIgnoreCase("END")){

                				if (tieneORG == false && tieneEQU == false) {
                					System.out.println("ENTRO ORG EQU");
                					contlocalidades.conloc = "0000";
                					tieneORG = true;
                					tieneEQU = true;
                					//linea_ens_paraReEscribir.errorEnd(archErr,"ERROR el programa deberia iniciar con directiva ORG o EQU");
                				}
                				boolean tiene_directivasuorg = contlocalidades.EmpiezaConDirectivaOConOrg(linea_ens_paraReEscribir,archErr,numerolinea,archInst,arboletq,archTABSIM,nombrearch);

                				//if (tiene_directivasuorg == false)


                				if (tiene_directivasuorg == false) {
                					boolean exist = abo.existe(linea_ens_paraReEscribir, archInst,numerolinea,archErr,contlocalidades,arboletq,archTABSIM, nombrearch);
                					//if (exist == false){
                						//linea_ens.guardarError(archErr,"ERROR El Codigo de Operacion no existe en el TABOP",numeroLinea);
                					//}
                				}
                				System.out.println("JAJAJAJA");
                				arboletq.imprimirEntre();
                			}
                		}





                }

            }
            // Cerramos el archivo
            arch.close();
            // Cerrar archivos
			archErr.close();
            archInst.close();
            archTABSIM.close();
            arcHI.close();
        }catch (Exception e){ //Catch de excepciones
            System.err.println("Ocurrio un errorsazo: " + e.getMessage());
        }
    }


    public void InstDefinitivo (File fichero, String name) {
    	File aux=new File((fichero.getName()).substring(0,(fichero.getName()).lastIndexOf('.'))+".INST");
    	if (aux.exists()) {
        	if (aux.delete())
  				 System.out.println("El fichero ha sido borrado satisfactoriamente");
			else
   				System.out.println("El fichero no puede ser borrado");
        	
        	aux.delete();
        }
        try{
    	 	RandomAccessFile arch = new RandomAccessFile(name+".txt","rw");
    		arch.seek(0);
			FileWriter auxInst = new FileWriter(aux,true);


            System.out.println ("ENTRO A DEFINITIVAMENTE REESCRIBIR INST");
            while (arch.getFilePointer()!=arch.length()) {

                String strLinea=arch.readLine();
                auxInst.write(strLinea+"\r\n");
                System.out.println (strLinea);
                /*if(!strLinea.startsWith("*")){
                	auxInst.write(strLinea+"\r\n");
                		System.out.println (strLinea);
                }*/

            }
            // Cerramos el archivo
            auxInst.close();
            arch.close();
        }catch (Exception e){ //Catch de excepciones
            System.err.println("Ocurrio un errorsazo: " + e.getMessage());
        }
    }

    public void escribirCodigoMaquina(ArbolBinarioOrdenado abo,String name,Linea linea_ens,ArbolEtiquetas arboletq){
    	String strLinea="";
    	File fichero=new File(name+".INST");
    	File ficheroErrores=new File(name+".ERR");

		File aux=new File((fichero.getName()).substring(0,(fichero.getName()).lastIndexOf('.'))+".txt");
    	if (aux.exists()) {
        	aux.delete();
        }

    	 try{
    	 	RandomAccessFile arch = new RandomAccessFile(name+".INST","rw");
    		arch.seek(0);


    		RandomAccessFile archErr = new RandomAccessFile(name+".ERR","rw");
    		archErr.seek(0);

    	 	while (archErr.getFilePointer()!=archErr.length()){
    	 		strLinea=archErr.readLine();
    	 	}
    	 	archErr.writeBytes("\r\n");


            System.out.println ("ENTRO A PONER CODIGO MAQUINA");

			FileWriter auxInst = new FileWriter(aux,true);

            auxInst.write("LINEA\t\tCONTLOC\t\tETQ\t\tCODOP\t\tOPER\t\tMODODIR\t\tCODMAQ\r\n");
        	auxInst.write("------------------------------------------------------------------------------------------------------\r\n");

            while (arch.getFilePointer()!=arch.length())   {
                System.out.println ("SILOHIZO");
                long point = arch.getFilePointer();
                strLinea=arch.readLine();
                long point2 = arch.getFilePointer();
                StringTokenizer st = new StringTokenizer(strLinea," \t",false); //con false omiti tomar como token el espacio y el tabulador
                String contenido = st.nextToken();
                int numerolinea = 0;
                if (!contenido.equals("LINEA") && !contenido.equals("------------------------------------------------------------------------------------------------------")) {
                	//if(st.hasMoreTokens()){
        				String conloc = st.nextToken();
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

	        			}
        				System.out.println ("----------"+contenido+oper);
        				String mododir = "NULL";
        				auxInst.write(strLinea);
        				//if (codop.equalsIgnoreCase("DB")){
        				if (codop.equalsIgnoreCase("DB") || codop.equalsIgnoreCase("DC.B") || codop.equalsIgnoreCase("FCB")) {
        						String codmaqenhex = aHexa(oper);
        						if (codmaqenhex.length() == 1)
        							codmaqenhex = "0"+ codmaqenhex;

        						auxInst.write("\t\t\t\t"+codmaqenhex.toUpperCase());
        				}
        				else {
        					//if (codop.equalsIgnoreCase("DW")) {
        					if (codop.equalsIgnoreCase("DW") || codop.equalsIgnoreCase("DC.W") || codop.equalsIgnoreCase("FDB")) {
        						String codmaqenhex = aHexa(oper);
        						if (codmaqenhex.length() == 1)
        							codmaqenhex = "000"+ codmaqenhex;
        						else
        							if (codmaqenhex.length() == 2)
        								codmaqenhex = "00"+ codmaqenhex;
        							else
        								if (codmaqenhex.length() == 3)
        									codmaqenhex = "0"+ codmaqenhex;

        						auxInst.write("\t\t\t\t"+codmaqenhex.toUpperCase());
        					}
        					else
        						if (codop.equalsIgnoreCase("FCC")) {
        							auxInst.write("\t\t");
									int entero2 = 92;
    								char enteroString2 = (char) entero2;
    								for (int i = 1; i <= oper.length()-2; i++) {
    									if(oper.charAt(i) == enteroString2) {
    										i++;

    									}
    									int ascii = oper.codePointAt(i);
    									//int i = Integer.parseInt(opEnHexa);
       									String hexa = (Integer.toHexString(ascii)).toUpperCase();
    									//String hex = aHexa(oper.codePointAt(i));;
    									auxInst.write(hexa);
    								}


        						}
        				}

        				if (st.hasMoreTokens()) {
        					mododir = st.nextToken();
        					System.out.println("MODODIR: "+mododir);
        					System.out.println("codop: "+codop);
        					boolean esta = abo.escribirCodMaq(codop,mododir,abo,auxInst,oper,arboletq,conloc,point, point2,archErr,arch,contenido,linea_ens);
        					System.out.println("estaono: "+esta);

        				}
        				else {
        					auxInst.write("\r\n");
        				}

                }

            }
            // Cerramos el archivo
            arch.close();
            auxInst.close();
        }catch (Exception e){ //Catch de excepciones
            System.err.println("Ocurrio un errorsazo: " + e.getMessage());
        }
    }

    public String aHexa(String oper){
 				String opEnHexa;
 				boolean empieza= oper.startsWith("$");
    			StringTokenizer opConvertir = new StringTokenizer(oper,"$",false);

    			if (empieza) {
    				opEnHexa = opConvertir.nextToken();
    			}
    			else {
    				empieza= oper.startsWith("%");
    				opConvertir = new StringTokenizer(oper,"%",false);
    				if (empieza) {
    					opEnHexa = opConvertir.nextToken();
    					int i = Integer.parseInt(opEnHexa,2);
       					opEnHexa = Integer.toHexString(i);

    				}
    				else {
    					empieza= oper.startsWith("@");
    					opConvertir = new StringTokenizer(oper,"@",false);
    					if (empieza) {
    						opEnHexa = opConvertir.nextToken();
    						int i = Integer.parseInt(opEnHexa,8);
       						opEnHexa = Integer.toHexString(i);
    					}
    					else{
    						opEnHexa = oper;
    						int i = Integer.parseInt(opEnHexa);
       						opEnHexa = Integer.toHexString(i);
    					}

    				}
    			}
    			System.out.println("opEnHexa: "+opEnHexa);

    			//conlocdeequ = PonerA4caracteres(conlocdeequ);
    			return opEnHexa;
 	}
 	
 	public void escribirenINST(int numeroLinea,String conloc,Linea linea_ens, String nombrearch){
    /*	try{
    		archErr.write("Linea "+numeroLinea+": "+error+"\r\n");
    	}
    	catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }*/
            try {
              			RandomAccessFile archInst = new RandomAccessFile(nombrearch+".INST","rw");
    					archInst.seek(0);
    					while (archInst.getFilePointer()!=archInst.length()){
    	 					String strLinea=archInst.readLine();
    	 				}
    	 				//archInst.writeBytes("\r\n");
    					archInst.writeBytes(numeroLinea+"\t\t"+conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n");
    					archInst.close();
              		}
              		catch (Exception e){ //Catch de excepciones
            			System.err.println("Ocurrio un error: " + e.getMessage());
             		}
    }


    public static void main(String[] args) {
       	Linea linea_ens=new Linea("NULL","NULL","NULL");
       	String ruta = new String();
       	boolean existeono, banderaEnd = false, edoERROR = false;
       	int numeroLinea = 0;
		ArbolBinarioOrdenado abo = new ArbolBinarioOrdenado ();
       	ContadorDeLocalidades contlocalidades = new ContadorDeLocalidades();
       	int cantORG = 0;
       	boolean tieneORG = false;
       	boolean tieneEQU = false;
       	ArbolEtiquetas arboletq = new ArbolEtiquetas ();

    	Scanner teclado=new Scanner(System.in);

    	System.out.print("Escribe la ruta del archivo *.ASM: ");
        ruta = teclado.next();

    	File fichero=new File(ruta);
        existeono = linea_ens.AbrirArchivo(fichero);

        File archivoInst=new File((fichero.getName()).substring(0,(fichero.getName()).lastIndexOf('.'))+".INST");
        if (archivoInst.exists()) {
        	archivoInst.delete();
        }

        File archivoErrores=new File((fichero.getName()).substring(0,(fichero.getName()).lastIndexOf('.'))+".ERR");
        if (archivoErrores.exists()) {
        	archivoErrores.delete();
        }



		String name = (fichero.getName()).substring(0,(fichero.getName()).lastIndexOf('.'));
		linea_ens.n = name;
		File archTbs=new File((fichero.getName()).substring(0,(fichero.getName()).lastIndexOf('.'))+".TDS");
    	if (archTbs.exists()) {
    		archTbs.delete();
    	}

        if(existeono){
        	boolean haytabop = linea_ens.abrirTabop(abo,teclado);
        	try{
        		RandomAccessFile archivo = new RandomAccessFile(ruta,"rw");
    			archivo.seek(0);
    			//Empiezo con tabop

            	if (haytabop) {
            		String strLinea;

	            	//Para archivo Errores
    	        	FileWriter archErr = new FileWriter(archivoErrores,true);
        			//archErr.write("ERRORES---------------\r\n\r\n");

  		      		//Para archivo INST
        	    	FileWriter archInst = new FileWriter(archivoInst,true);
        			//archInst.write("LINEA\t\tCONTLOC\t\tETQ\t\tCODOP\t\tOPER\t\tMODOS DE DIRECCIONAMIENTO\t\t\r\n");
     		   		//archInst.write("------------------------------------------------------------------------------------------------------\r\n");

					RandomAccessFile arcHI = new RandomAccessFile(name+".INST","rw");
    				arcHI.seek(0);
    	 			while (arcHI.getFilePointer()!=arcHI.length()){
    	 				strLinea=arcHI.readLine();
    	 			}
    	 			arcHI.writeBytes("LINEA\t\tCONTLOC\t\tETQ\t\tCODOP\t\tOPER\t\tMODOS DE DIRECCIONAMIENTO\t\t\r\n");
     		   		arcHI.writeBytes("------------------------------------------------------------------------------------------------------\r\n");


					//PARA TABSIM
					FileWriter archTABSIM = new FileWriter(archTbs,true);
					//archivo.seek(0);
					archTABSIM.write("TABSIM---------------\r\n");

            		// Leer el archivo linea por linea
            		while (archivo.getFilePointer()!=archivo.length() && banderaEnd == false){
            			strLinea=archivo.readLine();
						System.out.println(strLinea);
            			numeroLinea++;
            			edoERROR = false;

      		          	linea_ens.etq = "NULL";
            			linea_ens.codop = "NULL";
            			linea_ens.oper = "NULL";

            			StringTokenizer st = new StringTokenizer(strLinea," \t",false); //con false omiti tomar como token el espacio y el tabulador

        	        	//Veo con que empieza la linea para ir a los metodos
            	    	Pattern pat = Pattern.compile("^;.*"); //Espresion regular
     					Matcher mat = pat.matcher(strLinea); //Coincidencia a partir del patron
     				  	if(st.countTokens() != 0){
            	    		if(mat.matches()){
                				//La linea es un comentario
                				linea_ens.validarComentario(linea_ens);
                			}
  		              		else{
        	        			//System.out.println(strLinea);
            	    			pat = Pattern.compile("^[a-zA-Z].*");
     							mat = pat.matcher(strLinea);
                				if(mat.matches()){
                					System.out.println("Empezo con ETIQUETA");
                					//Empezo con ETIQUETA
           		     				if (st.countTokens()== 1){
                						edoERROR = true;
                						linea_ens.errorLinea = true;
                						linea_ens.guardarError(archErr,"ERROR la linea solo contiene etiqueta",numeroLinea);
                					}
                					else
                						edoERROR = linea_ens.validarEtiqueta(st,linea_ens,edoERROR,archErr,numeroLinea);
                				}
                				else{
                						pat = Pattern.compile("^[\t ].*"); //Espresion regular
     									mat = pat.matcher(strLinea); //Coincidencia a partir del patron
     									System.out.println("WIII EMPEZO ESPACIO PUEDE SER CODOP");

     									String contenido = new String (st.nextToken());
     									System.out.println("CONTENIDO: "+contenido);
        	        					if(mat.matches() && contenido.matches("^[a-zA-Z].*")){
            	    						//Empezo con CODOP
            	    						System.out.println("Empezo con CODOP CONTENIDO: "+contenido);

                							edoERROR = linea_ens.validarCodigo(st,contenido,linea_ens,edoERROR,archErr,numeroLinea);
                						}
                						else
                							if(contenido.matches("^;.*"))
                								linea_ens.validarComentario(linea_ens);

    	            						else{
                								edoERROR = true;
                								linea_ens.errorLinea = true;
    											linea_ens.guardarError(archErr,"ERROR la etiqueta debe empezar con letra",numeroLinea);
                							}

                				}
                			}
         		       		if((linea_ens.codop).equalsIgnoreCase("END")){
                				if ((linea_ens.oper).equalsIgnoreCase("NULL")) {
                					banderaEnd = true;
                					//escribirenINST(1,"2",linea_ens,"HI");
                					//escribirenINST(numeroLinea,contlocalidades.conloc,linea_ens,nombrearch);
                					//linea_ens.escribirenINST(numeroLinea,contlocalidades.conloc,linea_ens,name);
                					//archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n");
                					try {
              							RandomAccessFile archInstt = new RandomAccessFile(name+".INST","rw");
    									archInstt.seek(0);
    									while (archInstt.getFilePointer()!=archInstt.length()){
    	 									String strlinea=archInstt.readLine();
    	 								}
    	 								//archInst.writeBytes("\r\n");
    									archInstt.writeBytes(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n");
    									archInstt.close();
              						}
              						catch (Exception e){ //Catch de excepciones
            							System.err.println("Ocurrio un error: " + e.getMessage());
             						}
                					if (!linea_ens.etq.equalsIgnoreCase("NULL"))
                						contlocalidades.EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades.conloc);

                				}
                				else {
                					linea_ens.errorLinea = true;
                					linea_ens.guardarError(archErr,"ERROR la directiva END no debe contener operando",numeroLinea);
                				}

                			}
              	    	}
                		if ((linea_ens.etq.equals("NULL") && linea_ens.codop.equals("NULL") && linea_ens.oper.equals("NULL")) || banderaEnd == true || edoERROR == true){
                			//no imp
                		}
                		else{
                			if(linea_ens.codop.equalsIgnoreCase("ORG")){
                				if(!linea_ens.oper.equalsIgnoreCase("NULL")) {
                					cantORG++;
          		      				if (cantORG == 1) {
                						boolean tiene_directivasuorg = contlocalidades.EmpiezaConDirectivaOConOrg(linea_ens,archErr,numeroLinea,archInst,arboletq,archTABSIM,name);
                						tieneORG = true;
                					}
                					else {
                						linea_ens.errorLinea = true;
                						linea_ens.guardarError(archErr,"ERROR Solo debe de existir un ORG",numeroLinea);
                					}

                				}
                				else{
                					linea_ens.errorLinea = true;
                					linea_ens.guardarError(archErr,"ERROR La directiva ORG debe de tener operando",numeroLinea);
                				}

                			}
                			else
                				if(linea_ens.codop.equalsIgnoreCase("EQU")){
                					if(!linea_ens.etq.equalsIgnoreCase("NULL") && !linea_ens.oper.equalsIgnoreCase("NULL")) {
                						boolean tiene_directivasuorg = contlocalidades.EmpiezaConDirectivaOConOrg(linea_ens,archErr,numeroLinea,archInst,arboletq,archTABSIM,name);
              		  					tieneEQU = true;
                						/*equseguidodeswi = 3;*/
                					}
            	    				if(linea_ens.etq.equalsIgnoreCase("NULL") && linea_ens.oper.equalsIgnoreCase("NULL")) {
            	    					linea_ens.errorLinea = true;
                						linea_ens.guardarError(archErr,"ERROR La directiva EQU debe de tener etiqueta y operando",numeroLinea);
                					}
                					if(!linea_ens.etq.equalsIgnoreCase("NULL") && linea_ens.oper.equalsIgnoreCase("NULL")) {
                						linea_ens.errorLinea = true;
                						linea_ens.guardarError(archErr,"ERROR La directiva EQU debe operando",numeroLinea);
                					}
            	    				if(linea_ens.etq.equalsIgnoreCase("NULL") && !linea_ens.oper.equalsIgnoreCase("NULL")) {
            	    					linea_ens.errorLinea = true;
                						linea_ens.guardarError(archErr,"ERROR La directiva EQU debe de tener etiqueta",numeroLinea);
                					}
                				}
                				else {
                					if (!linea_ens.codop.equalsIgnoreCase("END")){

              			  				if (tieneORG == false && tieneEQU == false) {
                							System.out.println("ENTRO ORG EQU");
                							contlocalidades.conloc = "0000";
           			     					tieneORG = true;
                							tieneEQU = true;
                							linea_ens.errorLinea = true;
                							linea_ens.errorEnd(archErr,"ERROR el programa deberia iniciar con directiva ORG o EQU");
                						}
                						boolean tiene_directivasuorg = contlocalidades.EmpiezaConDirectivaOConOrg(linea_ens,archErr,numeroLinea,archInst,arboletq,archTABSIM,name);

		                				//if (tiene_directivasuorg == false)


        		        				if (tiene_directivasuorg == false) {

                							/*if(equseguidodeswi == 3 && linea_ens.codop.equalsIgnoreCase("SWI")) {
                								equseguidodeswi = 0;
                								contlocalidades.conloc = "0000";
                							}*/


                							System.out.println("ANTES verif si esta en tabop");
                							boolean exist = abo.existe(linea_ens, archInst,numeroLinea,archErr,contlocalidades,arboletq,archTABSIM,name);
                							System.out.println("verif si esta en tabop");
                							System.out.println("exist: " +exist);
                							if (exist == false){
                								linea_ens.errorLinea = true;
                								System.out.println("no esta en tabop");
                								linea_ens.guardarError(archErr,"ERROR El Codigo de Operacion no existe en el TABOP",numeroLinea);
                								System.out.println("no esta en tabop");
                							}

                						}
            		    				System.out.println("JAJAJAJA");
                						arboletq.imprimirEntre();
                					}
                				}
                		}
                		System.out.println("....*****........."+numeroLinea+ "  "+linea_ens.etq+"  "+linea_ens.codop+"  "+ linea_ens.oper);


    	            	}

         	       if (banderaEnd == false){
            	       	edoERROR = true;
            	       	linea_ens.errorLinea = true;
                		linea_ens.errorEnd(archErr,"ERROR no se encuentra la directiva END (con su formato de linea correcto)");
            	    }
           			// Cerrar archivos
      	    	  	archivo.close();
					archErr.close();
					arcHI.close();
	            	archInst.close();
    	        	archTABSIM.close();
        	    	}
            		else {
            			System.out.println("\nEl archivo no existe o la ruta es incorrecta, VERIFIQUELO...");
            		}
            }catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
             }
             if(haytabop) {
              	boolean errorr = linea_ens.VerifEtisOpers(arboletq,name,linea_ens);
           		linea_ens.reEscribirInst (fichero,name);
              	linea_ens.reCalcularCONLOC(fichero,name,arboletq,abo,name);
              	if (errorr == false && linea_ens.errorLinea == false) {
              		linea_ens.escribirCodigoMaquina(abo,name,linea_ens,arboletq);
              		//linea_ens.InstDefinitivo(fichero,name);
              		linea_ens.reEscribirInst (fichero,name);
              		linea_ens.reCalcularCONLOC(fichero,name,arboletq,abo,name);
              		errorr = linea_ens.VerifEtisOpers(arboletq,name,linea_ens);
           			linea_ens.reEscribirInst (fichero,name);
              		linea_ens.reCalcularCONLOC(fichero,name,arboletq,abo,name);
              		linea_ens.escribirCodigoMaquina(abo,name,linea_ens,arboletq);
              		linea_ens.InstDefinitivo(fichero,name);

              		if (linea_ens.errorLinea == false) {
              			s19 archs19 = new s19();
              			//archs19.crears19("File: AsmTemp.asm",name);
              			archs19.crears19(ruta,name);
              		}
              	}
              	else {
              		try {
              			RandomAccessFile archErr = new RandomAccessFile(name+".ERR","rw");
    					archErr.seek(0);
    					while (archErr.getFilePointer()!=archErr.length()){
    	 					String strLinea=archErr.readLine();
    	 				}
    	 				archErr.writeBytes("\r\n");
    					archErr.writeBytes("ERROR No puede seguir al paso 2\r\n");
              		}
              		catch (Exception e){ //Catch de excepciones
            			System.err.println("Ocurrio un error: " + e.getMessage());
             		}

						//arch.seek(point2);

              	}
            }
        }
        else{
        	System.out.println("\nEl archivo no existe o tiene la extension incorrecta, VERIFIQUELO...");
        }
    }
    
    
}

//e:\tronador10_1_2013b.asm
//e:\tronador10_3_2013b.asm
//f:\hola.asm

//e:\HOLA4.asm

//e:\Practica_10\TABOP.txt