package com.edulearn.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page object for the Search Results page
 */
public class SearchResultsPage extends BasePage {
    
    @FindBy(id = "search-results-count")
    private WebElement searchResultsCount;
    
    @FindBy(id = "search-keyword")
    private WebElement searchKeyword;
    
    @FindBy(className = "search-result-item")
    private List<WebElement> searchResultItems;
    
    @FindBy(id = "filter-dropdown")
    private WebElement filterDropdown;
    
    @FindBy(id = "sort-dropdown")
    private WebElement sortDropdown;
    
    @FindBy(id = "no-results-message")
    private WebElement noResultsMessage;
    
    @FindBy(id = "back-to-dashboard")
    private WebElement backToDashboardButton;
    
    /**
     * Gets the search results count text
     *
     * @return search results count text
     */
    public String getSearchResultsCountText() {
        return getText(searchResultsCount);
    }
    
    /**
     * Gets the number of search results
     *
     * @return number of search results
     */
    public int getNumberOfSearchResults() {
        return searchResultItems.size();
    }
    
    /**
     * Gets the search keyword text
     *
     * @return search keyword text
     */
    public String getSearchKeywordText() {
        return getText(searchKeyword);
    }
    
    /**
     * Clicks on a search result by index
     *
     * @param index index of the search result (0-based)
     * @return CoursePage instance
     */
    public CoursePage clickSearchResult(int index) {
        if (index >= 0 && index < searchResultItems.size()) {
            click(searchResultItems.get(index));
            logger.info("Clicked on search result at index: {}", index);
            return new CoursePage();
        } else {
            logger.error("Invalid search result index: {}", index);
            throw new IndexOutOfBoundsException("Invalid search result index: " + index);
        }
    }
    
    /**
     * Selects a filter from the filter dropdown
     *
     * @param filterOption filter option to select
     * @return SearchResultsPage instance
     */
    public SearchResultsPage selectFilter(String filterOption) {
        click(filterDropdown);
        selectByVisibleText(filterDropdown, filterOption);
        logger.info("Selected filter option: {}", filterOption);
        return this;
    }
    
    /**
     * Selects a sort option from the sort dropdown
     *
     * @param sortOption sort option to select
     * @return SearchResultsPage instance
     */
    public SearchResultsPage selectSort(String sortOption) {
        click(sortDropdown);
        selectByVisibleText(sortDropdown, sortOption);
        logger.info("Selected sort option: {}", sortOption);
        return this;
    }
    
    /**
     * Checks if no results message is displayed
     *
     * @return true if no results message is displayed
     */
    public boolean isNoResultsMessageDisplayed() {
        return isElementDisplayed(noResultsMessage);
    }
    
    /**
     * Gets the no results message text
     *
     * @return no results message text
     */
    public String getNoResultsMessageText() {
        return getText(noResultsMessage);
    }
    
    /**
     * Clicks the back to dashboard button
     *
     * @return DashboardPage instance
     */
    public DashboardPage clickBackToDashboard() {
        click(backToDashboardButton);
        logger.info("Clicked back to dashboard button");
        return new DashboardPage();
    }
}
