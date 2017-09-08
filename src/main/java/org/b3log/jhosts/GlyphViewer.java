package org.b3log.jhosts;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.svg.SVGGlyph;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Author: Zhang Yu
 * Date: 17年9月8日
 * Email: yu.zhang@7fresh.com
 */
public class GlyphViewer extends VBox{
    private static final int MIN_ICON_SIZE = 8;
    private static final int DEFAULT_ICON_SIZE = 128;
    private static final int MAX_ICON_SIZE = 256;

    private final ObjectProperty<SVGGlyph> glyph = new SimpleObjectProperty<>();

    public GlyphViewer() {
        GridPane details = new GridPane();
        details.setHgap(10);
        details.setVgap(10);
        details.setPadding(new Insets(24));
    }

    public final SVGGlyph getGlyph() {
        return glyph.get();
    }

    final ObjectProperty<SVGGlyph> glyphProperty() {
        return glyph;
    }
}
