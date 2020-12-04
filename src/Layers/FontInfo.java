package Layers;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.Serializable;

public class FontInfo implements Serializable {

    public String fontName;
    public int size;
    public FontPosture fontPosture;
    public FontWeight fontWeight;

    public FontInfo(String fontName, int size, FontPosture fontPosture,FontWeight fontWeight){
        this.fontName=fontName;
        this.size=size;
        this.fontPosture=fontPosture;
        this.fontWeight=fontWeight;
    }

    public FontInfo(FontInfo fontInfo){
        this.fontName=fontInfo.fontName;
        this.size=fontInfo.size;
        this.fontPosture=fontInfo.fontPosture;
        this.fontWeight=fontInfo.fontWeight;
    }

    public Font getFont(){
        return Font.font(fontName,fontWeight,fontPosture,size);
    }

}
