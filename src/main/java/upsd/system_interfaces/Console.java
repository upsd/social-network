package upsd.system_interfaces;

import java.util.Scanner;

public class Console {

    public void printLine(String textToPrint) {
        System.out.println(textToPrint);
    }

    public String readInput() {
        Scanner reader = new Scanner(System.in);
        String text = reader.nextLine();
        return text;
    }
}
