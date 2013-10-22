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
                					edoERROR = linea_ens.validarCodigo(str,contenido,linea_ens,edoERROR,archErr,numeroLinea);
                				}
                				else{
                					edoERROR = true;
    								linea_ens.guardarError(archErr,"ERROR El codigo de operacion debe empezar con letra",numeroLinea);
                				}
                			}
    		}
    		else{
    			edoERROR = true;
    			linea_ens.guardarError(archErr,"ERROR la Etiqueta contiene caracteres no alfanumericos",numeroLinea);
    		}

    	}
    	else{
    		edoERROR = true;
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
 									linea_ens.codop = contenido;
 								}
 								else{
 									edoERROR = true;
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
 									linea_ens.guardarError(archErr,"ERROR el codigo de operacion solo puede contener caracteres alfanumericos y/o un punto",numeroLinea);
 								}

 							break;
 					}

 				}
 				else{
 					edoERROR = true;
 					linea_ens.guardarError(archErr,"ERROR el codigo de operacion solo puede contener letras y/o un punto",numeroLinea);
 				}
 				else {// este es de si hay mas puntos en vecespuntos
 					edoERROR = true;
 					linea_ens.guardarError(archErr,"ERROR el codigo de operacion solo puede contener letras y/o un punto",numeroLinea);
 				}
    		}
    		else{
    			edoERROR = true;
    			linea_ens.guardarError(archErr,"ERROR el codigo de operacion solo puede empezar con letras mayusculas o minusculas",numeroLinea);
    		}
    		while (str.hasMoreTokens() && edoERROR == false) {
    			contenido = str.nextToken();
    			edoERROR = linea_ens.validarOperando(str,contenido,linea_ens,edoERROR,archErr,numeroLinea);
       		}
    	}
    	else{
    		edoERROR = true;
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
        	if(contenido.charAt(0) == enteroChar) {
        		System.out.println("Aqui para FCC");
        		linea_ens.oper = contenido;
        		if(str.hasMoreTokens()){
        			contenido = str.nextToken();
        			linea_ens.oper = linea_ens.oper + " " + contenido;
        		}

        	}
        	else {
        		if(contenido.matches(".+")){
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
    					linea_ens.guardarError(archErr,"ERROR los operandos deben de ir separados por comas, no por espacios",numeroLinea);
        			}
        		}
        	}
        }
        return edoERROR;
    }

    public void validarComentario(Linea linea_ens){
    	linea_ens = new Linea("NULL","NULL","NULL");
    }

    public void guardarError(FileWriter archErr, String error, int numeroLinea){
    	try{
    		archErr.write("Linea "+numeroLinea+": "+error+"\r\n");
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

    public void abrirTabop(ArbolBinarioOrdenado abo,Scanner teclado){
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

    }

    public void VerifEtisOpers(ArbolEtiquetas arboletq,String name, FileWriter archErr, Linea linea_ens){
    	String strLinea="";
    	File fichero=new File(name+".INST");
    	if(fichero.exists()){
        	System.out.println("Nombre del archivo "+fichero.getName());
        	System.out.println("Camino             "+fichero.getPath());
        	System.out.println("Camino absoluto    "+fichero.getAbsolutePath());
        	System.out.println("Se puede escribir  "+fichero.canRead());
        	System.out.println("Se puede leer      "+fichero.canWrite());
        	System.out.println("Tamaño             "+fichero.length());
   		}
    	 try{
    	 	archErr.write("\r\n");

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
                if (!contenido.equals("LINEA")) {
                	if(st.hasMoreTokens()){
                		if (l == 0) {
                			String nlinea = contenido;
                			numerolinea = Integer.parseInt(nlinea,10);
                			l = 1;
                		}
        				contenido = st.nextToken();
        				contenido = st.nextToken();
        				contenido = st.nextToken();
        				contenido = st.nextToken();
        				System.out.println ("----------"+numerolinea+contenido);


        				if (contenido.matches ("^[a-zA-Z].*") && contenido.matches("[a-zA-Z0-9_]+") && !contenido.equalsIgnoreCase("NULL")){
    						if(contenido.length()<=8){
    							System.out.println("Oper etiqueta");
    							try{
    								//archInst.write(numeroLinea+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\t\t"+"EXT"+"\r\n");
    								boolean existeEtq = arboletq.existe (contenido,arboletq);
    								if (existeEtq == false) {
    									arch.seek(point);
    									arch.writeBytes("-------------------------------------------------------------------------------------");
    									linea_ens.guardarError(archErr,"ERROR El operando como etiqueta no existe",numerolinea);

    								}
    								else {
    									//PERFECTO SI EXISTE
    								}

    								}
    							catch (Exception e){ //Catch de excepciones
    								System.err.println("Ocurrio un error: " + e.getMessage());
              					}
    						}
    						else {
    							System.out.println ("ERROR Operando Etiqueta fuera de longitud");

    							linea_ens.guardarError(archErr,"ERROR Operando Etiqueta fuera de longitud",numerolinea);
    						}
    					}






        				//linea_ens.oper = linea_ens.oper + " " + contenido;
        			}
                }

            }
            // Cerramos el archivo
            arch.close();
            archErr.close();
        }catch (Exception e){ //Catch de excepciones
            System.err.println("Ocurrio un error: " + e.getMessage());
        }


    }

    public static void main(String[] args) {
       	Linea linea_ens=new Linea("NULL","NULL","NULL");
       	String ruta = new String();
       	boolean existeono, banderaEnd = false, edoERROR = false;
       	int numeroLinea = 0;

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
		File archTbs=new File((fichero.getName()).substring(0,(fichero.getName()).lastIndexOf('.'))+".TDS");
    	if (archTbs.exists()) {
    		archTbs.delete();
    	}

        if(existeono){
        	try{
        		RandomAccessFile archivo = new RandomAccessFile(ruta,"rw");
    			archivo.seek(0);
    			//Empiezo con tabop
            	ArbolBinarioOrdenado abo = new ArbolBinarioOrdenado ();

            	linea_ens.abrirTabop(abo,teclado);

            	String strLinea;

            	//Para archivo Errores
            	FileWriter archErr = new FileWriter(archivoErrores,true);
        		archErr.write("ERRORES---------------\r\n\r\n");

        		//Para archivo INST
            	FileWriter archInst = new FileWriter(archivoInst,true);
        		archInst.write("LINEA\t\tCONTLOC\t\tETQ\t\tCODOP\t\tOPER\t\tMODOS DE DIRECCIONAMIENTO\t\t\r\n");
        		archInst.write("------------------------------------------------------------------------------------------------------\r\n");

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
                				linea_ens.guardarError(archErr,"ERROR la linea solo contiene etiqueta",numeroLinea);
                				}
                				else
                					edoERROR = linea_ens.validarEtiqueta(st,linea_ens,edoERROR,archErr,numeroLinea);
                			}
                			else{
                					pat = Pattern.compile("^[\t ].*"); //Espresion regular
     								mat = pat.matcher(strLinea); //Coincidencia a partir del patron

     								String contenido = new String (st.nextToken());
                					if(mat.matches() && contenido.matches("^[a-zA-Z].*")){
                						//Empezo con CODOP

                						edoERROR = linea_ens.validarCodigo(st,contenido,linea_ens,edoERROR,archErr,numeroLinea);
                					}
                					else
                						if(contenido.matches("^;.*"))
                							linea_ens.validarComentario(linea_ens);

                						else{
                								edoERROR = true;
    											linea_ens.guardarError(archErr,"ERROR la etiqueta debe empezar con letra",numeroLinea);
                						}

                			}
                		}
                		if((linea_ens.codop).equalsIgnoreCase("END")){
                			if ((linea_ens.oper).equalsIgnoreCase("NULL"))
                			{
                				banderaEnd = true;
                				archInst.write(numeroLinea+"\t\t"+contlocalidades.conloc+"\t\t"+linea_ens.etq+"\t\t"+linea_ens.codop+"\t\t"+linea_ens.oper+"\r\n");
                				contlocalidades.EscribirEnTABSIM(archTABSIM,linea_ens,contlocalidades.conloc);

                			}
                			else
                				linea_ens.guardarError(archErr,"ERROR la directiva END no debe contener operando",numeroLinea);
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
                					boolean tiene_directivasuorg = contlocalidades.EmpiezaConDirectivaOConOrg(linea_ens,archErr,numeroLinea,archInst,arboletq,archTABSIM);
                					tieneORG = true;
                				}
                				else
                					linea_ens.guardarError(archErr,"ERROR Solo debe de existir un ORG",numeroLinea);
                			}
                			else
                				linea_ens.guardarError(archErr,"ERROR La directiva ORG debe de tener operando",numeroLinea);
                		}
                		else
                			if(linea_ens.codop.equalsIgnoreCase("EQU")){
                				if(!linea_ens.etq.equalsIgnoreCase("NULL") && !linea_ens.oper.equalsIgnoreCase("NULL")) {
                					boolean tiene_directivasuorg = contlocalidades.EmpiezaConDirectivaOConOrg(linea_ens,archErr,numeroLinea,archInst,arboletq,archTABSIM);
                					tieneEQU = true;
                					/*equseguidodeswi = 3;*/
                				}
                				if(linea_ens.etq.equalsIgnoreCase("NULL") && linea_ens.oper.equalsIgnoreCase("NULL")) {
                					linea_ens.guardarError(archErr,"ERROR La directiva EQU debe de tener etiqueta y operando",numeroLinea);
                				}
                				if(!linea_ens.etq.equalsIgnoreCase("NULL") && linea_ens.oper.equalsIgnoreCase("NULL")) {
                					linea_ens.guardarError(archErr,"ERROR La directiva EQU debe operando",numeroLinea);
                				}
                				if(linea_ens.etq.equalsIgnoreCase("NULL") && !linea_ens.oper.equalsIgnoreCase("NULL")) {
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
                					linea_ens.errorEnd(archErr,"ERROR el programa deberia iniciar con directiva ORG o EQU");
                				}
                				boolean tiene_directivasuorg = contlocalidades.EmpiezaConDirectivaOConOrg(linea_ens,archErr,numeroLinea,archInst,arboletq,archTABSIM);

                				//if (tiene_directivasuorg == false)


                				if (tiene_directivasuorg == false) {

                					/*if(equseguidodeswi == 3 && linea_ens.codop.equalsIgnoreCase("SWI")) {
                						equseguidodeswi = 0;
                						contlocalidades.conloc = "0000";
                					}*/


                					boolean exist = abo.existe(linea_ens, archInst,numeroLinea,archErr,contlocalidades,arboletq,archTABSIM);
                					if (exist == false){
                						linea_ens.guardarError(archErr,"ERROR El Codigo de Operacion no existe en el TABOP",numeroLinea);
                					}

                				}
                				System.out.println("JAJAJAJA");
                				arboletq.imprimirEntre();
                			}
                		}
                	}
            	  }

                if (banderaEnd == false){
                   	edoERROR = true;
                	linea_ens.errorEnd(archErr,"ERROR no se encuentra la directiva END (con su formato de linea correcto)");
                }
           		// Cerrar archivos
            	archivo.close();

            	archInst.close();
            	archTABSIM.close();
            	linea_ens.VerifEtisOpers(arboletq,name,archErr,linea_ens);

            }catch (Exception e){ //Catch de excepciones
            	System.err.println("Ocurrio un error: " + e.getMessage());
              }
        }
        else{
        	System.out.println("\nEl archivo no existe o tiene la extension incorrecta, VERIFIQUELO...");
        }
    }
}

//F:\hola.asm
//F:\P_4\Practica_4\TABOP.txt


//F:\tronador03_2013b.asm
//F:\P_4\Practica_4\TABOP.txt

//F:\tronador04_2013b.asm
//F:\P_4\Practica_4\TABOP.txt
