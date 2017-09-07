package org.b3log.jhosts;

import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;

import javax.annotation.PostConstruct;

@ViewController(value = "/hxml/ui/ListView.fxml", title = "Material Design Example")
public class UatHostController{
    @FXML
    protected JFXListView<?> list1;
    @FXML
    protected JFXListView<?> subList;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
        list1.depthProperty().set(1);
    }
}
