package team.project.weather;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    private static final String APPID = "8673a08097591104b9aa183591c4a5ff";
    private static final String MAIN_URL = "http://api.openweathermap.org/data/2.5/weather";

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void url_build() {
        String url = String.format("%s?appid=%s", this.MAIN_URL, this.APPID);
        assertEquals(url, "http://api.openweathermap.org/data/2.5/weather?appid=8673a08097591104b9aa183591c4a5ff");
    }

    @Test
    public void url_by_city() {
        String url = String.format("%s&q=%s", "http://api.openweathermap.org/data/2.5/weather?appid=8673a08097591104b9aa183591c4a5ff", "Sofia");
        assertEquals(url, "http://api.openweathermap.org/data/2.5/weather?appid=8673a08097591104b9aa183591c4a5ff&q=Sofia");
    }
}