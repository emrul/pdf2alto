package jp.ne.sakura.vopaldragon.pdf2alto.model;

import javax.xml.bind.annotation.XmlValue;

public class ALTOMesurementUnit {

    public ALTOMesurementUnit(String value) {
        this.value = value;
    }

    private String value;

    @XmlValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
