package org.b3log.jhosts;

import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.beanutils.BeanUtils;

import javax.annotation.PostConstruct;
import java.util.List;

@ViewController(value = "/hxml/ui/Content.fxml", title = "Material Design Example")
public class ContentController {
    @FXML
    private JFXListView<Label> hostGroup;

    @FXML
    private AnchorPane anchorPane;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
        FileService fileService = new FileServiceImpl();
        List<String> groupList = fileService.getGroup();
        for (String group : groupList) {
            hostGroup.getItems().add(new Label(group));
        }
//        anchorPane.setMaxHeight(300);
//        hostGroup.depthProperty().set(1);
//        hostGroup.expandedProperty().set(true);
    }
}
