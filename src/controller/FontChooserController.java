package controller;

import Layers.FontInfo;
import Main.FontChooserFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Event receiver for font-choosing frame
 */
public class FontChooserController implements Initializable {

    @FXML
    ListView<String> fontList;

    @FXML
    ListView<String> weightList;

    @FXML
    ListView<String> sizeList;

    @FXML
    CheckBox italicCheckBox;

    @FXML
    TextArea preview;

    public static FontInfo font=new FontInfo("Arial",30,FontPosture.REGULAR,FontWeight.NORMAL);

    /**
     * Singleton Pattern
     */

    static FontChooserController instance;

    public FontChooserController() {
        instance = this;
    }

    public static FontChooserController getInstance() {
        return instance;
    }

    /**
     * Display font on the right side, for intuitive display
     */
    public void redisplayFont(){
        String fontName=fontList.getSelectionModel().getSelectedItem();
        FontPosture fontPosture= italicCheckBox.isSelected()?FontPosture.ITALIC:FontPosture.REGULAR;
        FontWeight fontWeight=FontWeight.findByName(weightList.getSelectionModel().getSelectedItem());
        int fontSize=Integer.parseInt(sizeList.getSelectionModel().getSelectedItem());
        font=new FontInfo(fontName,fontSize,fontPosture,fontWeight);
        preview.setFont(font.getFont());
    }

    /**
     * initialize event listeners and data, which not produced in JavaFX
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Search font in system
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fonts = ge.getAvailableFontFamilyNames();
        for (String font : fonts) {
            fontList.getItems().add(font);
        }
        fontList.getSelectionModel().select(0);
        // add styles
        weightList.getItems().addAll("Thin","Extra Light","Light","Normal","Medium","Semi Bold","Bold","Extra Bold","Black");
        weightList.getSelectionModel().select(0);
        // add sizes
        for(int i=5;i<=100;i++){
            sizeList.getItems().add(String.valueOf(i));
        }
        sizeList.getSelectionModel().select("50");

        fontList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                redisplayFont();
            }
        });

        weightList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                redisplayFont();
            }
        });

        sizeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                redisplayFont();
            }
        });
    }

    /**
     * confirm button callback: close window
     */
    public void confirm(){
        FontChooserFrame.fontStage.close();
    }

}
