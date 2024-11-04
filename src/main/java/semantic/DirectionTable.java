package semantic;

import java.util.List;

public class DirectionTable {
    String id;
    int token;
    int line;
    int vci;

    public DirectionTable() {

    }

    public DirectionTable(String id, int token, int line, int vci) {
        this.id = id;
        this.token = token;
        this.line = line;
        this.vci = vci;
    }

    public void createDirectionsTable(List<Symbol> symbolList, List<DirectionTable> directionTableList){
        for(Symbol symbol : symbolList){
            DirectionTable directionTable = new DirectionTable(symbol.getId(), symbol.getToken(),
                    0,0);
            directionTableList.add(directionTable);
        }
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

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getVci() {
        return vci;
    }

    public void setVci(int vci) {
        this.vci = vci;
    }
}
