package org.timattt;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        parse();
    }

    private static final int linesToTake = 10;

    @SneakyThrows
    private static void processFile(List<String> dest, String path) {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"hadoop", "fs", "-tail", path};
        Process proc = rt.exec(commands);

        int status = proc.waitFor();

        Scanner scanner = new Scanner(proc.getInputStream());

        for (int i = 0; i < linesToTake && scanner.hasNext(); i++) {
            String line = scanner.nextLine();
            dest.add(line);
        }

        scanner.close();
    }

    private static String getName(int i) {
        String result = "/user/hobod2024s052/output/ResortJobResult/part-r-000";
        if (i < 10) {
            result += "0";
        }
        result += i;
        return result;
    }

    @SneakyThrows
    private static void parse() {
        LinkedList<String> dest = new LinkedList<String>();
        for (int i = 0; i < 16; i++) {
            processFile(dest,  getName(i));
        }

        for (String line : dest) {
            if (!line.contains("\t")) {
                continue;
            }
            String[] divs = line.split("\t");
            int count = - Integer.parseInt(divs[0]);
            String word = divs[1];
            System.out.println(count + " " + word);
        }
    }
}
