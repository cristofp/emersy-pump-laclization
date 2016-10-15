package com.emersy.dto;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class TubeTrackSerializationTest {

    @Autowired
    private JacksonTester<TubeTrack> json;
    @Autowired
    private ResourceLoader resourceLoader;


    @Test
    public void testDeserialize() throws Exception {
        String content=
                IOUtils.toString(resourceLoader.getResource("classpath:com/emersy/controller/tubeTrack.json")
                        .getInputStream(), "UTF-8");

        TubeTrack tubeTrack = new TubeTrack();
        tubeTrack.setHydrant(new Point(46.49272999999999, 11.321550000000002));
        tubeTrack.setFire(new Point(46.4925854984408, 11.32392168045044));
        tubeTrack.setPoints(Arrays.asList(
                new Point(46.49241506053793, 11.321745067834854),
                new Point(46.492422446811005, 11.322176903486252),
                new Point(46.49249686336335, 11.322709321975708)
        ));
        tubeTrack.setPa(8);
        tubeTrack.setFlowRate(800);

        assertThat(this.json.parse(content))
                .isEqualTo(tubeTrack);
    }

}



