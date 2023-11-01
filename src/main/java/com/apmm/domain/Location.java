package com.apmm.domain;

import java.util.Date;


public class Location {
    //@Id
    //private String id;
    private String source;
    private String destination;
    private Date created_date;
    private Date modified_date;
    private String modified_by;
    private String created_by;

    public Location(){
        super();
    }
    public Location(String source, String destination, Date created_date, Date modified_date, String modified_by, String created_by) {
        super();
        this.source = source;
        this.destination = destination;
        this.created_date = created_date;
        this.modified_date = modified_date;
        this.modified_by = modified_by;
        this.created_by = created_by;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public void setModified_date(Date modified_date) {
        this.modified_date = modified_date;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public Date getModified_date() {
        return modified_date;
    }

    public String getModified_by() {
        return modified_by;
    }

    public String getCreated_by() {
        return created_by;
    }


}
