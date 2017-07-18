package br.com.elotech.hibernate.envers;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.ToString;

@ToString
public class RevisionRecord<T> {

    @JsonUnwrapped
    private T entity;

    @JsonUnwrapped
    private final RevisionEnvers revision;

    private final RevisionRecordType revisionType;

    public RevisionRecord(T entity, RevisionEnvers revision, RevisionRecordType revisionType) {
        super();
        this.entity = entity;
        this.revision = revision;
        this.revisionType = revisionType;
    }

    public T getEntity() {
        return entity;
    }

    public RevisionEnvers getRevision() {
        return revision;
    }

    public RevisionRecordType getRevisionType() {
        return revisionType;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

}
