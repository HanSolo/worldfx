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


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.Ikon;


/**
 * Created by hansolo on 20.11.16.
 */
public class Location {
    private static final double                   EARTH_RADIUS      = 6_371_000; // [m]
    private static final int                      DEFAULT_ICON_SIZE = 12;
    private              String                   name;
    private              double                   latitude;
    private              double                   longitude;
    private              String                   info;
    private              Color                    color;
    private              Ikon                     iconCode;
    private              int                      iconSize;
    private              EventHandler<MouseEvent> mouseEnterHandler;
    private              EventHandler<MouseEvent> mousePressHandler;
    private              EventHandler<MouseEvent> mouseReleaseHandler;
    private              EventHandler<MouseEvent> mouseExitHandler;


    // ******************** Constructors **************************************
    public Location() {
        this("", 0, 0, "", null, null, DEFAULT_ICON_SIZE);
    }
    public Location(final String NAME) {
        this(NAME, 0, 0, "", null, null, DEFAULT_ICON_SIZE);
    }
    public Location(final String NAME, final int ICON_SIZE) {
        this(NAME, 0, 0, "", null, null, ICON_SIZE);
    }
    public Location(final double LATITUDE, final double LONGITUDE) {
        this("", LATITUDE, LONGITUDE, "", null, null, DEFAULT_ICON_SIZE);
    }
    public Location(final double LATITUDE, final double LONGITUDE, final int ICON_SIZE) {
        this("", LATITUDE, LONGITUDE, "", null, null, ICON_SIZE);
    }
    public Location(final double LATITUDE, final double LONGITUDE, final Ikon ICON_CODE) {
        this("", LATITUDE, LONGITUDE, "", null, ICON_CODE, DEFAULT_ICON_SIZE);
    }
    public Location(final double LATITUDE, final double LONGITUDE, final Ikon ICON_CODE, final int ICON_SIZE) {
        this("", LATITUDE, LONGITUDE, "", null, ICON_CODE, ICON_SIZE);
    }
    public Location(final double LATITUDE, final double LONGITUDE, final Color COLOR ,final Ikon ICON_CODE) {
        this("", LATITUDE, LONGITUDE, "", COLOR, ICON_CODE, DEFAULT_ICON_SIZE);
    }
    public Location(final double LATITUDE, final double LONGITUDE, final Color COLOR ,final Ikon ICON_CODE, final int ICON_SIZE) {
        this("", LATITUDE, LONGITUDE, "", COLOR, ICON_CODE, ICON_SIZE);
    }
    public Location(final String NAME, final double LATITUDE, final double LONGITUDE) {
        this(NAME, LATITUDE, LONGITUDE, "", null, null, DEFAULT_ICON_SIZE);
    }
    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final int ICON_SIZE) {
        this(NAME, LATITUDE, LONGITUDE, "", null, null, ICON_SIZE);
    }
    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final Ikon ICON_CODE) {
        this(NAME, LATITUDE, LONGITUDE, "", null, ICON_CODE, DEFAULT_ICON_SIZE);
    }
    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final Ikon ICON_CODE, final int ICON_SIZE) {
        this(NAME, LATITUDE, LONGITUDE, "", null, ICON_CODE, ICON_SIZE);
    }
    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final Color COLOR) {
        this(NAME, LATITUDE, LONGITUDE, "", COLOR, null, DEFAULT_ICON_SIZE);
    }
    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final Color COLOR, final int ICON_SIZE) {
        this(NAME, LATITUDE, LONGITUDE, "", COLOR, null, ICON_SIZE);
    }
    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final Color COLOR, final Ikon ICON_CODE) {
        this(NAME, LATITUDE, LONGITUDE, "", COLOR, ICON_CODE, DEFAULT_ICON_SIZE);
    }
    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final Color COLOR, final Ikon ICON_CODE, final int ICON_SIZE) {
        this(NAME, LATITUDE, LONGITUDE, "", COLOR, ICON_CODE, ICON_SIZE);
    }
    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final String INFO, final Color COLOR, final Ikon ICON_CODE) {
        this(NAME, LATITUDE, LONGITUDE, INFO, COLOR, ICON_CODE, DEFAULT_ICON_SIZE);
    }
    public Location(final String NAME, final double LATITUDE, final double LONGITUDE, final String INFO, final Color COLOR, final Ikon ICON_CODE, final int ICON_SIZE) {
        name      = NAME;
        latitude  = LATITUDE;
        longitude = LONGITUDE;
        info      = INFO;
        color     = COLOR;
        iconCode  = ICON_CODE;
        iconSize  = ICON_SIZE;
    }


    // ******************** Methods *******************************************
    public String getName() { return name; }
    public void setName(final String NAME) { name = NAME; }

    public double getLatitude() { return latitude; }
    public void setLatitude(final double LATITUDE) { latitude = LATITUDE; }

    public double getLongitude() { return longitude; }
    public void setLongitude(final double LONGITUDE) { longitude = LONGITUDE; }

    public String getInfo() { return info; }
    public void setInfo(final String INFO) { info = INFO; }

    public Color getColor() { return color; }
    public void setColor(final Color COLOR) { color = COLOR; }

    public Ikon getIconCode() { return iconCode; }
    public void setIconCode(final Ikon ICON_CODE) { iconCode = ICON_CODE; }

    public int getIconSize() { return iconSize; }
    public void setIconSize(final int SIZE) { iconSize = clamp(6, 24, SIZE); }

    public EventHandler<MouseEvent> getMouseEnterHandler() { return mouseEnterHandler; }
    public void setMouseEnterHandler(final EventHandler<MouseEvent> HANDLER) { mouseEnterHandler = HANDLER; }

    public EventHandler<MouseEvent> getMousePressHandler() { return mousePressHandler; }
    public void setMousePressHandler(final EventHandler<MouseEvent> HANDLER) { mousePressHandler = HANDLER; }

    public EventHandler<MouseEvent> getMouseReleaseHandler() { return mouseReleaseHandler; }
    public void setMouseReleaseHandler(final EventHandler<MouseEvent> HANDLER) { mouseReleaseHandler = HANDLER;  }

    public EventHandler<MouseEvent> getMouseExitHandler() { return mouseExitHandler; }
    public void setMouseExitHandler(final EventHandler<MouseEvent> HANDLER) { mouseExitHandler = HANDLER; }

    public double getDistanceTo(final Location LOCATION) { return calcDistanceInMeter(this, LOCATION); }

    public double calcDistanceInMeter(final Location P1, final Location P2) {
        return calcDistanceInMeter(P1.getLatitude(), P1.getLongitude(), P2.getLatitude(), P2.getLongitude());
    }
    public double calcDistanceInKilometer(final Location P1, final Location P2) {
        return calcDistanceInMeter(P1, P2) / 1000.0;
    }
    public double calcDistanceInMeter(final double LAT_1, final double LON_1, final double LAT_2, final double LON_2) {
        final double LAT_1_RADIANS     = Math.toRadians(LAT_1);
        final double LAT_2_RADIANS     = Math.toRadians(LAT_2);
        final double DELTA_LAT_RADIANS = Math.toRadians(LAT_2-LAT_1);
        final double DELTA_LON_RADIANS = Math.toRadians(LON_2-LON_1);

        final double A = Math.sin(DELTA_LAT_RADIANS * 0.5) * Math.sin(DELTA_LAT_RADIANS * 0.5) + Math.cos(LAT_1_RADIANS) * Math.cos(LAT_2_RADIANS) * Math.sin(DELTA_LON_RADIANS * 0.5) * Math.sin(DELTA_LON_RADIANS * 0.5);
        final double C = 2 * Math.atan2(Math.sqrt(A), Math.sqrt(1-A));

        final double DISTANCE = EARTH_RADIUS * C;

        return DISTANCE;
    }

    private int clamp(final int MIN_VALUE, final int MAX_VALUE, final int VALUE) {
        if (VALUE < MIN_VALUE) return MIN_VALUE;
        if (VALUE > MAX_VALUE) return MAX_VALUE;
        return VALUE;
    }
}
