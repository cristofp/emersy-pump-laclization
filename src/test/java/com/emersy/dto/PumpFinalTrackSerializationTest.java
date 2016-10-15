package com.emersy.dto;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class PumpFinalTrackSerializationTest {

    @Autowired
    private JacksonTester<PumpFinalTrack> json;
    @Autowired
    private ResourceLoader resourceLoader;


    @Test
    public void testDeserialize() throws Exception {
        String content=
                IOUtils.toString(resourceLoader.getResource("classpath:com/emersy/controller/pumpFinalTrack.json")
                        .getInputStream(), "UTF-8");

        PumpFinalTrack pumpFinalTrack = new PumpFinalTrack();
        Point hydrant = new Point(46.49272999999999,
                11.321550000000002);
        pumpFinalTrack.setHydrant(hydrant);
        List<PumpPosition> pumps = new ArrayList<>();
        pumps.add(new PumpPosition(46.49272999999999, 11.321550000000002, 123.5, 1.02, 10));
        pumps.add(new PumpPosition(46.49241506053793,11.321745067834854,140.1,1.6,5));
        pumps.add(new PumpPosition(46.492422446811005,11.322176903486252,150.2,1.9,17));
        pumps.add(new PumpPosition(46.49249686336335,11.322709321975708,145.8,1.2,19));
        pumpFinalTrack.setPumps(pumps);


        assertThat(this.json.parse(content))
                .isEqualTo(pumpFinalTrack);
    }

}



