package discordstudyquery;

import java.io.*;

public class LogToFileAndConsole {
    private static FileOutputStream fos;
    private static PrintStream filePrintStream;
    private static PrintStream originalSystemOut;
    private static PrintStream originalSystemErr;

    public static void set() {
        // Define the file where you want to log the messages
        File logFile = new File("log.txt");

        try {
            System.out.println("Logging to file: " + logFile.getCanonicalPath());
            // Create a FileOutputStream to write to the log file
            fos = new FileOutputStream(logFile);

            // Create a PrintStream to wrap the FileOutputStream for file logging
            filePrintStream = new PrintStream(fos);

            // Create a new PrintStream to wrap the original System.out for console output
            originalSystemOut = System.out;
            originalSystemErr = System.err;

            // Redirect System.out and System.err to both filePrintStream and consolePrintStream
            System.setOut(new MultiPrintStream(filePrintStream, originalSystemOut));
            System.setErr(new MultiPrintStream(filePrintStream, originalSystemErr));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            filePrintStream.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MultiPrintStream extends PrintStream {
    private final PrintStream second;

    public MultiPrintStream(PrintStream main, PrintStream second) {
        super(main);
        this.second = second;
    }

    @Override
    public void println(String x) {
        super.println(x);
        second.println(x);
    }
}
