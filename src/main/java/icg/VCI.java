package icg;

import syntax.Component;
import syntax.RegexValidator;

import java.util.ArrayList;
import java.util.List;

public class VCI {
    Component component;
    int value;
    RegexValidator validator = new RegexValidator();

    public VCI(Component component, int value) {
        this.component = component;
        this.value = value;
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

    public void makeVCIObjects(List<Component> componentList, ArrayList<VCI> vciArrayList){
        /*
        for(Component com : componentList){
            if(validator)
            VCI vci = new VCI();
        }

         */
    }

    public int setPriorityValue(Component component){
        if (component.getToken() == -42){
            return 10;
        } else if (component.getToken() == -41) {
            return 20;
        } else if (component.getToken() == -43) {
            return 30;
        } else if (validator.isRelationalOperator(component.getLex())) {
            return 40;
        } else if (component.getToken() == -24 ||
        component.getToken() == -25) {
            return 50;
        } else if (component.getToken() == -21 ||
                component.getToken() == -22 ||
                component.getToken() == -27) {
            return 60;
        }else {
            return 0;
        }
    }
}
