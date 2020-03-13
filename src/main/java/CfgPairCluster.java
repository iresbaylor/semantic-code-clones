import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CfgPairCluster {
    private List<CfgPair> typeA;
    private List<CfgPair> typeB;
    private List<CfgPair> typeC;
    private List<CfgPair> all;

    public CfgPairCluster(){
        this.typeA = new ArrayList<>();
        this.typeB = new ArrayList<>();
        this.typeC = new ArrayList<>();
        this.all = new ArrayList<>();
    }

    public void addCfg(CfgPair cfg){
        if (cfg.getAll() >= 0.9 ) {
            typeA.add(cfg);
        }
        if (cfg.getAll() < 0.9 && cfg.getAll() >= 0.81) {
            typeB.add(cfg);
        }
        if (cfg.getAll() < 0.81 && cfg.getAll() > 0.7) {
            typeC.add(cfg);
        }
        all.add(cfg);
    }

    public void printAll(){

    }

    public void printMetaData(){
        System.out.println("======== METADATA ======== ");
        System.out.println(+ all.size());
    }

    public void printClusters(){
//        printCluster(typeA, "TYPE A");
        printCluster(typeB, "TYPE B");
//        printClusterA(typeC, "TYPE C");
    }

    public void printCluster(List<CfgPair> cluster, String str){
        System.out.println("======== " + str + " ======== ");
        StringBuilder s = new StringBuilder();
        s.append("\\begin{table*}[t]\n");
        s.append("\\begin{tabular}{|l|l|l|l|}\n");
        s.append("\\hline\n");
        s.append("Module A Name & Module A Cfg & Module B Name & Module B Cfg \\\\ \\hline");
        System.out.println(s.toString());
        for (CfgPair c: cluster
        ) {
            StringBuilder sb = new StringBuilder();
            sb.append(c.getA().getModuleName());
            sb.append(" & ");
            sb.append(c.getA().getCfgName());
            sb.append(" & ");
            sb.append(c.getB().getModuleName());
            sb.append(" & ");
            sb.append(c.getB().getCfgName());
            sb.append(" & ");
            sb.append(c.getAll());
            sb.append(" \\\\ ");
            sb.append(" \\hline");
            System.out.println(sb.toString());
        }
        s = new StringBuilder();
        s.append("\\end{tabular}\n");
        s.append("\\caption{Categorizing found clones}");
        s.append("\\label{table:count2}");
        s.append("\\end{table*}");
        System.out.println(s.toString());
    }

    public void printCfgPercentage(){
        System.out.println("======== PERCENTAGE ======== ");
        StringBuilder s = new StringBuilder();
        s.append("\\begin{table}[h!]\n");
        s.append("\\begin{tabular}{|l|l|l|}\n");
        s.append("\\hline\n");
        s.append("Cluster & Total Nr & Percentage \\\\ \\hline \n");
        s.append("Type A & " + typeA.size() + " & " + getTypePercentage(typeA) + " \\% \\\\ \\hline \n");
        s.append("Type B & " + typeB.size() + " & "+ getTypePercentage(typeB) + " \\% \\\\ \\hline \n");
        int rest = all.size() - typeC.size() - typeB.size() - typeA.size();
        Double restPercent = new Double(rest) / new Double(all.size());
        restPercent = restPercent * 100;
        DecimalFormat df = new DecimalFormat("#.##");
        String restPercentStr = df.format(restPercent);
        s.append("Type C & " + typeC.size() + " & "+ getTypePercentage(typeC) + " \\% \\\\ \\hline \n");
        s.append("Others & " + rest + " & " + restPercentStr + " \\% \\\\ \\hline \n");
        s.append("\\end{tabular}\n");
        s.append("\\caption{CFG clones percentage}\n");
        s.append("\\label{table:count2}\n");
        s.append("\\end{table}");
        System.out.println(s.toString());
    }

    public String getTypePercentage(List<CfgPair> types){
        Double all = new Double(this.all.size());
        Double result = (new Double(types.size()) / all ) * new Double(100);
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(result);
    }
}
