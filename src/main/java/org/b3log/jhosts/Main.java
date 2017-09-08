package org.b3log.jhosts;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyphLoader;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.b3log.jhosts.controller.MainController;

import java.io.IOException;

/**
 * Author: Zhang Yu
 * Date: 17年9月6日
 * Email: yu.zhang@7fresh.com
 */
//@SpringBootApplication
public class Main extends Application /*implements CommandLineRunner*/ {
    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    public static void main(String[] args){
//        SpringApplication.run(Main.class,args);
        Main.launch(args);
    }

//    @Override
//    public void run(String... strings) throws Exception {
//        Main.launch(strings);
//    }

    private static final String GROUP = "组";
    private static final String IP_ADDRESS = "IP地址";
    private static final String DOMAIN_NAME = "域名";

    @Override
    public void start(Stage stage) throws Exception {
//        new Thread(() -> {
//            try {
//                SVGGlyphLoader.loadGlyphsFont(Main.class.getResourceAsStream("/fonts/icomoon.svg"),
//                        "icomoon.svg");
//            } catch (IOException ioExc) {
//                ioExc.printStackTrace();
//            }
//        }).start();

        Flow flow = new Flow(MainController.class);
        DefaultFlowContainer container = new DefaultFlowContainer();
        flowContext = new ViewFlowContext();
        flowContext.register("Stage", stage);
        flow.createHandler(flowContext).start(container);

        JFXDecorator decorator = new JFXDecorator(stage, container.getView());
        decorator.setCustomMaximize(true);

        double width = 700;
        double height = 200;
        try {
            Rectangle2D bounds = Screen.getScreens().get(0).getBounds();
            width = bounds.getWidth() / 2.5;
            height = bounds.getHeight() / 1.35;
        }catch (Exception e){ }

        Scene scene = new Scene(decorator, width, height);
        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(Main.class.getResource("/css/jfoenix-fonts.css").toExternalForm(),
                Main.class.getResource("/css/jfoenix-design.css").toExternalForm(),
                Main.class.getResource("/css/jhosts-main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


}
