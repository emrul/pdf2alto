package jp.ne.sakura.vopaldragon.pdf2alto.model;

public class ALTOSourceImageInformation {

    private ALTOFileName fileName;

    public ALTOSourceImageInformation(ALTOFileName fileName) {
        this.fileName = fileName;
    }

    public ALTOFileName getFileName() {
        return fileName;
    }

    public void setFileName(ALTOFileName fileName) {
        this.fileName = fileName;
    }

}
