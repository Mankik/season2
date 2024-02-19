package com.ivon.purba.service;

import com.ivon.purba.dto.aiService.PhotoAnalysisResponse;
import com.ivon.purba.exception.AIAnalysisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class AiServiceImpl implements AiService {

    private static final String AI_API_URL = "https://example.com/ai/api"; //미구현

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public PhotoAnalysisResponse analyzePhoto(String photoUrl) {
        try {
            return restTemplate.postForObject(AI_API_URL, photoUrl, PhotoAnalysisResponse.class);
        } catch (RestClientException e) {
            throw new AIAnalysisException("AI 분석 실패: " + e.getMessage());
        }
    }
}
