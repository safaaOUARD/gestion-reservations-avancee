package com.example.repository;

import com.example.model.Salle;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalleRepositoryImpl implements SalleRepository {

    private final EntityManager entityManager;

    public SalleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // ==================== Méthode corrigée (la plus fiable) ====================
    @Override
    public List<Salle> findAvailableRooms(LocalDateTime start, LocalDateTime end) {
        String jpql = "SELECT s FROM Salle s " +
                      "WHERE NOT EXISTS (" +
                      "    SELECT r FROM Reservation r " +
                      "    WHERE r.salle = s " +
                      "      AND r.dateDebut < :end " +
                      "      AND r.dateFin > :start" +
                      ")";

        return entityManager.createQuery(jpql, Salle.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    @Override
    public List<Salle> findByCriteria(Map<String, Object> criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Salle> query = cb.createQuery(Salle.class);
        Root<Salle> root = query.from(Salle.class);

        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, Object> entry : criteria.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "nom":
                    predicates.add(cb.like(root.get("nom"), "%" + value + "%"));
                    break;
                case "capaciteMin":
                    predicates.add(cb.greaterThanOrEqualTo(root.get("capacite"), (Integer) value));
                    break;
                case "capaciteMax":
                    predicates.add(cb.lessThanOrEqualTo(root.get("capacite"), (Integer) value));
                    break;
                case "batiment":
                    predicates.add(cb.equal(root.get("batiment"), value));
                    break;
                case "etage":
                    predicates.add(cb.equal(root.get("etage"), value));
                    break;
                case "equipement":
                    // Jointure sécurisée
                    Join<Object, Object> equipJoin = root.join("equipements");
                    predicates.add(cb.equal(equipJoin.get("id"), value));
                    break;
            }
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        query.orderBy(cb.asc(root.get("id")));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Salle> findAllPaginated(int page, int size) {
        return entityManager.createQuery(
                "SELECT s FROM Salle s ORDER BY s.id", Salle.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(s) FROM Salle s", Long.class)
                .getSingleResult();
    }

    @Override
    public Salle findById(Long id) {
        return entityManager.find(Salle.class, id);
    }

    @Override
    public List<Salle> findAll() {
        return entityManager.createQuery("SELECT s FROM Salle s", Salle.class)
                .getResultList();
    }

    @Override
    public void save(Salle salle) {
        entityManager.persist(salle);
    }

    @Override
    public void update(Salle salle) {
        entityManager.merge(salle);
    }

    @Override
    public void delete(Salle salle) {
        Salle managedSalle = entityManager.contains(salle) ? salle : entityManager.merge(salle);
        entityManager.remove(managedSalle);
    }
}