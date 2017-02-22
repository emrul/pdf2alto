/*
 */
package jp.ne.sakura.vopaldragon.pdf2alto.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class ALTOPage {

    public ALTOPage() {
    }

    public ALTOPage(String id, int height, int width) {
        this.id = id;
        this.height = height;
        this.width = width;
        this.textBlocks = new ArrayList<ALTOTextBlock>();
    }

    public void addTextBlock(ALTOTextBlock textBlock) {
        this.textBlocks.add(textBlock);
    }

    @XmlElement(name = "TopMargin")
    public String getTopMargin() {
        return "";
    }

    @XmlElement(name = "LeftMargin")
    public String getLeftMargin() {
        return "";
    }

    @XmlElement(name = "RightMargin")
    public String getRightMargin() {
        return "";
    }

    @XmlElement(name = "BottomMargin")
    public String getBottomMargin() {
        return "";
    }

    private String id;
    private int height;
    private int width;
    private List<ALTOTextBlock> textBlocks;

    @XmlElementWrapper(name = "PrintSpace")
    @XmlElement(name = "TextBlock")
    public List<ALTOTextBlock> getTextBlocks() {
        return textBlocks;
    }

    @XmlAttribute(name = "ID")
    public String getId() {
        return id;
    }

    @XmlAttribute(name = "HEIGHT")
    public int getHeight() {
        return height;
    }

    @XmlAttribute(name = "WIDTH")
    public int getWidth() {
        return width;
    }

    public void setTextBlocks(List<ALTOTextBlock> textBlocks) {
        this.textBlocks = textBlocks;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
