import java.text.DecimalFormat;
import java.util.*;

public class Module implements Comparable<Module>{

    private String moduleNr;
    private String moduleName;
    private List<Cfg> cfgs;
    private TreeMap<String, Integer> treemapA;
    private TreeMap<String, Integer> treemapB;
    private TreeMap<String, Integer> treemapC;

    private List<CfgPair> typeA;
    private List<CfgPair> typeB;
    private List<CfgPair> typeC;
    private List<CfgPair> all;

    public Module(String moduleNr) {
        this.moduleNr = moduleNr;
        this.cfgs = new ArrayList<>();
        this.treemapA = new TreeMap<String, Integer>();
        this.treemapB = new TreeMap<String, Integer>();
        this.treemapC = new TreeMap<String, Integer>();

        this.typeA = new ArrayList<>();
        this.typeB = new ArrayList<>();
        this.typeC = new ArrayList<>();
        this.all = new ArrayList<>();

    }

    public void printModuleA(List<Module> mod){
        if (treemapA.values().size() > 0){
            System.out.print(moduleNr);
            System.out.print(" & ");
            System.out.print(moduleName);
            System.out.print(" & ");
            System.out.print(cfgs.size());
            System.out.print(" & ");
            Map sortedMap = sortByValues(treemapA);
            Set set = sortedMap.entrySet();
            Iterator i = set.iterator();

            while(i.hasNext()) {
                Map.Entry<String, Integer> me = (Map.Entry)i.next();
                Double restPercent = new Double(me.getValue()) / new Double(cfgs.size());
                DecimalFormat df = new DecimalFormat("#.##");
                restPercent = restPercent * 100;
                String restPercentStr = df.format(restPercent);
                System.out.print(me.getKey() + ": ");
                System.out.print(restPercentStr + " % , ");
            }
            System.out.print(" \\\\ \\hline \n");
        }
    }


    public void printModuleB(List<Module> mod){
        if (treemapA.values().size() > 0){
            System.out.print(moduleNr);
            System.out.print(" & ");
            System.out.print(moduleName);
            System.out.print(" & ");
            System.out.print(cfgs.size());
            System.out.print(" & ");
            Map sortedMap = sortByValues(treemapB);
            Set set = sortedMap.entrySet();
            Iterator i = set.iterator();

            while(i.hasNext()) {
                Map.Entry<String, Integer> me = (Map.Entry)i.next();
                Double restPercent = new Double(me.getValue()) / new Double(cfgs.size());
                DecimalFormat df = new DecimalFormat("#.##");
                restPercent = restPercent * 100;
                String restPercentStr = df.format(restPercent);
                System.out.print(me.getKey() + ": ");
                System.out.print(restPercentStr + " % , ");
            }
            System.out.print(" \\\\ \\hline \n");
        }
    }

    public void printModuleC(List<Module> mod){
        if (treemapA.values().size() > 0){
            System.out.print(moduleNr);
            System.out.print(" & ");
            System.out.print(moduleName);
            System.out.print(" & ");
            System.out.print(cfgs.size());
            System.out.print(" & ");
            Map sortedMap = sortByValues(treemapC);
            Set set = sortedMap.entrySet();
            Iterator i = set.iterator();

            while(i.hasNext()) {
                Map.Entry<String, Integer> me = (Map.Entry)i.next();
                Double restPercent = new Double(me.getValue()) / new Double(cfgs.size());
                DecimalFormat df = new DecimalFormat("#.##");
                restPercent = restPercent * 100;
                String restPercentStr = df.format(restPercent);
                System.out.print(me.getKey() + ": ");
                System.out.print(restPercentStr + " % , ");
            }
            System.out.print(" \\\\ \\hline \n");
        }
    }



//    public void printModule2(){
//        if (treemapA.values().size() > 0){
//            System.out.print(moduleName);
//            System.out.print(",");
//            System.out.print(moduleNr);
//            System.out.print(",");
//            System.out.print(cfgs.size());
//            System.out.print(",");
//            Map sortedMap = sortByValues(treemapA);
//            Set set = sortedMap.entrySet();
//            Iterator i = set.iterator();
//            while(i.hasNext()) {
//                Map.Entry<String, Integer> me = (Map.Entry)i.next();
//                Double restPercent = new Double(me.getValue()) / new Double(cfgs.size());
//                DecimalFormat df = new DecimalFormat("#.####");
//                String restPercentStr = df.format(restPercent);
//                System.out.print(restPercentStr+", ");
//            }
//            System.out.print("\n");
//        }
//    }

