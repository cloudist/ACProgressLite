package cc.cloudist.acplibrary.components;

public class RemoveACPLException extends RuntimeException {

    public RemoveACPLException() {
        super("CPL not attached to window manager, is it already dismissed?");
    }

}
