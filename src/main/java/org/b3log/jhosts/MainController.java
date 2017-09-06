package org.b3log.jhosts;

import com.jfoenix.controls.JFXDrawer;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

import javax.annotation.PostConstruct;

@ViewController(value = "/hxml/Main.fxml", title = "Material Design Example")
public final class MainController {

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    private StackPane root;

    @FXML
    private JFXDrawer drawer;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() throws Exception {
        context = new ViewFlowContext();
        Flow innerFlow = new Flow(ContentController.class);

        final FlowHandler flowHandler = innerFlow.createHandler(context);
        context.register("ContentFlowHandler", flowHandler);
        context.register("ContentFlow", innerFlow);
        drawer.setContent(flowHandler.start());
        context.register("ContentPane", drawer.getContent().get(0));
    }
}
