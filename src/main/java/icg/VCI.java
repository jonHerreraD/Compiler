package icg;

import lombok.Data;
import syntax.Component;

@Data
public class VCI {

    private Component component;
    private int value;
    private String type;

    private int direction;

    public VCI(Component component, int value, String type, int direction) {
        this.component = component;
        this.value = value;
        this.type = type;
        this.direction = direction;
    }
}
