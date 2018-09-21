package ru.valiullin;

import com.sun.javafx.binding.StringFormatter;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.*;


public class Main {

    public static void main(String[] args) throws IOException {

        //todo Написать программу, которая будет создавать, переименовывать, копировать и удалять файл.
        //Написать рекурсивный обход всех файлов и подкаталогов внутри заданного каталога.
        // Программа должна следить за глубиной рекурсии, сдвигая название файла/каталога на соответствующее количество пробелов.

        //Создаем

        String notNewFile = "." + File.separator + "notNewFile.txt";
        String newFile = "." + File.separator + "NewFile.txt";
        File file = new File(newFile);
        //File dir = new File("." + File.separator + "dir");
        Path path = file.toPath();
        Path copyPath = Paths.get(".","copyDir", "NewFile.txt");

        if(file.createNewFile()){
            System.out.println("File created!");
        } else {
            System.out.println("May be file already exist?");
        }
        //копируем
        Files.createDirectories(copyPath.getParent());
        try {
            Files.copy(path, copyPath);
        } catch (FileAlreadyExistsException ex) {
            System.out.println(ex);
        }
        //переименовываем и удаляем
        file.renameTo(new File(notNewFile));
        try {
            Files.deleteIfExists(Paths.get(notNewFile));
        } catch (FileAlreadyExistsException ex) {
            System.out.println(ex);
        }

        File toRecurs = new File(".\\recursDir");
        //System.out.println(Arrays.toString(toRecurs.listFiles()));
        recursiveDir(toRecurs);
        System.out.println("/--------------------------/");
        recursiveDirVer2(toRecurs);
        System.out.println("/--------------------------/");
        recursiveDirVer3(toRecurs);
    }
//String.format ??
    private static void recursiveDir(File toRecurs) {
//ver1
        if (toRecurs.isDirectory()) {
            //System.out.println(files.getParent());
            File[] files = toRecurs.listFiles();
            for (File file : files) {
                int a = file.getParent().length();
                //System.out.println(toRecurs.getName());
                System.out.println(String.format( "%" + a + "s","") + file.getName());
                recursiveDir(file);
            }
        }
    }
    private static void recursiveDirVer2(File toRecurs) {
        //ver2
        StringBuilder sb = new StringBuilder();
        if (toRecurs.isDirectory()) {
            File[] files = toRecurs.listFiles();
            for (File file : files) {
                int a = file.getParent().length();
                for (int i = 0; i < a; i++) {
                    sb.append(" ");
                }
                System.out.println(sb+file.getName());
                recursiveDirVer2(file);
            }
        }
    }
    //ver3
    private static void recursiveDirVer3(File toRecurs) {
        if (toRecurs.isDirectory()) {
            File[] files = toRecurs.listFiles();
            for (File file : files) {
                int a = file.getParent().length();
                System.out.println(Stream.generate(() -> " ").limit(a).collect(Collectors.joining()) + file.getName());
                //System.out.println(String.format( "%" + a + "s","") + file.getName());
                recursiveDir(file);
            }
        }
    }
}
