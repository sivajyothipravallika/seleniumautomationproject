package com.ui.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.utility.BrowserUtility;

public class SearchResultPage extends BrowserUtility {

    private static final By PRODUCT_LISTING_TITLE_LOCATOR = By.xpath("//span[@class='lighter']");
    private static final By PRODUCT_LISTING_LOCATOR = By.xpath("//h5[@itemprop='name']/a");

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public String getSearchResultTitle() {
        return getVisibleText(PRODUCT_LISTING_TITLE_LOCATOR);
    }

    public boolean isSearchTermPresentInProductList(String keywords) {
        Arrays.asList(keywords.toLowerCase().split(" "));
        List<String> productNamesList =getAllVisibleText(PRODUCT_LISTING_LOCATOR);
        boolean result = productNamesList.stream().
                        anyMatch(productName -> (keywords.stream().anyMatch(name.toLowerCase()::contains)));
        return result;
}
}
