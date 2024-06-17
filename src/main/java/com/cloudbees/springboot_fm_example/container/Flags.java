package com.cloudbees.springboot_fm_example.container;

import io.rollout.configuration.RoxContainer;
import io.rollout.flags.*;
import org.springframework.stereotype.Component;

@Component
// Create Roxflags in the Flags container class
public class Flags implements RoxContainer {
    // Define the feature flags
    public RoxFlag showMessage = new RoxFlag(false);
    public final RoxString message = new RoxString("default message");
    public final RoxString fontColor = new RoxString("red", new String[] { "red", "green", "blue", "black"});
    public final RoxInt fontSize = new RoxInt(12, new int[] { 12, 16, 24 });
}