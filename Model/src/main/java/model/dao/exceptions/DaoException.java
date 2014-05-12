package model.dao.exceptions;


public class DaoException extends Exception {

    private Exception exception;
//    private String msg;

    public DaoException (Exception ex) {
        this.exception = ex;
    }

//    public DaoException (String msg) {
//        this.msg = msg;
//    }


    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return this.exception.toString();
    }
}
