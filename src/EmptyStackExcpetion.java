/**
 * Created by derek on 9/17/16.
 */
public class EmptyStackExcpetion extends Exception {

    public EmptyStackExcpetion() {
        this("Woops!!!..... The stack is currently empty");
    }

    public EmptyStackExcpetion(String s) {
        super(s);
    }
}
