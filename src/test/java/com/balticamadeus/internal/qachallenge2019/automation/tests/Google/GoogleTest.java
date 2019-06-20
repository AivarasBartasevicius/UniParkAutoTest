package com.balticamadeus.internal.qachallenge2019.automation.tests.Google;

import com.balticamadeus.internal.qachallenge2019.automation.tests.Google.pages.GooglePage;
import com.balticamadeus.internal.qachallenge2019.automation.tests.Google.pages.GoogleResultPage;
import com.balticamadeus.internal.qachallenge2019.automation.tests.Google.testconfig.Config;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GoogleTest {
  WebDriver driver;
  GooglePage googlePage;
  String searchCritiria;

  @Before
  public void setUp(){

    driver = new ChromeDriver(new ChromeOptions().addArguments(Config.ARGUMENTS));
    googlePage = new GooglePage(driver);
    searchCritiria = "cheese";
  }

  @Test
  public void googleForCheese() {
    googlePage.openGooglePage()
            .searchFor(searchCritiria)
            .checkIfSearchResultsContain(searchCritiria);
  }

  @Test
  public void openGoogleResultPage(){
    new GoogleResultPage(driver)
            .openGoogleResultPage(searchCritiria)
            .checkIfSearchResultsContain(searchCritiria);
  }

  @Test
  public void checkGoogleSearchAutoSuggestion() {
    googlePage.openGooglePage()
            .inputIntoSearchField(searchCritiria)
            .checkIfsuggestionsContain(searchCritiria);
  }

  @Test
  public void checkGoogleSearchAutoSuggestion2() {
    googlePage.openGooglePage()
            .inputIntoSearchField(searchCritiria)
            .checkIfsuggestionsContain(searchCritiria)
            .checkIfsuggestionsDoesntContain("sosig");
  }

  @After
  public void quitBrowser() {
    driver.quit();
  }
}
