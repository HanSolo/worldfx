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

import eu.hansolo.fx.world.World.Resolution;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Random;

import static eu.hansolo.fx.world.Country.*;
import static eu.hansolo.fx.world.Country.GB;
import static eu.hansolo.fx.world.Country.PL;


/**
 * User: hansolo
 * Date: 20.09.16
 * Time: 13:37
 */
public class Main extends Application {
    private static final Random        RND = new Random();
    private              World         world;
    private              CountryRegion europeanUnion;


    @Override public void init() {
        europeanUnion = new CountryRegion("EU", BE, GR, LT, PT, BG, ES, LU, RO, CZ, FR, HU, SI, DK, HR, MT, SK, DE, IT, NL, FI, EE, CY, AT, SE, IE, LV, PL, GB);
        //europeanUnion.setColor(Color.LIGHTBLUE);
        //for (Country country : europeanUnion.getCountries()) {
        //    country.setColor(Color.rgb(RND.nextInt(127) + 100, 0, RND.nextInt(127) + 128));
        //}

        BusinessRegion.EU.setColor(Color.rgb(124, 208, 255));
        //BusinessRegion.APAC.setColor(Color.LIGHTSALMON);

        world = WorldBuilder.create()
                            .resolution(Resolution.HI_RES)
                            //.backgroundColor(Color.web("#4aa9d7"))
                            //.fillColor(Color.web("#dcb36c"))
                            //.strokeColor(Color.web("#987028"))
                            //.hoverColor(Color.web("#fec47e"))
                            //.pressedColor(Color.web("#6cee85"))
                            //.locationColor(Color.web("#0000ff"))
                            //.selectedColor(Color.MAGENTA)
                            .locationIconCode(MaterialDesign.MDI_STAR)
                            .locations(new Location("SFO", 37.619751, -122.374366),
                                       new Location("YYC", 51.128148, -114.010791),
                                       new Location("ORD", 41.975806, -87.905294),
                                       new Location("YOW", 45.321867, -75.668200),
                                       new Location("JFK", 40.642660, -73.781232),
                                       new Location("GRU", -23.427337, -46.478853),
                                       new Location("RKV", 64.131830, -21.945686),
                                       new Location("MAD", 40.483162, -3.579211),
                                       new Location("CDG", 49.014162, 2.541908),
                                       new Location("LHR", 51.471125, -0.461951),
                                       LocationBuilder.create()
                                                      .name("FRA").latitude(50.040864).longitude(8.560409)
                                                      .color(Color.CRIMSON)
                                                      .iconCode(MaterialDesign.MDI_HEART)
                                                      .mouseEnterHandler(e -> ((FontIcon) e.getSource()).setFill(Color.CYAN))
                                                      .mousePressHandler(e -> {
                                                          System.out.println("Frankfurt Airport");
                                                          ((FontIcon) e.getSource()).setFill(Color.MAGENTA);
                                                      })
                                                      .mouseReleaseHandler(e -> ((FontIcon) e.getSource()).setFill(Color.CYAN))
                                                      .mouseExitHandler(e -> ((FontIcon) e.getSource()).setFill(Color.CRIMSON))
                                                      .build(),
                                       new Location("SVO", 55.972401, 37.412537),
                                       new Location("DEL", 28.555839, 77.100956),
                                       new Location("PEK", 40.077624, 116.605458),
                                       new Location("NRT", 35.766948, 140.385254),
                                       new Location("SYD", -33.939040, 151.174996))
                            .mousePressHandler(evt -> {
                                CountryPath countryPath = (CountryPath) evt.getSource();
                                Locale      locale      = countryPath.getLocale();
                                System.out.println(locale.getDisplayCountry() + " (" + locale.getISO3Country() + ")");
                                System.out.println(Country.valueOf(countryPath.getName()).getValue() + " million people");
                            })
                            .zoomEnabled(true)
                            .selectionEnabled(true)
                            .build();

        // Population per country in 2016
        Map<String, Double> data = new HashMap<>();
        addPopulationData(data);


        for (Country country : Country.values()) {
            try {
                String iso3Key = new Locale("", country.name()).getISO3Country();
                Double value   = data.get(iso3Key);
                country.setValue(new PopulationValueObject(null == value ? -1 : value));
            } catch (MissingResourceException e) {}
        }
    }

    private static class PopulationValueObject implements ValueObject {
        private final double value;

        private PopulationValueObject(final double VALUE) { value = VALUE; }

