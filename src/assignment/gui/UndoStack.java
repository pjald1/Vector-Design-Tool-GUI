package assignment.gui;

import java.util.Stack;

/** A stack is initialised where every shape drawn is added to
 * after they are undone
 * @param <T> generic type where drawn objects that are undone are added into
 */
public class UndoStack<T> extends Stack<T> {
    private final int stackSize;

    /** Constructs a stack that sets the maximum number of drawn
     * objects that can be undone
     * @param size the overall number of objects the stack can store
     */
    public UndoStack(int size) {
        super();
        this.stackSize = size;
    }


    /** Removes the objects that has been drawn and stored into the stack
     * @param object the shape drawn that will be removed
     * @return the drawn shape that was undone
     */
    @Override
    public Object push(Object object) {
        while (this.size() > stackSize) {
            this.remove(0);
        }
        return super.push((T) object);
    }
}
