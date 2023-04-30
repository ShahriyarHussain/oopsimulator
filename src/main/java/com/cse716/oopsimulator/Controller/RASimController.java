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
@CrossOrigin
public class RASimController {

    private final RASimulatorService raSimulatorService;

    @PostMapping("/selection/{tableName}")
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

    @PostMapping("/rename")
    public ResponseEntity<ResponseDto> getRenamedData(@RequestBody RenameDto renameDto) throws SQLException {
        return ResponseEntity.ok(raSimulatorService.alterColumnName(renameDto));
    }

    @GetMapping("/crossProduct")
    public ResponseEntity<List<Map<String, String>>> getCrossProductData(@RequestBody JoinRequestDto joinRequestDto) {
        return ResponseEntity.ok(raSimulatorService.getCrossProductResult(joinRequestDto));
    }

    @GetMapping("/thetaJoin")
    public ResponseEntity<List<Map<String, String>>> getThetaJoinData(@RequestBody JoinRequestDto joinRequestDto) {
        return ResponseEntity.ok(raSimulatorService.getThetaJoinResult(joinRequestDto));
    }

    @GetMapping("/naturalJoin")
    public ResponseEntity<List<Map<String, String>>> getNaturalJoinData(@RequestBody JoinRequestDto joinRequestDto) {
        return ResponseEntity.ok(raSimulatorService.getNaturalJoinResults(joinRequestDto));
    }

    @GetMapping("/leftOuterJoin")
    public ResponseEntity<List<Map<String, String>>> getLeftOuterJoinData(@RequestBody JoinRequestDto joinRequestDto) {
        return ResponseEntity.ok(raSimulatorService.getLeftOuterJoinResults(joinRequestDto));
    }

    @GetMapping("/rightOuterJoin")
    public ResponseEntity<List<Map<String, String>>> getRightOuterJoinData(@RequestBody JoinRequestDto joinRequestDto) {
        return ResponseEntity.ok(raSimulatorService.getRightOuterJoinResults(joinRequestDto));
    }

    @GetMapping("/rightFullJoin")
    public ResponseEntity<List<Map<String, String>>> getRightFullJoinData(@RequestBody JoinRequestDto joinRequestDto) {
        return ResponseEntity.ok(raSimulatorService.getRightFullJoinResults(joinRequestDto));
    }
}
