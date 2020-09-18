package com.studyveloper.baedalon.infra.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("baedalon.app")
@Getter
@Setter
public class AppProperties {

    private String name;

    private String version;

    private String host;

}
