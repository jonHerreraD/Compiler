package semantic;

import syntax.Component;
import syntax.RegexValidator;

import java.util.ArrayList;
import java.util.List;

public class Semantic {

    RegexValidator validator = new RegexValidator();
    String context ="";
    int bodyBegins = 0;

    Component programName;




    public void getClassName(List<Component> componentList){
        int i = 0;
        if(componentList.get(i).getToken() == -1){
            context = componentList.get(i+1).getLex();
            programName = componentList.get(i+1);
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
        int pos = 0;
        while(componentList.get(i).getToken() != -2){
            if(validator.isIdentifier(componentList.get(i).getLex())){
                Component component = componentList.get(i);
                String type = getIdentifierType(component);
                Symbol symbol = new Symbol(component.getLex(), component.getToken(),
                        0,type, context);
                if(findSymbolInTable(symbol.getId(),symbolList) != null){
                    System.out.println("Error: Variable " + symbol.getId() + " ya ha sido declarada");
                    break;
                }else{
                    symbolList.add(symbol);
                    for(int j = i; j<componentList.size(); j++){
                        if(componentList.get(j).getLex().equals(component.getLex())){
                            componentList.get(j
                            ).setSymbolPosition(pos);
                        }
                    }

                    pos++;
                }
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

    public void isAssignment(List<Component> componentList, List<Symbol> symbolList, IndexErrorWrapper indexErrorWrapper){
        int i = indexErrorWrapper.index;
        ArrayList<Component> assignmentLine = new ArrayList<>();
        while(validator.isIdentifier(componentList.get(i).getLex())){
            if (validator.isIdentifier(componentList.get(i).getLex())
                    && componentList.get(i+1).getToken() == -26){
                while (componentList.get(i).getToken() != -75){
                    assignmentLine.add(componentList.get(i));
                    i++;
                }
                i++;
                indexErrorWrapper.index = i;
                Component leftIdentifier = assignmentLine.get(0);
                Symbol leftSymbol = findSymbolInTable(leftIdentifier.getLex(), symbolList);
                if (leftSymbol == null) {
                    indexErrorWrapper.error = true;
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
                            indexErrorWrapper.error = true;
                            System.out.println("Error: tipo incompatible en la asignación de '" + rightComponent.getLex() +
                                    "'. Se esperaba tipo " + leftType + " pero se encontró " + rightType);
                            return; // Error, tipo no compatible
                        }
                    }
                }
                assignmentLine.clear();
            }else{
                i++;
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
        }  else if (validator.isLogicalValue(component.getLex())) {
            return "logic";
        }
        return "desconocido";
    }

    public void isComparison(List<Component> componentList, List<Symbol> symbolList,IndexErrorWrapper indexErrorWrapper){
        int i = indexErrorWrapper.index;
        ArrayList<Component> comparisonLine = new ArrayList<>();
        /*
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

         */

        if(componentList.get(i).getToken() == -6 ||
                componentList.get(i).getToken() == -8 ||
                componentList.get(i).getToken() == -10){
            while (componentList.get(i).getToken() != -74){
                comparisonLine.add(componentList.get(i));
                i++;
            }

            indexErrorWrapper.index = i;

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
                        indexErrorWrapper.error = true;
                        System.out.println("Error: tipo incompatible en la comparacion de '" + rightComponent.getLex() +
                                "'. Se esperaba tipo " + leftType + " pero se encontró " + rightType);
                        return; // Error, tipo no compatible
                    }
                }
            }
        }
    }

    public boolean isSemanticGood(List<Component>componentList,
                                 List<Symbol> symbolList, List<DirectionTable> directionTableList){
        IndexErrorWrapper indexErrorWrapper = new IndexErrorWrapper(0,false,0);
        getClassName(componentList);
        checkVariables(componentList,symbolList);
        DirectionTable directionTable = new DirectionTable();
        directionTable.createDirectionsTable(programName,directionTableList);

        indexErrorWrapper.index = bodyBegins + 1;
        if (indexErrorWrapper.error != true){
            isBodyGood(componentList,symbolList,indexErrorWrapper);
        }else{
            return indexErrorWrapper.error;
        }

        return indexErrorWrapper.error;
    }

    private void isBodyGood(List<Component> componentList, List<Symbol> symbolList,IndexErrorWrapper indexErrorWrapper) {

        int blockLevel = 0; // Nivel de bloque para distinguir entre distintos bloques begin-end

        while (indexErrorWrapper.index < componentList.size() &&
                componentList.get(indexErrorWrapper.index).getToken() != -3 && // no ha encontrado el end final
                indexErrorWrapper.error != true) {


            // Procesa asignaciones y comparaciones
            isAssignment(componentList, symbolList, indexErrorWrapper);
            isComparison(componentList, symbolList, indexErrorWrapper);

            // Si se detecta un error, se interrumpe el ciclo
            if (indexErrorWrapper.error ||
                    componentList.getLast().equals(componentList.get(indexErrorWrapper.index))) {
                break;
            }

            // Avanza al siguiente componente
            indexErrorWrapper.index++;
            Component currentComponent = componentList.get(indexErrorWrapper.index);
            // Detecta el inicio de un bloque
            if (currentComponent.getToken() == -2) { // Suponiendo que BEGIN_TOKEN es el token de 'begin'
                blockLevel++;
            }
            // Detecta el final de un bloque
            if (currentComponent.getToken() == -3) { // Suponiendo que END_TOKEN es el token de 'end'
                if (blockLevel > 0) {
                    blockLevel--; // Reduce el nivel del bloque al encontrar un 'end'
                    indexErrorWrapper.index++;
                } else {
                    break; // Si blockLevel es 0, estamos al final del programa
                }
            }
        /*
        while(componentList.get(indexErrorWrapper.index).getToken() != -3 &&
                !componentList.getLast().equals(componentList.get(indexErrorWrapper.index)) &&
                indexErrorWrapper.error != true){
            isAssignment(componentList,symbolList,indexErrorWrapper);
            isComparison(componentList,symbolList,indexErrorWrapper);
            if (indexErrorWrapper.error){
                break;
            }


            indexErrorWrapper.index++;
            if(!componentList.getLast().equals(componentList.get(indexErrorWrapper.index))){
                if (componentList.get(indexErrorWrapper.index).getToken() == -3){
                    indexErrorWrapper.index++;
                }else {

                }
            }else{
                break;
            }


         */
        }
    }
}
