import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.Test
import org.openqa.selenium.By.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.io.File
import java.time.Instant

class SeleniumBenchmark {
  data class Locator(val by: String, val expression: String)

  private val commandsByName = listOf(::xpath, ::id, ::name, ::cssSelector).associateBy { it.name }

  private val target = "https://www.youtube.com/"
  private val numberOfCycle = 100
  private val testLocators =
      listOf(
          Locator("xpath", "//*[@id=\"search\"]"),
          Locator(
              "xpath",
              "/html/body/ytd-app/div[1]/div/ytd-masthead/div[4]/div[2]/ytd-searchbox/form/div[1]/div[1]/input"),
          Locator("xpath", "//input[contains(@id,\"search\")]"),
          Locator("id", "search"),
          Locator("name", "search_query"),
          Locator("cssSelector", "#search"),
      )

  private lateinit var driver: ChromeDriver

  private fun benchmark(by: String, expression: String): Long {
    println("start $by")
    val start = Instant.now().toEpochMilli()
    println("start: $start")

    driver.findElement(commandsByName[by]!!(expression))

    val end = Instant.now().toEpochMilli()
    println("end: $end")
    println("delta: ${end - start}")
    return end - start
  }

  @Test
  fun `Selenium Locator Benchmark`() {
    WebDriverManager.chromedriver().setup()
    val chromeOptions = ChromeOptions()
    chromeOptions.addArguments("--no-sandbox")
    chromeOptions.addArguments("--headless")
    chromeOptions.addArguments("disable-gpu")
    driver = ChromeDriver(chromeOptions)

    driver.get(target)

    Thread.sleep(5000)
    for (i in 1..testLocators.size) {
      File("results.csv").appendText("\"${testLocators[i-1].by}\"")
      if (i != testLocators.size) {
        File("results.csv").appendText(",")
      }
    }
    File("results.csv").appendText("\n")

    for (i in 1..numberOfCycle) {
      for (j in 1..testLocators.size) {
        File("results.csv")
            .appendText("${benchmark(testLocators[j-1].by,testLocators[j-1].expression)}")
        if (j != testLocators.size) {
          File("results.csv").appendText(",")
        }
      }
      File("results.csv").appendText("\n")
    }

    driver.quit()
  }
}
