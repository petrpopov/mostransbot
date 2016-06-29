package com.innerman;

import com.innerman.geo.Line;
import com.innerman.geo.LocationEntity;
import com.innerman.service.GeoMeter;
import org.junit.Assert;
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
	public void testMeterTrue() {

        LocationEntity loc = new LocationEntity(48.137024, 11.575249);
        Line line = new Line(new LocationEntity(48.139115, 11.578081), new LocationEntity(48.146303, 11.593102));

		boolean res = geoMeter.intersects(loc, line, 1000.0);
        Assert.assertTrue(res);
    }

    @Test
    public void testMeterFalse() {

        LocationEntity loc = new LocationEntity(58.137024, 12.575249);
        Line line = new Line(new LocationEntity(48.139115, 11.578081), new LocationEntity(48.146303, 11.593102));

        boolean res = geoMeter.intersects(loc, line, 1000.0);
        Assert.assertFalse(res);
    }
}
