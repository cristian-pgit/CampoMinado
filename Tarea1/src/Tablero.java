import java.util.*;

public class Tablero {

    public Tablero() {
    }

    static Random ran = new Random();
    static Scanner sc = new Scanner(System.in);

    public static int puntaje = 0;
    public static boolean perder = false;

    public static int dif = 1;

    public static List<Jugadas> jugadas = new ArrayList<>();

    public int movimientos = 0;

    static String[][] tableroJ = new String[5][5];
    static String[][] tableroM = new String[5][5];

    public static void generarTablero(String[][] tablero) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tablero[i][j] = "[-]"; // inicializar y dejar en valor blanc en ves que esten null.
            }
        }
    }

    public static void ubicarMinas(String[][] tableroM) {
        if (dif == 3) {
            for (int i = 0; i < 16; i++) {
                int row = ran.nextInt(5);
                int col = ran.nextInt(5);
                while (!validarPosicion(tableroM, row, col)) {
                    row = ran.nextInt(15 - 1);
                    col = ran.nextInt(15 - 1);
                }
                for (int j = 0; j < 1; j++) {
                    tableroM[row + j][col] = "[" + Ut.tCyan("M") + "]";
                }
            }
        } else if (dif == 2) {
            for (int i = 0; i < 13; i++) {
                int row = ran.nextInt(5);
                int col = ran.nextInt(5);
                while (!validarPosicion(tableroM, row, col)) {
                    row = ran.nextInt(15 - 1);
                    col = ran.nextInt(15 - 1);
                }
                for (int j = 0; j < 1; j++) {
                    tableroM[row + j][col] = "[" + Ut.tCyan("M") + "]";
                }
            }
        } else {
            for (int i = 0; i < 10; i++) {
                int row = ran.nextInt(5);
                int col = ran.nextInt(5);
                while (!validarPosicion(tableroM, row, col)) {
                    row = ran.nextInt(15 - 1);
                    col = ran.nextInt(15 - 1);
                }
                for (int j = 0; j < 1; j++) {
                    tableroM[row + j][col] = "[" + Ut.tCyan("M") + "]";
                }
            }
        }

    }

    private static boolean validarPosicion(String[][] tableroM, int row, int col) {
        // Checkear si esta dentro de los limites del tablero
        if (row < 0 || row + 1 > tableroM.length || col < 0 || col >= tableroM[0].length) {
            return false;
        }
        // Ver si hay algo ya en la ubicacion designada
        for (int i = 0; i < 1; i++) {
            if (!tableroM[row + i][col].equals("[-]")) {
                return false;
            }
        }
        // si no hay drama, todo ok
        return true;
    }


    public static void setDif() {
        boolean difOK = false;
        while (!difOK) {
            System.out.println(Ut.tYell("Indique la Dificultad a la que se desea jugar, Facil, Medio o Dificil "));
            System.out.println(Ut.tPurp("El Juego por Default parte en Facil.") +
                    "Favor indique solo con un caracter" + Ut.tVerde("F") + "/" + Ut.tYell("M") + "/" + Ut.tRojo("D"));
            System.out.println(Ut.tRojo("[ADVERTENCIA]") + "Esto reiniciara las jugadas y el tablero.");
            System.out.print("Indique dificultad: ");
            String ans = "";
            ans = sc.nextLine();
            if (ans.matches("^[FfMmdD]+$")) {
                if (ans.equalsIgnoreCase("d")) {
                    Tablero.dif = 3;
                    System.out.println(Ut.tRojo("la dificultad actual es Dificil : ") + dif);
                    generarTablero(tableroM);
                    generarTablero(tableroJ);
                    ubicarMinas(tableroM);
                    puntaje = 0;
                    jugadas.removeAll(jugadas);
                    difOK = true;
                } else if (ans.equalsIgnoreCase("m")) {
                    Tablero.dif = 2;
                    System.out.println(Ut.tYell("la dificultad actual es Medio : ") + dif);
                    generarTablero(tableroM);
                    generarTablero(tableroJ);
                    ubicarMinas(tableroM);
                    puntaje = 0;
                    jugadas.removeAll(jugadas);
                    difOK = true;
                } else {
                    Tablero.dif = 1;
                    System.out.println(Ut.tVerde("la dificultad actual es Facil : ") + dif);
                    generarTablero(tableroM);
                    generarTablero(tableroJ);
                    ubicarMinas(tableroM);
                    puntaje = 0;
                    jugadas.removeAll(jugadas);
                    difOK = true;
                }
            }
        }
    }


    //Metodo Inspeccionar donde se descubre lo que hay en la casilla seleccionada.
    public static void inspeccionarArea(String[][] tableroM, String[][] tableroJ) {
        int x;
        int y;
        boolean lanzamientoOk = false;
        while (!lanzamientoOk) {
            try {
                x = askX();
                y = askY();
                if (tableroJ[x][y].equals("[" + Ut.tBlue("I") + "]")) {
                    System.out.println("Ya has inspeccionado esta posicion");
                    break;
                }
                //Se identifica el huevo con la letra H
                lanzamientoOk = true;
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 1; j++) {
                        tableroJ[x + j][y] = "[" + Ut.tBlue("I") + "]";
                    }
                }
                Jugadas jug = new Jugadas();
                jugadas.add(jug);
                verificarInspeccion(tableroM, tableroJ, x, y);
            } catch (Exception e) {
                System.out.println("Posicion incorrecta. Debe de ser de 0 a 4");
            }

        }
    }

    //Te muestra el tablero a cambio de puntos, en caso de que no, se da mensaje.
    public static void mostrarTableroM(String[][] tableroM) {
        boolean consentir = false;
        while (!consentir) {
            System.out.print(Ut.tRojo("[ADVERTENCIA]") + " Ver el tablero te costara 10pts, desea continuar? (y/n)");
            String ans = sc.nextLine();
            if (ans.matches("^[YyNn]$")) {
                if (ans.equalsIgnoreCase("y")) {
                    System.out.print("tramposo.... supongo te gano la angustia de volar en pedazos muy seguido...aqui tienes");
                    System.out.print(Ut.tPurp("\t10 pts han sido restados"));
                    puntaje -= 10;
                    System.out.println(Ut.tRojo("-10"));
                    System.out.println(Ut.tYell("| 0  1  2  3  4 |"));
                    for (int x = 0; x < tableroM.length; x++) {
                        System.out.print("|");
                        for (int y = 0; y < tableroM[x].length; y++) {
                            System.out.print(tableroM[x][y]);
                            if (y != tableroM[x].length - 1) System.out.print("");
                        }
                        System.out.println("|" + Ut.tYell(String.valueOf(x)));
                        consentir = true;
                    }
                } else {
                    if (ans.equalsIgnoreCase("n")) {
                        System.out.println("Bien me parece, sin hacer trampas");
                        break;
                    }
                    consentir = true;
                }
            }
        }

    }

    public static void mostrarTableroJ(String[][] tableroJ) {
        System.out.println("f/c|" + Ut.tYell(" 0  1  2  3  4 |"));
        for (int x = 0; x < tableroJ.length; x++) {
            System.out.print(Ut.tYell(String.format("%2d ", x)) + "|");
            for (int y = 0; y < tableroJ[x].length; y++) {
                System.out.print(tableroJ[x][y]);
                if (y != tableroJ[x].length - 1) System.out.print("");
            }
            System.out.println("|" + Ut.tYell(String.valueOf(x)));
        }
    }


    //Metodo que verifica si la celda inspeccionada tenia o no una mina Oculta.
    public static void verificarInspeccion(String[][] tableroM, String[][] tableroJ, int x, int y) {
        if (!tableroM[x][y].contains("[-]")) {
            if (tableroM[x][y].contains("M")) {
                System.out.println(Ut.tRojo("HABIA UNA MINA OCULTA!! ") + Ut.tPurp("has volado en pedazos...."));
                tableroJ[x][y] = "[" + Ut.tRojo("E") + "]";
                gameOver();
                endGameShow();
            }
        } else if (tableroM[x][y].equals("[-]")) {
            System.out.println(Ut.tCyan("La celda inspeccionada afortunadamente era segura, no hay minas aqui"));
            System.out.println(Ut.tBlue("...tu alivio te reconforta") + Ut.tVerde(" [+] has ganado 3pts de supervivencia"));
            mostrarTableroJ(tableroJ);
            puntaje += 3;
        }
    }

    // metodo para preguntar por valor X
    public static int askX() {
        int x = -1;
        boolean preguntaOk = false;
        while (!preguntaOk) {
            try {
                System.out.println("Ingrese la Fila (" + Ut.tYell("--") + ") donde desea inspeccionar (de 0 a 4):");
                String ax = sc.nextLine();
                if (!ax.matches("^([0-4])$")) {
                    throw new Exception();
                }
                x = Integer.parseInt(ax);
                preguntaOk = true;
            } catch (Exception e) {
                System.out.println("Debe ser un número de 0 a 4, sin letras ni caracteres.");
            }
        }
        return x;
    }

    // metodo para preguntar por valor Y
    public static int askY() {
        int y = -1;
        boolean preguntaOk = false;
        while (!preguntaOk) {
            try {
                System.out.println("Ingrese la Columna (" + Ut.tYell("|") + ") donde desea inspeccionar (de 0 a 4):");
                String ay = sc.nextLine();
                if (!ay.matches("^([0-4])$")) {
                    throw new Exception();
                }
                y = Integer.parseInt(ay);
                preguntaOk = true;
            } catch (Exception e) {
                System.out.println("Debe ser un número de 0 a 4, sin letras ni caracteres.");
            }
        }
        return y;
    }

    private static void gameOver() {
        System.out.println(Ut.tRojo("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⡀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣤⠀⠀⠀⢀⣴⣿⡶⠀⣾⣿⣿⡿⠟⠛⠁\n" +
                "⠀⠀⠀⠀⠀⠀⣀⣀⣄⣀⠀⠀⠀⠀⣶⣶⣦⠀⠀⠀⠀⣼⣿⣿⡇⠀⣠⣿⣿⣿⠇⣸⣿⣿⣧⣤⠀⠀⠀\n" +
                "⠀⠀⢀⣴⣾⣿⡿⠿⠿⠿⠇⠀⠀⣸⣿⣿⣿⡆⠀⠀⢰⣿⣿⣿⣷⣼⣿⣿⣿⡿⢀⣿⣿⡿⠟⠛⠁⠀⠀\n" +
                "⠀⣴⣿⡿⠋⠁⠀⠀⠀⠀⠀⠀⢠⣿⣿⣹⣿⣿⣿⣿⣿⣿⡏⢻⣿⣿⢿⣿⣿⠃⣼⣿⣯⣤⣴⣶⣿⡤⠀\n" +
                "⣼⣿⠏⠀⣀⣠⣤⣶⣾⣷⠄⣰⣿⣿⡿⠿⠻⣿⣯⣸⣿⡿⠀⠀⠀⠁⣾⣿⡏⢠⣿⣿⠿⠛⠋⠉⠀⠀⠀\n" +
                "⣿⣿⠲⢿⣿⣿⣿⣿⡿⠋⢰⣿⣿⠋⠀⠀⠀⢻⣿⣿⣿⠇⠀⠀⠀⠀⠙⠛⠀⠀⠉⠁⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠹⢿⣷⣶⣿⣿⠿⠋⠀⠀⠈⠙⠃⠀⠀⠀⠀⠀⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠈⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⣤⣴⣶⣦⣤⡀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡀⠀⠀⠀⠀⠀⠀⠀⣠⡇⢰⣶⣶⣾⡿⠷⣿⣿⣿⡟⠛⣉⣿⣿⣿⠆\n" +
                "⠀⠀⠀⠀⠀⠀⢀⣤⣶⣿⣿⡎⣿⣿⣦⠀⠀⠀⢀⣤⣾⠟⢀⣿⣿⡟⣁⠀⠀⣸⣿⣿⣤⣾⣿⡿⠛⠁⠀\n" +
                "⠀⠀⠀⠀⣠⣾⣿⡿⠛⠉⢿⣦⠘⣿⣿⡆⠀⢠⣾⣿⠋⠀⣼⣿⣿⣿⠿⠷⢠⣿⣿⣿⠿⢻⣿⣧⠀⠀⠀\n" +
                "⠀⠀⠀⣴⣿⣿⠋⠀⠀⠀⢸⣿⣇⢹⣿⣷⣰⣿⣿⠃⠀⢠⣿⣿⢃⣀⣤⣤⣾⣿⡟⠀⠀⠀⢻⣿⣆⠀⠀\n" +
                "⠀⠀⠀⣿⣿⡇⠀⠀⢀⣴⣿⣿⡟⠀⣿⣿⣿⣿⠃⠀⠀⣾⣿⣿⡿⠿⠛⢛⣿⡟⠀⠀⠀⠀⠀⠻⠿⠀⠀\n" +
                "⠀⠀⠀⠹⣿⣿⣶⣾⣿⣿⣿⠟⠁⠀⠸⢿⣿⠇⠀⠀⠀⠛⠛⠁⠀⠀⠀⠀⠀⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠈⠙⠛⠛⠛⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"));
        System.out.println(Ut.tPurp("*suena de fondo*") + Ut.tCyan("dum dum  dum, Another one bites the dust!"));
        perder = true;
    }

    public static void juegoPerdido(String[][] tableroM) {
        if (perder) {
            boolean resp = false;
            while (!resp) {
                System.out.println(Ut.tBlue("Desea volver a Jugar?, se reseteara el juego (Y/N): "));
                String ans = sc.nextLine();
                if (ans.matches("^[YyNn]$")) {
                    if (ans.equalsIgnoreCase("y") ||ans.equalsIgnoreCase("Y")) {
                        System.out.println("Dale!, otra oportunidad... me gusta tu persistencia");
                        System.out.println(Ut.tPurp("\tTus puntos han sido reseteados y el sistema se ha renovado"));
                        generarTablero(tableroM);
                        generarTablero(tableroJ);
                        ubicarMinas(tableroM);
                        jugadas.removeAll(jugadas);
                        puntaje = 0;
                        perder = false;
                        resp = true;
                    } else if (ans.equalsIgnoreCase("n")) {
                        System.out.println("Hasta la Proxima!");
                        System.exit(0);
                    } else if (ans.equalsIgnoreCase("n")) {
                        System.out.println("Hasta la Proxima!");
                        System.exit(0);
                    }
                }
            }
        }
    }

    private static void youWin() {
        System.out.println(Ut.tVerde("\n" +
                "Y88b   d88P                       888       888 d8b          \n" +
                " Y88b d88P                        888   o   888 Y8P          \n" +
                "  Y88o88P                         888  d8b  888              \n" +
                "   Y888P   .d88b.  888  888       888 d888b 888 888 88888b.  \n" +
                "    888   d88\"\"88b 888  888       888d88888b888 888 888 \"88b \n" +
                "    888   888  888 888  888       88888P Y88888 888 888  888 \n" +
                "    888   Y88..88P Y88b 888       8888P   Y8888 888 888  888 \n" +
                "    888    \"Y88P\"   \"Y88888       888P     Y888 888 888  888 \n" +
                "                                                             \n" +
                "                                                             \n" +
                "                                                             \n"));
    }

    public void contarMovimientos() {
        movimientos = jugadas.size();
        if (jugadas.isEmpty()) {
            movimientos = 0;
        }
    }

    public static void ganarJuego() {
        if (jugadas.size() >= 6) {
            youWin();
            endGameShow();
            boolean resp = false;
            while (!resp) {
                System.out.println(Ut.tBlue("Desea volver a Jugar?, se reseteara el juego (Y/N): "));
                String ans = sc.nextLine();
                if (ans.matches("^[YyNn]$")) {
                    if (ans.equalsIgnoreCase("y")||ans.equalsIgnoreCase("Y")) {
                        System.out.print(Ut.tCyan("Dale!, Vamos por otra Victoria!"));
                        System.out.print(Ut.tPurp("\tTus puntos han sido reseteados y el sistema se ha renovado"));
                        generarTablero(tableroM);
                        generarTablero(tableroJ);
                        ubicarMinas(tableroM);
                        puntaje = 0;
                        jugadas.removeAll(jugadas);
                        perder = false;
                        resp=true;
                    } else if (ans.equalsIgnoreCase("N") ||ans.equalsIgnoreCase("n")){
                            System.out.println("Hasta la Proxima!");
                            System.exit(0);
                            resp = true;
                        }

                } else {
                    System.out.println("opcion invalida");
                }
            }
        }
    }

    public static void endGameShow(){
        System.out.println("Solo por si acaso, aca estaban las minas ubicadas");
        System.out.println(Ut.tYell("| 0  1  2  3  4 |"));
        for (int x = 0; x < tableroM.length; x++) {
            System.out.print("|");
            for (int y = 0; y < tableroM[x].length; y++) {
                System.out.print(tableroM[x][y]);
                if (y != tableroM[x].length - 1) System.out.print("");
            }
            System.out.println("|" + Ut.tYell(String.valueOf(x)));
        }
    }
}

