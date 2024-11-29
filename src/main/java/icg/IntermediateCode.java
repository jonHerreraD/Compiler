package icg;

import semantic.IndexErrorWrapper;
import syntax.Component;
import syntax.RegexValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;

public class IntermediateCode {
    Component component;
    int value;

    String type = "Undefined";
    RegexValidator validator = new RegexValidator();

    private final Map<String, Consumer<VCI>> handlers = new HashMap<>();

    //private int index;

    private Stack<VCI> operators = new Stack<>();
    private Stack<VCI> words = new Stack<>();

    private Stack<Integer> directions = new Stack<>();
    private Stack<Integer> indexCycle = new Stack<>();
    IndexErrorWrapper indexErrorWrapper = new IndexErrorWrapper(0,false,-1);
    public IntermediateCode() {

    }


    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getType(){return type;}

    public void setType(String type){this.type = type;}



    public void getBodyIndex(List<Component> componentList){
        for (int c = 0; c<componentList.size(); c++){
            if(componentList.get(c).getToken() == -2){
                indexErrorWrapper.index = c+1;
                break;
            }
        }
    }

    public VCI makeVCIObject(Component component){
        VCI vci = new VCI(component,0,type,0);
        setVCIType(vci);
        return vci;
    }

    public void setVCIType(VCI vci){
        Component component = vci.getComponent();
        if(validator.isLogicalOperator(component.getLex()) ||
                validator.isArithmeticOperator(component.getLex()) ||
                validator.isRelationalOperator(component.getLex()) ||
                component.getToken() == -73 || component.getToken() == -74){
            vci.setType("Operator");
        }else if (validator.isReservedWord(component.getLex())){
            switch (component.getLex()){
                case "if":
                vci.setType("IF");
                break;
                case "while":
                    vci.setType("While");
                    break;
                case "write":
                    vci.setType("Write");
                    break;
                case "repeat":
                    vci.setType("Repeat");
                    break;
                default:
                    vci.setType("UNKNOWN"); // Opcional: Maneja valores inesperados
                    break;
            }
        }else if (validator.isIdentifier(component.getLex())){
            vci.setType("Identifier");
        }

    }

    public void setPriorityValue(VCI vci){
        Component component = vci.getComponent();
        if (component.getToken() == -42){
            vci.setValue(10);
        } else if (component.getToken() == -41) {
            vci.setValue(20);
        } else if (component.getToken() == -43) {
            vci.setValue(30);
        } else if (validator.isRelationalOperator(component.getLex())) {
            vci.setValue(40);
        } else if (component.getToken() == -24 ||
                component.getToken() == -25) {
            vci.setValue(50);
        } else if (component.getToken() == -21 ||
                component.getToken() == -22 ||
                component.getToken() == -27) {
            vci.setValue(60);
        }else {
            vci.setValue(0);
        }
    }

    public void addToOperatorsStack(VCI vci, List<VCI> vciList){
        if (operators.isEmpty()){
            operators.push(vci);
        } else if (operators.peek().getValue()< vci.getValue()) {
            operators.push(vci);
        }else {
            while (!operators.isEmpty() && operators.peek().getValue() >= vci.getValue()) {
                vciList.add(operators.pop());
            }
            operators.push(vci);
        }
    }

    public void addSubComponentsToOperatorsStack(VCI vci, List<VCI> vciList,
                                                 List<Component> componentList, int index, int cycleIndex){
        operators.push(vci);
        index++;
        while (!(componentList.get(index).getToken() == -74)){
            Component currentComponent = componentList.get(index);
            VCI vci2 = new VCI(currentComponent,0,type,0);
            setVCIType(vci2);
            if (vci2.getType().equals("Operator")){
                setPriorityValue(vci2);
                addToOperatorsStack(vci2,vciList);
            }else if (vci2.getComponent().getToken() == -74){
                while (!operators.isEmpty()){
                    vciList.add(operators.pop());
                }
                vciList.add(vci2);
            }else {
                vciList.add(vci2);
                if (cycleIndex == -1){
                    indexCycle.add(vciList.indexOf(vci2));
                    cycleIndex = 0;
                }
            }

            index++;
        }
        while (operators.peek().getComponent().getToken() != vci.getComponent().getToken() ){
            vciList.add(operators.pop());
        }
        if (operators.peek().getComponent().getToken() == vci.getComponent().getToken()){
            operators.pop();
        }
        indexErrorWrapper.index = index;
    }

    public int assignment(VCI vci, List<VCI> vciList, List<Component> componentList,int i){
        for (int j = i; j<componentList.size(); j++){
            vci = makeVCIObject(componentList.get(j));
            if (vci.getType().equals("Operator")){
                if(vci.getComponent().getToken() == -73){
                    setPriorityValue(vci);
                    addSubComponentsToOperatorsStack(vci,vciList,componentList,j,0);
                    j = indexErrorWrapper.index;
                }else {
                    setPriorityValue(vci);
                    addToOperatorsStack(vci,vciList);
                }
            }else if (vci.getComponent().getToken() == -75){
                while (!operators.isEmpty()){
                    vciList.add(operators.pop());
                }
                return j + 1;
            }else {
                vciList.add(vci);
            }
        }

        return componentList.size();
    }

    public int ifStatement(VCI vci, List<VCI> vciList, List<Component> componentList, int i) {
        VCI vci2 = makeVCIObject(componentList.get(i));
        addSubComponentsToOperatorsStack(vci2,vciList,componentList,i,0);
        i = indexErrorWrapper.index;
        Component compVac = new Component("VAC",0,0,0);
        VCI vac = new VCI(compVac,0,"VAC",0);
        vciList.add(vac);
        directions.add(vciList.indexOf(vac));
        vciList.add(words.peek());
        return i + 3;
    }

