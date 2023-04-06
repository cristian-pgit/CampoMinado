public class Ut {


    public static String tRojo(String msj){
        return ANSI_RED+msj+ANSI_RESET;
    }
    public static String tVerde(String msj){
        return ANSI_GREEN+msj+ANSI_RESET;
    }
    public static String tCyan(String msj){
        return ANSI_CYAN+msj+ANSI_RESET;
    }
    public static String tYell(String msj){
        return ANSI_YELLOW+msj+ANSI_RESET;
    }
    public static String tPurp(String msj){
        return ANSI_PURPLE+msj+ANSI_RESET;
    }

    public static String tBlue(String msj){
        return ANSI_BLUE+msj+ANSI_RESET;
    }


    /** The Constant ANSI_PURPLE. */
    public static final String ANSI_PURPLE = "\u001B[35m";

    /** The Constant ANSI_BLUE. */
    public static final String ANSI_BLUE = "\u001B[34m";

    /** The Constant ANSI_CYAN. */
    public static final String ANSI_CYAN = "\u001B[36m";

    /** The Constant ANSI_GREEN. */
    public static final String ANSI_GREEN = "\u001B[32m";

    /** The Constant ANSI_YELLOW. */
    public static final String ANSI_YELLOW = "\u001B[33m";

    /** The Constant ANSI_RESET. */
    public static final String ANSI_RESET = "\u001B[0m";

    /** The Constant ANSI_RED. */
    public static final String ANSI_RED = "\u001B[31m";



}
