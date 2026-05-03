package com.padelnewss.service;

import com.padelnewss.entity.Paiement;

import java.util.List;

public interface PaiementService {
    Paiement pay(Long reservationId, Long membreId);
    Paiement getById(Long id);
    Paiement getByReservationId(Long reservationId);
    List<Paiement> getByMembreId(Long membreId);
    void checkUnpaidBeforeMatch();
}
