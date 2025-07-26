package com.sos.obs.decc.evita.repository;

import com.sos.obs.decc.evita.domain.CiscoIndicators;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

/**
 * @Author SLS --- on mai, 2019
 */


@Component
public class CiscoIndicatorsRepositoryImpl implements CiscoIndicatorsRepository {
	/*
    @PersistenceContext(unitName="evitaEntityManagerFactory")
    private EntityManager em;

    @Override
    public List<CiscoIndicators> getAllCiscoIndicators() {
        StoredProcedureQuery query = em.createStoredProcedureQuery("sp_afficheurs_evita");
        List<CiscoIndicators> result = query.getResultList();
        return result;
    }
    */
}
