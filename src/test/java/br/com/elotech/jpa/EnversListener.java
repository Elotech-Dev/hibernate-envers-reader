package br.com.elotech.jpa;

import org.hibernate.envers.RevisionListener;

import br.com.elotech.hibernate.envers.RevisionEnvers;

public class EnversListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        RevisionEnvers revision = (RevisionEnvers) revisionEntity;
        revision.setUserName("GALO");
        revision.setRemoteIpAddress("666.666.666.666");
        revision.setActiveView("FRM_GALO");
        revision.setApplication("GALO_APP");
        revision.setOpenedScreens("GALO_SCREEN");
        revision.setOsUser("GALO");
    }

}
