package com.menonvarun.test.automation.framework.util;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.menonvarun.test.automation.framework.pagemodel.Browser;
import com.menonvarun.test.automation.framework.pagemodel.PageClass;

/**
 * Util class containing some util methods to be used while automation.
 * @author  Varun Menon
 *
 */
public class Utilities {
	private static int DEFAULT_TIMEOUT =60;
	
	/**
	 * Util method to wait for the element to be present on the page.
	 * This method should be used while using Page Object Factory model of locating strategy provided by the framework.
	 * This method will wait for the default timeout of 60sec
	 * @param driver {@link WebDriver} object. This object is not used by the method but as {@link WebDriverWait} class 
	 * needs it, it have to be provided.
	 * @param element  {@link WebElement} object of the element that we have to wait for to be present. 
	 */
	public void waitForElementPresent(WebDriver driver, WebElement element){
		this.waitForElementPresent(driver, element,DEFAULT_TIMEOUT);
	}
	
	/**
	 * Util method to wait for the element to be present on the page.
	 * This method should be used while using Page Object Factory model of locating strategy provided by the framework.
	 * @param driver  {@link WebDriver} object. This object is not used by the method but as {@link WebDriverWait} class 
	 * needs it, it have to be provided.
	 * @param element  {@link WebElement} object of the element that we have to wait for to be present. 
	 * @param timeout  Time to wait for.
	 */
	public void waitForElementPresent(WebDriver driver, WebElement element, long timeout){
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(presenceOfElement(element));
	}
	
	/**
	 * Util method to wait for the List of elements to be present on the page.
	 * This method should be used while using Page Object Factory model of locating strategy provided by the framework.
	 * This method will wait for the default timeout of 60sec
	 * @param driver  {@link WebDriver} object. This object is not used by the method but as {@link WebDriverWait} class 
	 * needs it, it have to be provided.
	 * @param elements  {@link List} of {@link WebElement} object of elements that we have to wait for to be present. 
	 */
	public void waitForElementPresent(WebDriver driver, List<WebElement> elements){
		this.waitForElementPresent(driver, elements,DEFAULT_TIMEOUT);
	}
	
	/**
	 * Util method to wait for the List of elements to be present on the page.
	 * This method should be used while using Page Object Factory model of locating strategy provided by the framework.
	 * @param driver  {@link WebDriver} object. This object is not used by the method but as {@link WebDriverWait} class 
	 * needs it, it have to be provided.
	 * @param elements  {@link List} of {@link WebElement} object of elements that we have to wait for to be present. 
	 * @param timeout  Time to wait for.
	 */
	public void waitForElementPresent(WebDriver driver, List<WebElement> elements,long timeout){		
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(presenceOfElements(elements));
	}

	/**
	 * Util method for waiting for the page object model page to appear or displayed. 
	 * This verification is done on the basis of the {@link PageClass#at()} method implemented by the implementing Page Object class.
	 * @param browser  Browser object of the current page or test class from where this method is call'd
	 * @param pageClass  Page class extending {@link PageClass} for which {@link WebDriver} should wait.
	 * @param timeout  Time to wait for.
	 */
	public void waitForPage(Browser browser, Class<?> pageClass, long timeout){
		WebDriverWait wait = new WebDriverWait(browser.getDriver(), timeout);
		wait.until(presenceOfPage(browser, pageClass));		
	}
	
	/**
	 * Util method for waiting for the page object model page to appear or displayed. 
	 * This verification is done on the basis of the {@link PageClass#at()} method implemented by the implementing Page Object class.
	 * Uses default timeout of 60 sec.
	 * @param browser  Browser object of the current page or test class from where this method is call'd
	 * @param pageClass  Page class extending {@link PageClass} for which {@link WebDriver} should wait.
	 */	
	public void waitForPage(Browser browser, Class<?> pageClass){
		this.waitForPage(browser, pageClass, DEFAULT_TIMEOUT);
	}
	
	private ExpectedCondition<Boolean> presenceOfElement(
			final WebElement element) {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try{
					element.getTagName();
				}catch(ElementNotFoundException e){
					return false;
				}
				return true;
			}

			@Override
			public String toString() {
				return "presence of element located by: " + element;
			}
		};
	}
	
	private ExpectedCondition<Boolean> presenceOfElements(
			final List<WebElement> elements) {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				if(elements.size()>0)
					return true;
				return false;
			}

			@Override
			public String toString() {
				return "presence of element located by: " + elements;
			}
		};
	}
	
	private ExpectedCondition<Boolean> presenceOfPage(final Browser browser,
			final Class<?> pageClass) {
		return new ExpectedCondition<Boolean>() {
			CommonMethods commonMethods = new CommonMethods();
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean)commonMethods.isAt(browser,pageClass);				
			}

			@Override
			public String toString() {
				return "Presence of page: " + pageClass.getName();
			}
		};
	}
	
}
