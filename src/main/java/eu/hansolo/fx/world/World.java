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

import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;


/**
 * Created by hansolo on 20.11.16.
 */
public interface World {
    Map<String, List<CountryPath>> getCountryPaths();

    void setMouseEnterHandler(EventHandler<MouseEvent> HANDLER);
    void setMousePressHandler(EventHandler<MouseEvent> HANDLER);
    void setMouseReleaseHandler(EventHandler<MouseEvent> HANDLER);
    void setMouseExitHandler(EventHandler<MouseEvent> HANDLER);

    Color getBackgroundColor();
    void setBackgroundColor(Color COLOR);
    ObjectProperty<Color> backgroundColorProperty();

    Color getFillColor();
    void setFillColor(Color COLOR);
    ObjectProperty<Color> fillColorProperty();

    Color getStrokeColor();
    void setStrokeColor(Color COLOR);
    ObjectProperty<Color> strokeColorProperty();

    Color getHoverColor();
    void setHoverColor(Color COLOR);
    ObjectProperty<Color> hoverColorProperty();

    Color getPressedColor();
    void setPressedColor(Color COLOR);
    ObjectProperty<Color> pressedColorProperty();

    Color getLocationColor();
    void setLocationColor(Color COLOR);
    ObjectProperty<Color> locationColorProperty();


    void addLocation(Location LOCATION);
    void removeLocation(Location LOCATION);

    void addLocations(Location... LOCATIONS);
    void clearLocations();

    void showLocations(boolean SHOW);
}
