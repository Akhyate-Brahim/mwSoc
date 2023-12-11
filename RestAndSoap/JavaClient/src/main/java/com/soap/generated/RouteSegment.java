
package com.soap.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour RouteSegment complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="RouteSegment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="distance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="routePositions" type="{http://schemas.datacontract.org/2004/07/ProxyCacheService}ArrayOfPosition" minOccurs="0"/&gt;
 *         &lt;element name="steps" type="{http://schemas.datacontract.org/2004/07/RouteService}ArrayOfStep" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RouteSegment", namespace = "http://schemas.datacontract.org/2004/07/RouteService", propOrder = {
    "distance",
    "duration",
    "routePositions",
    "steps"
})
public class RouteSegment {

    protected Double distance;
    protected Double duration;
    @XmlElementRef(name = "routePositions", namespace = "http://schemas.datacontract.org/2004/07/RouteService", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfPosition> routePositions;
    @XmlElementRef(name = "steps", namespace = "http://schemas.datacontract.org/2004/07/RouteService", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfStep> steps;

    /**
     * Obtient la valeur de la propriété distance.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * Définit la valeur de la propriété distance.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDistance(Double value) {
        this.distance = value;
    }

    /**
     * Obtient la valeur de la propriété duration.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDuration() {
        return duration;
    }

    /**
     * Définit la valeur de la propriété duration.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDuration(Double value) {
        this.duration = value;
    }

    /**
     * Obtient la valeur de la propriété routePositions.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPosition }{@code >}
     *     
     */
    public JAXBElement<ArrayOfPosition> getRoutePositions() {
        return routePositions;
    }

    /**
     * Définit la valeur de la propriété routePositions.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPosition }{@code >}
     *     
     */
    public void setRoutePositions(JAXBElement<ArrayOfPosition> value) {
        this.routePositions = value;
    }

    /**
     * Obtient la valeur de la propriété steps.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfStep }{@code >}
     *     
     */
    public JAXBElement<ArrayOfStep> getSteps() {
        return steps;
    }

    /**
     * Définit la valeur de la propriété steps.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfStep }{@code >}
     *     
     */
    public void setSteps(JAXBElement<ArrayOfStep> value) {
        this.steps = value;
    }

}
