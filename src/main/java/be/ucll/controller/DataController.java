package be.ucll.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.repository.DbInitializer;

@RestController
@RequestMapping("/data")
public class DataController {
    private final DbInitializer dbInitializer;

    public DataController(DbInitializer dbInitializer) {
        this.dbInitializer = dbInitializer;
    }

    @PostMapping("/reset")
    public Map<String, String> reset() {
        dbInitializer.reset();
        return Map.of("message", "Database reset successfully.");
    }
}
