import java.util.List;

public class Comparer {

    public static Double compareCtrl(Cfg a, Cfg b){
        Double compared = 0.0;
        Double same = 0.0;
        if (a.getArgument().equals(b.getArgument())){
            compared++;
            same++;
        } else {
            compared++;
        }

        if (!b.getArgument2().equals("") && !a.getArgument2().equals("")){
            if (a.getArgument2().equals(b.getArgument2())){
                same++;
            }
            compared++;

        }

        if (!b.getArgument2().equals("")){
            if (a.getArgument().equals(b.getArgument2())){
                same++;
            }
            compared++;
        }

        if (!a.getArgument2().equals("")){
            if (a.getArgument2().equals(b.getArgument())){
                same++;
            }
            compared++;
        }

        if (a.getHttpMethod().toLowerCase().equals(b.getHttpMethod().toLowerCase())){
            compared++;
            same++;
        } else {
            compared++;
        }

        if (a.getReturnType().toLowerCase().equals(b.getReturnType().toLowerCase())){
            compared++;
            same++;
        } else {
            compared++;
        }
        return Double.valueOf(same / compared);
    }

    public static Double compareService(List<Service> services, List<Service> services1) {
        Double same = 0.0;
        Double compared = 0.0;
        for (int i = 0; i < services.size(); i++){
            for (int j = 0; j < services1.size(); j++){
                compared++;
                if (services.get(i).getReference().equals(services1.get(j).getReference())){
                    same = same + 0.5;
                }
                if (services.get(i).getReferenceReturnType().equals(services1.get(j).getReferenceReturnType())){
                    same = same + 0.5;
                }
            }
        }
        return same / compared;
    }

    public static Double compareDb(Cfg a, Cfg b) {
        Double same = 0.0;
        Double compared = 2.0;
        if (a.getDbOperation().equals(b.getDbOperation())){
            same = same + 1.0;
        }
        if (a.getDbReturnType().equals(b.getDbReturnType())){
            same = same + 1.0;
        }
        return same / compared;
    }

    public static Double compareAll(Double ctrl, Double service, Double db) {
        return ( (ctrl*0.4) + (service*0.4) + (db*0.2) );
    }
}
