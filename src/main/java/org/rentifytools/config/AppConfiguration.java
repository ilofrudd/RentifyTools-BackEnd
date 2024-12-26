package org.rentifytools.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.rentifytools.dto.toolDto.ToolRequestDto;
import org.rentifytools.entity.Tool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        TypeMap<ToolRequestDto, Tool> typeMap = mapper.createTypeMap(ToolRequestDto.class, Tool.class);
        typeMap.addMappings(mapping -> mapping.skip(Tool::setImageUrls));

        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return mapper;
    }
}
