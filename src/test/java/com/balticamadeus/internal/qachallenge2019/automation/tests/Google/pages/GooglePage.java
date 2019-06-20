package com.balticamadeus.internal.qachallenge2019.automation.tests.Google.pages;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class GooglePage extends BasePage {

  private By searchField = By.cssSelector("[name='q']");
  private By suggestionList = By.cssSelector("[role='listbox'] [role='presentation']");

  public GooglePage(WebDriver driver){
    super(driver);
  }

  public GooglePage openGooglePage(){
     open("/");
    return this;
  }

  public GoogleResultPage searchFor(String query){
    inputIntoSearchField(query);
    return submitSearchField();
  }

  public GooglePage inputIntoSearchField(String criteria){
    getSearchField().sendKeys(criteria);
    return this;
  }

  public GoogleResultPage submitSearchField(){
    getSearchField().submit();
    return new GoogleResultPage(driver);
  }

  public WebElement getSearchField(){
    return driver.findElement(searchField);
  }

  public GooglePage checkIfsuggestionsContain(String critiria){
    return checkIfSuggestionList(containsString(critiria.toLowerCase()));
  }

  public GooglePage checkIfsuggestionsDoesntContain(String critiria){
    return checkIfSuggestionList(not(containsString(critiria.toLowerCase())));
  }

  private GooglePage checkIfSuggestionList(Matcher matcher){
    getSuggestionList().forEach((element) -> Assert.assertThat(element.getText().toLowerCase(), matcher));
    return this;
  }

  public List<WebElement> getSuggestionList(){
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(suggestionList));
    return driver.findElements(suggestionList);
  }
}
