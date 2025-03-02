import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<StaffMember> staffs = new ArrayList<>(List.of(
                new Volunteer(StaffMember.staffIDCounter, "Sebastian Wilder", "California", 2000),
                new SalariedEmployee(StaffMember.staffIDCounter, "Mia Dolan", "Nevada", 2500.50, 20),
                new HourlySalaryEmployee(StaffMember.staffIDCounter, "Luke Dunphy", "California", 40, 10.50),
                new Volunteer(StaffMember.staffIDCounter, "Alex Dunphy", "California", 5000.50),
                new HourlySalaryEmployee(StaffMember.staffIDCounter, "Claire Thompson", "Texas", 40, 15.5),
                new SalariedEmployee(StaffMember.staffIDCounter, "Kevin Brown", "Florida", 3550.50, 500),
                new Volunteer(StaffMember.staffIDCounter, "David Williams", "Arizona", 2100)
        ));
        MAIN :while (true){
            int selectedMainOption = Utility.menu();
            switch (selectedMainOption) {
                case 1 ->{
                    while (true){
                        int option = Utility.insertEmployeeMenu();
                        switch (option){
                            case 1-> Utility.inputStaffInfo(Volunteer.class, staffs);
                            case 2-> Utility.inputStaffInfo(SalariedEmployee.class, staffs);
                            case 3-> Utility.inputStaffInfo(HourlySalaryEmployee.class, staffs);
                            case 4->{
                                System.out.println("\n");
                                continue MAIN;
                            }
                        }
                    }
                }
                case 2 -> Utility.updateEmployeeByID(staffs);
                case 3 -> Utility.displayEmployeeList(staffs);
                case 4 -> Utility.removeEmployeeByID(staffs);
                case 5 ->{
                    System.out.println(Utility.GREEN + "============================================");
                    System.out.println("=    Thank You For Using This Program!!!   =");
                    System.out.println("=             Break a Leg!!!               =");
                    System.out.println("=                  \uD83D\uDE0A                      =");
                    System.out.println("============================================");
                    break MAIN;
                }
            }
        }
    }
}