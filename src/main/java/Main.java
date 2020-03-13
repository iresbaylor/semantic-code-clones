import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static final String COMMA_DELIMITER = ",";

    public static void main(String[] args) throws FileNotFoundException {
        Map<String, Cfg> records = new HashMap();

        ModuleCluster moduleCluster = new ModuleCluster();
        CfgPairCluster cfgPairCluster = new CfgPairCluster();

        try (Scanner scanner = new Scanner(new File("data2.csv"));) {
            while (scanner.hasNextLine()) {
                Cfg cfg = getRecordFromLine(scanner.nextLine());
                moduleCluster.addCfgToModule(cfg);
                if (records.containsKey(cfg.getCfgNr())){
                    Cfg val = records.get(cfg.getCfgNr());
                    val.addServices(cfg.getServices());
                    records.put(val.getCfgNr(), val);
                } else {
                    records.put(cfg.getCfgNr(), cfg);
                }
            }
        }
        List<Cfg> vals = new ArrayList<>(records.values());
        List<CfgPair> pairs = new ArrayList<>();
        for (int i = 0; i < vals.size(); i++){
            for (int j = i + 1; j < vals.size(); j++){
                CfgPair cfgPair = new CfgPair(vals.get(i), vals.get(j));
                pairs.add(cfgPair);
                moduleCluster.addCfgPairToModule(cfgPair);
                cfgPairCluster.addCfg(cfgPair);
            }
        }
        moduleCluster.printClusterA();
        moduleCluster.printClusterB();
        moduleCluster.printClusterC();
//        moduleCluster.printCluster2();
//        cfgPairCluster.printClusters();
//        cfgPairCluster.printCfgPercentage();

    }

    private static Cfg getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        Cfg cfg = new Cfg();
        cfg.setValues(values);
        return cfg;
    }
}
