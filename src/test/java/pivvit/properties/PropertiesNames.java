package pivvit.properties;

public enum PropertiesNames {
    BROWSER("browser"),
    PLATFORM("platform"),
    THREADS("threads"),
    VIEWPORT("viewport"),
    DRIVERS_DIRECTORY("drivers.dir"),
    CONFIG_DIRECTORY("config.dir"),
    TESTDATA_DIRECTORY("testdata.dir"),
    SERIALIZED_DIRECTORY("serialized.dir"),
    BASE_URL("base.url");

    private final String propertyName;

    PropertiesNames(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getValue() {
        return System.getProperty(propertyName);
    }

    @Override
    public String toString() {
        return propertyName;
    }
}
