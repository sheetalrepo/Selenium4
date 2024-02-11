#Selenium 3 vs Selenium 4
Selenium 3.0
1. It follows JSON wire protocol
2. Via JSON wire protocol Selenium Webdriver APIs talk to the browser native APIs.

Selenium 4.0
1. It follows the W3C standard protocol
2. Almost all browsers such as Chrome, Safari, and IE are already W3C standard compliant.
3. Relative Locators - above, below, toLeftOf, toRightOf, and near.
4. withTagName()
5. Easy to work with multiple tabs or windows
    WindowType.TAB to newWindow() method
    WindowType.WINDOW to newWindow()


    driver.get(https://www.google.com/);
    driver.switchTo().newWindow(WindowType.TAB);
    driver.navigate().to(https://www.softwaretestingmaterial.com/);