package br.com.elotech.hibernate.envers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;

import br.com.elotech.hibernate.support.HibernateProxyUtils;

public class RevisionReader {

    private final EntityManager em;

    public RevisionReader(EntityManager em) {
        super();
        this.em = em;
    }

    private <U extends Serializable, T extends EntityId<U>> List<RevisionRecord<T>> createListRevisionRecord(
            List<?> history) {
        List<RevisionRecord<T>> listHistory = new ArrayList<>();

        for (Object revisionRecord : history) {

            Object[] revision = (Object[]) revisionRecord;

            RevisionType revisionType = (RevisionType) revision[2];

            RevisionEnvers revisionEntity = (RevisionEnvers) revision[1];

            revisionEntity = HibernateProxyUtils.initializeProxys(revisionEntity);

            @SuppressWarnings("unchecked")
            T entity = (T) revision[0];

            entity = HibernateProxyUtils.initializeProxys(entity);

            listHistory.add(new RevisionRecord<T>(entity, revisionEntity, RevisionRecordType
                    .fromRevisionType(revisionType)));

        }
        return listHistory;
    }

    private <U extends Serializable, T extends EntityId<U>> void validAudited(AuditReader reader, Class<T> clazz) {

        if (!reader.isEntityClassAudited(clazz)) {

            throw new IllegalArgumentException(String.format("Entidade %s não é auditada.", clazz.getSimpleName()));
        }
    }

    public <U extends Serializable, T extends EntityId<U>> List<RevisionRecord<T>>
            findHistory(Class<T> clazz, U key) {

        AuditReader reader = AuditReaderFactory.get(em);

        validAudited(reader, clazz);

        List<?> history = reader.createQuery()
                .forRevisionsOfEntity(clazz, false, false)
                .add(AuditEntity.id().eq(key))
                .addOrder(AuditEntity.revisionNumber().desc())
                .getResultList();

        return createListRevisionRecord(history);

    }

    public <U extends Serializable, T extends EntityId<U>> List<RevisionRecord<T>> findDeletes(Class<T> clazz) {

        AuditReader reader = AuditReaderFactory.get(em);

        validAudited(reader, clazz);

        List<?> deletes = reader.createQuery().forRevisionsOfEntity(clazz, false, true)
                .add(AuditEntity.revisionType().eq(RevisionType.DEL)).getResultList();

        List<RevisionRecord<T>> deletesRecords = createListRevisionRecord(deletes);

        for (RevisionRecord<T> revisionRecord : deletesRecords) {

            Object entity = reader.createQuery().forRevisionsOfEntity(clazz, true, false)
                    .add(AuditEntity.id().eq(revisionRecord.getEntity().getId()))
                    .add(AuditEntity.revisionNumber().lt(revisionRecord.getRevision().getRevisionNumber()))
                    .addOrder(AuditEntity.revisionNumber().desc()).setMaxResults(1)
                    .getSingleResult();

            @SuppressWarnings("unchecked")
            T lastRevisionentity = (T) entity;

            lastRevisionentity = HibernateProxyUtils.initializeProxys(lastRevisionentity);

            revisionRecord.setEntity(lastRevisionentity);

        }

        return deletesRecords;
    }
}
