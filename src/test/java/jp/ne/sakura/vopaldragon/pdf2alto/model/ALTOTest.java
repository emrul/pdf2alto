/*
 */
package jp.ne.sakura.vopaldragon.pdf2alto.model;

import javax.xml.bind.JAXB;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Stormbringer
 */
public class ALTOTest {

    public ALTOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getXmlns method, of class ALTO.
     */
    @Test
    public void testALTO() {
        ALTODescription desc = new ALTODescription(new ALTOMesurementUnit("pixel"), new ALTOSourceImageInformation(new ALTOFileName("file_name01")));
        ALTOPage page = new ALTOPage("P1", 1200, 1300);
        ALTO hoge = new ALTO(desc, page);

        ALTOTextBlock textBlock = new ALTOTextBlock(page.getId(), 1, 0, 0, 100, 100);
        page.addTextBlock(textBlock);
        ALTOTextLine textLine = textBlock.getTextLine();

        textLine.addString(new ALTOString("あ", "P1", 1, 0, 0, 20, 10));
        textLine.addString(new ALTOString("い", "P1", 1, 0, 0, 20, 10));

        JAXB.marshal(hoge, System.out);
    }

}