    public String getModuleNr() {
        return moduleNr;
    }

    public void setModuleNr(String moduleNr) {
        this.moduleNr = moduleNr;
    }

    public void addCfg(Cfg cfg) {
        this.moduleName = cfg.getModuleName();
        this.cfgs.add(cfg);
    }

    public void addCfgPair(CfgPair cfg){

        if (cfg.getAll() >= 0.9 ) {
            typeA.add(cfg);

            String a = cfg.getA().getModuleNr();
            String b = cfg.getB().getModuleNr();
            if (a.equals(moduleNr)) {
                Integer i = this.treemapA.get(b);
                if (i == null){
                    i = 0;
                }
                i++;
                this.treemapA.put(b,i);
            } else {
                Integer i = this.treemapA.get(a);
                if (i == null){
                    i = 0;
                }
                i++;
                this.treemapA.put(a,i);
            }

        }
        if (cfg.getAll() < 0.9 && cfg.getAll() >= 0.81) {
            typeB.add(cfg);

            String a = cfg.getA().getModuleNr();
            String b = cfg.getB().getModuleNr();
            if (a.equals(moduleNr)) {
                Integer i = this.treemapB.get(b);
                if (i == null){
                    i = 0;
                }
                i++;
                this.treemapB.put(b,i);
            } else {
                Integer i = this.treemapB.get(a);
                if (i == null){
                    i = 0;
                }
                i++;
                this.treemapB.put(a,i);
            }
        }
        if (cfg.getAll() < 0.81 && cfg.getAll() > 0.7) {
            typeC.add(cfg);

            String a = cfg.getA().getModuleNr();
            String b = cfg.getB().getModuleNr();
            if (a.equals(moduleNr)) {
                Integer i = this.treemapC.get(b);
                if (i == null){
                    i = 0;
                }
                i++;
                this.treemapC.put(b,i);
            } else {
                Integer i = this.treemapC.get(a);
                if (i == null){
                    i = 0;
                }
                i++;
                this.treemapC.put(a,i);
            }
        }
        all.add(cfg);

    }

    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =
                new Comparator<K>() {
                    public int compare(K k1, K k2) {
                        int compare =
                                map.get(k1).compareTo(map.get(k2));
                        if (compare == 0)
                            return 1;
                        else
                            return compare;
                    }
                };

        Map<K, V> sortedByValues =
                new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    @Override
    public int compareTo(Module o) {
        return this.all.size() - o.getAll().size();
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<Cfg> getCfgs() {
        return cfgs;
    }

    public void setCfgs(List<Cfg> cfgs) {
        this.cfgs = cfgs;
    }

    public TreeMap<String, Integer> getTreemapA() {
        return treemapA;
    }

    public void setTreemapA(TreeMap<String, Integer> treemapA) {
        this.treemapA = treemapA;
    }

    public List<CfgPair> getTypeA() {
        return typeA;
    }

    public void setTypeA(List<CfgPair> typeA) {
        this.typeA = typeA;
    }

    public List<CfgPair> getTypeB() {
        return typeB;
    }

    public void setTypeB(List<CfgPair> typeB) {
        this.typeB = typeB;
    }

    public List<CfgPair> getTypeC() {
        return typeC;
    }

    public void setTypeC(List<CfgPair> typeC) {
        this.typeC = typeC;
    }

    public List<CfgPair> getAll() {
        return all;
    }

    public void setAll(List<CfgPair> all) {
        this.all = all;
    }
}
