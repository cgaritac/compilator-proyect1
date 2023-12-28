/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carlosgarita.proyecto1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author cgari
 */
public class Procesamiento {
    
    public void manejoArchivo (String nombArch, String nombNuevArch) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(nombArch));
            BufferedWriter writer = new BufferedWriter(new FileWriter(nombNuevArch));
            
            ArrayList<String> arregloDeStrings = new ArrayList<>();
            ArrayList<String> arregloDeEnteros = new ArrayList<>();
            ArrayList<String> arregloDeBooleanos = new ArrayList<>();
            ArrayList<String> arregloDeLong = new ArrayList<>();
            ArrayList<String> arregloDeFloat = new ArrayList<>();
            ArrayList<String> arregloDeDouble = new ArrayList<>();
            ArrayList<String> arregloDeChar = new ArrayList<>();
            ArrayList<String> arregloDeShort = new ArrayList<>();
            
            String linea;
            
            int lineCount = 0;
            int errorCode = 0;
            int contAvance = 0;
            int tipo = 0;
            int llavesApertura = 0;
            int llavesCierre = 0;
            int parentCuadApertura = 0;
            int parentCuadCierre = 0;
            int parentRedonApertura = 0;
            int parentRedonCierre = 0;
            int palabrasReservadas = 0;
            int identificador = 0;
            int signosIgualdad = 0;
            int operadoresAritmeticos = 0;
            int operadoresRelacionales = 0;

            String variable = "";
            
            // Patrón para buscar la línea que comienza con "package" seguido de un nombre válido.
            Pattern packagePattern = Pattern.compile("^\\s*package\\s+([a-zA-Z]+)\\s*;\\s*$");    
            
            // Patrón para buscar la línea que comienza con "import" seguido de un nombre válido.
            Pattern importPattern = Pattern.compile("^\\s*import\\s+([a-zA-Z.]+)\\s*;\\s*$");    
            
            // Patrón para buscar la línea que comienza con "import" seguido de un nombre válido.
            Pattern publicClassPattern = Pattern.compile("^\\s*public\\s+class\\s+([a-zA-Z]+)\\s*\\s*$");    
            
             while ((linea = reader.readLine()) != null) {
                lineCount++;               

                if (lineCount <= 9){
                    writer.write(lineCount + "   " + linea + "\n");
                    writer.flush();
                }else{
                    writer.write(lineCount + "  " + linea + "\n");
                    writer.flush();
                }
                
                if (!(linea.trim().startsWith("/*")||linea.trim().startsWith("*")||linea.trim().startsWith("*/"))) {
                    
                    llavesApertura += contarCaracter(linea, '{');
                    llavesCierre += contarCaracter(linea, '}');
                    parentCuadApertura += contarCaracter(linea, '[');
                    parentCuadCierre += contarCaracter(linea, ']');
                    parentRedonApertura += contarCaracter(linea, '(');
                    parentRedonCierre += contarCaracter(linea, ')');
                    signosIgualdad += contarCaracter(linea, '=');
                    
                    // Elimina los espacios en blanco y divide la línea en palabras
                    String[] palabras = linea.trim().split("\\s+");
                    
                    for (String palabra : palabras) {
                        if (palabra.matches("abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|int|interface|long|native|new|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while")) {
                            palabrasReservadas++;
                            writer.write(palabra+" es una palabra reservada\n");
                            writer.flush();  
                        } else if (palabra.matches("[a-zA-Z_]\\w*")) {
                            identificador++;
                        } else if (palabra.matches("[+\\-*/%]")){
                            operadoresAritmeticos++;
                        } else if (palabra.matches("[<>]=?|==|!=")){
                            operadoresRelacionales++;
                        }
                    }
                    
                    if(linea.trim().startsWith("int")||linea.trim().startsWith("double")||linea.trim().startsWith("String")||linea.trim().startsWith("boolean")||linea.trim().startsWith("char")||linea.trim().startsWith("long")||linea.trim().startsWith("short")||linea.trim().startsWith("float")){
                        if(linea.trim().startsWith("int")){
                            variable = linea.trim().substring(3).trim();
                            arregloDeEnteros.add(variable);
                        }
                        if(linea.trim().startsWith("double")){
                            variable = linea.trim().substring(6).trim();
                            arregloDeDouble.add(variable);
                        }
                        if(linea.trim().startsWith("String")){
                            variable = linea.trim().substring(6).trim();
                            arregloDeStrings.add(variable);
                        }
                        if(linea.trim().startsWith("boolean")){
                            variable = linea.trim().substring(7).trim();
                            arregloDeBooleanos.add(variable);
                        }
                        if(linea.trim().startsWith("char")){
                            variable = linea.trim().substring(4).trim();
                            arregloDeChar.add(variable);
                        }
                        if(linea.trim().startsWith("long")){
                            variable = linea.trim().substring(4).trim();
                            arregloDeLong.add(variable);
                        }
                        if(linea.trim().startsWith("short")){
                            variable = linea.trim().substring(5).trim();
                            arregloDeShort.add(variable);
                        }
                        if(linea.trim().startsWith("float")){
                            variable = linea.trim().substring(5).trim();
                            arregloDeFloat.add(variable);
                        }
                    }
                }
                
                //Validación de Package
                if (contAvance == 0 && !(linea.trim().startsWith("/*")||linea.trim().startsWith("*")||linea.trim().startsWith("*/")) && !"".equals(linea.trim())) {
                    tipo = 1;
                    contAvance = lineCount;
                    if (linea.trim().startsWith("package")) {
                        
                        // Validar si la línea comienza con "package" seguido de un nombre válido.
                        Matcher packageMatcher = packagePattern.matcher(linea);
                        
                        if(packageMatcher.find()){
                           contAvance = lineCount;
                           
                        }else{
                            errorCode = 3;
                            writer.write(contAvance+"    Error " + errorCode + ": El nombre del paquete no es válido o falta el ';' al final.\n");
                            writer.flush();    
                        }
                    }else {
                        // Si al final del archivo no se ha encontrado la palabra "package", emite un mensaje de error.
                        errorCode = 2;
                        writer.write("    Error " + errorCode + ": No se encontró la palabra 'package'.\n");
                        writer.flush();
                    }
                }
                
                //Validación de imports
                if (!linea.trim().startsWith("public") && (tipo == 1 || tipo == 2) && lineCount > contAvance  && !(linea.trim().startsWith("/*")||linea.trim().startsWith("*")||linea.trim().startsWith("*/")) && !"".equals(linea.trim())) {
                    
                    if (linea.trim().startsWith("import")) {

                        // Validar si la línea comienza con "import" seguido de un nombre válido.
                        Matcher importMatcher = importPattern.matcher(linea);

                        if(importMatcher.find()){
                            contAvance = lineCount;
                            tipo = 2;
                        }else{
                            errorCode = 5;
                            writer.write("    Error " + errorCode + ": El nombre de import no es válido o hace falta el ';' al final.\n");
                            writer.flush();    
                        }
                    }else{
                    errorCode = 4;
                    writer.write("    Error " + errorCode + ": No se encontró la declaración 'import' después de 'package'.\n");
                    writer.flush();
                    } 
                }
                
                //Validación de public class
                if (tipo == 2 && !linea.trim().startsWith("import") && lineCount > contAvance  && !(linea.trim().startsWith("/*")||linea.trim().startsWith("*")||linea.trim().startsWith("*/")) && !"".equals(linea.trim())) {
                    tipo = 3;
                    contAvance = lineCount;
                    if (linea.trim().startsWith("public class")) {

                        // Validar si la línea comienza con "public class" seguido de un nombre válido.
                        Matcher importMatcher = publicClassPattern.matcher(linea);

                        if(!importMatcher.find()){
                            errorCode = 7;
                            writer.write("    Error " + errorCode + ": El nombre de public class no es válido.\n");
                            writer.flush();  
                            
                        }
                    }else{
                    errorCode = 6;
                    writer.write("    Error " + errorCode + ": No se encontró la declaración 'puclic class' después de 'import'.\n");
                    writer.flush();
                    }  
                }
                
                //Validación de public static void main(String[] args)
                if (tipo == 3 && lineCount > contAvance && !(linea.trim().startsWith("/*")||linea.trim().startsWith("*")||linea.trim().startsWith("*/")) && !"".equals(linea.trim())) {
                    contAvance = lineCount;
                    tipo = 4;
                    if (!linea.trim().contains("public static void main(String[] args)")) {
                        errorCode = 8;
                        writer.write("    Error " + errorCode + ": No se encontró la declaración 'public static void main(String[] args)'.\n");
                        writer.flush();
                    } 
                }
                
                //Validación del resto del texto
                if (tipo == 4 && lineCount > contAvance && !(linea.trim().startsWith("/*")||linea.trim().startsWith("*")||linea.trim().startsWith("*/")) && !"".equals(linea.trim())) {
                    
                    if(contarCaracter(linea, '(') != contarCaracter(linea, ')')){
                        errorCode = 9;
                        writer.write("    Error " + errorCode + ": El número de paréntesis no es igual, hace falta cerrar un paréntesis redondo'.\n");
                        writer.flush();
                    }

                    if(contarCaracter(linea, '[') != contarCaracter(linea, ']')){
                        errorCode = 10;
                        writer.write("    Error " + errorCode + ": El número de paréntesis no es igual, hace falta cerrar un paréntesis cuadrado'.\n");
                        writer.flush();
                    }

                    if(!(linea.trim().startsWith("{")||linea.trim().startsWith("}")||linea.trim().startsWith("switch")||linea.trim().startsWith("case"))){
                        if(!linea.trim().endsWith(";")){
                             errorCode = 12;
                             writer.write("    Error " + errorCode + ": Hace falta el punto y coma al final'.\n");
                             writer.flush();
                        }
                    }
                   
                    if(linea.contains("System.out.println")){

                        int inicioParentesis = linea.indexOf("(");
                        int finParentesis = linea.lastIndexOf(")");

                        if (inicioParentesis != -1 && finParentesis != -1) {
                            
                            // Obtener el contenido entre los paréntesis
                            String contenido = linea.substring(inicioParentesis + 1, finParentesis);

                            // Comprueba si el contenido contiene comillas dobles
                            if (contenido.contains("\"")) {
                                if(contarCaracter(contenido, '"')%2!=0){
                                    errorCode = 13;
                                    writer.write("    Error " + errorCode + ": Hace falta cerrar las comillas'.\n");
                                    writer.flush();
                                }                                
                            }
                        }
                    }
                    
                    if (linea.trim().length() > 80 && !(linea.trim().startsWith("/*")||linea.trim().startsWith("*")||linea.trim().startsWith("*/"))) {
                    errorCode = 1;
                    writer.write("    Error " + errorCode + ": La línea tiene más de 80 caracteres.\n");
                    writer.flush();
                    }
                }
             }
             if(llavesApertura != llavesCierre){
                 errorCode = 11;
                 writer.write("    Error " + errorCode + ": Hace falta cerrar una llave'.\n");
                 writer.flush();
             }
             
             writer.write("-------------- Estadísticas ----------------\n");
             
             writer.write("Cantidad de Llaves Apertura: " + llavesApertura + "\n");
             writer.write("Cantidad de Llaves Cierre: " + llavesCierre + "\n");
             writer.write("Cantidad de Paréntesis Cuadrados de Apertura: " + parentCuadApertura + "\n");
             writer.write("Cantidad de Paréntesis Cuadrados de Cierre: " + parentCuadCierre + "\n");
             writer.write("Cantidad de Paréntesis Redondos de Apertura: " + parentRedonApertura + "\n");
             writer.write("Cantidad de Paréntesis Redondos de Cierre: " + parentRedonCierre + "\n");
             writer.write("Cantidad de Palabras Reservadas: " + palabrasReservadas + "\n");
             writer.write("Cantidad de Identificadores: " + identificador + "\n");
             writer.write("Cantidad de Signos de Igualdad: " + signosIgualdad + "\n");
             writer.write("Cantidad de Operadores Aritméticos: " + operadoresAritmeticos + "\n");
             writer.write("Cantidad de Operadores Relacionales: " + operadoresRelacionales + "\n");

             writer.flush();

        } catch (FileNotFoundException ex)
        {
            System.out.println("Archivo no encontrado");
        } catch (IOException ex){
            System.out.println("Archivo no encontrado o no se pudo abrir");
        }
    }
    
    public int contarCaracter(String linea, char caracter){
        int cantidad = 0;
        
        for(int i = 0; i < linea.length(); i++){
            if (linea.charAt(i) == caracter) {
                cantidad++;
            }
        }
        
        return cantidad;
    }
}
