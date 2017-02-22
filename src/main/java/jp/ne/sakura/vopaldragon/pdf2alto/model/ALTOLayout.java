/*
 */
package jp.ne.sakura.vopaldragon.pdf2alto.model;

import javax.xml.bind.annotation.XmlElement;

public class ALTOLayout {

    private ALTOPage page;

    @XmlElement(name = "Page")
    public ALTOPage getPage() {
	return page;
    }

    public void setPage(ALTOPage page) {
	this.page = page;
    }

}
