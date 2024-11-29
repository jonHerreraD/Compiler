package semantic;

public class IndexErrorWrapper {
    public int index;
    public boolean error;

    public int cycleIndex;

    public IndexErrorWrapper(int index, boolean error,int cycleIndex) {
        this.index = index;
        this.error = error;
        this.cycleIndex = cycleIndex;

    }
}
