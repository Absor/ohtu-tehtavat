
package ohtu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Tester {
    public static void main(String[] args) {
        WebDriver driver = new HtmlUnitDriver();

        driver.get("http://localhost:8080");
        WebElement element = driver.findElement(By.linkText("login"));       
        element.click(); 
        
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akke");
        element = driver.findElement(By.name("login"));
        element.submit();
        
        if (driver.getPageSource().contains("wrong username or password")) {
            System.out.println("epäonnistunut kirjautuminen");
        } 
        
        element = driver.findElement(By.name("username"));
        element.sendKeys("ekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akke");
        element = driver.findElement(By.name("login"));
        element.submit();
        
        if (driver.getPageSource().contains("wrong username or password")) {
            System.out.println("kirjautuminen eiolemassaolevalla käyttäjätunnuksella");
        }
        
        element = driver.findElement(By.linkText("back to home"));       
        element.click();
        
        element = driver.findElement(By.linkText("register new user"));       
        element.click();
        
        element = driver.findElement(By.name("username"));
        element.sendKeys("paavo");
        element = driver.findElement(By.name("password"));
        element.sendKeys("p44vonpassu");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("p44vonpassu");
        element = driver.findElement(By.name("add"));
        element.submit();
        
        if (driver.getPageSource().contains("info for newbie")) {
            System.out.println("uuden käyttäjä tunnuksen luominen onnistui");
        } 
        
        element = driver.findElement(By.linkText("continue to application mainpage"));       
        element.click();
        
        element = driver.findElement(By.linkText("logout"));       
        element.click();
        
        element = driver.findElement(By.linkText("login"));       
        element.click(); 
        
        element = driver.findElement(By.name("username"));
        element.sendKeys("paavo");
        element = driver.findElement(By.name("password"));
        element.sendKeys("p44vonpassu");
        element = driver.findElement(By.name("login"));
        element.submit();
        
        if (driver.getPageSource().contains("logout")) {
            System.out.println("uudella käyttäjätunnuksella kirjautuminen onnistui");
        } 
        
    }
}
