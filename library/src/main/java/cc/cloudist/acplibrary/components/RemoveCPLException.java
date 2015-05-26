package cc.cloudist.acplibrary.components;

public class RemoveCPLException extends RuntimeException {

    public RemoveCPLException() {
        super("CPL not attached to window manager, is it already dismissed?");
    }

}
