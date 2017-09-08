package org.b3log.jhosts.controller;

import com.jfoenix.controls.JFXListView;
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
import org.b3log.jhosts.service.FileService;

import javax.annotation.PostConstruct;
import java.util.Objects;

@ViewController(value = "/hxml/ui/SideMenu.fxml", title = "Material Design Example")
public class SideMenuController {
    @FXMLViewFlowContext
    private ViewFlowContext context;
    //TODO 预设五种环境，这些host总会写入到同一个文件中，只不过右侧将对五中环境进行简化显示
    @FXML
    @ActionTrigger("all")
    private Label all;
    @FXML
    @ActionTrigger("product")
    private Label product;
    @FXML
    @ActionTrigger("uat")
    private Label uat;
    @FXML
    @ActionTrigger("test")
    private Label test;
    @FXML
    @ActionTrigger("local")
    private Label local;
    @FXML
    private JFXListView<Label> sideList;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
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
        bindNodeToController(all, AllHostController.class, contentFlow, contentFlowHandler);
        bindNodeToController(product, ProdHostController.class, contentFlow, contentFlowHandler);
        bindNodeToController(uat, UatHostController.class, contentFlow, contentFlowHandler);
        bindNodeToController(test, TestHostController.class, contentFlow, contentFlowHandler);
        bindNodeToController(local, LocalHostController.class, contentFlow, contentFlowHandler);
    }
    private void bindNodeToController(Node node, Class<?> controllerClass, Flow flow, FlowHandler flowHandler) {
        flow.withGlobalLink(node.getId(), controllerClass);
    }
}
