package org.b3log.jhosts;

import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.annotation.PostConstruct;

@ViewController(value = "/hxml/ui/ListView.fxml", title = "Material Design Example")
public class AllHostController{
    @FXML
    protected JFXListView<Label> groupList;
    @FXML
    protected JFXListView<?> subList;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
        groupList.depthProperty().set(1);
        FileService fileService = new FileServiceImpl();
        for(String group : fileService.getGroup().subList(1,10)){
            groupList.getItems().add(new Label(group));
        }
    }
}
