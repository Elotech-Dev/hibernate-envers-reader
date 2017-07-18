package br.com.elotech.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import br.com.elotech.hibernate.envers.RevisionEnvers;
import br.com.elotech.jpa.EnversListener;
import lombok.ToString;

@RevisionEntity(value = EnversListener.class)
@Entity
@Table(name = "revision")
@ToString
public class Revision implements RevisionEnvers {

    @Id
    @RevisionNumber
    @SequenceGenerator(sequenceName = "seq_revision", allocationSize = 1, name = "seq_revision")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_revision")
    private Long revisionNumber;

    @RevisionTimestamp
    @Column(name = "revisiondate")
    private Date revisionDate;

    @Column(name = "username", length = 30)
    private String userName;

    @Column(name = "remoteipaddress", length = 40)
    private String remoteIpAddress;

    @Column(name = "activeview", length = 100)
    private String activeView;

    @Column(length = 100)
    private String application;

    @Column(name = "osuser", length = 100)
    private String osUser;

    @Column(name = "openedscreens", length = 4000)
    private String openedScreens;

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getActiveView() {
        return activeView;
    }

    public void setActiveView(String activeView) {
        this.activeView = activeView;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemoteIpAddress() {
        return remoteIpAddress;
    }

    public void setRemoteIpAddress(String remoteIpAddress) {
        this.remoteIpAddress = remoteIpAddress;
    }

    public String getOsUser() {
        return osUser;
    }

    public void setOsUser(String osUser) {
        this.osUser = osUser;
    }

    public String getOpenedScreens() {
        return openedScreens;
    }

    public void setOpenedScreens(String openedScreens) {
        this.openedScreens = openedScreens;
    }

    public Long getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Long revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

}
