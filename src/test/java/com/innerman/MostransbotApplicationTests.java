package com.innerman;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MostransbotApplication.class)
@WebAppConfiguration
public class MostransbotApplicationTests {

    @Autowired
    private GeoMeter geoMeter;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testMeter() {

        Location loc = new Location(48.137024, 11.575249);
        Line line = new Line(new Location(48.139115, 11.578081), new Location(48.146303, 11.593102));

        geoMeter.intersects(loc, line, 1000.0);
	}
}
