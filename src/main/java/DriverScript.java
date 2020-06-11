
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author harith.fanani
 * @created 11/06/2020
 */
public class DriverScript {

    public static WebDriver driver = null;
    public static String email = "";//Set Email e.g xxx@xxx.com
    public static String password = "";//Set Password e.g 123456

    @BeforeMethod
    public void setup(){
        String chromeDriverPath = "src/drivers/chromedriver";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void close() throws InterruptedException {
        driver.quit();
        Thread.sleep(1000);
    }

    public void openURL(String url){
        driver.get(url);
    }

    public void loggedIn(){
        openURL("https://gist.github.com/");
        driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/a[1]")).click();
        driver.findElement(By.id("login_field")).clear();
        driver.findElement(By.id("login_field")).sendKeys(email);
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"login\"]/form/div[3]/input[9]")).click();
    }

    public void navToGistList(){
        driver.findElement(By.xpath("//*[@id=\"user-links\"]/details/summary")).click();
        driver.findElement(By.xpath("//*[@id=\"user-links\"]/details/details-menu/a[1]")).click();
    }

    public void selectListAtIndex(int idx){//start from 0
        driver.findElements(By.xpath("//*[@id=\"file-test-txt-LC1\"]")).get(idx).click();
    }

    @Test(description = "As a user, I want to create a public gist.")
    public void testCreatePublicGists(){
        loggedIn();
        openURL("https://gist.github.com/");
        driver.findElement(By.xpath("//*[@id=\"gists\"]/input")).sendKeys("Test Public Gists");
        driver.findElement(By.xpath("//*[@id=\"gists\"]/div[2]/div/div[1]/div[1]/input[2]")).sendKeys("Test.txt");
        driver.findElement(By.xpath("//*[@id=\"gists\"]/div[2]/div/div[2]/div/div[5]/div[1]/div/div/div/div[5]/div/pre")).sendKeys("Hello World");
        driver.findElement(By.xpath("//*[@id=\"new_gist\"]/div/div[2]/button[1]")).click();
    }

    @Test(description = "As a user, I want to edit an existing gist.")
    public void testEditGists(){
        loggedIn();
        openURL("https://gist.github.com/");
        navToGistList();
        selectListAtIndex(0);
        driver.findElement(By.xpath("//*[@id=\"gist-pjax-container\"]/div[1]/div/div[1]/ul/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"gists\"]/div[2]/div[2]/div[2]/div/div[5]/div[1]/div/div/div/div[5]/div/pre")).clear();
        driver.findElement(By.xpath("//*[@id=\"gists\"]/div[2]/div[2]/div[2]/div/div[5]/div[1]/div/div/div/div[5]/div/pre")).sendKeys("Hello World 2");
        driver.findElement(By.xpath("//*[@id=\"edit_gist_103657090\"]/div/div[2]/button")).click();
    }

    @Test(description = "As a user, I want to delete an existing gist.")
    public void testDeleteGists(){
        loggedIn();
        openURL("https://gist.github.com/");
        navToGistList();
        selectListAtIndex(0);
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
        driver.switchTo().alert().accept();//Click YES Button
    }

    @Test(description = "As a user, I want to see my list of gists.")
    public void testListOfGists(){
        loggedIn();
        openURL("https://gist.github.com/");
        navToGistList();//User Able to see list of gists
    }
}

