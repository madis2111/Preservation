import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        GameProgress game1 = new GameProgress(50, 2, 20, 2.6);
        GameProgress game2 = new GameProgress(60, 4, 25, 2.9);
        GameProgress game3 = new GameProgress(70,  5, 30, 3.6);

        saveGame("D:\\Games\\savegames\\save1.dat", game1);
        saveGame("D:\\Games\\savegames\\save2.dat", game2);
        saveGame("D:\\Games\\savegames\\save3.dat", game3);

        zipFiles("D:\\Games\\savegames\\saved.zip",
                List.of("D:\\Games\\savegames\\save1.dat",
                        "D:\\Games\\savegames\\save2.dat",
                        "D:\\Games\\savegames\\save3.dat"));

    }

    public static void saveGame(String filePath, GameProgress game) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(game);
            oos.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void zipFiles(String zipFilePath, List<String> filesToZip) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {

            for (String filePath : filesToZip) {
                File file = new File(filePath);

                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zos.putNextEntry(zipEntry);

                    int data;
                    while ((data = fis.read()) != -1) {
                        zos.write(data);
                    }

                    zos.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}