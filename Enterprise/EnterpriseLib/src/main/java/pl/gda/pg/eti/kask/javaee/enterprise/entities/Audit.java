package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author psysiu
 */
@Getter
@Setter
@MappedSuperclass
public class Audit implements Serializable {

    @Version
    private int version;

    @Column(name = "modification_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;

    @PreUpdate
    public void preUpdate() {
        modificationDate = new Date();
    }
}
