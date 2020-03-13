import java.util.Objects;

public class ModulePair {
    private String a;
    private String b;

    public ModulePair(){}

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ModulePair)) return false;
        ModulePair modulePair = (ModulePair) o;
        return (this.getA().equals(modulePair.getA()) && this.getB().equals(modulePair.getB())) ||
                (this.getB().equals(modulePair.getA()) && this.getA().equals(modulePair.getB()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
