package com.balticamadeus.internal.qachallenge2019.automation.tests.Google.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;

public class GoogleResultPage extends BasePage {

  private By searchResults = By.cssSelector(".srg .g");

  public GoogleResultPage(WebDriver driver){
    super(driver);
  }

  public GoogleResultPage checkIfSearchResultsContain(String critiria){
    List<WebElement> result = driver.findElements(searchResults);
    for(WebElement temp : result) {
      Assert.assertThat(temp.getText().toLowerCase(), containsString(critiria.toLowerCase()));
    }
    return this;
  }

  public GoogleResultPage openGoogleResultPage(String query){
    open("/search?q=" + query);
    return this;
  }
}
