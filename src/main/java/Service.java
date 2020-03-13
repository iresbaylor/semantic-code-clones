public class Service {

    private String reference; //8
    private String referenceReturnType; //9

    public Service(){}

    public Service(String reference, String referenceReturnType) {
        this.reference = reference;
        this.referenceReturnType = referenceReturnType;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReferenceReturnType() {
        return referenceReturnType;
    }

    public void setReferenceReturnType(String referenceReturnType) {
        this.referenceReturnType = referenceReturnType;
    }

    @Override
    public String toString() {
        return "Service{" +
                "reference='" + reference + '\'' +
                ", referenceReturnType='" + referenceReturnType + '\'' +
                '}';
    }
}
