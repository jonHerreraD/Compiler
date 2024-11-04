package semantic;

import syntax.Component;
import syntax.RegexValidator;

import java.util.ArrayList;
import java.util.List;

public class Semantic {

    RegexValidator validator = new RegexValidator();
    String context ="";
    int bodyBegins = 0;


    public void getClassName(List<Component> componentList){
        int i = 0;
        if(componentList.get(i).getToken() == -1){
            context = componentList.get(i+1).getLex();
        }
    }
    public void checkVariables(List<Component> componentList, List<Symbol> symbolList){
        int index = 0;
        while(componentList.get(index).getToken() != -15){
            index++;
            if (componentList.get(index).getToken() == -15){
                getVariables(componentList,symbolList, index);
            }
        }
    }

    public void getVariables(List<Component> componentList, List<Symbol> symbolList, int index){
        int i = index;
        while(componentList.get(i).getToken() != -2){
            if(validator.isIdentifier(componentList.get(i).getLex())){
                Component component = componentList.get(i);
                String type = getIdentifierType(component);
                Symbol symbol = new Symbol(component.getLex(), component.getToken(),
                        0,type, context);
                symbolList.add(symbol);
            }
            i++;
        }
        bodyBegins = i;
    }

    public String getIdentifierType(Component component){
        switch (component.getToken()) {
            case -51:
                return "int";
            case -52:
                return "float";
            case -53:
                return "string";
            case -54:
                return "logic";
            case -55:
                return "class";
        }
        return "null";
    }

    public void checkAssignments(List<Component> componentList, List<Symbol> symbolList){
        int i = bodyBegins +1;
        ArrayList<Component> assignmentLine = new ArrayList<>();
        while(validator.isIdentifier(componentList.get(i).getLex())){
            if (validator.isIdentifier(componentList.get(i).getLex())
                    && componentList.get(i+1).getToken() == -26){
                while (componentList.get(i).getToken() != -75){
                    assignmentLine.add(componentList.get(i));
                    i++;
                }
                i++;
                Component leftIdentifier = assignmentLine.get(0);
                Symbol leftSymbol = findSymbolInTable(leftIdentifier.getLex(), symbolList);
                if (leftSymbol == null) {
                    System.out.println("Error: variable '" + leftIdentifier.getLex() + "' no declarada.");
                    return; // Error, variable no encontrada
                }
                String leftType = leftSymbol.getType();

                // Verificar los tipos de los componentes a la derecha del operador :=
                for(int j = 2; j < assignmentLine.size(); j++){
                    Component rightComponent = assignmentLine.get(j);
                    if (validator.isArithmeticOperator(rightComponent.getLex()) ||
                            validator.isLogicalOperator(rightComponent.getLex()) ||
                            validator.isRelationalOperator(rightComponent.getLex()) ||
                            validator.isSpecialCharacter(rightComponent.getLex())) {


                    }else {
                        String rightType = getComponentType(rightComponent,symbolList);
                        if(!leftType.equals(rightType)){
                            System.out.println("Error: tipo incompatible en la asignación de '" + rightComponent.getLex() +
                                    "'. Se esperaba tipo " + leftType + " pero se encontró " + rightType);
                            return; // Error, tipo no compatible
                        }
                    }
                }
                assignmentLine.clear();
            }
        }
    }

    public Symbol findSymbolInTable(String lex, List<Symbol> symbolList){
        for (Symbol symbol : symbolList){
            if (symbol.getId().equals(lex)){
                return symbol;
            }
        }
        return null;
    }

    public String getComponentType(Component component, List<Symbol> symbolList){
        if(validator.isIdentifier(component.getLex())){
            Symbol symbol = findSymbolInTable(component.getLex(), symbolList);
            return symbol != null ? symbol.getType() : "desconocido";
        }else if (validator.isIntegerNumber(component.getLex())) {
            return "int";
        } else if (validator.isDecimalNumber(component.getLex())) {
            return "float";
        } else if (validator.isConstantString(component.getLex())) {
            return "string";
        }
        return "desconocido";
    }

    public void checkComparison(List<Component> componentList, List<Symbol> symbolList){
        int i = bodyBegins +1;
        ArrayList<Component> comparisonLine = new ArrayList<>();

        while(componentList.get(i).getToken() != -6 ||
                componentList.get(i).getToken() != -8 ||
                componentList.get(i).getToken() != -10){
            i++;
            if (componentList.get(i).getToken() == -6 ||
                    componentList.get(i).getToken() == -8 ||
                    componentList.get(i).getToken() == -10 ||
                    i>= componentList.size()){
                break;
            }
        }
        if (i>= componentList.size()){

        }

        if(componentList.get(i).getToken() == -6 ||
                componentList.get(i).getToken() == -8 ||
                componentList.get(i).getToken() == -10){
            while (componentList.get(i).getToken() != -74){
                comparisonLine.add(componentList.get(i));
                i++;
            }

            int j = 0;
            while(validator.isReservedWord(comparisonLine.get(j).getLex()) ||
            validator.isSpecialCharacter(comparisonLine.get(j).getLex())){
                j++;
            }
            Component leftComponent = comparisonLine.get(j);
            String leftType = getComponentType(leftComponent, symbolList);
            for(int x = j+2; x < comparisonLine.size(); x++){
                Component rightComponent = comparisonLine.get(x);
                if (validator.isArithmeticOperator(rightComponent.getLex()) ||
                        validator.isLogicalOperator(rightComponent.getLex()) ||
                        validator.isRelationalOperator(rightComponent.getLex()) ||
                        validator.isSpecialCharacter(rightComponent.getLex())) {


                }else {
                    String rightType = getComponentType(rightComponent,symbolList);
                    if(!leftType.equals(rightType)){
                        System.out.println("Error: tipo incompatible en la comparacion de '" + rightComponent.getLex() +
                                "'. Se esperaba tipo " + leftType + " pero se encontró " + rightType);
                        return; // Error, tipo no compatible
                    }
                }
            }
        }
    }
}
