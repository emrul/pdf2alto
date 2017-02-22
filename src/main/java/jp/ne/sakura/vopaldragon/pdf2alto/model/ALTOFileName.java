package jp.ne.sakura.vopaldragon.pdf2alto.model;

import javax.xml.bind.annotation.XmlValue;

public class ALTOFileName {

    private String value;

    public ALTOFileName(String value) {
        this.value = value;
    }

    @XmlValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
