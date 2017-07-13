package pivvit.base;

import org.openqa.selenium.Rectangle;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

public class CustomHtmlElement extends HtmlElement {
    @Override
    public Rectangle getRect() {
        return getWrappedElement().getRect();
    }
}
