public abstract class StaffMember {
    static int staffIDCounter = 1;
    protected final int id;
    protected String name;
    protected String address;

    public StaffMember(int id, String name, String address) {
        staffIDCounter++;
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAddress(
            String address){this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public abstract double pay();

    @Override
    public String toString() {
        return "StaffMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