        @Override public String toString() { return String.valueOf((int) value); }
    }

    private void addPopulationData(final Map<String, Double> DATA) {
        DATA.put("AFG", 32.739);
        DATA.put("ALB", 2.885);
        DATA.put("DZA", 40.654);
        DATA.put("AGO", 25.868);
        DATA.put("ATG", 0.09);
        DATA.put("ARG", 43.564);
        DATA.put("ARM", 2.991);
        DATA.put("AUS", 24.434);
        DATA.put("AUT", 8.594);
        DATA.put("AZE", 9.492);
        DATA.put("BHS", 0.368);
        DATA.put("BHR", 1.319);
        DATA.put("BGD", 161.513);
        DATA.put("BRB", 0.28);
        DATA.put("BLR", 9.451);
        DATA.put("BEL", 11.434);
        DATA.put("BLZ", 0.371);
        DATA.put("BEN", 11.128);
        DATA.put("BTN", 0.791);
        DATA.put("BOL", 11.725);
        DATA.put("BIH", 3.854);
        DATA.put("BWA", 2.154);
        DATA.put("BRA", 206.082);
        DATA.put("BRN", 0.423);
        DATA.put("BGR", 7.126);
        DATA.put("BFA", 18.42);
        DATA.put("BDI", 9.648);
        DATA.put("CPV", 0.531);
        DATA.put("KHM", 15.776);
        DATA.put("CMR", 23.685);
        DATA.put("CAN", 36.188);
        DATA.put("CAF", 4.888);
        DATA.put("TCD", 11.855);
        DATA.put("CHL", 18.196);
        DATA.put("CHN", 1381.454);
        DATA.put("COL", 48.75);
        DATA.put("COM", 0.823);
        DATA.put("COD", 84.13);
        DATA.put("COG", 4.46);
        DATA.put("CRI", 4.9);
        DATA.put("CIV", 24.327);
        DATA.put("HRV", 4.204);
        DATA.put("CYP", 0.864);
        DATA.put("CZE", 10.561);
        DATA.put("DNK", 5.683);
        DATA.put("DJI", 0.993);
        DATA.put("DMA", 0.071);
        DATA.put("DOM", 10.098);
        DATA.put("ECU", 16.529);
        DATA.put("EGY", 90.203);
        DATA.put("SLV", 6.403);
        DATA.put("GNQ", 0.821);
        DATA.put("ERI", 6.938);
        DATA.put("EST", 1.312);
        DATA.put("ETH", 91.196);
        DATA.put("FJI", 0.894);
        DATA.put("FIN", 5.5);
        DATA.put("FRA", 64.569);
        DATA.put("GAB", 1.881);
        DATA.put("GMB", 2.035);
        DATA.put("GEO", 3.678);
        DATA.put("DEU", 82.773);
        DATA.put("GHA", 27.573);
        DATA.put("GRC", 10.79);
        DATA.put("GRD", 0.107);
        DATA.put("GTM", 16.673);
        DATA.put("GIN", 12.654);
        DATA.put("GNB", 1.818);
        DATA.put("GUY", 0.769);
        DATA.put("HTI", 10.848);
        DATA.put("HND", 8.606);
        DATA.put("HKG", 7.357);
        DATA.put("HUN", 9.835);
        DATA.put("ISL", 0.332);
        DATA.put("IND", 1309.713);
        DATA.put("IDN", 258.802);
        DATA.put("IRN", 80.46);
        DATA.put("IRQ", 36.067);
        DATA.put("IRL", 4.675);
        DATA.put("ISR", 8.528);
        DATA.put("ITA", 61.151);
        DATA.put("JAM", 2.829);
        DATA.put("JPN", 126.541);
        DATA.put("JOR", 6.976);
        DATA.put("KAZ", 17.947);
        DATA.put("KEN", 45.478);
        DATA.put("KIR", 0.116);
        DATA.put("KOR", 50.835);
        DATA.put("UVK", 0.0);
        DATA.put("KWT", 4.225);
        DATA.put("KGZ", 6.059);
        DATA.put("LAO", 7.163);
        DATA.put("LVA", 1.976);
        DATA.put("LBN", 4.597);
        DATA.put("LSO", 1.937);
        DATA.put("LBR", 4.399);
        DATA.put("LBY", 6.385);
        DATA.put("LTU", 2.875);
        DATA.put("LUX", 0.577);
        DATA.put("MAC", 0.698);
        DATA.put("MKD", 2.076);
        DATA.put("MDG", 24.916);
        DATA.put("MWI", 18.632);
        DATA.put("MYS", 31.523);
        DATA.put("MDV", 0.354);
        DATA.put("MLI", 16.817);
        DATA.put("MLT", 0.431);
        DATA.put("MHL", 0.055);
        DATA.put("MRT", 3.794);
        DATA.put("MUS", 1.259);
        DATA.put("MEX", 128.632);
        DATA.put("FSM", 0.103);
        DATA.put("MDA", 3.553);
        DATA.put("MNG", 3.014);
        DATA.put("MNE", 0.623);
        DATA.put("MAR", 33.827);
        DATA.put("MOZ", 28.751);
        DATA.put("MMR", 52.254);
        DATA.put("NAM", 2.24);
        DATA.put("NPL", 28.758);
        DATA.put("NLD", 17.01);
        DATA.put("NZL", 4.687);
        DATA.put("NIC", 6.342);
        DATA.put("NER", 18.194);
        DATA.put("NGA", 183.636);
        DATA.put("NOR", 5.263);
        DATA.put("OMN", 3.957);
        DATA.put("PAK", 189.87);
        DATA.put("PLW", 0.018);
        DATA.put("PAN", 4.086);
        DATA.put("PNG", 7.911);
        DATA.put("PRY", 7.115);
        DATA.put("PER", 32.405);
        DATA.put("PHL", 104.195);
        DATA.put("POL", 38.003);
        DATA.put("PRT", 10.419);
        DATA.put("PRI", 3.472);
        DATA.put("QAT", 2.578);
        DATA.put("ROU", 19.869);
        DATA.put("RUS", 146.3);
        DATA.put("RWA", 11.59);
        DATA.put("WSM", 0.195);
        DATA.put("SMR", 0.031);
        DATA.put("STP", 0.208);
        DATA.put("SAU", 32.013);
        DATA.put("SEN", 15.406);
        DATA.put("SRB", 7.132);
        DATA.put("SYC", 0.093);
        DATA.put("SLE", 6.439);
        DATA.put("SGP", 5.584);
        DATA.put("SVK", 5.418);
        DATA.put("SVN", 2.065);
        DATA.put("SLB", 0.601);
        DATA.put("ZAF", 55.831);
        DATA.put("SSD", 12.499);
        DATA.put("ESP", 46.317);
        DATA.put("LKA", 21.252);
        DATA.put("KNA", 0.056);
        DATA.put("LCA", 0.174);
        DATA.put("VCT", 0.11);
        DATA.put("SDN", 39.599);
        DATA.put("SUR", 0.563);
        DATA.put("SWZ", 1.298);
        DATA.put("SWE", 10.027);
        DATA.put("CHE", 8.337);
        DATA.put("SYR", 0.0);
        DATA.put("TWN", 23.551);
        DATA.put("TJK", 8.655);
        DATA.put("TZA", 48.633);
        DATA.put("THA", 68.981);
        DATA.put("TLS", 1.187);
        DATA.put("TGO", 7.514);
        DATA.put("TON", 0.104);
        DATA.put("TTO", 1.364);
        DATA.put("TUN", 11.224);
        DATA.put("TUR", 78.559);
        DATA.put("TKM", 5.463);
        DATA.put("TUV", 0.011);
        DATA.put("UGA", 41.087);
        DATA.put("UKR", 42.501);
        DATA.put("ARE", 9.856);
        DATA.put("GBR", 65.572);
        DATA.put("USA", 324.328);
        DATA.put("URY", 3.427);
        DATA.put("UZB", 31.343);
        DATA.put("VUT", 0.275);
        DATA.put("VEN", 31.416);
        DATA.put("VNM", 92.637);
        DATA.put("YEM", 29.132);
        DATA.put("ZMB", 16.717);
        DATA.put("ZWE", 13.554);
    }

    @Override public void start(Stage stage) {
        StackPane pane = new StackPane(world);
        pane.setBackground(new Background(new BackgroundFill(world.getBackgroundColor(), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(pane);
        //scene.getStylesheets().add(Main.class.getResource("custom-styles.css").toExternalForm());

        stage.setTitle("World Map");
        stage.setScene(scene);
        stage.show();

        //world.zoomToCountry(Country.DE);
        //world.zoomToRegion(europeanUnion);
        world.zoomToRegion(BusinessRegion.EU);
    }

    @Override public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
