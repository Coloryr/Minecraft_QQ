package Color_yr.Minecraft_QQ.bStats.charts;

import Color_yr.Minecraft_QQ.bStats.json.JsonObjectBuilder;

import java.util.Map;
import java.util.concurrent.Callable;

public class MultiLineChart extends CustomChart {

    private final Callable<Map<String, Integer>> callable;

    /**
     * Class constructor.
     *
     * @param chartId  The id of the chart.
     * @param callable The callable which is used to request the chart data.
     */
    public MultiLineChart(String chartId, Callable<Map<String, Integer>> callable) {
        super(chartId);
        this.callable = callable;
    }

    @Override
    protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
        JsonObjectBuilder valuesBuilder = new JsonObjectBuilder();

        Map<String, Integer> map = callable.call();
        if (map == null || map.isEmpty()) {
            // Null = skip the chart
            return null;
        }
        boolean allSkipped = true;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 0) {
                continue; // Skip this invalid
            }
            allSkipped = false;
            valuesBuilder.appendField(entry.getKey(), entry.getValue());
        }
        if (allSkipped) {
            // Null = skip the chart
            return null;
        }

        return new JsonObjectBuilder()
                .appendField("values", valuesBuilder.build())
                .build();
    }

}