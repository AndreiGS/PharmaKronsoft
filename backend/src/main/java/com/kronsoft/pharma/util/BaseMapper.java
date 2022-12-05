package com.kronsoft.pharma.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public BaseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public<ENTITY, DTO> ENTITY dtoToEntity(DTO dto, Class<ENTITY> entityClass) {
        return this.modelMapper.map(dto, entityClass);
    }

    public<ENTITY, DTO> DTO entityToDto(ENTITY entity, Class<DTO> dtoClass) {
        return this.modelMapper.map(entity, dtoClass);
    }
}
