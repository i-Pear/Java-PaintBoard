package controller;

import Layers.Layer;
import Layers.LayerGroup;
import Main.MainFrame;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import Main.LayersControl;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
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
    public Button buttonSelect, buttonFreePen, buttonLine, buttonRectangle, buttonCircle, buttonEllipse, buttonText, buttonClear,buttonUndo,buttonRedo;

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

    public void export(){
        // ask for fileName
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export as...");
        fileChooser.setInitialFileName("New File.png");
        FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("PNG File", "*.png");
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setSelectedExtensionFilter(filter);
        File file = fileChooser.showSaveDialog(MainFrame.mainStage);
        if(file==null)return;

        WritableImage image=LayersControl.getInstance().getSnapshot();

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        }
        catch (Exception ignored) {}
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

    public void refreshHistoryButton(){
        buttonUndo.setDisable(!LayersControl.getInstance().getLayerHistory().canUndo());
        buttonRedo.setDisable(!LayersControl.getInstance().getLayerHistory().canRedo());
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
        LayersControl.getInstance().getLayerHistory().forward("Clear");
        LayersControl.getInstance().getLayerGroup().clear();
        LayersControl.getInstance().repaint();
        labelStatus.setText("Cleared");
    }

    public void undoButtonClicked(){
        LayersControl.getInstance().getLayerHistory().undo();
        LayersControl.getInstance().repaint();
        refreshHistoryButton();
    }

    public void redoButtonClicked(){
        LayersControl.getInstance().getLayerHistory().redo();
        LayersControl.getInstance().repaint();
        refreshHistoryButton();
    }

}
