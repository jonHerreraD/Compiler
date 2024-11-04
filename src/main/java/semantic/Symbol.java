package semantic;

public class Symbol {
    String id;
    int token;
    Object value;

    String type;
    String context;

    public Symbol(String id, int token, Object value, String type, String context) {
        this.id = id;
        this.token = token;
        this.value = value;
        this.type = type;
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType(){return type;}

    public void setType(String type){this.type = type;}

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
