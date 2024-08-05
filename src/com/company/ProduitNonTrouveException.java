package com.company;

public class ProduitNonTrouveException extends Exception {
    public ProduitNonTrouveException() {
        super();
    }

    public ProduitNonTrouveException(String message) {
        super(message);
    }

    public ProduitNonTrouveException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProduitNonTrouveException(Throwable cause) {
        super(cause);
    }
}
