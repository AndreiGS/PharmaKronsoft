package com.kronsoft.pharma.importProcess;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.lang.Thread.sleep;

@Service
public class ImportProcessService {

    private final ImportProcessRepository importProcessRepository;

    @Autowired
    public ImportProcessService(ImportProcessRepository importProcessRepository) {
        this.importProcessRepository = importProcessRepository;
    }


    public ImportProcess createImportProcess(Integer totalRecords) {
        ImportProcess process = new ImportProcess(null, ProcessStatus.IN_PROGRESS, 0, 1000);
        process = importProcessRepository.save(process);
        asyncImportArticles(process);
        return process;
    }
    @Async
    public void asyncImportArticles(ImportProcess process) {

        try {
            for (int i = 0; i < process.getTotalRecords(); i += 50) {
                sleep(1000);
                if (i % 100 == 0) {
                    process.setProcessedRecords(i);
                    process = importProcessRepository.save(process);
                }
            }
            process.setProcessedRecords(1000);
            process.setStatus(ProcessStatus.COMPLETED);
        } catch (InterruptedException e) {
            process.setStatus(ProcessStatus.FAILED);
        }
        importProcessRepository.save(process);
    }

    public List<ImportProcess> getAllProcesses() {
        return importProcessRepository.findAll();
    }

    public ImportProcess getImportProcessInfo(UUID processId) {
        return importProcessRepository.findById(processId).get();
    }
}
