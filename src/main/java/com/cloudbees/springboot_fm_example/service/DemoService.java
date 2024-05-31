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
        String demo = "";
        if (flags.showMessage.equals(new RoxFlag(true)))
            demo = demo + flags.message;
        return "message is: " + flags.message.getValue();
    }
}
