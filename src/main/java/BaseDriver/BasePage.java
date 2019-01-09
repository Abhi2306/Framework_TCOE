package BaseDriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;

public class BasePage {
	
	WebDriver driver;
	
	@FindBy(id="ch_login_icon")
	WebElement Locator1; 
	
	@FindBy(id="ch_login_email")
	WebElement Locator2;
	
	@FindBy(id="ch_login_password")
	WebElement Locator3;
	
	@FindBy(css="")
	WebElement SelectLocator;
	
	@FindBy(tagName="")
	WebElement ListLocator; 
	
	//Initializing WebDriver and WebElements
	public BasePage(WebDriver driver) {
		
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(factory, this);
    }
	
	public void ClickLogin() {
		
		Locator1.click();
	}
	//Method for setting user name
	public void SetUserName(String username) {
		
		Locator2.sendKeys(username);
	}
	
	//Method for setting password
	public void SetPassword(String password) {
		
		Locator3.sendKeys(password);
		Locator3.sendKeys(Keys.ENTER);
	}
	
	public void Login_Page(String username, String password) {
		
		this.ClickLogin();
		this.SetUserName(username);
		this.SetPassword(password);
	}
	
	//Method for using Select on a WebElement
	public void SelectForDropDown(String VisibleText, int Index) {
		
		Select select = new Select(SelectLocator);
		
		select.selectByVisibleText(VisibleText);
		select.selectByIndex(Index);
	}
	
	//Method for using List<WebElement> method for drop down
	public String[] ListForDropdown(String SearchTag) {
		
		List<WebElement> list_1 = ListLocator.findElements(By.tagName(SearchTag));
		
		int count=0;
		String[] list_values = new String[list_1.size()];
		
		for(WebElement list_2 : list_1) {
			
			list_values[count] = list_2.getText();
			count++;
		}
		
		return list_values;
	}
	
	
	
	

}
