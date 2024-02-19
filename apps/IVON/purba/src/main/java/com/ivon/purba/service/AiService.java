package com.ivon.purba.service;

import com.ivon.purba.dto.aiService.PhotoAnalysisResponse;

public interface AiService {
    PhotoAnalysisResponse analyzePhoto(String photoUrl);
}
