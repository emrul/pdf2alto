/*
 */
package jp.ne.sakura.vopaldragon.pdf2alto.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ALTOTextLine {

    public ALTOTextLine() {
    }

    public ALTOTextLine(String page, int id, int height, int width, int hpos, int vpos) {
	this.id = String.format("%s_TL%06d", page, id);
	this.height = height;
	this.width = width;
	this.hpos = hpos;
	this.vpos = vpos;
	this.strings = new ArrayList<>();
    }

    public void addString(ALTOString string) {
	this.strings.add(string);
    }

    private String id;
    private int height;
    private int width;
    private int hpos;
    private int vpos;

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

    private List<ALTOString> strings;

    @XmlElement(name = "String")
    public List<ALTOString> getStrings() {
	return strings;
    }

    public void setStrings(List<ALTOString> strings) {
	this.strings = strings;
    }

}
