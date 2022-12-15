package com.kronsoft.pharma.importProcess;

import com.kronsoft.pharma.importProcess.dto.ImportProcessResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/article")
public class ImportProcessController {

    private final ImportProcessService importProcessService;
    private final ModelMapper modelMapper;
    @Autowired
    public ImportProcessController(ImportProcessService importProcessService, ModelMapper modelMapper) {
        this.importProcessService = importProcessService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/import-process-info")
    public ImportProcessResponseDto getLoadingProcessInfo(@RequestParam("processId") UUID processId) {
        return modelMapper.map(importProcessService.getImportProcessInfo(processId), ImportProcessResponseDto.class);
    }

    @GetMapping(value = "/loading-process")
    public Optional<ImportProcessResponseDto> getLoadingProcess() {
        Optional<ImportProcess> optionalImportProcess = importProcessService.getLoadingProcess();

        return optionalImportProcess.isPresent() ? Optional.of(modelMapper.map(optionalImportProcess, ImportProcessResponseDto.class))
                                                 : Optional.empty();
    }
}
