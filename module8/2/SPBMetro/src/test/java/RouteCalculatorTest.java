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
    Line l3 = new Line(3,"Third");
    Line l4 = new Line(4,"Fourth");


//            d             n  o
//            ^             ^  ^
//   a > b > c/e > g > h > i/m/p
//            ^             ^  ^
//       j > f/k   >  l  >>>^  q

    @Override
    protected void setUp() throws Exception {
        stations = new ArrayList<>();
        si = new StationIndex();

        initStationMap();

        rc = new RouteCalculator(si);

    }

    public void testGetShortestRouteOnTheLine() {
        List<Station> actual = rc.getShortestRoute(si.getStation("a"), si.getStation("b"));
        List<Station> expected = new ArrayList<>();
        expected.add(new Station("a", l1));
        expected.add(new Station("b", l1));
        assertEquals(expected, actual);
    }
    public void testGetShortestRouteOnTheLineReverseDirection() {
        List<Station> actual = rc.getShortestRoute(si.getStation("b"), si.getStation("a"));
        List<Station> expected = new ArrayList<>();
        expected.add(new Station("b", l1));
        expected.add(new Station("a", l1));
        assertEquals(expected, actual);
    }


    public void testGetShortestRouteWithOneConnections() {
        List<Station> actual = rc.getShortestRoute(si.getStation("d"), si.getStation("l"));
        List<Station> expected = new ArrayList<>();
        expected.add(new Station("d", l2));
        expected.add(new Station("e", l2));
        expected.add(new Station("f", l2));
        expected.add(new Station("k", l3));
        expected.add(new Station("l", l3));
        assertEquals(expected, actual);
    }

    public void testGetShortestRouteWithTwoConnections() {
        List<Station> actual = rc.getShortestRoute(si.getStation("h"), si.getStation("q"));
        List<Station> expected = new ArrayList<>();
        expected.add(new Station("h", l1));
        expected.add(new Station("i", l1));
        expected.add(new Station("m", l3));
        expected.add(new Station("p", l4));
        expected.add(new Station("q", l4));
        assertEquals(expected, actual);
    }

    public void testCalculateDuration() {
        List<Station> input = rc.getShortestRoute(si.getStation("d"), si.getStation("l"));
        double actual = RouteCalculator.calculateDuration(input);
        double expected  = 11.;
        assertEquals(expected, actual);
    }


    public void initStationMap() {
        stations.add(new Station("a", l1));
        stations.add(new Station("b", l1));
        stations.add(new Station("c", l1));
        stations.add(new Station("g", l1));
        stations.add(new Station("h", l1));
        stations.add(new Station("i", l1));
        stations.add(new Station("d", l2));
        stations.add(new Station("e", l2));
        stations.add(new Station("f", l2));
        stations.add(new Station("j", l3));
        stations.add(new Station("k", l3));
        stations.add(new Station("l", l3));
        stations.add(new Station("m", l3));
        stations.add(new Station("n", l3));
        stations.add(new Station("o", l4));
        stations.add(new Station("p", l4));
        stations.add(new Station("q", l4));

        l1.addStation(new Station("a" , l1));
        l1.addStation(new Station("b" , l1));
        l1.addStation(new Station("c" , l1));
        l1.addStation(new Station("g" , l1));
        l1.addStation(new Station("h" , l1));
        l1.addStation(new Station("i" , l1));
        l2.addStation(new Station("d" , l2));
        l2.addStation(new Station("e" , l2));
        l2.addStation(new Station("f" , l2));
        l3.addStation(new Station("j" , l3));
        l3.addStation(new Station("k" , l3));
        l3.addStation(new Station("l" , l3));
        l3.addStation(new Station("m" , l3));
        l3.addStation(new Station("n" , l3));
        l4.addStation(new Station("o" , l4));
        l4.addStation(new Station("p" , l4));
        l4.addStation(new Station("q" , l4));

        si.addStation(new Station("a", l1));
        si.addStation(new Station("b", l1));
        si.addStation(new Station("c", l1));
        si.addStation(new Station("g", l1));
        si.addStation(new Station("h", l1));
        si.addStation(new Station("i", l1));
        si.addStation(new Station("d", l2));
        si.addStation(new Station("e", l2));
        si.addStation(new Station("f", l2));
        si.addStation(new Station("j", l3));
        si.addStation(new Station("k", l3));
        si.addStation(new Station("l", l3));
        si.addStation(new Station("m", l3));
        si.addStation(new Station("n", l3));
        si.addStation(new Station("o", l4));
        si.addStation(new Station("p", l4));
        si.addStation(new Station("q", l4));
        si.addLine(l1);
        si.addLine(l2);
        si.addLine(l3);
        si.addLine(l4);

        List<Station> connections = new ArrayList<>();
        connections.add(si.getStation("c", 1));
        connections.add(si.getStation("e", 2));
        si.addConnection(connections);
        connections.clear();
        connections.add(si.getStation("f", 2));
        connections.add(si.getStation("k", 3));
        si.addConnection(connections);
        connections.clear();
        connections.add(si.getStation("i", 1));
        connections.add(si.getStation("m", 3));
        si.addConnection(connections);
        connections.clear();
        connections.add(si.getStation("m", 3));
        connections.add(si.getStation("p", 4));
        si.addConnection(connections);
        connections.clear();
    }
}