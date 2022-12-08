import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Properties;

import flooder.Flooder;

public class App {
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        Properties props = new Properties();

        props.load(new FileInputStream("config.properties"));

        int num = Integer.parseInt(props.getProperty("num"));
        String id = props.getProperty("id");
        String name = props.getProperty("name");
        byte threads = Byte.parseByte(props.getProperty("threads"));
        
        Flooder fl = new Flooder(num, id, name, threads);
        fl.start();
    }
}
