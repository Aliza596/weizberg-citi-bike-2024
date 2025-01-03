package weizberg.citibike.ui;

import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jxmapviewer.JXMapViewer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.*;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CitibikeFrame extends JFrame {

    JTextField fromTextField = new JTextField();
    JTextField toTextField = new JTextField();
    JButton sendButton = new JButton("Get Bike");
    CitibikeController controller = new CitibikeController();
    Waypoint from;
    Waypoint to;
    Waypoint startStation;
    Waypoint endStation;
    CompositeDisposable disposables = new CompositeDisposable();

    public CitibikeFrame() {
        setSize(800, 800);
        setTitle("Citibike");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JXMapViewer mapViewer = new JXMapViewer();
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        tileFactory.setThreadPoolSize(8);
        mapViewer.setZoom(2);
        mapViewer.setAddressLocation(new GeoPosition(40.77207958021334, -73.988297713292));
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);

        mapViewer.addMouseListener(new CenterMapListener(mapViewer));

        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));

        mapViewer.addKeyListener(new PanKeyListener(mapViewer));

        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                Point2D.Double point = new Point2D.Double(x, y);
                GeoPosition position = mapViewer.convertPointToGeoPosition(point);
                if (fromTextField.getText().trim().isEmpty()) {
                    fromTextField.setText(position.toString());
                } else if (toTextField.getText().trim().isEmpty()) {
                    toTextField.setText(position.toString());
                }
            }
        });

        add(mapViewer, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(fromTextField);
        panel.add(toTextField);

        sendButton.addActionListener(e -> {
            try {
                controller.retrieveBikeInformation(fromTextField.getText(), toTextField.getText());
                disposables.add(
                        controller.sendRequest()
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.single())
                                .subscribe(response -> {
                                    WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
                                    Set<Waypoint> waypoints = Set.of(
                                            from = controller.startLocation(),
                                            to = controller.endLocation(),
                                            startStation = controller.bikePickUpLocation(),
                                            endStation = controller.bikeDropOffLocation()
                                    );
                                    waypointPainter.setWaypoints(waypoints);

                                    List<GeoPosition> track = new ArrayList<>();
                                    track.add(from.getPosition());
                                    track.add(startStation.getPosition());
                                    track.add(endStation.getPosition());
                                    track.add(to.getPosition());

                                    RoutePainter routePainter = new RoutePainter(track);

                                    List<Painter<JXMapViewer>> painters = List.of(
                                            routePainter,
                                            waypointPainter
                                    );

                                    CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
                                    mapViewer.setOverlayPainter(painter);
                                    mapViewer.zoomToBestFit(
                                            Set.of(from.getPosition(), startStation.getPosition(),
                                                    endStation.getPosition(), to.getPosition()), 1.0
                                    );
                                }, Throwable::printStackTrace)
                );
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        panel.add(sendButton);

        add(panel, BorderLayout.SOUTH);

    }

    @Override
    public void dispose() {
        super.dispose();
        disposables.dispose();
    }

}
