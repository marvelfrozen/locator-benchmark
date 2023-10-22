import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.io.File
import java.time.Instant

class SeleniumBenchmark {

  @Test
  fun `Selenium Locator Benchmark`() {
    WebDriverManager.chromedriver().setup()
    val chromeOptions = ChromeOptions()
    chromeOptions.addArguments("--no-sandbox")
    chromeOptions.addArguments("--headless")
    chromeOptions.addArguments("disable-gpu")
    val driver = ChromeDriver(chromeOptions)

    driver.get("https://www.youtube.com/")

    Thread.sleep(5000)

    for (i in 1..100) {
      println("start xpath")
      val startXpath1 = Instant.now().toEpochMilli()
      println("start: $startXpath1")

      driver.findElement(By.xpath("//*[@id=\"search\"]"))

      val endXpath1 = Instant.now().toEpochMilli()
      println("end: $endXpath1")
      println("delta: ${endXpath1 - startXpath1}")

      println("\nstart full xpath")
      val startXpath2 = Instant.now().toEpochMilli()
      println("start: $startXpath2")

      driver.findElement(
          By.xpath(
              "/html/body/ytd-app/div[1]/div/ytd-masthead/div[4]/div[2]/ytd-searchbox/form/div[1]/div[1]/input"))

      val endXpath2 = Instant.now().toEpochMilli()
      println("end: $endXpath2")
      println("delta: ${endXpath2 - startXpath2}")

      println("\nstart id")
      val startId = Instant.now().toEpochMilli()
      println("start: $startId")

      driver.findElement(By.id("search"))

      val endId = Instant.now().toEpochMilli()
      println("end: $endId")
      println("delta: ${endId - startId}")

      println("\nstart name")
      val startName = Instant.now().toEpochMilli()
      println("start: $startName")

      driver.findElement(By.name("search_query"))

      val endName = Instant.now().toEpochMilli()
      println("end: $endName")
      println("delta: ${endName - startName}")

      println("\nstart css selector")
      val startCss = Instant.now().toEpochMilli()
      println("start: $startCss")

      driver.findElement(By.cssSelector("#search"))

      val endCss = Instant.now().toEpochMilli()
      println("end: $endCss")
      println("delta: ${endCss - startCss}")

      File("results.csv")
          .appendText(
              "\n$i,${endXpath1 - startXpath1},${endXpath2 - startXpath2},${endId - startId},${endName - startName},${endCss - startCss}")
    }
    driver.quit()
  }
}
