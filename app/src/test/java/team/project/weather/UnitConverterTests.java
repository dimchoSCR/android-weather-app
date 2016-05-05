package team.project.weather;

import org.junit.Test;
import static org.junit.Assert.*;

public class UnitConverterTests {

    @Test
    public void kelvinToCelsius() {
        double celsuis = TemperatureUnitConvertor.kelvinToCelsius(1);
        assertEquals(String.valueOf(celsuis), "-272.15");
    }
}
