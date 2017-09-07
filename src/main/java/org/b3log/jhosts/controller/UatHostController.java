package org.b3log.jhosts.controller;

import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import org.b3log.jhosts.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@ViewController(value = "/hxml/ui/ListView.fxml", title = "Material Design Example")
public class UatHostController{
    @FXML
    protected JFXListView<?> list1;
    @FXML
    protected JFXListView<?> subList;
    @Autowired
    FileService fileService;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
        list1.depthProperty().set(1);
    }
}
