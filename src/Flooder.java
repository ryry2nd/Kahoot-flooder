import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;


public class Flooder {
    private int numBots;
    private String name;
    private String id;
    private ChromeOptions options;

    public Flooder(int nBts, String identification, String nme) throws InterruptedException {
        numBots = nBts;
        id = identification;
        name = nme;
        options = new ChromeOptions();
        options.addArguments("--headless");
        
        for (int i = 0; i < nBts; i++) {
            logInABot(0);
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

    public static void main(String[] args) throws InterruptedException {
        Flooder fl = new Flooder(100, "5050140", "poopoo");
        while (true) {Thread.sleep(1000);}
    }
}