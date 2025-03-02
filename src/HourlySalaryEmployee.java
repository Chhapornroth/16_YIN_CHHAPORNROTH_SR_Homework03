public class HourlySalaryEmployee extends StaffMember{
    private int hourWorked;
    private double rate;

    public HourlySalaryEmployee(int id, String name, String address, int hourWorked, double rate) {
        super(id, name, address);
        this.hourWorked = hourWorked;
        this.rate = rate;
    }

    public int getHourWorked() {
        return hourWorked;
    }

    public void setHourWorked(int hourWorked) {
        this.hourWorked = hourWorked;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public double pay(){
        return rate * hourWorked;
    }

    @Override
    public String toString() {
        return "HourlySalaryEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", hourWorked=" + hourWorked +
                ", rate=" + rate +
                '}';
    }
}
