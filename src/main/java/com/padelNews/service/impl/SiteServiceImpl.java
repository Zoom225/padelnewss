package com.padelNews.service.impl;


import com.padelNews.entity.Site;
import com.padelNews.exception.ResourceNotFoundException;
import com.padelNews.repository.SiteRepository;
import com.padelNews.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;

    @Override
    public Site create(Site site) {
        return siteRepository.save(site);
    }

    @Override
    public Site getById(Long id) {
        return siteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Site not found with id : " + id));
    }

    @Override
    public List<Site> getAll() {
        return siteRepository.findAll();
    }

    @Override
    public Site update(Long id, Site site) {
        Site existing = getById(id);
        existing.setNom(site.getNom());
        existing.setAdresse(site.getAdresse());
        existing.setHeureOuverture(site.getHeureOuverture());
        existing.setHeureFermeture(site.getHeureFermeture());
        existing.setDureeMatchMinutes(site.getDureeMatchMinutes());
        existing.setDureeEntreMatchMinutes(site.getDureeEntreMatchMinutes());
        existing.setAnneeCivile(site.getAnneeCivile());
        return siteRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Site existing = getById(id);
        siteRepository.delete(existing);
    }
}
