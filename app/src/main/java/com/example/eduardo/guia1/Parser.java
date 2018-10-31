package com.example.tito.guia1;

/**
 * Created by admin on 15/8/17.
 */

/*
*
* Parser creado en la materia de Compiladores - USO
* Fecha : Noviembre - 2016
* Autor : Jonathan Giovanni Hernandez
* Descripcion : el parser evalua una expresion matematica
*               y muestra el resultado, ademas controla errores
*               de entrada, y posee la capacidad para evaluar parentesis
*               de manera recursiva, utiliza el analisis descendente recursivo
* */


public class Parser {
    public static String evaluar(final String entrada) {
        return new Object() {
            int pos = -1, ch;
            void siguienteChar() {
                ch = (++pos < entrada.length()) ? entrada.charAt(pos) : -1;
            }

            boolean comer(int charAComer) {
                while (ch == ' ') siguienteChar();
                if (ch == charAComer) {
                    siguienteChar();
                    return true;
                }
                return false;
            }

            String parse() {
                siguienteChar();
                double x = parseExpresion();
                if (pos < entrada.length()){
                    return "Error en "+(char)ch;
                }
                return simplificar(x);
            }

            String simplificar(double input){
                if(input%1!=0){
                    return Double.toString(input);
                }else{
                    return Integer.toString((int)input);
                }
            }

            // Gramatica:
            // expresion = termino | expresion '+' termino | expresion '-' termino
            // termino = factor | termino '*' factor | termino '/' factor
            // factor = '(' expresion ')' | numero

            double parseExpresion() {
                double x = parseTermino();
                for (;;) {
                    if      (comer('+')) x += parseTermino(); // suma
                    else if (comer('-')) x -= parseTermino(); // resta
                    else return x;
                }
            }

            double parseTermino() {
                double x = parseFactor();
                for (;;) {
                    if      (comer('*')) x *= parseFactor(); // multiplicacion
                    else if (comer('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                double x=0;
                int startPos = this.pos;
                if (comer('(')) { // parentesis
                    x = parseExpresion();
                    comer(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numeros
                    while ((ch >= '0' && ch <= '9') || ch == '.') siguienteChar();
                    x = Double.parseDouble(entrada.substring(startPos, this.pos));
                } else {
                    //throw new RuntimeException("Error inesperado en :"+(char)ch  );
                }
                return x;
            }
        }.parse();
    }


}
