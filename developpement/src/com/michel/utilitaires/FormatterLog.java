package com.michel.utilitaires;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * classe format du log
 */
public class FormatterLog extends Formatter {
    /**
     * format du log
     * @param logRecord the log record to be formatted.
     * @return String message log
     */
    public String format(LogRecord logRecord) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        StringBuilder result = new StringBuilder();

        result.append(format.format(new Date()));
        result.append(" Level : " );
        result.append(logRecord.getLevel());

        result.append(" / Message : ");
        result.append(logRecord.getMessage());

        result.append(" / Méthode : ");
        result.append(logRecord.getSourceMethodName());

        result.append(" / Classe : ");
        result.append(logRecord.getSourceClassName());


        result.append("\n");
        return result.toString();
    }
}
