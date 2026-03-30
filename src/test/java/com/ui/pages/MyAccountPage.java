package com.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.utility.BrowserUtility;

public final class MyAccountPage extends BrowserUtility{

    private static final By USERNAME_LOCATOR = By.xpath("//a[@title='View my customer account']/span");
    private static final By SEARCH_TEXT_BOX_LOCATOR = By.id("search_query_top");
    private static final By SEARCH_BUTTON_LOCATOR = By.id("search_button");

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }
    public String getUserName(){
        return getVisibleText(USERNAME_LOCATOR);
    }

    public SearchResultPage searchProduct(String productName){
        enterText(SEARCH_TEXT_BOX_LOCATOR, productName);
        enterSpecialKey(SEARCH_TEXT_BOX_LOCATOR, Keys.ENTER);
        return new SearchResultPage(getDriver());
    }

}