    public int blockEnd(VCI vci, List<VCI> vciList, List<Component> componentList, int i) {
        if (!words.isEmpty()) {
            // Finaliza el bloque actual y actualiza las direcciones.
            String word = words.peek().getComponent().getLex();
            words.pop();
            int direction = 0;
            if (!directions.isEmpty()){
                direction = directions.pop();
            }

            // Verifica si es un "else" o el final del bloque
            if (componentList.get(i + 1).getLex().equals("else")) {
                // Agrega un nuevo "VAC" para marcar la salida del bloque completo
                Component vacComp = new Component("VAC", 0, 0, 0);
                VCI vac = new VCI(vacComp, 0, "VAC", 0);
                vciList.add(vac);
                directions.add(vciList.indexOf(vac));

                // Manejo del bloque "else"
                Component elseComp = new Component("ELSE", 0, 0, 0);
                VCI elseVCI = new VCI(elseComp, 0, "ELSE", 0);
                vciList.add(elseVCI);
                words.push(elseVCI);

                // Ajustar la dirección del "VAC" al inicio del "else"
                vciList.get(direction).getComponent().setLex(String.valueOf(vciList.size()));
                return i + 3;
            } else {

                if (word.equals("while")) {
                    Component vacComp = new Component("VAC", 0, 0, 0);
                    VCI vac = new VCI(vacComp, 0, "VAC", 0);
                    vciList.add(vac);
                    directions.add(vciList.indexOf(vac));

                    // Manejo del bloque "else"
                    Component endWhileComp = new Component("ENDWHILE", 0, 0, 0);
                    VCI endWhileVCI = new VCI(endWhileComp, 0, "ENDWHILE", 0);
                    vciList.add(endWhileVCI);
                    vciList.get(direction).getComponent().setLex(String.valueOf(vciList.size()));
                    direction = directions.pop();
                    vciList.get(direction).getComponent().setLex(String.valueOf(indexCycle.pop()));
                    return i + 1;
                }else if(word.equals("repeat")){
                    i = i + 2;
                    VCI condition = makeVCIObject(componentList.get(i));
                    addSubComponentsToOperatorsStack(condition,vciList,componentList,i,0);
                    i = indexErrorWrapper.index;
                    Component vacComp = new Component("VAC", 0, 0, 0);
                    VCI vac = new VCI(vacComp, 0, "VAC", 0);
                    vciList.add(vac);
                    if (direction != 0){
                        directions.add(direction);
                    }
                    directions.add(vciList.indexOf(vac));
                    direction = directions.pop();
                    vciList.get(direction).getComponent().setLex(String.valueOf(indexCycle.pop()));
                    Component endRepeatComp = new Component("ENDREPEAT", 0, 0, 0);
                    VCI endRepeatVCI = new VCI(endRepeatComp, 0, "ENDREPEAT", 0);
                    vciList.add(endRepeatVCI);
                    return i + 1;

                }else {
                    // Manejo del final del bloque (sin "else")
                    vciList.get(direction).getComponent().setLex(String.valueOf(vciList.size()));
                    return i + 1;
                }
            }


        }
        return i;
    }


    public int write(VCI vci, List<VCI> vciList, List<Component> componentList, int i){
        VCI write = vci;
        i = i +2;
        Component var = componentList.get(i);
        VCI writeVar = new VCI(var,0,type,0);
        vciList.add(writeVar);
        vciList.add(write);
        return  i +3;
    }

    public int whileCicle(VCI vci, List<VCI> vciList, List<Component> componentList, int i, int cycleIndex) {
        VCI vci2 = makeVCIObject(componentList.get(i));
        addSubComponentsToOperatorsStack(vci2,vciList,componentList,i,cycleIndex);
        i = indexErrorWrapper.index;
        Component compVac = new Component("VAC",0,0,0);
        VCI vac = new VCI(compVac,0,"VAC",0);
        vciList.add(vac);
        directions.add(vciList.indexOf(vac));
        vciList.add(words.peek());
        return i + 3;
    }

    public int repeat(VCI vci, List<VCI> vciList, List<Component> componentList, int i){
        i = i +2;
        indexCycle.add(vciList.size());
        return i;
    }
    public void buildVCI(List<Component> componentList, List<VCI> vciList){
        getBodyIndex(componentList);
        int i = indexErrorWrapper.index;
        int cycleBegins = indexErrorWrapper.cycleIndex;
        while(i<componentList.size()){
            VCI vci = makeVCIObject(componentList.get(i));
            if (vci.getType().equals("Identifier")){
                i = assignment(vci,vciList,componentList,i);
            } else if (vci.getType().equals("IF")) {
                words.add(vci);
                i = ifStatement(vci,vciList,componentList,i+1);
            } else if (vci.getType().equals("While")) {
                words.add(vci);
                i = whileCicle(vci,vciList,componentList,i+1,cycleBegins);
            }else if (vci.getType().equals("Write")) {
                i = write(vci,vciList,componentList,i);
            }else if (vci.getType().equals("Repeat")) {
                words.add(vci);
                i = repeat(vci,vciList,componentList,i);
            }else if (vci.getComponent().getLex().equals("end")) {
                i = blockEnd(vci,vciList,componentList,i);
                if (componentList.get(i).getLex().equals("end") && componentList.get(i) == componentList.getLast()){
                    i++;
                }
            }else {
                i++;
            }
        }
        // Asegúrate de que el último componente sea "..."
        Component finalComp = new Component("...", 0, 0, 0);
        VCI finalVCI = new VCI(finalComp, 0, "End", 0);
        vciList.add(finalVCI);
    }
}
