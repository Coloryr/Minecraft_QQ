package coloryr.minecraft_qq.bstats.charts;

import coloryr.minecraft_qq.bstats.json.JsonObjectBuilder;

import java.util.Map;
import java.util.concurrent.Callable;

public class SimpleBarChart extends CustomChart {

    private final Callable<Map<String, Integer>> callable;

    /**
     * Class constructor.
     *
     * @param chartId  The id of the chart.
     * @param callable The callable which is used to request the chart data.
     */
    public SimpleBarChart(String chartId, Callable<Map<String, Integer>> callable) {
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
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            valuesBuilder.appendField(entry.getKey(), new int[]{entry.getValue()});
        }

        return new JsonObjectBuilder()
                .appendField("values", valuesBuilder.build())
                .build();
    }

}