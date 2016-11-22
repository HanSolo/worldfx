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
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.util.List;

import static javafx.scene.input.MouseEvent.MOUSE_ENTERED;
import static javafx.scene.input.MouseEvent.MOUSE_EXITED;
import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;
import static javafx.scene.input.MouseEvent.MOUSE_RELEASED;


/**
 * User: hansolo
 * Date: 20.09.16
 * Time: 12:20
 */
@DefaultProperty("children")
public class WorldLowRes extends World {
    private static final double PREFERRED_WIDTH  = 1009;
    private static final double PREFERRED_HEIGHT = 665;


    // ******************** Constructors **************************************
    public WorldLowRes() {
        super();
    }


    // ******************** Initialization ************************************
    protected void initGraphics() {
        if (Double.compare(getPrefWidth(), 0.0) <= 0 || Double.compare(getPrefHeight(), 0.0) <= 0 ||
            Double.compare(getWidth(), 0.0) <= 0 || Double.compare(getHeight(), 0.0) <= 0) {
            if (getPrefWidth() > 0 && getPrefHeight() > 0) {
                setPrefSize(getPrefWidth(), getPrefHeight());
            } else {
                setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
            }
        }

        getStyleClass().add("world");

        Color fill   = getFillColor();
        Color stroke = getStrokeColor();

        for(CountryLowRes country : CountryLowRes.values()) {
            List<CountryPath> paths = country.getPaths();
            pane.getChildren().addAll(paths);

            countryPaths.put(country.name(), paths);

            for(CountryPath path : paths) {
                path.setFill(null == country.getColor() ? fill : country.getColor());
                path.setStroke(stroke);
                path.setStrokeWidth(0.5);
                path.setOnMouseEntered(_mouseEnterHandler);
                path.setOnMousePressed(_mousePressHandler);
                path.setOnMouseReleased(_mouseReleaseHandler);
                path.setOnMouseExited(_mouseExitHandler);
            }
        }

        group.getChildren().add(pane);

        getChildren().setAll(group);

        setBackground(new Background(new BackgroundFill(getBackgroundColor(), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    protected void handleMouseEvent(final MouseEvent EVENT, final EventHandler<MouseEvent> HANDLER) {
        final CountryPath COUNTRY_PATH = (CountryPath) EVENT.getSource();
        final String      COUNTRY_NAME = COUNTRY_PATH.getName();
        final Country     COUNTRY      = CountryLowRes.valueOf(COUNTRY_NAME);

        final EventType TYPE = EVENT.getEventType();
        if (MOUSE_ENTERED == TYPE) {
            if (isSelectionEnabled() && COUNTRY.equals(getSelectedCountry())) {
                for (SVGPath path : COUNTRY.getPaths()) { path.setFill(getSelectedColor()); }
            } else {
                for (SVGPath path : COUNTRY.getPaths()) { path.setFill(getHoverColor()); }
            }
        } else if (MOUSE_PRESSED == TYPE) {
            if (isSelectionEnabled() && null != getSelectedCountry()) {
                for (SVGPath path : CountryHighRes.valueOf(getSelectedCountry().getName()).getPaths()) { path.setFill(getFillColor()); }
            } else {
                for (SVGPath path : COUNTRY.getPaths()) { path.setFill(getPressedColor()); }
            }
        } else if (MOUSE_RELEASED == TYPE) {
            if (isSelectionEnabled()) {
                setSelectedCountry(COUNTRY);
                for (SVGPath path : COUNTRY.getPaths()) { path.setFill(getSelectedColor()); }
            } else {
                for (SVGPath path : COUNTRY.getPaths()) { path.setFill(getHoverColor()); }
            }
        } else if (MOUSE_EXITED == TYPE) {
            if (isSelectionEnabled() && COUNTRY.equals(getSelectedCountry())) {
                for (SVGPath path : COUNTRY.getPaths()) { path.setFill(getSelectedColor()); }
            } else {
                for (SVGPath path : COUNTRY.getPaths()) { path.setFill(getFillColor()); }
            }
        }

        if (null != HANDLER) HANDLER.handle(EVENT);
    }

    protected void setFillAndStroke() {
        for (CountryLowRes country : CountryLowRes.values()) {
            for (CountryPath path : country.getPaths()) {
                path.setFill(null == country.getColor() ? getFillColor() : country.getColor());
                path.setStroke(getStrokeColor());
            }
        }
    }
}
