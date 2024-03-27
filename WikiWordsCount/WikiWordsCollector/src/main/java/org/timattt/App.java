package org.timattt;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        parse(args[0], Integer.parseInt(args[1]));
    }

    @SneakyThrows
    private static boolean processFile(List<String> dest, String path) {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"hadoop", "fs", "-tail", path};
        Process proc = rt.exec(commands);

        int status = proc.waitFor();

        if (status != 0) {
            return true;
        }

        Scanner scanner = new Scanner(proc.getInputStream());

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            dest.add(line);
        }

        scanner.close();

        return false;
    }

    private static String getName(String dir, int i) {
        String result = dir + "/ResortJobResult/part-r-000";
        if (i < 10) {
            result += "0";
        }
        result += i;
        return result;
    }

    @SneakyThrows
    private static void parse(String dir, int maxOut) {
        LinkedList<String> dest = new LinkedList<String>();
        for (int i = 0; true; i++) {
            if (processFile(dest,  getName(dir, i))) {
                break;
            }
        }

        Set<Pair> pairs = new TreeSet<Pair>();

        for (String line : dest) {
            try {
                String[] divs = line.split("\t");
                int count = Integer.parseInt(divs[0]);
                String word = divs[1];
                pairs.add(new Pair(count, word));
            } catch (Exception ignored) {
            }
        }

        int total = 0;
        for (Pair p : pairs) {
            System.out.println(p.word + "\t" + p.count);
            total++;
            if (total == maxOut) {
                return;
            }
        }
    }

    @AllArgsConstructor
    private static class Pair implements Comparable<Pair> {
        int count;
        String word;

        @Override
        public int compareTo(Pair o) {
            return -count + o.count;
        }
    }
}
