package org.b3log.jhosts;

import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.commons.beanutils.BeanUtils;

import javax.annotation.PostConstruct;

@ViewController(value = "/hxml/ui/Content.fxml", title = "Material Design Example")
public class ContentController {
    @FXML
    private JFXListView<Label> hostGroup;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
        hostGroup.getItems().add(new Label("Zephyr 1"));
        hostGroup.getItems().add(new Label("Zephyr 2"));
        hostGroup.getItems().add(new Label("Zephyr 3"));
        hostGroup.depthProperty().set(1);
        hostGroup.expandedProperty().set(true);
    }
}
