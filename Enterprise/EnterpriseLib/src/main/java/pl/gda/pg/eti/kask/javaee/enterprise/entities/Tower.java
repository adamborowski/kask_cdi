package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import java.io.Serializable;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
//
@Entity
@Table(name = "towers")
@NamedQuery(name = "Tower.findAll", query = "SELECT b FROM Tower b")
public class Tower implements Serializable {

    @XmlElement(required = true, name = "wizzard")
    //
    @OneToMany(mappedBy = "tower", cascade = CascadeType.ALL)
    protected List<Sorcerer> wizzards = new ArrayList<>();
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlAttribute(name = "id", required = true)
    protected Integer id;
    @Column
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @Column
    @XmlAttribute(name = "height")
    protected Integer height;
    @XmlTransient
    @ManyToOne()
    @JoinColumn(name = "user_id")
    protected User user;
}
