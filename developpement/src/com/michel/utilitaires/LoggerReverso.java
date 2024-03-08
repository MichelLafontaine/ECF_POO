package com.michel.utilitaires;

/**
 * classe avec pour connaitre nom class log
 */
public class LoggerReverso {

    private LoggerReverso(){}
    /**
     * logger
     */
    public static final java.util.logging.Logger LOGGER =
            java.util.logging.Logger.getLogger(LoggerReverso.class.getName());
}
