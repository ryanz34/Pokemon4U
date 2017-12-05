import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class pokeTools {
    private static Scanner input = new Scanner(System.in);
    private static Random rNumber = new Random();

    public static String getData(String helper, String error, String[] data) {
        HashSet<String> correctResponse = new HashSet<>(Arrays.asList(data));
        String response;

        while (true) {
            pokeTools.delayPrint(helper);
            response = input.next();
            if (correctResponse.contains(response)) {
                return response;
            }

            pokeTools.delayPrintln(error);
        }
    }

    public static int getRange(String error, String helper, int starting, int end) {
        String response;
        int selection;

        while (true) {
            pokeTools.delayPrint(helper);
            response = input.next();

            if (response.matches("^[0-9]+$")) {
                selection = Integer.parseInt(response);
                if (selection >= starting && selection <= end) {
                    return selection;
                }
            }

            pokeTools.delayPrintln(error);
        }
    }

    public static void mainMenu () {
        String[] pokeTitle = {
                "                               .::.                           ",
                "                              .;:**'                          ",
                "                              `                               ",
                "  .:XHHHHk.              db.   .;;.     dH  MX                ",
                "oMMMMMMMMMMM       ~MM  dMMP :MMMMMR   MMM  MR      ~MRMN     ",
                "QMMMMMb  'MMX       MMMMMMP !MX' :M~   MMM MMM  .oo. XMMM 'MMM",
                "  `MMMM.  )M> :X!Hk. MMMM   XMM.o'  .  MMMMMMM X?XMMM MMM>!MMP",
                "   'MMMb.dM! XM M'?M MMMMMX.`MMMMMMMM~ MM MMM XM `' MX MMXXMM ",
                "    ~MMMMM~ XMM. .XM XM`'MMMb.~*?**~ .MMX M t MMbooMM XMMMMMP ",
                "     ?MMM>  YMMMMMM! MM   `?MMRb.    `MM   !L'MMMMM XM IMMM   ",
                "      MMMX   'MMMM'  MM       ~%:           !Mh.''' dMI IMMP  ",
                "      'MMM.                                             IMX   ",
                "       ~M!M                                             IM    "
        };

        for (String l : pokeTitle) {
            delayPrintTable(l);
        }
        delayPrintln("");
        delayPrintln("Game made by: Ryan Zhang");
        delayPrintln("");

        delayPrintln("Welcome to pokemon");

        pokeTools.delayPrint("Press enter to continue...");
        try {System.in.read();} catch (Exception e){}
    }

    public static int randint(int min, int max) {
        return rNumber.nextInt(max-min+1) + min;
    }

    public static void delayPrintln(String toPrint) {
        delayPrint(toPrint + "\n", 5, 0);
    }

    public static void delayPrintTable(String toPrint) {
        delayPrint(toPrint + "\n",0, 1000000);
    }

    public static void delayPrint(String toPrint) {
        delayPrint(toPrint, 5, 0);
    }

    public static void delayPrint(String toPrint, int micro, int nano) {
        for (char c : toPrint.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(micro, nano);
            } catch (Exception e) {
            }
        }
    }

    public static void clearScreen() {
        for (int j = 0; j < 100; j++)
            System.out.println();
    }

}