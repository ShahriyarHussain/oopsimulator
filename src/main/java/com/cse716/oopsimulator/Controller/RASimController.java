package com.cse716.oopsimulator.Controller;

import com.cse716.oopsimulator.Dto.*;
import com.cse716.oopsimulator.Service.RASimulatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ra_simulator")
@RequiredArgsConstructor
public class RASimController {

    private final RASimulatorService raSimulatorService;

    @GetMapping("/selection/{tableName}")
    public ResponseEntity<List<StudentDto>> getSelectionData(@PathVariable String tableName,
                                                             @RequestBody List<ConditionDto> conditions) {
        return ResponseEntity.ok(raSimulatorService.getSelectionResult(tableName, conditions));
    }

    @GetMapping("/projection/{tableName}")
    public ResponseEntity<List<Map<String, Object>>> getProjectionData(@PathVariable String tableName,
                                                                       @RequestBody List<String> projectionAttributes) {
        return ResponseEntity.ok(raSimulatorService.getProjectionResult(tableName, projectionAttributes));
    }

    @GetMapping("/operation")
    public ResponseEntity<List<Map<String, Object>>> getOperationData(@RequestBody OperationDto operationDto) {
        return ResponseEntity.ok(raSimulatorService.getOperationResult(operationDto));
    }

    @GetMapping("/rename")
    public ResponseEntity<ResponseDto> getRenamedData(@RequestBody RenameDto renameDto) throws SQLException {
        return ResponseEntity.ok(raSimulatorService.alterColumnName(renameDto));
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
