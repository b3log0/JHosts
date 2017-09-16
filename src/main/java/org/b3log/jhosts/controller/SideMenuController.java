package org.b3log.jhosts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.effects.JFXDepthManager;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@ViewController(value = "/hxml/ui/SideMenu.fxml", title = "Material Design Example")
public class SideMenuController {
    @FXMLViewFlowContext
    private ViewFlowContext context;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private JFXMasonryPane masonryPane;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
        ArrayList<Node> children = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            StackPane child = new StackPane();
            double width = 100;
            child.setPrefWidth(width);
            double height = 50;
            child.setPrefHeight(height);
            JFXDepthManager.setDepth(child, 1);
            children.add(child);
            // create content
            StackPane header = new StackPane();
            String headerColor = getDefaultColor(i % 12);
            header.setStyle("-fx-background-radius: 5 5 5 5; -fx-background-color: " + headerColor);
            VBox.setVgrow(header, Priority.ALWAYS);
            VBox content = new VBox();
            content.getChildren().addAll(header);
            JFXButton button = new JFXButton();
            button.setButtonType(JFXButton.ButtonType.RAISED);
            button.setPrefHeight(height);
            button.setText("hello alsdjflasjf");
            child.getChildren().addAll(content, button);
        }
        masonryPane.getChildren().addAll(children);
    }

    private String getDefaultColor(int i) {
        String color = "#FFFFFF";
        switch (i) {
            case 0:
                color = "#8F3F7E";
                break;
            case 1:
                color = "#B5305F";
                break;
            case 2:
                color = "#CE584A";
                break;
            case 3:
                color = "#DB8D5C";
                break;
            case 4:
                color = "#DA854E";
                break;
            case 5:
                color = "#E9AB44";
                break;
            case 6:
                color = "#FEE435";
                break;
            case 7:
                color = "#99C286";
                break;
            case 8:
                color = "#01A05E";
                break;
            case 9:
                color = "#4A8895";
                break;
            case 10:
                color = "#16669B";
                break;
            case 11:
                color = "#2F65A5";
                break;
            case 12:
                color = "#4E6A9C";
                break;
            default:
                break;
        }
        return color;
    }
}
