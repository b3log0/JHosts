package org.b3log.jhosts.controller;

import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.b3log.jhosts.service.FileService;

import javax.annotation.PostConstruct;
import java.util.Objects;

@ViewController(value = "/hxml/ui/ListView.fxml", title = "Material Design Example")
public class AllHostController extends BaseController{

    @FXML
    protected JFXListView<?> groupList;
    @FXML
    protected JFXListView<?> subList;
    @FXML
    protected JFXListView<?> subList1;
    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
        groupList.depthProperty().set(1);

        /*for(String group : fileService.getGroup()){
            groupList.getItems().add(new Label(group));
            JFXListView<Label> subList = new JFXListView<>();
            subList.getItems().add(new Label("Test1"));
            subList.getItems().add(new Label("Test2"));
            subList.getItems().add(new Label("Test3"));
            groupList.getItems().add(subList);
        }*/
    }
}
