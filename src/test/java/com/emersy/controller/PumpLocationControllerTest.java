package com.emersy.controller;

import com.emersy.dto.PumpFinalTrack;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PumpLocationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ResourceLoader resourceLoader;


    @Test
    public void pumpLocationTest() throws IOException {
        String postContent =
                IOUtils.toString(resourceLoader.getResource("classpath:com/emersy/controller/tubeTrack.json")
                        .getInputStream(), "UTF-8");

        PumpFinalTrack pumpFinalTrack = this.restTemplate.postForObject("/pumpLocation", postContent, PumpFinalTrack.class);

    }
}
