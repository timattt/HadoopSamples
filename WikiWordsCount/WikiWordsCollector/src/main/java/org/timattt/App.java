package org.timattt;

import lombok.SneakyThrows;

import java.io.File;
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
        parse(Path.of(args[0]));
    }

    private static final int linesToTake = 10;

    @SneakyThrows
    private static void processFile(List<String> dest, Path path) {
        Scanner scanner = new Scanner(path.toFile());

        for (int i = 0; i < linesToTake && scanner.hasNext(); i++) {
            String line = scanner.nextLine();
            dest.add(line);
        }

        scanner.close();
    }

    @SneakyThrows
    private static void parse(Path dir) {
        LinkedList<String> dest = new LinkedList<>();
        Files.list(dir).forEach(path -> processFile(dest, path));

        for (String line : dest) {
            String[] divs = line.split("\t");
            int count = - Integer.parseInt(divs[0]);
            String word = divs[1];
            System.out.println(count + " " + word);
        }
    }
}
