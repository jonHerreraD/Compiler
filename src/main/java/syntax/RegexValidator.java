/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package syntax;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author diazj
 */
public class RegexValidator {
    
    String expression;
    Pattern pattern;
    Matcher matcher;
    
    public boolean isAComment(String str){
        expression = "\\/\\/.+?\\/\\/";
        pattern = Pattern.compile(expression);
        matcher = pattern.matcher(str);
        if(matcher.matches()){
            return true;
        }else{
             return false;
        }
    }
    
    public boolean isRelationalOperator(String str){
        expression = "<|>|<=|>=|==|!=";
        pattern = Pattern.compile(expression);
        matcher = pattern.matcher(str);
        return matcher.find();
    }
    
    public boolean isSpecialCharacter(String str){
        expression = "\\(|\\)|;|,|:";
        pattern = Pattern.compile(expression);
        matcher = pattern.matcher(str);
        if(matcher.matches()){
            return true;
        }else{
             return false;
        }
    }
    
    public boolean isIdentifier(String str){
        expression = "[A-Za-z][A-Za-z0-9_]*[#%&$?]";
        pattern = Pattern.compile(expression);
        matcher = pattern.matcher(str);
        if(matcher.matches()){
            return true;
        }else{
            return false;   
        }
    }
    
    public boolean isArithmeticOperator(String str){
        expression = "-|\\+|\\/|\\*|:=|%%";
        pattern = Pattern.compile(expression);
        matcher = pattern.matcher(str);
        if(matcher.matches()){
            return true;
        }else{
            return false;   
        }
    }
    
    public boolean isLogicalOperator(String str){
        expression = "&&|\\|\\||!";
        pattern = Pattern.compile(expression);
        matcher = pattern.matcher(str);
        if(matcher.matches()){
            return true;
        }else{
            return false;   
        }
    }
    
    public boolean isReservedWord(String str){
        expression = "program|begin|end|read|write|int|real|string|bool|if|else|then|while|do|repeat|until|var";
        pattern = Pattern.compile(expression);
        matcher = pattern.matcher(str);
        if(matcher.matches()){
            return true;
        }else{
            return false;   
        }
    }
    
    public boolean isDecimalNumber(String str){
        expression = "-?\\d+\\.\\d+";
        pattern = Pattern.compile(expression);
        matcher = pattern.matcher(str);
        if(matcher.matches()){
            return true;
        }else{
            return false;   
        }
    }
    
    public boolean isConstantString(String str){
        expression = "\\\".+?\\\"";
        pattern = Pattern.compile(expression);
        matcher = pattern.matcher(str);
        if(matcher.matches()){
            return true;
        }else{
            return false;   
        }
    }
    
    public boolean isLogicalValue(String str){
        expression = "true|false";
        pattern = Pattern.compile(expression);
        matcher = pattern.matcher(str);
        if(matcher.matches()){
            return true;
        }else{
            return false;   
        }
    }
    
    public boolean hasNoToken(String str){
        expression ="[.\\s\\t\\r]";
        pattern = Pattern.compile(expression);
        matcher = pattern.matcher(str);
        if(matcher.matches()){
            return true;
        }else{
            return false;   
        }
    }
    
    public boolean isIntegerNumber(String str){
        expression ="^[0-9]+$";
        pattern = Pattern.compile(expression);
        matcher = pattern.matcher(str);
        if(matcher.matches()){
            return true;
        }else{
            return false;   
        }
    }
     
}
