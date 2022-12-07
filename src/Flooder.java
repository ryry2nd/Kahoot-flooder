//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import queue.Queue;

public class Flooder {
    private String name;
    private String id;
    private ChromeOptions options;
    private Queue que;
    private int numBots;

    public Flooder(int numBts, String identification, String nme){
        id = identification;
        name = nme;
        numBots = numBts;
        que = new Queue();
        options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments(" --allowed-ips");

        addJobs();
    }

    public void start() {
        worker();
    }

    private void addJobs() {
        for (int i = 0; i < numBots; i++) {
            que.addJob(i);
        }
    }

    private void worker() {
        int it;

        while (!que.isEmpty()) {
            it = que.getJob();
            try {
                logInABot(it);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        
        Flooder fl = new Flooder(num, id, name);
        fl.start();

        while (true) {Thread.sleep(1000);}
    }
}