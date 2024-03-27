package domeniu;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private static final Properties properties = new Properties();

    public Settings() {
    }

    public static String getRepositoryType() {
        return properties.getProperty("Repository");
    }
    public static String getRepoFileCakes() {
        return properties.getProperty("Cakes");
    }

    public static String getRepoFileOrders() {
        return properties.getProperty("Orders");
    }

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Raluca\\Documents\\GitHub\\a4-c-raluca\\lab5\\src\\main\\java\\domeniu\\settings.properties");

            try {
                properties.load(fileInputStream);
            } catch (Throwable var4) {
                try {
                    fileInputStream.close();
                } catch (Throwable var3) {
                    var4.addSuppressed(var3);
                }

                throw var4;
            }

            fileInputStream.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }
    }

}
