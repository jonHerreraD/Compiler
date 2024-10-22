/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package syntax;

/**
 *
 * @author diazj
 */
public class Tokens { 
    public int arithmeticTokens(String str){
         switch (str) {
                case "+":
                    return -24;
                case"-":
                    return -25;
                case"*":
                        return -21;
                case "/":
                        return -22;
                 case ":=":
                        return -26;
             case "%%":
                 return -27;
         }
         return 0;
    }
    
    public int identifierTokens(String str){
        String last = str.substring(str.length()-1);
         switch (last) {
                case "&":
                    return -51;
                case"%":
                    return -52;
                case"#":
                        return -53;
                case "$":
                        return -54;
                 case "?":
                        return -55;
         }
         return 0;
    }
    
    public int relationalTokens(String str){
         switch (str) {
                case "<":
                    return -31;
                case">":
                    return -33;
                case"<=":
                    return -32;
                case ">=":
                    return -34;
                 case "==":
                    return -35;
                case "!=":
                    return -36;
         }
         return 0;
    }
    
     public int specialCharacterTokens(String str){
         switch (str) {
                case "(":
                    return -73;
                case")":
                    return -74;
                case";":
                    return -75;
                case ",":
                    return -76;
                 case ":":
                    return -77;
         }
         return 0;
    }
     
    
     public int logicTokens(String str){
         switch (str) {
                case "&&":
                    return -41;
                case"||":
                    return -42;
                case"!":
                    return -43;
         }
         return 0;
    }
     
     public int reservedWordTokens(String str){
         switch (str) {
                case "program":
                    return -1;
                case"begin":
                    return -2;
                case"end":
                    return -3;
                case "read":
                    return -4;
                 case "write":
                    return -5;
                case "int":
                    return -11;
                case "real":
                    return -12;
                case "string":
                    return -13;
                case "bool":
                    return -14;
                case "if":
                    return -6;
                case "else":
                    return -7;
                case "then":
                    return -16;
                case "while":
                    return -8;
                case "do":
                    return -17;
                case "repeat":
                    return -9;
                case "until":
                    return -10;
                case "var":
                    return -15;
         }
         return 0;
    }
     
      public int logicalValueTokens(String str){
         switch (str) {
                case "true":
                    return -64;
                case"false":
                    return -65;
         }
         return 0;
    }
    
}
