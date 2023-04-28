package com.cse716.oopsimulator.Controller;

import com.cse716.oopsimulator.Dto.ConditionDto;
import com.cse716.oopsimulator.Dto.StudentDto;
import com.cse716.oopsimulator.Service.RASimulatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ra_simulator")
@RequiredArgsConstructor
public class RASimController {

    private final RASimulatorService raSimulatorService;

    @GetMapping("/selection/{tableName}")
    public ResponseEntity<List<StudentDto>> getSelectionData(@PathVariable String tableName, @RequestBody List<ConditionDto> conditions) {
        return ResponseEntity.ok(raSimulatorService.getSelectionResult(tableName, conditions));
    }

    @GetMapping("/projection")
    public String getProjectionData(List<String> projectionAttributes) {
        return null;
    }

    @GetMapping("/union")
    public String getUnionData() {
        return null;
    }

    @GetMapping("/difference")
    public String getDifferenceData() {
        return null;
    }

    @GetMapping("/intersect")
    public String getIntersectData() {
        return null;
    }

    @GetMapping("/rename")
    public String getRenamedData() {
        return null;
    }

    @GetMapping("/crossProduct")
    public String getCrossProductData() {
        return null;
    }

    @GetMapping("/thetaJoin")
    public String getThetaJoinData() {
        return null;
    }

    @GetMapping("/naturalJoin")
    public String getNaturalJoinData() {
        return null;
    }

    @GetMapping("/leftOuterJoin")
    public String getLeftOuterJoinData() {
        return null;
    }

    @GetMapping("/rightOuterJoin")
    public String getRightOuterJoinData() {
        return null;
    }

    @GetMapping("/rightFullJoin")
    public String getRightFullJoinData() {
        return null;
    }
}
