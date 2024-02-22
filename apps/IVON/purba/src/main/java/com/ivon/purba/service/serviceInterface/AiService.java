package com.ivon.purba.service.serviceInterface;

import com.google.gson.JsonElement;
import com.ivon.purba.dto.aiService.PhotoAnalysisResponse;

public interface AiService {
    PhotoAnalysisResponse analyzePhoto(String photoUrl);
}
