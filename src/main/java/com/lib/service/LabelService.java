package com.lib.service;

import com.lib.dto.lib.LabelDto;

import java.util.List;

public interface LabelService {
    List<LabelDto> getLabelsByBookId(Long bookId);
    List<LabelDto> getLabels();
}
