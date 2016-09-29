/*
 * Copyright (c) 2016 by Gerrit Grunwald
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.hansolo.fx.world;

import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * User: hansolo
 * Date: 20.09.16
 * Time: 12:20
 */
@DefaultProperty("children")
public class World extends Region {
    private static final double                   EARTH_RADIUS     = 6_371_000;
    private static final double                   PREFERRED_WIDTH  = 1009;
    private static final double                   PREFERRED_HEIGHT = 665;
    private static final double                   MINIMUM_WIDTH    = 100;
    private static final double                   MINIMUM_HEIGHT   = 66;
    private static final double                   MAXIMUM_WIDTH    = 2018;
    private static final double                   MAXIMUM_HEIGHT   = 1330;
    private static final Color                    FILL_COLOR       = Color.web("d9d9dc");
    private static final Color                    STROKE_COLOR     = Color.BLACK;
    private static final Color                    HOVER_COLOR      = Color.web("#456acf");
    private static final Color                    SELECTION_COLOR  = Color.web("#ef6050");
    private static final double                   ASPECT_RATIO = PREFERRED_HEIGHT / PREFERRED_WIDTH;
    private              double                   width;
    private              double                   height;
    private              Pane                     pane;
    private              Map<String, List<CountryPath>> countryPaths;
    private              ScalableContentPane      scalableContentPane;
    private              EventHandler<MouseEvent> mouseEnterHandler;
    private              EventHandler<MouseEvent> mousePressHandler;
    private              EventHandler<MouseEvent> mouseReleaseHandler;
    private              EventHandler<MouseEvent> mouseExitHandler;


    // ******************** Constructors **************************************
    public World() {
        getStylesheets().add(World.class.getResource("world.css").toExternalForm());
        countryPaths = new HashMap<>();
        mouseEnterHandler = evt -> {
            CountryPath countryPath = (CountryPath) evt.getSource();
            for(CountryPath path : countryPaths.get(countryPath.getName())) { path.setFill(HOVER_COLOR); }
        };
        mousePressHandler = evt -> {
            if (evt.getClickCount() == 2) {
                // DoubleClick -> Zoom in at that area
                System.out.println("Double Click");
            } else {
                CountryPath countryPath = (CountryPath) evt.getSource();
                Locale      locale      = countryPath.getLocale();
                System.out.println(locale.getDisplayCountry() + " (" + locale.getISO3Country() + ")");
                System.out.println((int) Country.valueOf(countryPath.getName()).value + " million people");
                for (SVGPath path : countryPaths.get(countryPath.getName())) { path.setFill(SELECTION_COLOR); }
            }
        };
        mouseReleaseHandler = evt -> {
            CountryPath countryPath = (CountryPath) evt.getSource();
            for(SVGPath path : countryPaths.get(countryPath.getName())) { path.setFill(HOVER_COLOR); }
        };
        mouseExitHandler = evt -> {
            CountryPath countryPath = (CountryPath) evt.getSource();
            for(SVGPath path : countryPaths.get(countryPath.getName())) { path.setFill(FILL_COLOR); }
        };
        initGraphics();
        registerListeners();
    }


    // ******************** Initialization ************************************
    private void initGraphics() {
        if (Double.compare(getPrefWidth(), 0.0) <= 0 || Double.compare(getPrefHeight(), 0.0) <= 0 ||
            Double.compare(getWidth(), 0.0) <= 0 || Double.compare(getHeight(), 0.0) <= 0) {
            if (getPrefWidth() > 0 && getPrefHeight() > 0) {
                setPrefSize(getPrefWidth(), getPrefHeight());
            } else {
                setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
            }
        }

        getStyleClass().add("world");

        pane = new Pane();
        for(Country country : Country.values()) {
            // Add children to pane
            List<CountryPath> paths=country.getPaths();
            pane.getChildren().addAll(paths);

            // Add to map
            countryPaths.put(country.name(), paths);

            // Attach mouse handlers
            for(CountryPath path : paths) {
                setFillAndStroke(path, FILL_COLOR, STROKE_COLOR);
                path.setOnMouseEntered(mouseEnterHandler);
                path.setOnMousePressed(mousePressHandler);
                path.setOnMouseReleased(mouseReleaseHandler);
                path.setOnMouseExited(mouseExitHandler);
            }
        }
        pane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        scalableContentPane = new ScalableContentPane();
        scalableContentPane.setContent(pane);

        getChildren().setAll(scalableContentPane);

        setBackground(new Background(new BackgroundFill(Color.web("#3f3f4f"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
    }


    // ******************** Methods *******************************************
    @Override protected double computeMinWidth(final double HEIGHT)  { return MINIMUM_WIDTH; }
    @Override protected double computeMinHeight(final double WIDTH)  { return MINIMUM_HEIGHT; }
    @Override protected double computePrefWidth(final double HEIGHT) { return super.computePrefWidth(HEIGHT); }
    @Override protected double computePrefHeight(final double WIDTH) { return super.computePrefHeight(WIDTH); }
    @Override protected double computeMaxWidth(final double HEIGHT)  { return MAXIMUM_WIDTH; }
    @Override protected double computeMaxHeight(final double WIDTH)  { return MAXIMUM_HEIGHT; }

    @Override public ObservableList<Node> getChildren() { return super.getChildren(); }

    public Map<String, List<CountryPath>> getCountryPaths() { return countryPaths; }

    private void setFillAndStroke(final CountryPath PATH, final Color FILL, final Color STROKE) {
        PATH.setFill(FILL);
        PATH.setStrokeWidth(0.5);
        PATH.setStroke(STROKE);
    }


    // ******************** Resizing ******************************************
    private void resize() {
        width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();

        if (ASPECT_RATIO * width > height) {
            width = 1 / (ASPECT_RATIO / height);
        } else if (1 / (ASPECT_RATIO / height) > width) {
            height = ASPECT_RATIO * width;
        }

        if (width > 0 && height > 0) {
            pane.setCache(true);
            pane.setCacheHint(CacheHint.SCALE);

            scalableContentPane.setMaxSize(width, height);
            scalableContentPane.setPrefSize(width, height);
            scalableContentPane.relocate((getWidth() - width) * 0.5, (getHeight() - height) * 0.5);

            pane.setCache(false);
        }
    }
}
