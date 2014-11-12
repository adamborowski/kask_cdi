package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
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
@ToString(exclude = "books")
@EqualsAndHashCode(exclude = "books")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "author")
@Entity
@Table(name = "authors")
@NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a")
public class Author extends Audit implements Serializable {

    @XmlAttribute
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @XmlAttribute
    @Column
    private String name;

    @XmlAttribute
    @Column
    private String surname;
    
    @XmlTransient
    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

}
