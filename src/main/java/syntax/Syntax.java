
package syntax;

import java.util.List;

/**
 *
 * @author diazj
 */
public class Syntax {

   RegexValidator validator = new RegexValidator();
    //List<Component> componentList = new ArrayList<>();
    boolean error = false;
    
    public Syntax() {

    }
    
    public boolean checkSyntax(List<Component> componentList){
        if(componentList.get(0).token == -1){
            componentList.remove(0);
            //System.out.println(componentList.toString());
            if(validator.isIdentifier(componentList.get(0).getLex())){
                componentList.remove(0);
               // System.out.println(componentList.toString());
                if(componentList.get(0).getLex().equals(";")){
                    componentList.remove(0);
                    if(checkVariable(componentList)){
                        if(main(componentList)){
                            
                           // System.out.println("Correcto");
                           // System.out.println("Variables");
                             //for(Variables v : variables){
                           // System.out.println(v.getName()+", "+v.getValue()+","+v.getType());
                        //}
                        System.out.println("Sintaxis Correcta");
                        
                        if(error == true){
                            error = false;
                            return false;
                        }
                        }
                    }
                }else{
                    System.out.println("Error en linea: "
                            + componentList.get(0).getLine() + " se esperaba: ;" );
                    error = true;
                }
            }else{
                System.out.println("Error en linea: "
                        + componentList.get(0).getLine() + " se esperaba: Identidicador" );
                error = true;
            }
            
        }else{
            System.out.println("Error en linea: "
                    + componentList.get(0).getLine() + " se esperaba: Palabra reservada program" );
            error = true;
        }
        return false;
    }
    
     public boolean main(List<Component> componentList){
        if(componentList.get(0).getToken() == -2){
            componentList.remove(0);
            while(componentList.get(0).getToken()!=-3){

                if(assignment(componentList)){

                }

                if(checkIf(componentList) ||checkWhile(componentList)||
                        checkRepeat(componentList)||checkWrite(componentList)||checkRead(componentList)){
                    if(componentList.isEmpty()){
                        return true;
                    }
                }
                 
                 if(error == true){
                     return false;
                 }
                 
                 if(componentList.get(0).getToken() == -3){
                     return true;
                 }
            }
           
        }
        return false;
    }
     
    public void checkElse(List<Component> componentList){
        if(componentList.get(0).getToken() == -7){
            componentList.remove(0);
            if(componentList.get(0).getToken() == -2){
                componentList.remove(0);
                while(componentList.get(0).getToken() != -3){
                    if(assignment(componentList)||checkIf(componentList) ||checkWhile(componentList)||
                    checkRepeat(componentList)||checkWrite(componentList)||checkRead(componentList)){
                        }
                }
                 if(componentList.get(0).getToken() == -3){
                    componentList.remove(0);

                }else{
                     System.out.println("Error en linea: "
                             + componentList.get(0).getLine() + " se esperaba: end" );
                     error = true;
                 }
            }else{
                System.out.println("Error en linea: "
                        + componentList.get(0).getLine() + " se esperaba: begin" );
                error = true;
            }
        }
    }
    
    public boolean checkIf(List<Component> componentList){
        if(componentList.get(0).getToken() == -6){
            componentList.remove(0);
            if(componentList.get(0).getLex().equals("(")){
                componentList.remove(0);
                
                if(validator.isIdentifier(componentList.get(0).getLex()) ||
                        validator.isDecimalNumber(componentList.get(0).getLex())||
                        validator.isIntegerNumber(componentList.get(0).getLex())){
                    componentList.remove(0);
                    if(validator.isRelationalOperator(componentList.get(0).getLex())||
                    validator.isArithmeticOperator(componentList.get(0).getLex())){
                        componentList.remove(0);
                        if(validator.isIdentifier(componentList.get(0).getLex()) ||
                                validator.isDecimalNumber(componentList.get(0).getLex())||
                                validator.isIntegerNumber(componentList.get(0).getLex())||
                                validator.isLogicalValue(componentList.get(0).getLex())){
                            componentList.remove(0);
                            if(validator.isArithmeticOperator(componentList.get(0).getLex())||
                            validator.isRelationalOperator(componentList.get(0).getLex())){
                                componentList.remove(0);
                                if (validator.isIntegerNumber(componentList.get(0).getLex())||
                                        validator.isIdentifier(componentList.get(0).getLex())||
                                        validator.isDecimalNumber(componentList.get(0).getLex())){
                                    componentList.remove(0);
                                }
                            }
                            if(componentList.get(0).getLex().equals(")")){
                                componentList.remove(0);
                                if(componentList.get(0).getToken() ==-16){
                                    componentList.remove(0);
                                    if(componentList.get(0).getToken() == -2){
                                        componentList.remove(0);
                                        while(componentList.get(0).getToken() != -3){
                                            if(assignment(componentList)||checkIf(componentList)
                                                    ||checkWhile(componentList)|| checkRepeat(componentList)||
                                                    checkWrite(componentList)||checkRead(componentList)){
                                            }
                                        }
                                        if(componentList.get(0).getToken() == -3){
                                            componentList.remove(0);
                                            checkElse(componentList);
                                            return true;
                                        }
                                    } 
                            }else{
                                System.out.println("Error en linea: "
                                        + componentList.get(0).getLine() + " se esperaba: then" );
                                error = true;
                            }
                        }else{
                          System.out.println("Error en linea: "
                                  + componentList.get(0).getLine() + " se esperaba: )" );
                          error = true;
                        }
                    }else{
                            System.out.println("Error en linea: "
                                    + componentList.get(0).getLine() + " se esperaba: identificador o numero" );
                            error = true;
                        }

                }else{
                        System.out.println("Error en linea: "
                                + componentList.get(0).getLine() + " se esperaba: Operador relacional" );
                        error = true;
                    }
            }else{
                    System.out.println("Error en linea: "
                            + componentList.get(0).getLine() + " se esperaba: identificador o numero" );
                    error = true;
                }
        }else{
                System.out.println("Error en linea: "
                        + componentList.get(0).getLine() + " se esperaba: (" );
                error = true;
            }

    }
        return false;
    }
    
