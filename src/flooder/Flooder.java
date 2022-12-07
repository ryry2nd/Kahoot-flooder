package flooder;

//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class Flooder{
    private String name;
    private String id;
    private ChromeOptions options;
    private Queue que;
    private int numBots;
    private byte numThreads;

    public Flooder(int numBts, String identification, String nme){
        id = identification;
        name = nme;
        numBots = numBts;
        numThreads = 1;
        que = new Queue();
        options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments(" --allowed-ips");

        addJobs();
    }

    public Flooder(int numBts, String identification, String nme, byte threads) {
        id = identification;
        name = nme;
        numBots = numBts;
        numThreads = threads;
        que = new Queue();
        options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments(" --allowed-ips");

        addJobs();
    }

    public void start() {
        Worker thread;
        for (byte i = 0; i < numThreads; i++) {
            thread = new Worker();
            thread.start();
        }
    }

    private class Worker extends Thread{
        public void run() {
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
    }

    private void addJobs() {
        for (int i = 0; i < numBots; i++) {
            que.addJob(i);
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
}