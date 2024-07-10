package ru.manxix69.school.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.manxix69.school.service.InfoService;
import ru.manxix69.school.service.InfoServiceImp;

@Controller
@RequestMapping("info")
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("port")
    public ResponseEntity<Integer> getPort() {
        Integer port = infoService.getPort();
        return ResponseEntity.ok(port);
    }

    @GetMapping("test")
    public ResponseEntity<Integer> testMethod() {
        return ResponseEntity.ok(infoService.testStreamParallel());
    }
}
