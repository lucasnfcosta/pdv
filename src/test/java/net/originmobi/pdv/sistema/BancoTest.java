package net.originmobi.pdv.sistema;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BancoTest {

    private ChromeDriver driver;

    @BeforeClass
    public static void configuraDriver() {
        System.setProperty("webdriver.chrome.driver", "D:\\Ferramentas\\ChromeDriver\\chromedriver.exe");
    }
    
    @Before
    public void createDriver() {
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/login");
        preencheLogin(driver);
        selecionaBanco(driver);
    }

    @Test
    public void testaSuprimento() {

    }

    private void preencheLogin(ChromeDriver driver) {
        WebElement user = driver.findElement(By.id("user"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement entrar = driver.findElement(By.id("btn-login"));

        user.sendKeys("gerente");
        password.sendKeys("123");
        entrar.click();
    }

    private void selecionaBanco(ChromeDriver driver) {
        WebElement banco = driver.findElement(By.xpath("/html/body/div[3]/div/div[4]/a"));
        banco.click();
    }
    
}
