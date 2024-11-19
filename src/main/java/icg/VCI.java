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
}
