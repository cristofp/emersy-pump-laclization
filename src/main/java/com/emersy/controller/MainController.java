package com.emersy.controller;

import com.emersy.dto.PumpFinalTrack;
import com.emersy.dto.TubeTrack;
import com.emersy.service.PumpLocatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired private PumpLocatingService pumpLocatingService;


    @RequestMapping("/pumpLocation")
    public PumpFinalTrack pumpLocation(@RequestBody TubeTrack tubeTrack) throws Exception {
        return pumpLocatingService.locatePumps(tubeTrack);
    }
}
