package br.com.elotech.hibernate.envers;

public interface RevisionEnvers {

    void setUserName(String name);

    void setRemoteIpAddress(String headerValueStr);

    void setActiveView(String headerValueStr);

    void setApplication(String headerValueStr);

    void setOpenedScreens(String headerValueStr);

    void setOsUser(String headerValueStr);

    String getRemoteIpAddress();

    Number getRevisionNumber();

}
