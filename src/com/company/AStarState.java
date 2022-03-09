package com.company;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;
    /** Инициализировать карту всех открытых путевых точек и их местоположений **/
    private Map<Location, Waypoint> open_waypoints = new HashMap<Location, Waypoint> ();
    /** Инициализировать карту всех закрытых путевых точек и их местоположений. **/
    private Map<Location, Waypoint> closed_waypoints = new HashMap<Location, Waypoint> ();

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /** Этот метод сканирует все открытые путевые точки и возвращает путевую точку с минимальной общей стоимостью. Если открытых путевых точек нет, этот метод возвращает <code>null</code>. **/
    public Waypoint getMinOpenWaypoint()
    {
        // В случае если открытых путевых точек нет, то возвращает <code>null</code>.
        if (numOpenWaypoints() == 0)
        return null;
        // Тут требуется инициализировать набор ключей всех открытых путевых точек, а также средство взаимодействия для перебора набора и переменную для хранения наилучшей путевой точки и стоимости для этой путевой точки.
        Set open_waypoint_keys = open_waypoints.keySet();
        Iterator i = open_waypoint_keys.iterator();
        Waypoint best = null;
        float best_cost = Float.MAX_VALUE;
        // Тут идет сканирование всех открытых путевых точек.
        while (i.hasNext())
        {
            // Сохраняет текущее местоположение.
            Location location = (Location)i.next();
            // Сохраняет текущую путевую точку.
            Waypoint waypoint = open_waypoints.get(location);
            // Сохраняет общую стоимость для текущей путевой точки.
            float waypoint_total_cost = waypoint.getTotalCost();
            // В случае если общая стоимость для текущей путевой точки лучше (ниже), чем сохраненная стоимость для сохраненной наилучшей путевой точки, заменить сохраненную путевую точку текущей путевой точкой, а сохраненную общую стоимость текущей общей стоимостью.
            if (waypoint_total_cost < best_cost)
            {
                best = open_waypoints.get(location);
                best_cost = waypoint_total_cost;
            }
        }
        // Возвращается путевая точка с минимальной общей стоимостью.
        return best;
    }

    /**
     * Этот метод добавляет путевую точку к (или потенциально обновляет уже имеющуюся путевую точку
     * в) коллекция "открытые путевые точки". Если еще нет открытого
     * путевая точка в местоположении новой путевой точки, тогда новая путевая точка просто
     * добавлено в коллекцию. Однако, если в
     местоположении * новой путевой точки уже есть путевая точка, новая путевая точка заменяет только старую <em>
     * если</em> значение "предыдущей стоимости" новой путевой точки меньше текущего
     * значение "предыдущей стоимости" путевой точки.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        // Находит местоположение новой путевой точки.
        Location location = newWP.getLocation();
        // Проверяет, есть ли уже открытая путевая точка в местоположении новой путевой точки.
        if (open_waypoints.containsKey(location))
        {
            // Если в местоположении новой путевой точки уже есть открытая путевая точка, проверяет, не меньше ли значение "предыдущей стоимости" новой путевой точки, чем значение "предыдущей стоимости" текущей путевой точки.
            Waypoint current_waypoint = open_waypoints.get(location);
            if (newWP.getPreviousCost() < current_waypoint.getPreviousCost())
            {
                // Если значение "предыдущей стоимости" новой путевой точки меньше значения "предыдущей стоимости" текущей путевой точки, новая путевая точка заменяет старую путевую точку и возвращает значение true.
                open_waypoints.put(location, newWP);
                return true;
            }
            // Если значение "предыдущей стоимости" новой путевой точки не меньше значения "предыдущей стоимости" текущей путевой точки, верните значение false.
            return false;
        }
        // Если в местоположении новой путевой точки еще нет открытой путевой точки, добавьте новую путевую точку в коллекцию открытых путевых точек и верните значение true.
        open_waypoints.put(location, newWP);
        return true;
    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        return open_waypoints.size();
    }


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
        Waypoint waypoint = open_waypoints.remove(loc);
        closed_waypoints.put(loc, waypoint);
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closed_waypoints.containsKey(loc);
    }
}
