/*
 */
package jp.ne.sakura.vopaldragon.pdf2alto.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ALTOString {

    public ALTOString() {
    }

    public ALTOString(String character, String page, int id, int height, int width, int hpos, int vpos) {
	this.id = String.format("%s_ST%06d", page, id);
	this.content = character;
	this.height = height;
	this.width = width;
	this.hpos = hpos;
	this.vpos = vpos;
	this.symbol = new ALTOSymbol(character, page, id, height, width, hpos, vpos);
    }

    private ALTOSymbol symbol;

    @XmlElement(name = "Symbol")
    public ALTOSymbol getSymbol() {
	return symbol;
    }

    public void setSymbol(ALTOSymbol symbol) {
	this.symbol = symbol;
    }

    private String id;
    private int height;
    private int width;
    private int hpos;
    private int vpos;
    private String content;

    @XmlAttribute(name = "CONTENT")
    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

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

}