    public boolean checkWhile(List<Component> componentList){
        if(componentList.get(0).getToken() == -8){
            componentList.remove(0);
            if(componentList.get(0).getLex().equals("(")){
                componentList.remove(0);
                if(validator.isIdentifier(componentList.get(0).getLex()) ||
                        validator.isDecimalNumber(componentList.get(0).getLex())||
                        validator.isIntegerNumber(componentList.get(0).getLex())){
                    componentList.remove(0);
                    if(validator.isRelationalOperator(componentList.get(0).getLex())){
                        componentList.remove(0);
                        if(validator.isIdentifier(componentList.get(0).getLex()) ||
                                validator.isDecimalNumber(componentList.get(0).getLex())||
                                validator.isIntegerNumber(componentList.get(0).getLex())){
                            componentList.remove(0);
                            if(componentList.get(0).getLex().equals(")")){
                                componentList.remove(0);
                                 if(componentList.get(0).getToken() == -17){
                                      componentList.remove(0);
                                      if(componentList.get(0).getToken() ==-2){
                                          componentList.remove(0);
                                          while(componentList.get(0).getToken() != -3){
                                              if(assignment(componentList)||checkIf(componentList) ||
                                                      checkWhile(componentList)|| checkRepeat(componentList)||
                                                      checkWrite(componentList)||checkRead(componentList)){
                                              }
                                            }
                                             if(componentList.get(0).getToken() == -3){
                                                componentList.remove(0);
                                                return true;
                                            }else{
                                                 System.out.println("Error en linea: "
                                                         + componentList.get(0).getLine() + " se esperaba: Palabra reservada end" );
                                                 error = true;
                                             }
                                      }else{
                                          System.out.println("Error en linea: "
                                                  + componentList.get(0).getLine() + " se esperaba: Palabra reservada begin" );
                                          error = true;
                                      }
                                 }else {
                                     System.out.println("Error en linea: "
                                             + componentList.get(0).getLine() + " se esperaba: Palabra reservada do" );
                                     error = true;
                                 }
                            }else{
                                System.out.println("Error en linea: "
                                        + componentList.get(0).getLine() + " se esperaba: )" );
                                error = true;
                            }
                        }else{
                            System.out.println("Error en linea: "
                                    + componentList.get(0).getLine() + " se esperaba: identificador o numero" );
                            error = true;
                        }
                    }else{
                        System.out.println("Error en linea: "
                                + componentList.get(0).getLine() + " se esperaba: Operador relacional" );
                        error = true;
                    }
                }else{
                    System.out.println("Error en linea: "
                            + componentList.get(0).getLine() + " se esperaba: identificador o numero" );
                    error = true;
                }
            }else{
                System.out.println("Error en linea: "
                        + componentList.get(0).getLine() + " se esperaba: (" );
                error = true;
            }
        }

        return false;
    }
    
