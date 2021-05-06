package com.lib.web;

import com.lib.dto.lib.LabelDto;
import com.lib.service.LabelService;
import com.lib.utils.api.ApiResponse;
import com.lib.utils.api.Responses;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/label")
public class LabelController {
    private final LabelService labelService;

    @GetMapping("/by-book")
    private ApiResponse<List<LabelDto>> getLabelsByBook(@RequestParam Long bookId) {
        log.info("Request on getting labels by book id - {}", bookId);
        List<LabelDto> result = labelService.getLabelsByBookId(bookId);
        log.info("Response's size on getting labels by book id - {}", result.size());
        return Responses.okResponse(result);
    }

    @GetMapping
    private ApiResponse<List<LabelDto>> getLabels() {
        log.info("Request on getting all labels");
        List<LabelDto> result = labelService.getLabels();
        log.info("Response's size on getting all labels - {}", result.size());
        return Responses.okResponse(result);
    }
}
