/**
 * Created by derek on 9/17/16.
 */
public interface iStack<E> {


    public void push(E element);    //Add an element to the top of the stack
    public E pop() throws EmptyStackExcpetion; //Remove the element from the top

    public E getTop(); //Return the element on the top
    public int getSize(); //Return the number of elements in the stack

    public String toString();   //Return the objects as a string
}
