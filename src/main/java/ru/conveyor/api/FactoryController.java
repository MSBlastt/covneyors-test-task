package ru.conveyor.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.conveyor.api.dto.FactoryStatusDto;
import ru.conveyor.api.dto.NewConveyorValueDto;
import ru.conveyor.service.FactoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FactoryController {

    private final FactoryService factoryService;

    public FactoryController(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    @PostMapping("/factory/conveyor/a/push")
    public Integer pushValueToConveyorA(@RequestBody @Valid NewConveyorValueDto dto) {
        return factoryService.pushA(dto.getValue());
    }

    @PostMapping("/factory/conveyor/b/push")
    public Integer pushValueToConveyorB(@RequestBody @Valid NewConveyorValueDto dto) {
        return factoryService.pushB(dto.getValue());
    }

    @GetMapping("/factory/status")
    public FactoryStatusDto getStatusForFactory() {
        return factoryService.getFactoryStatus();
    }

    @GetMapping("/factory/conveyor/a/status")
    public List<Integer> getStatusForConveyorA() {
        return factoryService.getStatusConveyorA();
    }

    @GetMapping("/factory/conveyor/b/status")
    public List<Integer> getStatusForConveyorB() {
        return factoryService.getStatusConveyorB();
    }
}