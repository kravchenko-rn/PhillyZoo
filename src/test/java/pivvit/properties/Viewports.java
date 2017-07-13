package pivvit.properties;

import org.javatuples.Triplet;

public enum Viewports {
    LARGE("L", 1200, 900),
    MEDIUM("M", 950, 900),
    SMALL("S", 650, 900);

    private Triplet<String, Integer, Integer> option;

    Viewports(String value, Integer width, Integer height) {
        option = new Triplet<>(value, width, height);
    }

    public String getValue() {
        return option.getValue0();
    }

    public Integer getWidth() {
        return option.getValue1();
    }

    public Integer getHeight() {
        return option.getValue2();
    }

    public static Viewports fromString(String text) {
        if (text != null) {
            for (Viewports viewport : Viewports.values()) {
                if (text.equalsIgnoreCase(viewport.getValue())) {
                    return viewport;
                }
            }
        }
        return null;
    }
}
