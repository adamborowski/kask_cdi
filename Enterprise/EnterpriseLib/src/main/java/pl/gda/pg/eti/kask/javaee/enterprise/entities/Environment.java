package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;

/**
 * @author psysiu
 */
@XmlType(name = "environment")
@XmlEnum
public enum Environment implements Serializable{
    water,
    fire,
    wind;

    static HashMap<Environment, String> labels = new HashMap<>();

    static {
        labels.put(water, "The Mineral Water");
        labels.put(fire, "The Cold Fire");
        labels.put(wind, "The Ows Wind");
    }

    @Override
    public String toString() {
        return labels.get(this);
    }
}