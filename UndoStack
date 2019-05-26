package JPanelNew;

import java.util.Stack;

public class UndoStack<T> extends Stack<T> {
    private final int stackmaxSize;

    public UndoStack(int size) {
        super();
        this.stackmaxSize = size;
    }

    @Override
    public Object push(Object object) {
        while (this.size() > stackmaxSize) {
            this.remove(0);
        }
        return super.push((T) object);
    }
}
