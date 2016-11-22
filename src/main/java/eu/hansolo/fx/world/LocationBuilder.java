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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.Ikon;

import java.util.HashMap;


/**
 * Created by hansolo on 22.11.16.
 */
public class LocationBuilder<B extends LocationBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected LocationBuilder() {}


    // ******************** Methods *******************************************
    public static final LocationBuilder create() { return new LocationBuilder(); }

    public final B name(final String NAME) {
        properties.put("name", new SimpleStringProperty(NAME));
        return (B)this;
    }

    public final B latitude(final double LATITUDE) {
        properties.put("latitude", new SimpleDoubleProperty(LATITUDE));
        return (B)this;
    }

    public final B longitude(final double LONGITUDE) {
        properties.put("longitude", new SimpleDoubleProperty(LONGITUDE));
        return (B)this;
    }

    public final B info(final String INFO) {
        properties.put("info", new SimpleStringProperty(INFO));
        return (B)this;
    }

    public final B color(final Color COLOR) {
        properties.put("color", new SimpleObjectProperty(COLOR));
        return (B)this;
    }

    public final B iconCode(final Ikon CODE) {
        properties.put("iconCode", new SimpleObjectProperty(CODE));
        return (B)this;
    }

    public final B iconSize(final int SIZE) {
        properties.put("iconSize", new SimpleIntegerProperty(SIZE));
        return (B)this;
    }

    public final B mouseEnterHandler(final EventHandler<MouseEvent> HANDLER) {
        properties.put("mouseEnterHandler", new SimpleObjectProperty(HANDLER));
        return (B)this;
    }

    public final B mousePressHandler(final EventHandler<MouseEvent> HANDLER) {
        properties.put("mousePressHandler", new SimpleObjectProperty(HANDLER));
        return (B)this;
    }

    public final B mouseReleaseHandler(final EventHandler<MouseEvent> HANDLER) {
        properties.put("mouseReleaseHandler", new SimpleObjectProperty(HANDLER));
        return (B)this;
    }

    public final B mouseExitHandler(final EventHandler<MouseEvent> HANDLER) {
        properties.put("mouseExitHandler", new SimpleObjectProperty(HANDLER));
        return (B)this;
    }
    

    public final Location build() {
        final Location LOCATION = new Location();

        for (String key : properties.keySet()) {
            if ("name".equals(key)) {
                LOCATION.setName(((StringProperty) properties.get(key)).get());
            } else if ("latitude".equals(key)) {
                LOCATION.setLatitude(((DoubleProperty) properties.get(key)).get());
            } else if ("longitude".equals(key)) {
                LOCATION.setLongitude(((DoubleProperty) properties.get(key)).get());
            } else if ("info".equals(key)) {
                LOCATION.setInfo(((StringProperty) properties.get(key)).get());
            } else if ("color".equals(key)) {
                LOCATION.setColor(((ObjectProperty<Color>) properties.get(key)).get());
            } else if ("iconCode".equals(key)) {
                LOCATION.setIconCode(((ObjectProperty<Ikon>) properties.get(key)).get());
            } else if ("iconSize".equals(key)) {
                LOCATION.setIconSize(((IntegerProperty) properties.get(key)).get());
            } else if ("mouseEnterHandler".equals(key)) {
                LOCATION.setMouseEnterHandler(((ObjectProperty<EventHandler<MouseEvent>>) properties.get(key)).get());
            } else if ("mousePressHandler".equals(key)) {
                LOCATION.setMousePressHandler(((ObjectProperty<EventHandler<MouseEvent>>) properties.get(key)).get());
            } else if ("mouseReleaseHandler".equals(key)) {
                LOCATION.setMouseReleaseHandler(((ObjectProperty<EventHandler<MouseEvent>>) properties.get(key)).get());
            } else if ("mouseExitHandler".equals(key)) {
                LOCATION.setMouseExitHandler(((ObjectProperty<EventHandler<MouseEvent>>) properties.get(key)).get());
            }
            
        }
        return LOCATION;
    }
}
