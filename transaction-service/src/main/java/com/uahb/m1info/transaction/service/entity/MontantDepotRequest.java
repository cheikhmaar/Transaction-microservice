package com.uahb.m1info.transaction.service.entity;

public class MontantDepotRequest {
    private int montantDepot; // Le montant de dépôt

    // Un constructeur vide (obligatoire pour la désérialisation JSON)
    public MontantDepotRequest() {
    }

    // Un constructeur avec le montant de dépôt
    public MontantDepotRequest(int montantDepot) {
        this.montantDepot = montantDepot;
    }

    // Méthode getter pour obtenir le montant de dépôt
    public int getMontantDepot() {
        return montantDepot;
    }

    // Méthode setter pour définir le montant de dépôt
    public void setMontantDepot(int montantDepot) {
        this.montantDepot = montantDepot;
    }
}

