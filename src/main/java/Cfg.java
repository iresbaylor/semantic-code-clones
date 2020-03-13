import java.util.ArrayList;
import java.util.List;

public class Cfg implements Comparable<Cfg>{
    private String cfgNr; //0
    private String  moduleNr; //1
    private String moduleName; //2
    private String cfgName; //3
    private String argument; //4
    private String argument2; //5
    private String returnType; //6
    private String httpMethod; //7
    private List<Service> services; //8,9
    private String dbOperation; //10
    private String dbReturnType; //11

    public Cfg(){
        this.services = new ArrayList<>();
    }

    public void setValues(List<String> values){
        this.cfgNr = values.get(0);
        this.moduleNr = values.get(1);
        this.moduleName = values.get(2);
        this.cfgName = values.get(3);
        this.argument = values.get(4);
        this.argument2 = values.get(5);
        this.returnType = values.get(6);
        this.httpMethod = values.get(7);
        this.services.add(new Service(values.get(8), values.get(9)));
        this.dbOperation = values.get(9);
        this.dbReturnType = values.get(10);
        if (this.dbReturnType.equals("")){
            this.dbReturnType = this.returnType;
        }
    }

    public void setService(){

    }

    public String getCfgNr() {
        return cfgNr;
    }

    public void setCfgNr(String cfgNr) {
        this.cfgNr = cfgNr;
    }

    public String getModuleNr() {
        return moduleNr;
    }

    public void setModuleNr(String moduleNr) {
        this.moduleNr = moduleNr;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getCfgName() {
        return cfgName;
    }

    public void setCfgName(String cfgName) {
        this.cfgName = cfgName;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public String getArgument2() {
        return argument2;
    }

    public void setArgument2(String argument2) {
        this.argument2 = argument2;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getDbOperation() {
        return dbOperation;
    }

    public void setDbOperation(String dbOperation) {
        this.dbOperation = dbOperation;
    }

    public String getDbReturnType() {
        return dbReturnType;
    }

    public void setDbReturnType(String dbReturnType) {
        this.dbReturnType = dbReturnType;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void addServices(List<Service> services) {
        this.services.addAll(services);
    }

    @Override
    public String toString() {
        return "Cfg{" +
                "cfgNr=" + cfgNr +
                ", moduleNr=" + moduleNr +
                ", moduleName='" + moduleName + '\'' +
                ", cfgName='" + cfgName + '\'' +
                ", argument='" + argument + '\'' +
                ", argument2='" + argument2 + '\'' +
                ", returnType='" + returnType + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", services=" + services +
                ", dbOperation='" + dbOperation + '\'' +
                ", dbReturnType='" + dbReturnType + '\'' +
                '}';
    }

    @Override
    public int compareTo(Cfg cfg) {
        return (Integer.parseInt(this.cfgNr) < Integer.parseInt(cfg.getCfgNr()) ? 1 : 0);
    }
}
