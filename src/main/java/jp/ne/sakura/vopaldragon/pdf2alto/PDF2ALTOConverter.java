package jp.ne.sakura.vopaldragon.pdf2alto;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.xml.bind.JAXB;
import jp.ne.sakura.vopaldragon.pdf2alto.model.ALTO;
import jp.ne.sakura.vopaldragon.pdf2alto.model.ALTODescription;
import jp.ne.sakura.vopaldragon.pdf2alto.model.ALTOFileName;
import jp.ne.sakura.vopaldragon.pdf2alto.model.ALTOMesurementUnit;
import jp.ne.sakura.vopaldragon.pdf2alto.model.ALTOPage;
import jp.ne.sakura.vopaldragon.pdf2alto.model.ALTOSourceImageInformation;
import jp.ne.sakura.vopaldragon.pdf2alto.model.ALTOString;
import jp.ne.sakura.vopaldragon.pdf2alto.model.ALTOTextBlock;
import org.apache.fontbox.util.BoundingBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType3Font;
import org.apache.pdfbox.pdmodel.interactive.pagenavigation.PDThreadBead;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDF2ALTOConverter extends PDFTextStripper {

    private final static Logger logger = LoggerFactory.getLogger(PDF2ALTOConverter.class);

    private BufferedImage image;
    private AffineTransform flipAT;
    private AffineTransform rotateAT;
    private AffineTransform transAT;
    static final int SCALE = 4;
    private Graphics2D g2d;

    private final PDDocument document;

    File xmlDir;
    File imgDir;

    private int dx;
    private int dy;
    private double sx;
    private double sy;

    public PDF2ALTOConverter(PDDocument document, File xmlDir, File imgDir, int dx, int dy, double sx, double sy) throws IOException {
        this.document = document;
        this.xmlDir = xmlDir;
        this.imgDir = imgDir;
        this.dx = dx;
        this.dy = dy;
        this.sx = sx;
        this.sy = sy;
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 6) {
            System.out.println("Usage: java -jar pdf2alto.jar [pdf file path] [output dir path] [dx(int)] [dy(int)] [sx(double)] [sy(double)] [image output dir path(optional)]");
            System.exit(0);
        }
        File pdfFile = new File(args[0]);
        File xmlDir = new File(args[1]);
        int dx = Integer.parseInt(args[2]);
        int dy = Integer.parseInt(args[3]);
        double sx = Double.parseDouble(args[4]);
        double sy = Double.parseDouble(args[5]);
        File imageDir = null;
        if (args.length > 6) {
            imageDir = new File(args[6]);
        }

        try (PDDocument document = PDDocument.load(pdfFile)) {
            logger.info("start converting {}", pdfFile.getName());
            PDF2ALTOConverter stripper = new PDF2ALTOConverter(document, xmlDir, imageDir, dx, dy, sx, sy);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                try {
                    stripper.stripPage(page);
                } catch (Throwable t) {
                    logger.error("Unexpected error", t);
                }
            }
            logger.info("finished converting");
        }
    }

    private ALTO alto;

    private void stripPage(int page) throws IOException {
        logger.info("start converting page {}", page);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        image = pdfRenderer.renderImage(page, SCALE);
        logger.debug("{}x{}", image.getWidth(), image.getHeight());

        //JAXB用オブジェクト準備
        ALTODescription desc = new ALTODescription(new ALTOMesurementUnit("pixel"), new ALTOSourceImageInformation(new ALTOFileName("")));
        String pageName = "P" + (page + 1);
        ALTOPage altoPage = new ALTOPage(pageName, image.getWidth(), image.getHeight());
        alto = new ALTO(desc, altoPage);

        PDPage pdPage = document.getPage(page);
        PDRectangle cropBox = pdPage.getCropBox();

        flipAT = new AffineTransform();
        // flip y-axis
        flipAT.translate(0, pdPage.getBBox().getHeight());
        flipAT.scale(1, -1);

        // page may be rotated
        rotateAT = new AffineTransform();
        int rotation = pdPage.getRotation();
        if (rotation != 0) {
            PDRectangle mediaBox = pdPage.getMediaBox();
            switch (rotation) {
                case 90:
                    rotateAT.translate(mediaBox.getHeight(), 0);
                    break;
                case 270:
                    rotateAT.translate(0, mediaBox.getWidth());
                    break;
                case 180:
                    rotateAT.translate(mediaBox.getWidth(), mediaBox.getHeight());
                    break;
                default:
                    break;
            }
            rotateAT.rotate(Math.toRadians(rotation));
        }

        // cropbox
        transAT = AffineTransform.getTranslateInstance(-cropBox.getLowerLeftX(), cropBox.getLowerLeftY());

        g2d = image.createGraphics();
        g2d.setStroke(new BasicStroke(0.1f));
        g2d.scale(SCALE, SCALE);

        setStartPage(page + 1);
        setEndPage(page + 1);

        Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
        writeText(document, dummy);

        // beads in green
        g2d.setStroke(new BasicStroke(0.4f));
        List<PDThreadBead> pageArticles = pdPage.getThreadBeads();
        for (PDThreadBead bead : pageArticles) {
            PDRectangle r = bead.getRectangle();
            Shape s = r.toGeneralPath().createTransformedShape(transAT);
            s = flipAT.createTransformedShape(s);
            s = rotateAT.createTransformedShape(s);
            g2d.setColor(Color.green);
            g2d.draw(s);
        }

        g2d.dispose();

        String page0pad = String.format("%03d", page);

        if (imgDir != null) {
            File imageFile = imgDir.toPath().resolve("image_" + page0pad + ".png").toFile();
            ImageIO.write(image, "png", imageFile);
        }
        image = null;
        File altoFile = xmlDir.toPath().resolve("alto_" + page0pad + ".xml").toFile();
        JAXB.marshal(alto, altoFile);
    }

    private List<ALTOString> strings = new ArrayList<>();
    private int lineCount = 1;

    @Override
    protected void writeParagraphStart() throws IOException {
        logger.debug("Paragraph start");
        strings = new ArrayList<>();
        super.writeParagraphStart();
    }

    @Override
    protected void writeParagraphEnd() throws IOException {
        logger.debug("Paragraph End");
        ALTOTextBlock block = new ALTOTextBlock(alto.getLayout().getPage().getId(), lineCount++, 0, 0, 0, 0);
        strings.stream().forEach(str -> block.getTextLine().addString(str));
        alto.getLayout().getPage().addTextBlock(block);
        super.writeParagraphEnd();
    }

    private int charCount = 1;

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
        for (TextPosition text : textPositions) {
            Rectangle2D.Float rect = new Rectangle2D.Float(
                text.getXDirAdj(),
                (text.getYDirAdj() - text.getHeightDir()),
                text.getWidthDirAdj(),
                text.getHeightDir());
            g2d.setColor(Color.red);

            PDFont font = text.getFont();
            BoundingBox bbox = font.getBoundingBox();

            float xadvance = font.getWidth(text.getCharacterCodes()[0]);
            rect = new Rectangle2D.Float(0, bbox.getLowerLeftY(), xadvance, bbox.getHeight());

            AffineTransform at = text.getTextMatrix().createAffineTransform();
            if (font instanceof PDType3Font) {
                at.concatenate(font.getFontMatrix().createAffineTransform());
            } else {
                at.scale(1 / 1000f, 1 / 1000f);
            }
            Shape s = at.createTransformedShape(rect);

            s = flipAT.createTransformedShape(s);
            s = rotateAT.createTransformedShape(s);

            g2d.setColor(Color.blue);
            g2d.draw(s);

            AffineTransform sc = AffineTransform.getScaleInstance(SCALE, SCALE);
            s = sc.createTransformedShape(s);
            Rectangle r = s.getBounds();
            strings.add(new ALTOString(text.getUnicode(), alto.getLayout().getPage().getId(), charCount++, (int) (r.height * sy), (int) (r.width * sx), (int) (r.x * sx + dx), (int) (r.y * sy + dy)));
            logger.debug("char:{} x:{} y:{} w:{} h:{}", text.getUnicode(), r.x, r.y, r.width, r.height);
        }
    }

}
