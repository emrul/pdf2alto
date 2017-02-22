package jp.ne.sakura.vopaldragon.pdf2alto.model;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "alto")
public class ALTO {

    public ALTO() {
    }

    public ALTO(ALTODescription description, ALTOPage page) {
        this.description = description;
        this.layout = new ALTOLayout();
        this.layout.setPage(page);
        this.xmlns =  "http://doc-proc.haifa.il.ibm.com/impact";
    }

    private ALTODescription description;
    private ALTOLayout layout;
    private String xmlns;

    @XmlAttribute(name = "xmlns")
    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }
    
    

    @XmlElement(name = "Description")
    public ALTODescription getDescription() {
        return description;
    }

    public void setDescription(ALTODescription description) {
        this.description = description;
    }

    @XmlElement(name = "Layout")
    public ALTOLayout getLayout() {
        return layout;
    }

    public void setLayout(ALTOLayout layout) {
        this.layout = layout;
    }

}
