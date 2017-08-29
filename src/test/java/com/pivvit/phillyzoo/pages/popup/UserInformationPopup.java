package com.pivvit.phillyzoo.pages.popup;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

@FindBy(css = "#mainContainer>section")
public class UserInformationPopup extends BasePopup {
    @FindBy(css = "[ng-show^='showAccountProfile']")
    WebElement userInformationForm;

    @FindBy(css = "[ng-model='accountProfile.firstName']")
    WebElement firstNameInput;

    @FindBy(css = "[ng-model='accountProfile.lastName']")
    WebElement lastNameInput;

    @FindBy(css = "[ng-model='accountProfile.email']")
    WebElement emailInput;

    @FindBy(css = "[ng-model='accountProfile.password']")
    WebElement passwordInput;

    @FindBy(css = "[ng-model='accountProfile.phone']")
    WebElement phoneInput;

    @FindBy(css = ".submit-purchase-button")
    WebElement submitButton;

    public UserInformationPopup() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    public UserInformationPopup waitTillLoadingIndicatorDisappears() {
        super.waitTillLoadingIndicatorDisappears();
        return new UserInformationPopup();
    }

    /**
     * Retrieves phone input text
     * @return string which contains phone input text
     */
    public String getPhoneInputText() {
        driver().switchTo().frame(contentIFrame);

        String result = phoneInput.getText();

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Sets text into the first name input field
     * @param firstName string which contains first name
     * @return {@link UserInformationPopup} page
     */
    public UserInformationPopup inputFirstName(String firstName) {
        inputText(firstNameInput, firstName);
        return this;
    }

    /**
     * Sets text into the last name input field
     * @param lasttName string which contains last name
     * @return {@link UserInformationPopup} page
     */
    public UserInformationPopup inputLastName(String lasttName) {
        inputText(lastNameInput, lasttName);
        return this;
    }

    /**
     * Sets text into the email input field
     * @param email string which contains email
     * @return {@link UserInformationPopup} page
     */
    public UserInformationPopup inputEmail(String email) {
        inputText(emailInput, email);
        return this;
    }

    /**
     * Sets text into the password input field
     * @param password string which contains password
     * @return {@link UserInformationPopup} page
     */
    public UserInformationPopup inputPassword(String password) {
        inputText(passwordInput, password);
        return this;
    }

    /**
     * Sets text into the phone input field
     * @param phone string which contains phone
     * @return {@link UserInformationPopup} page
     */
    public UserInformationPopup inputPhone(String phone) {
        inputText(phoneInput, phone);
        return this;
    }

    /**
     * Clicks submit purchase button
     * @return {@link UserInformationPopup} page
     */
    public UserInformationPopup clickSubmitButton() {
        driver().switchTo().frame(contentIFrame);

        click(submitButton, "Clicking submit purchase button.");

        driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Checks whether user information form is displayed
     * @return {@code true} in case when user information form is displayed
     */
    public boolean isUserInformationFormDisplayed() {
        driver().switchTo().frame(contentIFrame);

        boolean result = isElementVisible(userInformationForm,
                "Checking whether the user information form is displayed.");

        driver().switchTo().defaultContent();
        return result;
    }

    /**
     * Checks whether the user form first name validation error is displayed
     * @return {@code true} in case when the user form first name validation error is displayed
     */
    public boolean isFirstNameValidationErrorDisplayed(String errorText) {
        return isValidationErrorDisplayed(firstNameInput, errorText);
    }

    /**
     * Checks whether the user form last name validation error is displayed
     * @return {@code true} in case when the user form first name validation error is displayed
     */
    public boolean isLastNameValidationErrorDisplayed(String errorText) {
        return isValidationErrorDisplayed(lastNameInput, errorText);
    }

    /**
     * Checks whether the user form email validation error is displayed
     * @return {@code true} in case when the user form first name validation error is displayed
     */
    public boolean isEmailValidationErrorDisplayed(String errorText) {
        return isValidationErrorDisplayed(emailInput, errorText);
    }

    /**
     * Checks whether the user form email validation error is displayed
     * @return {@code true} in case when the user form first name validation error is displayed
     */
    public boolean isPasswordValidationErrorDisplayed(String errorText) {
        return isValidationErrorDisplayed(passwordInput, errorText);
    }
}
