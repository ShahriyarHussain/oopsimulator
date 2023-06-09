package com.cse716.oopsimulator.Controller;

import com.cse716.oopsimulator.Dto.TriggerDto;
import com.cse716.oopsimulator.Service.TriggerAndClusterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trigger_&_cluster")
@RequiredArgsConstructor
@CrossOrigin
public class TriggerAndClusterController {

    private final TriggerAndClusterService triggerAndClusterService;

    @PostMapping("/insertData/{tableName}")
    public boolean insertData(@RequestBody Map<String,String> values, @PathVariable String tableName) {
        return triggerAndClusterService.insertData(values, tableName);
    }

    @PostMapping("/createTrigger")
    public boolean createTrigger(@RequestBody TriggerDto triggerDto) {
        return triggerAndClusterService.createTrigger(triggerDto);
    }

    @GetMapping("/queryTableData/{tableName}")
    public ResponseEntity<List<Map<String, String>>> queryTableData(@PathVariable String tableName) {
        return ResponseEntity.ok(triggerAndClusterService.getTableDataByQuery(tableName));

    }

    @GetMapping("/queryTableData/getAllTables")
    public ResponseEntity<List<String>> getAllTables() {
        return ResponseEntity.ok(triggerAndClusterService.getTableNames());
    }

    @GetMapping("/queryTableData/getDbInfo")
    public ResponseEntity<Map<String, String>> getDbInfo() {
        return ResponseEntity.ok(triggerAndClusterService.getDbInfo());

    }

    @PostMapping("/createCluster/{tableName}")
    public boolean createCluster(@PathVariable String tableName) {
        return triggerAndClusterService.createCluster(tableName);
    }


}
