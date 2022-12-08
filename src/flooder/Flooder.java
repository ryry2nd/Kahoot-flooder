package flooder;

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
    private ChromeDriver[] drivers;
    private Worker[] threadClasses;

    public Flooder(int numBts, String identification, String nme){
        id = identification;
        name = nme;
        numBots = numBts;
        numThreads = 1;
        drivers = new ChromeDriver[numBots];
        threadClasses = new Worker[numThreads];
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
        drivers = new ChromeDriver[numBots];
        threadClasses = new Worker[numThreads];
        que = new Queue();
        options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments(" --allowed-ips");

        addJobs();
    }

    public void join() throws InterruptedException {
        int i;

        for (i = 0; i < numThreads; i++){
            threadClasses[i].join();
        }

        for (i = 0; i < numBots; i++) {
            if (drivers[i] != null) {
                drivers[i].close();
            }
        }
    }

    private void addJobs() {
        for (int i = 0; i < numBots; i++) {
            que.addJob(i);
        }
    }

    public void start() {
        Worker thread;
        for (byte i = 0; i < numThreads; i++) {
            thread = new Worker();
            thread.start();
            threadClasses[i] = thread;
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

    public void logInABot(int botNum) throws InterruptedException {
        ChromeDriver driver = new ChromeDriver(options);
        WebElement nameBox;
        
        driver.get("https://kahoot.it?pin=" + id + "&refer_method=link");
        
        Thread.sleep(500);

        nameBox = driver.findElement(By.xpath("//input[@name='nickname']"));

        nameBox.sendKeys(name + botNum);
        nameBox.sendKeys(Keys.ENTER);
        
        drivers[botNum] = driver;
    }
}