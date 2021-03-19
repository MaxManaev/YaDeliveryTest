import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Good dimensions.
 */
enum Dimensions {
    Large,
    Small
}

/**
 * Delivery workload.
 */
enum Workload {
    VeryHigh,
    High,
    Increased,
    Normal
}

/**
 * Delivery class
 */
public class Delivery {

    /**
     * Calculates cost of delivery.
     *
     * @param distance  Delivery distance.
     * @param dim       Dimensions of good.
     * @param wl        Delivery workload.
     * @param isFragile Fragility indicator.
     * @return Delivery cost.
     */
    public double Calculate(int distance, Dimensions dim, Workload wl, boolean isFragile) {
        return Math.round(Math.max(400, handleWorkload(wl) * (handleDistance(distance) + handleDimensions(dim) +
                handleFragility(isFragile, distance))));
    }

    private int handleDistance(int distance) {
        Map<Integer, Integer> distanceToCostMap = new LinkedHashMap<>() {
            {
                put(30, 300);
                put(10, 200);
                put(2, 100);
                put(0, 50);
            }
        };

        for (int distanceBorder : distanceToCostMap.keySet()) {
            if (distance >= distanceBorder) {
                return distanceToCostMap.get(distanceBorder);
            }
        }
        throw new IllegalArgumentException("Negative distance");
    }

    private int handleDimensions(Dimensions dim) {
        if (dim == Dimensions.Large) {
            return 200;
        }
        return 100;
    }

    private double handleWorkload(Workload wl) {
        Map<Workload, Double> workloadToFactorMap = new LinkedHashMap<>() {
            {
                put(Workload.VeryHigh, 1.6);
                put(Workload.High, 1.4);
                put(Workload.Increased, 1.2);
                put(Workload.Normal, 1.0);
            }
        };

        return workloadToFactorMap.get(wl);
    }


    private int handleFragility(Boolean isFragility, int distance) {
        if (isFragility) {
            if (distance > 30) {
                throw new IllegalArgumentException("Invalid distance for fragility good");
            }
            return 300;
        }

        return 0;
    }
}
