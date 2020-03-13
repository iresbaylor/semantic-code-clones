import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModuleCluster {

    private List<Module> modules;
    private Integer totalCount;

    public ModuleCluster(){
        this.modules = new ArrayList<>();
        for (int i = 1; i < 38; i++){
            modules.add(new Module( new Integer(i).toString()));
        }
        this.totalCount = 0;
    }

    public void addCfgToModule(Cfg cfg){
        this.totalCount++;
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getModuleNr().equals(cfg.getModuleNr())) {
                modules.get(i).addCfg(cfg);
            }
        }
    }

    public void addCfgPairToModule(CfgPair cfg){
        this.totalCount++;
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getModuleNr().equals(cfg.getA().getModuleNr())) {
                modules.get(i).addCfgPair(cfg);
            }
            if (modules.get(i).getModuleNr().equals(cfg.getB().getModuleNr())) {
                modules.get(i).addCfgPair(cfg);
            }
        }
    }


    public void printClusterA() {
        Arrays.sort(modules.toArray());
        System.out.println("======== CLUSTER A ======== ");
        StringBuilder s = new StringBuilder();
        s.append("\\begin{table}[h!]\n");
        s.append("\\begin{tabular}{|l|l|l|}\n");
        s.append("\\hline\n");
        s.append("Mod Number & Module Name & Nr of CFGs \\\\ \\hline ");
        System.out.println(s.toString());
        for (int i = 0; i < modules.size(); i++) {
            modules.get(i).printModuleA(modules);
        }
        s = new StringBuilder();
        s.append("\\end{tabular}\n");
        s.append("\\caption{CFG clones percentage}\n");
        s.append("\\label{table:count2}\n");
        s.append("\\end{table}");
        System.out.println(s.toString());
    }

    public void printClusterB() {
        Arrays.sort(modules.toArray());
        System.out.println("======== CLUSTER B ======== ");
        StringBuilder s = new StringBuilder();
        s.append("\\begin{table}[h!]\n");
        s.append("\\begin{tabular}{|l|l|l|}\n");
        s.append("\\hline\n");
        s.append("Mod Number & Module Name & Nr of CFGs \\\\ \\hline ");
        System.out.println(s.toString());
        for (int i = 0; i < modules.size(); i++) {
            modules.get(i).printModuleB(modules);
        }
        s = new StringBuilder();
        s.append("\\end{tabular}\n");
        s.append("\\caption{CFG clones percentage}\n");
        s.append("\\label{table:count2}\n");
        s.append("\\end{table}");
        System.out.println(s.toString());
    }


    public void printClusterC() {
        Arrays.sort(modules.toArray());
        System.out.println("======== CLUSTER C ======== ");
        StringBuilder s = new StringBuilder();
        s.append("\\begin{table}[h!]\n");
        s.append("\\begin{tabular}{|l|l|l|}\n");
        s.append("\\hline\n");
        s.append("Mod Number & Module Name & Nr of CFGs \\\\ \\hline ");
        System.out.println(s.toString());
        for (int i = 0; i < modules.size(); i++) {
            modules.get(i).printModuleC(modules);
        }
        s = new StringBuilder();
        s.append("\\end{tabular}\n");
        s.append("\\caption{CFG clones percentage}\n");
        s.append("\\label{table:count2}\n");
        s.append("\\end{table}");
        System.out.println(s.toString());
    }



//    public void printCluster2() {
//        Arrays.sort(modules.toArray());
//        System.out.println("======== CLUSTER2 ======== ");
//        for (int i = 0; i < modules.size(); i++) {
//            modules.get(i).printModule2();
//        }
//    }
}
