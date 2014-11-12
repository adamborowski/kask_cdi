package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author psysiu
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper =  true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comics")
@Entity
@Table(name = "books")
@NamedQuery(name = "Comics.findAll", query = "SELECT b FROM Comics b")
@DiscriminatorValue("comics")
public class Comics extends Book {
    
    @XmlAttribute
    private int volume;
    
}
