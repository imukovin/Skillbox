import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {
    RouteCalculator rc;
    List<Station> stations;
    StationIndex si;
    Line l1 = new Line(1,"First");
    Line l2 = new Line(2,"Second");

    @Override
    protected void setUp() throws Exception {
        stations = new ArrayList<>();
        si = new StationIndex();

        stations.add(new Station("a", l1));
        stations.add(new Station("b", l1));
        stations.add(new Station("c", l1));
        stations.add(new Station("d", l2));
        stations.add(new Station("f", l2));

        l1.addStation(new Station("a" , l1));
        l1.addStation(new Station("b" , l1));
        l1.addStation(new Station("c" , l1));
        l2.addStation(new Station("d" , l2));
        l2.addStation(new Station("f" , l2));

        si.addStation(new Station("a", l1));
        si.addStation(new Station("b", l1));
        si.addStation(new Station("c", l1));
        si.addStation(new Station("d", l2));
        si.addStation(new Station("f", l2));
        si.addLine(l1);
        si.addLine(l2);

        List<Station> connections = new ArrayList<>();
        connections.add(new Station("c", l1));
        connections.add(new Station("d", l2));
        si.addConnection(connections);

        rc = new RouteCalculator(si);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetShortestRoute() {
        List<Station> actual = rc.getShortestRoute(si.getStation("b"), si.getStation("d"));
        List<Station> expected = new ArrayList<>();
        expected.add(new Station("b", l1));
        expected.add(new Station("c", l1));
        expected.add(new Station("d", l2));
        assertEquals(expected, actual);
    }

    public void testCalculateDuration() {
        double actual = RouteCalculator.calculateDuration(stations);
        double expected  = 11.;
        assertEquals(expected, actual);
    }
}