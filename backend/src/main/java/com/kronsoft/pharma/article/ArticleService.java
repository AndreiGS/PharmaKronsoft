package com.kronsoft.pharma.article;

import com.kronsoft.pharma.article.mapper.ArticleMapper;
import com.kronsoft.pharma.importProcess.ImportProcess;
import com.kronsoft.pharma.importProcess.ImportProcessRepository;
import com.kronsoft.pharma.importProcess.ImportProcessService;
import com.kronsoft.pharma.importProcess.ProcessStatus;
import com.kronsoft.pharma.importProcess.dto.ImportProcessResponseDto;
import com.kronsoft.pharma.user.AppUser;
import com.kronsoft.pharma.user.UserRepository;
import com.kronsoft.pharma.util.PageOf;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ImportProcessService importProcessService;
    private final ImportProcessRepository importProcessRepository;
    private final UserRepository userRepository;
    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, ImportProcessService importProcessService, ImportProcessRepository importProcessRepository, UserRepository userRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.importProcessService = importProcessService;
        this.importProcessRepository = importProcessRepository;
        this.userRepository = userRepository;
        this.articleMapper = articleMapper;
    }

    public ImportProcessResponseDto importArticles(MultipartFile file/*, String userId*/) {

        /*AppUser initiatorUser = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User with id " + userId + " not found."));*/

        ImportProcess process;
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
            CSVFormat customFormat = CSVFormat.newFormat('\t');
            CSVParser csvParser = new CSVParser(fileReader, customFormat);

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            process = new ImportProcess(null/*, initiatorUser*/, ProcessStatus.IN_PROGRESS, 0, (int) csvParser.getRecordNumber());
            process = importProcessRepository.save(process);
            importProcessService.asyncImportArticles(process, csvRecords);
        } catch (IOException e) {
            process = new ImportProcess(null/*, initiatorUser*/, ProcessStatus.FAILED, 0, 0);
            process = importProcessRepository.save(process);
        }
        return articleMapper.mapProcessToDto(process);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public PageOf<Article> getPageOfItems(int pageNumber, int itemsPerPage, String sortBy, String order) {
        Pageable pageable;
        if(sortBy == null || order == null)
            pageable = PageRequest.of(pageNumber, itemsPerPage);
        else if(order.equals("asc"))
            pageable = PageRequest.of(pageNumber, itemsPerPage, Sort.by(sortBy).ascending());
        else pageable = PageRequest.of(pageNumber, itemsPerPage, Sort.by(sortBy).descending());
        Page<Article> articlePage = articleRepository.findAll(pageable);
        return articleMapper.mapDbPageToPageOfArticle(articlePage);
    }

}
