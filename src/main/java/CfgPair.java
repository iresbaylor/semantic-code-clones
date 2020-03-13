public class CfgPair {
    private Cfg a;
    private Cfg b;
    private Double ctrl;
    private Double service;
    private Double db;
    private Double all;

    public CfgPair(){}

    public CfgPair(Cfg a, Cfg b) {
        this.a = a;
        this.b = b;
        ctrl = Comparer.compareCtrl(a, b);
        service = Comparer.compareService(a.getServices(), b.getServices());
        db = Comparer.compareDb(a, b);
        all = Comparer.compareAll(ctrl, service, db);
        if (a.getModuleName().equals("ts-order-other-service") && b.getModuleName().equals("ts-food-service")){

            System.out.println(a.getModuleName() + " & " + a.getCfgName() + " & " + b.getModuleName() + " & " + b.getCfgName() +
                    " & " + ctrl + " & " + service + " & " + db + " & " + all );
        }

        //ts-order-other-service & getOrderById & ts-food-service & findFoodOrderByOrderId


    }

    public Cfg getA() {
        return a;
    }

    public void setA(Cfg a) {
        this.a = a;
    }

    public Cfg getB() {
        return b;
    }

    public void setB(Cfg b) {
        this.b = b;
    }

    public Double getCtrl() {
        return ctrl;
    }

    public void setCtrl(Double ctrl) {
        this.ctrl = ctrl;
    }

    public Double getService() {
        return service;
    }

    public void setService(Double service) {
        this.service = service;
    }

    public Double getDb() {
        return db;
    }

    public void setDb(Double db) {
        this.db = db;
    }

    public Double getAll() {
        return all;
    }

    public void setAll(Double all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return "CfgPair{" +
                "a=" + a.getCfgNr() +
                ", b=" + b.getCfgNr() +
                ", ctrl=" + ctrl +
                ", service=" + service +
                ", db=" + db +
                ", all=" + all +
                '}';
    }
}
