package ru.manxix69.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class InfoServiceImpTest implements InfoService {

    @Value("${server.port}")
    private Integer port;

    public InfoServiceImpTest() {
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
