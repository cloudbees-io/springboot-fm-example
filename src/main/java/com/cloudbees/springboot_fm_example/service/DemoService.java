package com.cloudbees.springboot_fm_example.service;

import com.cloudbees.springboot_fm_example.container.Flags;
import io.rollout.flags.RoxFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    Flags flags;

    @Autowired
    public DemoService(Flags flags) {
        this.flags = flags;
    }

    public String demo() {
        String demo =  " | fontColor: " + flags.fontColor.getValue() + " | fontSize: " + flags.fontSize.getValue();
        if (flags.showMessage.isEnabled())
            return "message is: " + flags.message + demo;
        else
            return "message is hidden" + demo;
    }
}
