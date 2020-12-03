package controller;

import Layers.Layer;
import Layers.LayerGroup;
import Main.MainFrame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import Main.LayersControl;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAdapter implements Initializable {

    public static TabPane tabPane;
    public TabPane _tabPane;
    public static ControllerAdapter instance;

    public enum Input_status {SELECT, PEN, LINE, RECTANGLE, CIRCLE, ELLIPSE, TEXT}

    public static Input_status input_status = Input_status.SELECT;

    @FXML
    Button buttonSelect, buttonFreePen, buttonLine, buttonRectangle, buttonCircle, buttonEllipse, buttonText, buttonClear;

    @FXML
    Label labelStatus;

    @FXML
    ColorPicker colorPicker;

    @FXML
    ToggleButton buttonFill;

    @FXML
    public ChoiceBox<String> lineChoiceBox;

    @FXML
    public Slider lineWidthSlider;

    public ControllerAdapter() throws IOException {
        instance = this;
    }

    void setButtonIcon(Button button, String path) {
        ImageView imageView = new ImageView(path);
        imageView.setFitWidth(22);
        imageView.setFitHeight(22);
        button.setGraphic(imageView);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtonIcon(buttonSelect, "resources/Big/select.png");
        setButtonIcon(buttonFreePen, "resources/Big/pen.png");
        setButtonIcon(buttonLine, "resources/Big/line.png");
        setButtonIcon(buttonRectangle, "resources/Big/rectangle.png");
        setButtonIcon(buttonCircle, "resources/Big/circle.png");
        setButtonIcon(buttonEllipse, "resources/Big/ellipse.png");
        setButtonIcon(buttonText, "resources/Big/text.png");
        setButtonIcon(buttonClear, "resources/Big/clear.png");
        {
            ImageView imageView = new ImageView("resources/Big/fill.png");
            imageView.setFitWidth(22);
            imageView.setFitHeight(22);
            buttonFill.setGraphic(imageView);
        }
        lineChoiceBox.getItems().addAll("Solid Line", "Dash Line", "Point Line");
        lineChoiceBox.setValue("Solid Line");
    }

    public static ControllerAdapter getInstance() {
        return instance;
    }

    public void newFile() {
        tabPane = _tabPane;
        LayersControl.getInstance().createNewLayer();
    }

    public void tabChange() {
        LayersControl.getInstance().tabChange();
    }

    public void saveFile() throws IOException {
        LayersControl.getInstance().getLayerGroup().saveFile();
    }

    public void saveFileAs() throws IOException {
        LayersControl.getInstance().getLayerGroup().saveFileAs();
    }

    public void openFile() throws IOException, ClassNotFoundException {
        // ask for fileName
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File...");
        FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("JPaint File", "*.jpf");
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setSelectedExtensionFilter(filter);
        File file = fileChooser.showOpenDialog(MainFrame.mainStage);
        if(file==null)return;
        String filename = file.getAbsolutePath();

        LayerGroup layerGroup=LayerGroup.readFile(filename);
        tabPane = _tabPane;
        LayersControl.getInstance().createNewLayer(layerGroup);
        LayersControl.getInstance().repaint();
    }

    // --- toolbox buttons callbacks ---

    static boolean doFill = false;

    public void toggleFill() {
        doFill = !doFill;
        buttonFill.setText(doFill ? "Fill On" : "Fill Off");
    }

    public Layer.LineType getLineType() {
        return switch (lineChoiceBox.getValue()) {
            case "Solid Line" -> Layer.LineType.FULL;
            case "Dash Line" -> Layer.LineType.DASH;
            case "Point Line" -> Layer.LineType.POINT;
            default -> null;
        };
    }

    public float getLineWidth() {
        return (float) lineWidthSlider.getValue();
    }

    public void chooseColor() {
        CanvasController.color = colorPicker.getValue();
    }

    public void buttonSelectClicked() {
        input_status = Input_status.SELECT;
        labelStatus.setText("Select Tool");
    }

    public void buttonFreePenClicked() {
        input_status = Input_status.PEN;
        labelStatus.setText("Free Pen Tool");
    }

    public void buttonLineClicked() {
        input_status = Input_status.LINE;
        labelStatus.setText("Line Tool");
    }

    public void buttonRectangleClicked() {
        input_status = Input_status.RECTANGLE;
        labelStatus.setText("Rectangle Tool");
    }

    public void buttonCircleClicked() {
        input_status = Input_status.CIRCLE;
        labelStatus.setText("Circle Tool");
    }

    public void buttonEllipseClicked() {
        input_status = Input_status.ELLIPSE;
        labelStatus.setText("Ellipse Tool");
    }

    public void buttonTextClicked() {
        input_status = Input_status.TEXT;
        labelStatus.setText("Text Tool");
    }

    public void buttonClearClicked() {
        LayersControl.getInstance().getLayerGroup().clear();
        LayersControl.getInstance().repaint();
        labelStatus.setText("Cleared");
    }

}
