package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;
import bearmaps.lab9_for_proj2C.MyTrieSet;

import java.util.*;

/**
 * An augmented graph that is more powerful than a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private Map<Point, Long> point_to_id;
    private WeirdPointSet weirdPointSet;
    private MyTrieSet trieSet;
    private Map<String, List<Node>> cleaned_name_to_nodes;



    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        List<Point> points = new LinkedList<>();

        point_to_id = new HashMap<>();
        trieSet = new MyTrieSet();
        cleaned_name_to_nodes = new HashMap<>();

        for (Node n : nodes) {
            if (n.name() != null) {
                String cleaned_name = cleanString(n.name());
                trieSet.add(cleaned_name);

                if (!cleaned_name_to_nodes.containsKey(cleaned_name)) {
                    cleaned_name_to_nodes.put(cleaned_name, new LinkedList<>());
                }

                List<Node> nodesList = cleaned_name_to_nodes.get(cleaned_name);
                nodesList.add(n);
                cleaned_name_to_nodes.put(cleaned_name, nodesList);
            }


            if (!neighbors(n.id()).isEmpty()) {
                Point p = new Point(n.lon(), n.lat());
                points.add(p);
                point_to_id.put(p, n.id());
            }
        }
        weirdPointSet = new WeirdPointSet(points);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point closest_point = weirdPointSet.nearest(lon, lat);
        return point_to_id.get(closest_point);
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        String cleaned_prefix = cleanString(prefix);
        List<String> cleaned_names_with_prefix = trieSet.keysWithPrefix(cleaned_prefix);
        List<String> locations_with_prefix = new ArrayList<>();

        for (String name : cleaned_names_with_prefix) {
            List<Node> nodes_with_this_cleaned_name = cleaned_name_to_nodes.get(name);
            for (Node n : nodes_with_this_cleaned_name) {
                locations_with_prefix.add(n.name());
            }
        }
        return locations_with_prefix;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> locations = new ArrayList<>();
        String cleaned_location_name = cleanString(locationName);
        if (cleaned_name_to_nodes.containsKey(cleaned_location_name)) {
            List<Node> nodes_with_this_cleaned_name = cleaned_name_to_nodes.get(cleaned_location_name);
            for (Node n : nodes_with_this_cleaned_name) {
                Map<String, Object> location = new HashMap<>();
                location.put("lat", n.lat());
                location.put("lon", n.lon());
                location.put("name", n.name());
                location.put("id", n.id());
                locations.add(location);
            }
        }
        return locations;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
