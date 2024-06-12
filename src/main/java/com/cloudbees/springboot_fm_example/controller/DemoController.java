package com.cloudbees.springboot_fm_example.controller;

import com.cloudbees.springboot_fm_example.container.Flags;
import com.cloudbees.springboot_fm_example.service.DemoService;
import io.rollout.client.NetworkConfigurationsOptions;
import io.rollout.rox.server.Rox;
import io.rollout.rox.server.RoxOptions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class DemoController implements InitializingBean {

    Flags flags;
    DemoService demoService;

    @Value("${rox.key:}")
    private String environmentKey;
    @Value("${rox.options.getConfigApiEndpoint:}")
    private String getConfigApiEndpoint;
    @Value("${rox.options.getConfigCloudEndpoint:}")
    private String getConfigCloudEndpoint;
    @Value("${rox.options.sendStateApiEndpoint:}")
    private String sendStateApiEndpoint;
    @Value("${rox.options.sendStateCloudEndpoint:}")
    private String sendStateCloudEndpoint;
    @Value("${rox.options.analyticsEndpoint:}")
    private String analyticsEndpoint;
    @Value("${rox.options.pushNotificationsEndpoint:}")
    private String pushNotificationsEndpoint;

    @Autowired
    public DemoController(
            Flags flags,
            DemoService demoService) {
        this.flags = flags;
        this.demoService = demoService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initFlags();
    }

    public void initFlags() {
        if (environmentKey.equals("<INSERT YOUR SDK KEY HERE>"))
            throw new RuntimeException("You haven't yet inserted your SDK Key into FeatureFlagsProvider.tsx - the application below will not update until you do so. Please check the README.md for instructions.");

        RoxOptions options = new RoxOptions.Builder()
                .withDisableSignatureVerification(true)
                .withNetworkConfigurationsOptions(
                        new NetworkConfigurationsOptions(
                                getConfigApiEndpoint,
                                getConfigCloudEndpoint,
                                sendStateApiEndpoint,
                                sendStateCloudEndpoint,
                                analyticsEndpoint,
                                pushNotificationsEndpoint
                        )
                ).build();
        Rox.register(flags);
        try {
            Rox.setup(environmentKey, options).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/demo")
    public String demo() {
        return demoService.demo();
    }

}
