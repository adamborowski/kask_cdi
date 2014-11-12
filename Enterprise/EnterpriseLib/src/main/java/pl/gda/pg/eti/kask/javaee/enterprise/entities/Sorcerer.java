    package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import java.io.Serializable;
import lombok.*;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.validators.GoodMana;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@NoArgsConstructor
@ToString(exclude = "tower")
@EqualsAndHashCode(exclude = "tower")
@AllArgsConstructor
@Getter
@Setter
//
@Entity
@Table(name = "wizzards")
@NamedQueries({
    @NamedQuery(name = "Sorcerer.findAll", query = "SELECT b FROM Sorcerer b"),
    @NamedQuery(name = "Sorcerer.training", query = "UPDATE Sorcerer b set b.mana=b.mana+:amount")
})
public class Sorcerer implements Serializable {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlAttribute(name = "id", required = true)
    protected Integer id;
    @Column
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @Column
    @XmlAttribute(name = "mana", required = true)
    @GoodMana(maximumMana = 1234, dividableBy = 5)
    protected Integer mana;
    @Column
    @Enumerated(EnumType.STRING)
    @XmlAttribute(name = "environment", required = true)
    protected Environment environment;
    @XmlTransient
    @ManyToOne()
    @JoinColumn(name = "tower_id")
    protected Tower tower;
}
