package com.padelnewss.service.impl;

import com.padelnewss.entity.Site;
import com.padelnewss.exception.ResourceNotFoundException;
import com.padelnewss.repository.SiteRepository;
import com.padelnewss.service.SiteService;
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
        // Correction : Utiliser la méthode standard findAll().
        // C'est maintenant la bonne approche car les collections sont en LAZY
        // et ne seront pas chargées, évitant ainsi l'erreur 500.
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
