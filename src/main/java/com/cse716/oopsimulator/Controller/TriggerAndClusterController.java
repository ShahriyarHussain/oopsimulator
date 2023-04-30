package com.cse716.oopsimulator.Controller;

import com.cse716.oopsimulator.Service.TriggerAndClusterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

    @PostMapping("/createTrigger/{tableName}")
    public boolean createTrigger(@PathVariable String tableName) {
        return triggerAndClusterService.createTrigger(tableName);
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/queryTableData/{tableName}")
    public ResponseEntity<List<Map<String, String>>> queryTableData(@PathVariable String tableName) {
        return ResponseEntity.ok(triggerAndClusterService.getTableDataByQuery(tableName));

    }

    @PostMapping("/createCluster/{tableName}")
    public boolean createCluster(@PathVariable String tableName) {
        return triggerAndClusterService.createCluster(tableName);
    }


}
