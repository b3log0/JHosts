package org.b3log.jhosts;

import com.jfoenix.controls.JFXListView;
import demos.gui.uicomponents.ButtonController;
import demos.gui.uicomponents.CheckboxController;
import demos.gui.uicomponents.ComboBoxController;
import demos.gui.uicomponents.DialogController;
import demos.gui.uicomponents.IconsController;
import demos.gui.uicomponents.ListViewController;
import demos.gui.uicomponents.MasonryPaneController;
import demos.gui.uicomponents.PickersController;
import demos.gui.uicomponents.PopupController;
import demos.gui.uicomponents.ProgressBarController;
import demos.gui.uicomponents.RadioButtonController;
import demos.gui.uicomponents.SVGLoaderController;
import demos.gui.uicomponents.ScrollPaneController;
import demos.gui.uicomponents.SliderController;
import demos.gui.uicomponents.SpinnerController;
import demos.gui.uicomponents.TextFieldController;
import demos.gui.uicomponents.ToggleButtonController;
import demos.gui.uicomponents.TreeTableViewController;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.beanutils.BeanUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@ViewController(value = "/hxml/ui/SideMenu.fxml", title = "Material Design Example")
public class SideMenuController {
    @FXMLViewFlowContext
    private ViewFlowContext context;

    //TODO 预设五种环境，这些host总会写入到同一个文件中，只不过右侧将对五中环境进行简化显示
    @FXML
    @ActionTrigger("profile0")
    private Label profile0;
    @FXML
    @ActionTrigger("profile1")
    private Label profile1;
    @FXML
    @ActionTrigger("profile1")
    private Label profile2;
    @FXML
    @ActionTrigger("profile1")
    private Label profile3;
    @FXML
    @ActionTrigger("profile1")
    private Label profile4;
    @FXML
    private JFXListView<Label> sideList;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
        FileService fileService = new FileServiceImpl();
        List<String> groupList = fileService.getGroup();
        for (String group : groupList) {
            sideList.getItems().add(new Label(group));
        }

        Objects.requireNonNull(context, "context");
        FlowHandler contentFlowHandler = (FlowHandler) context.getRegisteredObject("ContentFlowHandler");
        sideList.propagateMouseEventsToParent();
        sideList.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            new Thread(()->{
                Platform.runLater(()->{
                    if (newVal != null) {
                        try {
                            contentFlowHandler.handle(newVal.getId());
                        } catch (VetoException exc) {
                            exc.printStackTrace();
                        } catch (FlowException exc) {
                            exc.printStackTrace();
                        }
                    }
                });
            }).start();
        });
        Flow contentFlow = (Flow) context.getRegisteredObject("ContentFlow");
//        bindNodeToController(checkbox, CheckboxController.class, contentFlow, contentFlowHandler);
//        bindNodeToController(combobox, ComboBoxController.class, contentFlow, contentFlowHandler);
//        bindNodeToController(dialogs, DialogController.class, contentFlow, contentFlowHandler);
//        bindNodeToController(listview, ListViewController.class, contentFlow, contentFlowHandler);
//        bindNodeToController(radiobutton, RadioButtonController.class, contentFlow, contentFlowHandler);

    }
    private void bindNodeToController(Node node, Class<?> controllerClass, Flow flow, FlowHandler flowHandler) {
        flow.withGlobalLink(node.getId(), controllerClass);
    }
}
