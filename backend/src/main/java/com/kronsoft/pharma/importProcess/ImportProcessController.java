package com.kronsoft.pharma.importProcess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/article")
public class ImportProcessController {

    private final ImportProcessService importProcessService;

    @Autowired
    public ImportProcessController(ImportProcessService importProcessService) {
        this.importProcessService = importProcessService;
    }

    @GetMapping(value = "/import-process-info")
    public ImportProcess getLoadingProcessInfo(@RequestParam("processId") UUID processId) {
        return importProcessService.getImportProcessInfo(processId);
    }

    @GetMapping(value = "/loading-process")
    public List<ImportProcess> getAll() {
        return importProcessService.getAllProcesses();
    }
}
