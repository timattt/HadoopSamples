package org.timattt;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.*;

public class App 
{
    public static void main(String[] args) {
        parse(args[0]);
    }

    @SneakyThrows
    private static boolean processFile(List<String> dest, String path) {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"hadoop", "fs", "-cat", path};
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
        String result = dir + "/OnlyThreadResult/part-r-000";
        if (i < 10) {
            result += "0";
        }
        result += i;
        return result;
    }

    @SneakyThrows
    private static void parse(String dir) {
        LinkedList<String> dest = new LinkedList<String>();
        for (int i = 0; true; i++) {
            if (processFile(dest,  getName(dir, i))) {
                break;
            }
        }

        TreeMap<String, Double> data = new TreeMap<String, Double>();

        for (String line : dest) {
            String[] divs = line.split("\t");
            String thread = divs[0];
            double value = Double.parseDouble(divs[1]);
            if (data.containsKey(thread)) {
                data.put(thread, (data.get(thread) + value) / 2);
            } else {
                data.put(thread, value);
            }
        }

        for (String thread : data.keySet()) {
            System.out.println(thread + "\t" + data.get(thread));
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