    public boolean checkRepeat(List<Component> componentList){
         if(componentList.get(0).getToken() == -9){
            componentList.remove(0);
            if(componentList.get(0).getToken() == -2){
                componentList.remove(0);
                while(componentList.get(0).getToken() != -3){
                    if(assignment(componentList)||checkIf(componentList) ||
                            checkWhile(componentList)|| checkRepeat(componentList)||
                            checkWrite(componentList)||checkRead(componentList)){
                    }
                }
                if(componentList.get(0).getToken() == -3){
                    componentList.remove(0);
                    if(componentList.get(0).getToken() == -10){
                        componentList.remove(0);
                        if(componentList.get(0).getLex().equals("(")){
                            componentList.remove(0);
                             if(validator.isIdentifier(componentList.get(0).getLex()) ||
                                     validator.isDecimalNumber(componentList.get(0).getLex())||
                                     validator.isIntegerNumber(componentList.get(0).getLex())){
                                 componentList.remove(0);
                                 if(validator.isRelationalOperator(componentList.get(0).getLex())){
                                     componentList.remove(0);
                                     if(validator.isIdentifier(componentList.get(0).getLex()) ||
                                             validator.isDecimalNumber(componentList.get(0).getLex())||
                                             validator.isIntegerNumber(componentList.get(0).getLex())
                                             || validator.isLogicalValue(componentList.get(0).getLex()) ){
                                         componentList.remove(0);
                                         if(componentList.get(0).getLex().equals(")")){
                                             componentList.remove(0);
                                             if(componentList.get(0).getLex().equals(";")){
                                                 componentList.remove(0);
                                                 return true;
                                             }
                                         }
                                     }else{
                                         System.out.println("Error en linea: "
                                                 + componentList.get(0).getLine() + " se esperaba: Identificador, numero o valor logico" );
                                         error = true;
                                     }
                                 }else{
                                     System.out.println("Error en linea: "
                                             + componentList.get(0).getLine() + " se esperaba: Operador relacional" );
                                     error = true;
                                 }
                             }else{
                                 System.out.println("Error en linea: "
                                         + componentList.get(0).getLine() + " se esperaba: Identificador o numero" );
                                 error = true;
                             }
                            }else{
                            System.out.println("Error en linea: "
                                    + componentList.get(0).getLine() + " se esperaba: (" );
                            error = true;
                        }
                        }else{
                        System.out.println("Error en linea: "
                                + componentList.get(0).getLine() + " se esperaba: Palabra reservada until" );
                        error = true;
                    }
                    }else{
                    System.out.println("Error en linea: "
                            + componentList.get(0).getLine() + " se esperaba: Palabra reservada end" );
                    error = true;
                }
                 }else{
                System.out.println("Error en linea: "
                        + componentList.get(0).getLine() + " se esperaba: Palabra reservada begin" );
                error = true;
            }
            }
            
        return false;
    }
    
    public boolean checkVariable(List<Component> componentList){
        if(componentList.get(0).getToken() == -15){
            componentList.remove(0);
           // System.out.println(tokens.toString());
            while(componentList.get(0).getToken() != -2){
                if(componentList.get(0).getToken() == -11 ||
                        componentList.get(0).getToken() == -12|| componentList.get(0).getToken() == -13
                        || componentList.get(0).getToken() == -14){
                    componentList.remove(0);
                    if(componentList.get(0).getLex().equals(":")){
                        componentList.remove(0);
                        while(!componentList.get(0).getLex().equals(";")){
                            if(validator.isIdentifier(componentList.get(0).getLex())){
                                componentList.remove(0);
                            }
                            if(componentList.get(0).getLex().equals(",")){
                                componentList.remove(0);
                            }
                        }
                        if(componentList.get(0).getLex().equals(";")){
                                componentList.remove(0);
                        }else{
                            System.out.println("Error en linea: "
                                    + componentList.get(0).getLine() + " se esperaba: ;" );
                            error = true;
                        }
                }else{
                        System.out.println("Error de sintaxis: Se esperaba ':'");
                        return false;
                    }
                }else{
                    System.out.println("Error en linea: "
                            + componentList.get(0).getLine() + " se esperaba: Tipo de dato" );
                    error = true;
                }
                }
            if(componentList.get(0).getToken() == -2){
                return true;
            }
                            
        }
        return false;
    }
    
   
    
    public boolean assignment(List<Component> componentList){
        if(checkAssignment(componentList)){
            return true;
        }else{
            return false;
        }
}
    
    public void roundBrackets(List<Component> componentList){
        if(componentList.get(0).getLex().equals("(")){
            componentList.remove(0);
            while(!componentList.get(0).getLex().equals(")")){
                if(componentList.get(0).getLex().equals("(")){
                    roundBrackets(componentList);
                }
                if(validator.isIntegerNumber(componentList.get(0).getLex())||
                        validator.isDecimalNumber(componentList.get(0).getLex())||
                       validator.isIdentifier(componentList.get(0).getLex())||
                        validator.isLogicalValue(componentList.get(0).getLex())){
                    componentList.remove(0);
                }
                if(validator.isArithmeticOperator(componentList.get(0).getLex())||
                        validator.isRelationalOperator(componentList.get(0).getLex())){
                    if(componentList.get(1).getLex().equals(")")|| componentList.get(1).getLex().equals(";")){
                        break;
                    }else{
                        componentList.remove(0);
                    }
                }
                
                if(componentList.get(0).getLex().equals(";")){
                    break;
                }
            }
            if(componentList.get(0).getLex().equals(")")){
                componentList.remove(0);
            }
        }
    }
    
