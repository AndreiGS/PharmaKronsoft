package com.kronsoft.pharma.importProcess;


import com.kronsoft.pharma.article.Article;
import com.kronsoft.pharma.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.csv.CSVRecord;


import javax.persistence.EntityNotFoundException;

import static java.lang.Thread.sleep;

@Service
public class ImportProcessService {

    private final ImportProcessRepository importProcessRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public ImportProcessService(ImportProcessRepository importProcessRepository, ArticleRepository articleRepository) {
        this.importProcessRepository = importProcessRepository;
        this.articleRepository = articleRepository;
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
                System.out.println(e.getCause());
                int i = 1;
                for(String col : record.toList()) {
                    if(col.length() > 250)
                        System.out.println(i);
                    i++;
                }
            }
            if(record.getRecordNumber() % 100 == 0) {
                process.setProcessedRecords((int) record.getRecordNumber());
                try{
                    process = importProcessRepository.save(process);
                } catch (Exception e) {
                    process.setStatus(ProcessStatus.FAILED);
                    importProcessRepository.save(process);
                    return;
                }
            }
        }
        process.setStatus(ProcessStatus.COMPLETED);
        importProcessRepository.save(process);
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
