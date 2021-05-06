package com.lib.web;

import com.lib.dto.lib.ClassificationDto;
import com.lib.dto.lib.ClassificationType;
import com.lib.service.ClassificationService;
import com.lib.utils.api.ApiResponse;
import com.lib.utils.api.Responses;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/classification")
public class ClassificationController {
    private final ClassificationService classificationService;

    @GetMapping
    public ApiResponse<List<ClassificationDto>> getClassifications(@RequestParam ClassificationType type) {
        log.info("Request on getting classifications by type - {}", type);
        List<ClassificationDto> result = classificationService.getByType(type);
        log.info("Response on getting classifications by type - {}", result);
        return Responses.okResponse(result);
    }

    @GetMapping("/all")
    public ApiResponse<List<ClassificationDto>> getAllClassifications() {
        log.info("Request on getting all classifications");
        List<ClassificationDto> result = classificationService.getAll();
        log.info("Response's size on getting all classifications - {}", result.size());
        return Responses.okResponse(result);
    }

    @PostMapping
    public ApiResponse<ClassificationDto> createClassification(@RequestBody ClassificationDto request) {
        log.info("Request on creating classification - {}", request);
        ClassificationDto result = classificationService.create(request);
        log.info("Response on creating classification - {}", result);
        return Responses.okResponse(result);
    }

    @PutMapping
    public ApiResponse<ClassificationDto> editClassification(@RequestBody ClassificationDto request) {
        log.info("Request on creating classification - {}", request);
        ClassificationDto result = classificationService.edit(request);
        log.info("Response on creating classification - {}", result);
        return Responses.okResponse(result);
    }

    @PostMapping("/delete")
    public ApiResponse<Boolean> deleteClassification(@RequestBody ClassificationDto request) {
        log.info("Request on deleting classification - {}", request);
        boolean result = classificationService.delete(request);
        log.info("Response on deleting classification - {}", result);
        return Responses.okResponse(result);
    }
}