    public void checkString(List <Component> componentList){
        if(validator.isConstantString(componentList.get(0).getLex())){
            componentList.remove(0);
        }
    }
    
    public boolean checkAssignment(List<Component> componentList){
        if(validator.isIdentifier(componentList.get(0).getLex())){
            componentList.remove(0);
            if(componentList.get(0).getLex().equals(":=")){
                componentList.remove(0);
                while(!componentList.get(0).getLex().equals(";")){
                    if(validator.isIdentifier(componentList.get(0).getLex())||
                            validator.isIntegerNumber(componentList.get(0).getLex())
                            || validator.isDecimalNumber(componentList.get(0).getLex())||
                            validator.isLogicalValue(componentList.get(0).getLex())){
                        componentList.remove(0);
                        if(validator.isArithmeticOperator(componentList.get(0).getLex())||
                                validator.isRelationalOperator(componentList.get(0).getLex())){
                            componentList.remove(0);
                            roundBrackets(componentList);
                        }
                    }
                    roundBrackets(componentList);
                    checkString(componentList);
                    if(validator.isArithmeticOperator(componentList.get(0).getLex()) &&
                            componentList.get(1).getLex().equals(";")||
                            validator.isRelationalOperator(componentList.get(0).getLex()) &&
                                    componentList.get(1).getLex().equals(";")){
                        return false;
                    }else if(validator.isArithmeticOperator(componentList.get(0).getLex())||
                            validator.isRelationalOperator(componentList.get(0).getLex())){
                        componentList.remove(0);
                    }
                }
                if(componentList.get(0).getLex().equals(";")){
                    componentList.remove(0);
                    return true;
                }else{
                    System.out.println("Error en linea: "
                            + componentList.get(0).getLine() + " se esperaba: ;" );
                    error = true;
                }
            }else{
                System.out.println("Error en linea: "
                        + componentList.get(0).getLine() + " se esperaba: :=" );
                error = true;
            }
        }else{

        }
        return false;
    }
   
    
    public boolean checkWrite(List<Component> componentList){
        if(componentList.get(0).getToken()== -5){
            componentList.remove(0);
            if(componentList.get(0).getLex().equals("(")){
                componentList.remove(0);
                if(validator.isIdentifier(componentList.get(0).getLex())||
                        validator.isIntegerNumber(componentList.get(0).getLex())||
                        validator.isDecimalNumber(componentList.get(0).getLex())||
                        validator.isConstantString(componentList.get(0).getLex())){
                    componentList.remove(0);
                if(componentList.get(0).getLex().equals(")")){
                    componentList.remove(0);
                    if(componentList.get(0).getLex().equals(";")){
                        componentList.remove(0);
                        return true;
                    }else{
                        System.out.println("Error en linea: "
                                + componentList.get(0).getLine() + " se esperaba: ;" );
                        error = true;
                    }
                }else{
                    System.out.println("Error en linea: "
                            + componentList.get(0).getLine() + " se esperaba: )" );
                    error = true;
                }
            }else{
                    System.out.println("Error en linea: "
                            + componentList.get(0).getLine() + " se esperaba: Identificador o numero" );
                    error = true;
                }
            }else{
                System.out.println("Error en linea: "
                        + componentList.get(0).getLine() + " se esperaba: (" );
                error = true;
            }
        }
         return false;
    }
    
    public boolean checkRead(List<Component> componentList){
        if(componentList.get(0).getToken()== -4){
            componentList.remove(0);
            if(componentList.get(0).getLex().equals("(")){
                componentList.remove(0);
                if(validator.isIdentifier(componentList.get(0).getLex())||
                        validator.isIntegerNumber(componentList.get(0).getLex())||
                        validator.isDecimalNumber(componentList.get(0).getLex())){
                    componentList.remove(0);
                if(componentList.get(0).getLex().equals(")")){
                    componentList.remove(0);
                    if(componentList.get(0).getLex().equals(";")){
                        componentList.remove(0);
                        return true;
                    }else{
                        System.out.println("Error en linea: "
                                + componentList.get(0).getLine() + " se esperaba: ;" );
                        error = true;
                    }
                }else{
                    System.out.println("Error en linea: "
                            + componentList.get(0).getLine() + " se esperaba: )" );
                    error = true;
                }
            }else{
                    System.out.println("Error en linea: "
                            + componentList.get(0).getLine() + " se esperaba: Identificador o numero" );
                    error = true;
                }
            }else{
                System.out.println("Error en linea: "
                        + componentList.get(0).getLine() + " se esperaba: (" );
                error = true;
            }
        }
         return false;
    }

}

