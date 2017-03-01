/*
 */
package jp.ne.sakura.vopaldragon.pdf2alto.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ALTOTextBlock {

    public ALTOTextBlock(String page, int id, int height, int width, int hpos, int vpos) {
	this.id = String.format("%s_TB%06d", page, id);
	this.height = height;
	this.width = width;
	this.hpos = hpos;
	this.vpos = vpos;
	this.textLine = new ALTOTextLine(page, id, height, width, hpos, vpos);
    }

    private String id;
    private int height;
    private int width;
    private int hpos;
    private int vpos;
    private ALTOTextLine textLine;

    @XmlAttribute(name = "ID")
    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    @XmlAttribute(name = "HEIGHT")
    public int getHeight() {
	return height;
    }

    public void setHeight(int height) {
	this.height = height;
    }

    @XmlAttribute(name = "WIDTH")
    public int getWidth() {
	return width;
    }

    public void setWidth(int width) {
	this.width = width;
    }

    @XmlAttribute(name = "HPOS")
    public int getHpos() {
	return hpos;
    }

    public void setHpos(int hpos) {
	this.hpos = hpos;
    }

    @XmlAttribute(name = "VPOS")
    public int getVpos() {
	return vpos;
    }

    public void setVpos(int vpos) {
	this.vpos = vpos;
    }

    @XmlElement(name = "TextLine")
    public ALTOTextLine getTextLine() {
	return textLine;
    }

    public void setTextLine(ALTOTextLine textLine) {
	this.textLine = textLine;
    }

}
