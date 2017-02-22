package jp.ne.sakura.vopaldragon.pdf2alto.model;

import javax.xml.bind.annotation.XmlElement;

public class ALTODescription {

    private ALTOMesurementUnit mesurementUnit;
    private ALTOSourceImageInformation imageInformation;

    public ALTODescription(ALTOMesurementUnit mesurementUnit, ALTOSourceImageInformation imageInformation) {
        this.mesurementUnit = mesurementUnit;
        this.imageInformation = imageInformation;
    }

    @XmlElement(name = "MeasurementUnit")
    public ALTOMesurementUnit getMesurementUnit() {
        return mesurementUnit;
    }

    public void setMesurementUnit(ALTOMesurementUnit mesurementUnit) {
        this.mesurementUnit = mesurementUnit;
    }

    @XmlElement(name = "sourceImageInformation")
    public ALTOSourceImageInformation getImageInformation() {
        return imageInformation;
    }

    public void setImageInformation(ALTOSourceImageInformation imageInformation) {
        this.imageInformation = imageInformation;
    }

}