package me.r1411.graphpaint;

import javafx.util.Pair;
import me.r1411.graphpaint.objects.Graph;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        File workingDir = new File(System.getProperty("user.dir"));
        File inputFile = new File(workingDir, "input.txt");
        File outputFile = new File(workingDir, "output.txt");

        byte[][] graphMatrix;
        int k;

        try {
            Pair<Integer, byte[][]> p = getDataFromFile(inputFile);
            k = p.getKey();
            graphMatrix = p.getValue();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Не удалось прочитать файл: " + inputFile.getAbsolutePath(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Задана некорректная матрица смежности вершин", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Graph graph = new Graph(graphMatrix);

        colorizeGraph(graph, k, graph.getVertices().size(), outputFile);
    }

    private static void colorizeGraph(Graph graph, int k, int n, File outputFile) {
        int[] colors = new int[n];

        for(int i = 0; i < Math.pow(k, n); i++) {
            graph.colorize(colors);

            if(graph.isProperlyColorized()) {
                writeResultToFile(outputFile, colors, k, true);
                return;
            }

            //Переход на следующую комбинацию цветов
            colors[n - 1] += 1;
            for(int j = n - 1; j > 0; j--) {
                colors[j-1] += colors[j] / k;
                colors[j] = colors[j] % k;
            }
        }
        writeResultToFile(outputFile, colors,  k,false);
    }

    private static void writeResultToFile(File file, int[] colors, int k, boolean success) {
        List<String> lines = new ArrayList<>();
        lines.add("Результат: ");

        if(success) {
            StringBuilder str = new StringBuilder();
            HashMap<Integer, Set<Integer>> colorsMap = new HashMap<>();
            for(int i = 0; i < colors.length; i++) {
                if(!colorsMap.containsKey(colors[i]))
                    colorsMap.put(colors[i], new HashSet<>());
                colorsMap.get(colors[i]).add(i);
            }

            for(int color : colorsMap.keySet()) {
                lines.add("Вершины цвета " + color + ": " + colorsMap.get(color));
            }

            lines.add(str.toString());
        } else {
            lines.add("Раскраски для k = " + k + " не существует");
        }

        try {
            Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Не удалось записать файл: " + file.getAbsolutePath(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private static Pair<Integer, byte[][]> getDataFromFile(File file) throws IOException, NumberFormatException, IndexOutOfBoundsException {
        List<String> lines = Files.readAllLines(file.toPath());
        int k = Integer.parseInt(lines.remove(0));
        int vertexCount = lines.size();
        byte[][] matrix = new byte[vertexCount][vertexCount];


        for(int i = 0; i < vertexCount; i++) {
            String[] row = lines.get(i).trim().split(" ");
            for(int j = 0; j < row.length; j++) {
                matrix[i][j] = Byte.parseByte(row[j]);
            }
        }

        return new Pair<>(k, matrix);
    }
}
