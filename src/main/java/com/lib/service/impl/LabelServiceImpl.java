package com.lib.service.impl;

import com.lib.dto.lib.LabelDto;
import com.lib.entity.Label;
import com.lib.repository.LabelRepository;
import com.lib.service.LabelService;
import com.lib.utils.mapper.LibraryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;
    private final LibraryMapper libraryMapper;

    @Override
    public List<LabelDto> getLabelsByBookId(Long bookId) {
        return map(labelRepository.findAllByBook(bookId));
    }

    @Override
    public List<LabelDto> getLabels() {
        return map(labelRepository.findAll());
    }

    private List<LabelDto> map(List<Label> source) {
        return libraryMapper.mapToLabelsDto(source);
    }
}
