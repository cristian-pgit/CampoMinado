import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static Tablero board = new Tablero();

    //funcion main
    public static void main(String[] Args) {
        board.generarTableroM(); // genera el tablero seteando el valor default del [-]
        board.generarTableroJ();  // genera el tableroH seteando el valor default del [-]
        board.ubicarMinas(board.tableroM); //ubica las minas.
        //Historia
        System.out.println(Ut.tYell("[BRIEFING]")+Ut.tVerde("Soldado!, ha sido encomendado cruzar el siguente tramo...\n"+
                "pero al de hacerlo con mucho cuidado, pues es un campo Minado!,\n cualquier movimiento en falso y terminara volando por los cielos")+
                Ut.tBlue("\nBuena Suerte!"));
        // Menu
        Scanner sc = new Scanner(System.in); // invocacion del objeto scanner
        boolean keepPlaying = true;
        while (keepPlaying) {
            board.ganarJuego();
            board.juegoPerdido(board.tableroM);
            board.contarMovimientos();
            System.out.println();
            System.out.println(Ut.tVerde("Bientenido al Menu de <<")+Ut.tYell("CAMPO MINADO")+Ut.tVerde(">>") +
                    Ut.tVerde("\tTu Puntaje de Supervivencia: ") + Ut.tBlue(String.valueOf(board.puntaje)));
            System.out.println("Cantidad de Movimientos: "+board.movimientos+ "\tEstas a "+Ut.tBlue(String.valueOf(6- board.movimientos))+" de ganar");
            System.out.println(Ut.tCyan("Ingrese una opción:\n"
                    + "\t1. Regular dificultad\n"
                    + "\t2. Aventurarse e Inspeccionar celda\n"
                    + "\t3. Mostrar las minas\n"
                    + "\t4. Rendirse"));
            try {
                String op = sc.nextLine();
                if (!op.matches("^[1-4]$")) {
                    System.out.println("Valor incorrecto. Intente de nuevo");
                    continue;
                }
                int opcion = Integer.parseInt(op);
                switch (opcion) {
                    case 1:
                        board.setDif();
                        break;
                    case 2:
                        board.inspeccionarArea(board.tableroM, board.tableroJ);
                        break;
                    case 3:
                        board.mostrarTableroM(board.tableroM);
                        break;
                    case 4:
                        keepPlaying = false;
                        System.out.println("Hasta una proxima ocasion...");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Valor incorrecto. Intente de nuevo");
            }


        }

        sc.close();
    }


}