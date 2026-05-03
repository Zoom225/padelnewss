package com.padelnewss.mapper;

import com.padelnewss.entity.Match;
import com.padelnewss.entity.Membre;
import com.padelnewss.entity.Terrain;
import com.padelnewss.match.dto.MatchDto;
import org.springframework.stereotype.Service;

@Service
public class MatchMapper {

    public MatchDto toMatchDto(Match match) {
        if (match == null) {
            return null;
        }

        // Gestion des relations potentiellement nulles
        Terrain terrain = match.getTerrain();
        Membre organisateur = match.getOrganisateur();

        Long terrainId = (terrain != null) ? terrain.getId() : null;
        String terrainNom = (terrain != null) ? terrain.getNom() : null;
        Long organisateurId = (organisateur != null) ? organisateur.getId() : null;
        String organisateurNom = (organisateur != null && organisateur.getPrenom() != null && organisateur.getNom() != null)
                ? organisateur.getPrenom() + " " + organisateur.getNom()
                : null;

        return new MatchDto(
                match.getId(),
                terrainId,
                terrainNom,
                organisateurId,
                organisateurNom,
                match.getDateDebut(),
                match.getDateFin(),
                match.getTypeMatch(),
                match.getStatut(),
                match.getNbJoueursActuels(),
                match.getPrixParJoueur()
        );
    }
}
