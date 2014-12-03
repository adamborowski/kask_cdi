package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import java.io.Serializable;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tower")
public class Tower implements Serializable {

    @OneToMany(mappedBy = "tower", cascade = CascadeType.ALL)
    @XmlElement(name = "wizzard")
    protected List<Sorcerer> wizzards = new ArrayList<>();
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column
    protected String name;
    @Column
    protected Integer height;
    @ManyToOne()
    @XmlTransient
    @JoinColumn(name = "user_id")
    protected User user;

}
