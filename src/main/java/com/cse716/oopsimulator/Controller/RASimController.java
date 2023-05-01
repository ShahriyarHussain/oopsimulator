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
    public ResponseEntity<List<Map<String, String>>> getSelectionData(@PathVariable String tableName,
                                                             @RequestBody List<ConditionDto> conditions) {
        return ResponseEntity.ok(raSimulatorService.getSelectionResult(tableName, conditions));
    }

    @PostMapping("/projection/{tableName}")
    public ResponseEntity<List<Map<String, String>>> getProjectionData(@PathVariable String tableName,
                                                                       @RequestBody List<String> projectionAttributes) {
        return ResponseEntity.ok(raSimulatorService.getProjectionResult(tableName, projectionAttributes));
    }

    @PostMapping("/operation")
    public ResponseEntity<List<Map<String, String>>> getOperationData(@RequestBody OperationDto operationDto) {
        return ResponseEntity.ok(raSimulatorService.getOperationResult(operationDto));
    }

    @PostMapping("/rename")
    public ResponseEntity<ResponseDto> getRenamedData(@RequestBody RenameDto renameDto) throws SQLException {
        return ResponseEntity.ok(raSimulatorService.alterColumnName(renameDto));
    }

    @PostMapping("/joinOperation")
    public ResponseEntity<List<Map<String, String>>> getJoinOperationData(@RequestBody JoinRequestDto joinRequestDto) {
        return ResponseEntity.ok(raSimulatorService.getJoinOperationResult(joinRequestDto));
    }
}
