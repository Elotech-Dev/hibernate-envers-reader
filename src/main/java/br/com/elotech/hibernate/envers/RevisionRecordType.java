package br.com.elotech.hibernate.envers;

import org.hibernate.envers.RevisionType;

public enum RevisionRecordType {

    INSERIDO, ALTERADO, APAGADO;

    public static RevisionRecordType fromRevisionType(RevisionType revisionType) {

        switch (revisionType) {
        case ADD:
            return INSERIDO;
        case DEL:
            return APAGADO;
        case MOD:
            return ALTERADO;

        default:
            throw new IllegalArgumentException();
        }

    }

}
