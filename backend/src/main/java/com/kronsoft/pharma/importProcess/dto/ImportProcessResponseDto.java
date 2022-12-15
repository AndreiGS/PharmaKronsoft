package com.kronsoft.pharma.importProcess.dto;

import com.kronsoft.pharma.importProcess.ProcessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImportProcessResponseDto {
    private UUID processId;
   /* private String username;*/
    private ProcessStatus status;
    private Integer processedRecords;
    private Integer totalRecords;
}
