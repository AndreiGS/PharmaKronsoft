package com.kronsoft.pharma.importProcess;


import com.kronsoft.pharma.article.Article;
import com.kronsoft.pharma.article.ArticleRepository;
import com.kronsoft.pharma.notifications.DirectNotification;
import com.kronsoft.pharma.notifications.FCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.csv.CSVRecord;


import javax.persistence.EntityNotFoundException;

@Service
public class ImportProcessService {

    private final ImportProcessRepository importProcessRepository;
    private final ArticleRepository articleRepository;
    private final FCMService fcmService;

    @Autowired
    public ImportProcessService(ImportProcessRepository importProcessRepository, ArticleRepository articleRepository, FCMService fcmService) {
        this.importProcessRepository = importProcessRepository;
        this.articleRepository = articleRepository;
        this.fcmService = fcmService;
    }


//    public ImportProcess createImportProcess(Integer totalRecords) {
//        ImportProcess process = new ImportProcess(null, ProcessStatus.IN_PROGRESS, 0, 1000);
//        process = importProcessRepository.save(process);
//        asyncImportArticles(process);
//        return process;
//    }
    @Async
    public void asyncImportArticles(ImportProcess process, Iterable<CSVRecord> records) {
        for(CSVRecord record : records) {
            if(record.getRecordNumber() == 1)
                continue;
            String[] columns = new String[record.size()];

            try {
                articleRepository.save(new Article(record.toList().toArray(columns)));
            } catch (Exception e) {
                System.out.println("[E]" + e.getMessage());
            }
            if(record.getRecordNumber() % 100 == 0) {
                process.setProcessedRecords((int) record.getRecordNumber());
                try{
                    process = importProcessRepository.save(process);
                } catch (Exception e) {
                    System.out.println("[E2]" + e.getMessage());
                    process.setStatus(ProcessStatus.FAILED);
                    importProcessRepository.save(process);
                    return;
                }
            }
        }
        process.setStatus(ProcessStatus.COMPLETED);
        importProcessRepository.save(process);
        if(process.getTarget() != null)
            fcmService.sendNotificationToTarget(new DirectNotification(process.getTarget(), "Process completed.", "Import process completed."));
    }

    public List<ImportProcess> getAllProcesses() {
        return importProcessRepository.findAll();
    }

    public Optional<ImportProcess> getLoadingProcess() {
        return importProcessRepository.findByStatus(ProcessStatus.IN_PROGRESS);
    }

    public ImportProcess getImportProcessInfo(UUID processId) {
        return importProcessRepository.findById(processId).orElseThrow(() -> new EntityNotFoundException("Process with id " + processId + " not found."));
    }
}
