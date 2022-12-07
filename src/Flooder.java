import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Flooder {
    private String name;
    private String id;
    private ChromeOptions options;

    public Flooder(int numBots, String identification, String nme) throws InterruptedException {
        id = identification;
        name = nme;
        options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments(" --allowed-ips");
        
        for (int i = 0; i < numBots; i++) {
            logInABot(i);
        }
    }

    private void logInABot(int botNum) throws InterruptedException {
        ChromeDriver driver = new ChromeDriver(options);
        WebElement nameBox;
        
        driver.get("https://kahoot.it?pin=" + id + "&refer_method=link");
        
        Thread.sleep(500);

        nameBox = driver.findElement(By.xpath("//input[@name='nickname']"));

        nameBox.sendKeys(name + botNum);
        nameBox.sendKeys(Keys.ENTER);

        Thread.sleep(500);

        if (driver.findElements(By.xpath("//input[@name='nickname']")).size() > 0) {
            driver.close();
        }
    }

    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
        Properties props = new Properties();

        props.load(new FileInputStream("config.properties"));

        int num = Integer.parseInt(props.getProperty("num"));
        String id = props.getProperty("id");
        String name = props.getProperty("name");
        
        new Flooder(num, id, name);
        while (true) {Thread.sleep(1000);}
    }
}