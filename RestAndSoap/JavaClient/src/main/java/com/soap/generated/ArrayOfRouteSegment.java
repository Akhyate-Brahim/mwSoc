
package com.soap.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour ArrayOfRouteSegment complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRouteSegment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RouteSegment" type="{http://schemas.datacontract.org/2004/07/RouteService}RouteSegment" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRouteSegment", propOrder = {
    "routeSegment"
})
public class ArrayOfRouteSegment {

    @XmlElement(name = "RouteSegment", nillable = true)
    protected List<RouteSegment> routeSegment;

    /**
     * Gets the value of the routeSegment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the routeSegment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRouteSegment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RouteSegment }
     * 
     * 
     */
    public List<RouteSegment> getRouteSegment() {
        if (routeSegment == null) {
            routeSegment = new ArrayList<RouteSegment>();
        }
        return this.routeSegment;
    }

}
