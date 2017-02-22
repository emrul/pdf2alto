/*
 */
package jp.ne.sakura.vopaldragon.pdf2alto.model;

import javax.xml.bind.annotation.XmlAttribute;

public class ALTOVariant {

    public ALTOVariant() {
    }

    
    public ALTOVariant(String vc, String vs) {
	this.vc = vc;
	this.vs = vs;
    }
    
    

    private String vc;

    private String vs;

    @XmlAttribute(name = "VC")
    public String getVc() {
	return vc;
    }

    public void setVc(String vc) {
	this.vc = vc;
    }

    @XmlAttribute(name = "VS")
    public String getVs() {
	return vs;
    }

    public void setVs(String vs) {
	this.vs = vs;
    }

}
