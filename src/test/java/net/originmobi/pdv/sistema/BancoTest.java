package net.originmobi.pdv.sistema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
    }

    @After
    public void fechaBrowser() {
        driver.close();
    }

    @Test
    public void testaCriarNovoBanco() {
        preencheLogin(driver);
        selecionaBanco(driver);
        
        WebElement abrirNovo = driver.findElement(By.xpath("/html/body/section[2]/div/div/div/div[2]/a"));
        abrirNovo.click();

        WebElement observacao = driver.findElement(By.xpath("/html/body/section[1]/div/div/form/div[3]/div/input"));
        observacao.sendKeys("observação");

        WebElement selectBanco = driver.findElement(By.xpath("/html/body/section[1]/div/div/form/div[4]/div/select/option[3]"));
        selectBanco.click();

        WebElement agencia = driver.findElement(By.xpath("/html/body/section[1]/div/div/form/div[5]/div/input[1]"));
        agencia.sendKeys("11111");

        WebElement conta = driver.findElement(By.xpath("/html/body/section[1]/div/div/form/div[5]/div/input[2]"));
        conta.sendKeys("111111");

        WebElement valorAbertura = driver.findElement(By.xpath("/html/body/section[1]/div/div/form/div[6]/div/input"));
        valorAbertura.sendKeys("10000");

        WebElement abrirBotao = driver.findElement(By.xpath("/html/body/section[1]/div/div/form/a"));
        abrirBotao.click();

        WebDriverWait wait = new WebDriverWait(driver, Long.valueOf(10).longValue());
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section[1]/div/div/div[1]/div[1]/h2")));

        assertEquals(title.getText(), "Gerenciar Banco");
    }

    @Test
    public void testeSuprimento() {
        preencheLogin(driver);
        selecionaBanco(driver);

        WebElement gerenciar = driver.findElement(By.xpath("/html/body/section[2]/div/div/div/table/tbody/tr/td[7]/a"));
        gerenciar.click();

        WebElement suprimento = driver.findElement(By.xpath("/html/body/section[1]/div/div/div[3]/div[1]/button"));
        suprimento.click();

        WebDriverWait wait = new WebDriverWait(driver, Long.valueOf(10).longValue());
        WebElement valor = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section[1]/div/div/div[4]/div/div/div/div/form/div[1]/div/input")));

        valor.sendKeys("10000");

        WebElement obs = driver.findElement(By.xpath("/html/body/section[1]/div/div/div[4]/div/div/div/div/form/div[2]/div/input"));
        obs.sendKeys("observação");

        WebElement confirmar = driver.findElement(By.xpath("/html/body/section[1]/div/div/div[4]/div/div/div/div/form/div[3]/a"));
        confirmar.click();

        Alert alerta = wait.until(ExpectedConditions.alertIsPresent());
        String mensagemAlerta = alerta.getText();
        alerta.dismiss();

        assertEquals(mensagemAlerta, "Lançamento realizado com sucesso");
    }

    @Test
    public void testeRetirada() {
        preencheLogin(driver);
        selecionaBanco(driver);

        WebElement gerenciar = driver.findElement(By.xpath("/html/body/section[2]/div/div/div/table/tbody/tr/td[7]/a"));
        gerenciar.click();

        WebElement retirar = driver.findElement(By.xpath("/html/body/section[1]/div/div/div[3]/div[2]/button"));
        retirar.click();

        WebDriverWait wait = new WebDriverWait(driver, Long.valueOf(10).longValue());
        WebElement valor = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section[1]/div/div/div[5]/div/div/div/div/form/div[1]/div/input")));
        valor.sendKeys("10000");

        WebElement obs = driver.findElement(By.xpath("/html/body/section[1]/div/div/div[5]/div/div/div/div/form/div[2]/div/input"));
        obs.sendKeys("retirada");

        WebElement confirmar = driver.findElement(By.xpath("/html/body/section[1]/div/div/div[5]/div/div/div/div/form/div[3]/a"));
        confirmar.click();

        Alert alerta = wait.until(ExpectedConditions.alertIsPresent());
        String mensagemAlerta = alerta.getText();
        alerta.dismiss();

        assertEquals(mensagemAlerta, "Lançamento realizado com sucesso");
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

    private boolean isAlertPresent() { 
        try { 
            driver.switchTo().alert(); 
            return true;
        }
        catch (NoAlertPresentException Ex) { 
            return false; 
        }   
    }
    
}
