package com.padelNews.mapper;

import com.padelNews.dto.request.TerrainRequest;
import com.padelNews.dto.response.TerrainResponse;
import com.padelNews.entity.Terrain;
import org.springframework.stereotype.Component;

@Component
public class TerrainMapper {

    public Terrain toEntity(TerrainRequest request) {
        return Terrain.builder()
                .nom(request.getNom())
                .build();
    }

    public TerrainResponse toResponse(Terrain terrain) {
        return TerrainResponse.builder()
                .id(terrain.getId())
                .nom(terrain.getNom())
                .siteId(terrain.getSite().getId())
                .siteNom(terrain.getSite().getNom())
                .build();
    }
}
