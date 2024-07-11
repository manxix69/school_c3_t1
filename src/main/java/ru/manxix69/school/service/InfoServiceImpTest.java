package ru.manxix69.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@Profile("test")
public class InfoServiceImpTest implements InfoService {

    @Value("${server.port}")
    private Integer port;
    private Logger logger = LoggerFactory.getLogger(InfoServiceImpTest.class);
    public InfoServiceImpTest() {
    }

    public Integer getPort() {
        return port;
    }

    @Override
    public Integer testStreamParallel() {
        logger.info("Was invoke method testStreamParallel()");
        long start = System.currentTimeMillis();
//        int sum = Stream
//                .iterate(1, a -> a +1)
//                .limit(1_000_000)             // result 1 784 293 664
//                .parallel()                   //currentTimeMillis from 43 to 96
//                .reduce(0, (a, b) -> a + b ); //currentTimeMillis from 14  to 28
        int sum = 0;
        for (int i = 1; i <= 1_000_000; i++) {  //currentTimeMillis from 1 to 3
            sum = sum + i; // resul 1 784 293 664
        } // задача решается одним циклом и он работает быстрее чем выстроенный STREAM
        logger.debug("currentTimeMillis {} ", System.currentTimeMillis()- start );
        logger.debug("Sum={}",sum);
        return sum;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
