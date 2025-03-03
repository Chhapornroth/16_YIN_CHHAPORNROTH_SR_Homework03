import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;
import java.util.Scanner;

public class Utility {
    public static final String RED = "\u001b[31m";
    public static final String GREEN = "\u001b[32m";
    public static final String RESET_TEXT_COLOUR = "\u001b[0m";
    public static final String X_MARK = "❌";
    public static final String CHECK_MARK = "✅";

    public static int menu(){
        Table table = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
        CellStyle style = new CellStyle(CellStyle.HorizontalAlign.center);
        table.setColumnWidth(0, 40, 100);
        table.addCell("STAFF MANAGEMENT SYSTEM" , style);
        table.addCell("1. Insert Employee" , style);
        table.addCell("2. Update Employee" , style);
        table.addCell(" 3. Display Employee" , style);
        table.addCell("4. Remove Employee" , style);
        table.addCell("           5. Exit");
        System.out.println(table.render());
        System.out.println("--------------------------------------------");
        return Integer.parseInt(validation("^[1-5]$", "-> Choose an option() : ", "Option Only Accepts Value Between 1 to 5!!!"));
    }

    public static int insertEmployeeMenu(){
        System.out.println("\n============* INSERT EMPLOYEE *=============");
        System.out.println("Choose Type:");
        Table table = new Table(4, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
        table.addCell("1. Volunteer");
        table.addCell("2. Salaries Employee");
        table.addCell("3. Hourly Employee");
        table.addCell("4. Back");
        System.out.println(table.render());
        return Integer.parseInt(validation("^[1-4]$", "=> Enter Type Number : ", "Option Only Accepts Value Between 1 to 4!!!"));
    }

    public static <T extends StaffMember> void inputStaffInfo(Class<T> type, List<StaffMember> staffs){
        Scanner sc = new Scanner(System.in);
        System.out.println("=> ID : " + GREEN + StaffMember.staffIDCounter + RESET_TEXT_COLOUR);
        String name = validation("^[A-Za-z]+(?: [A-Za-z]+)*$", "=> Enter Name : ", "Name only accepts Letters and Spaces!!!");
        System.out.print("=> Enter Address : ");
        String address = sc.nextLine();
        if(type == Volunteer.class){
            double salary = Double.parseDouble(validation("^\\d+(\\.\\d+)?$", "=> Enter Salary : ", "It has to be a positive number!!!"));
            System.out.println(CHECK_MARK + GREEN + "You have added "+ name + " of type Volunteer successfully." + RESET_TEXT_COLOUR);
            staffs.add(new Volunteer(StaffMember.staffIDCounter, name, address, salary));
        }else if(type == SalariedEmployee.class){
            double salary = Double.parseDouble(validation("^\\d+(\\.\\d+)?$", "=> Enter Salary : ", "It has to be a positive number!!!"));
            double bonus = Double.parseDouble(validation("^\\d+(\\.\\d+)?$", "=> Enter the bonus amount : ", "It has to be a positive number!!!"));
            System.out.println(CHECK_MARK + GREEN + "You have added "+ name + " of type SalariedEmployee successfully." + RESET_TEXT_COLOUR);
            staffs.add(new SalariedEmployee(StaffMember.staffIDCounter, name, address, salary, bonus));
        }else if(type == HourlySalaryEmployee.class){
            int hourWorked = Integer.parseInt(validation("^\\d+$", "=> Enter the number of hours worked: ", "It has to be a positive number!!!"));
            double rate = Double.parseDouble(validation("^\\d+(\\.\\d+)?$", "=> Enter hourly wage : ", "It has to be a positive number!!!"));
            System.out.println(CHECK_MARK + GREEN + "You have added "+ name + " of type HourlySalaryEmployee successfully." + RESET_TEXT_COLOUR);
             staffs.add(new HourlySalaryEmployee(StaffMember.staffIDCounter, name, address, hourWorked, rate));
        }
    }

    public static void updateEmployeeByID(List<StaffMember> staffs){
        System.out.println("\n============* UPDATE EMPLOYEE *=============");
        int id = Integer.parseInt(validation("^\\d+$", "=> Enter or Search ID to update : ", "ID accepts only a positive number!!!"));
        staffs.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .ifPresentOrElse(staffMember -> updatingElement(id, staffMember),()->System.out.println(X_MARK + RED + " Staff Member with ID: " + id + ", doesn't exist!!!" + RESET_TEXT_COLOUR + "\n"));
    }

    public static void updatingElement(int id, StaffMember staffMember){
        String type = staffMember.getClass().getName();
        int col = type.equals("Volunteer") ? 6 : 7;
        MAIN:while (true){
            Table table = new Table(col, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
            CellStyle style = new CellStyle(CellStyle.HorizontalAlign.center);
            table.addCell("Type", style);
            table.addCell("ID", style);
            table.addCell("Name", style);
            table.addCell("Address", style);
            if(col == 6){
                table.addCell("Salary", style);
                type = "Volunteer";
            }else{
                if(type.equals("SalariedEmployee")){
                    table.addCell("Salary", style);
                    table.addCell("Bonus", style);
                }else {
                    table.addCell("Hour", style);
                    table.addCell("Rate", style);
                }
            }
            table.addCell("Pay", style);
            table.addCell(type, style);
            table.addCell(String.valueOf(id), style);
            table.addCell(String.valueOf(staffMember.getName()), style);
            table.addCell(String.valueOf(staffMember.getAddress()), style);
            switch (type) {
                case "Volunteer" -> {
                    Volunteer volunteer = (Volunteer) staffMember;
                    table.addCell("$" + volunteer.getSalary());
                }
                case "SalariedEmployee" -> {
                    SalariedEmployee salariedEmployee = (SalariedEmployee) staffMember;
                    table.addCell("$" + salariedEmployee.getSalary());
                    table.addCell("$" + salariedEmployee.getBonus());
                }
                case "HourlySalaryEmployee" -> {
                        HourlySalaryEmployee hourlySalaryEmployee = (HourlySalaryEmployee) staffMember;
                    table.addCell(String.valueOf(hourlySalaryEmployee.getHourWorked()));
                    table.addCell("$" + hourlySalaryEmployee.getRate());
                }
            }
            table.addCell("$" + staffMember.pay(), style);
            System.out.println(table.render());
            int option = 0;
            switch (type){
                case "Volunteer" -> option = updateMenu("Volunteer");
                case "SalariedEmployee" -> option = updateMenu("SalariedEmployee");
                case "HourlySalaryEmployee" -> option = updateMenu("HourlySalaryEmployee");
            }
            switch (option){
                case 1->{
                    String newName = validation("^[A-Za-z]+(?: [A-Za-z]+)*$", "=> Change name to : ", "Name only accepts Letters and Spaces!!!");
                    staffMember.setName(newName);
                    System.out.println(CHECK_MARK + GREEN + " Name has updated successfully!!!" + RESET_TEXT_COLOUR + "\n");
                }
                case 2->{
                    System.out.print("=> Change address to : ");
                    Scanner sc = new Scanner(System.in);
                    String newAddress = sc.nextLine();
                    staffMember.setAddress(newAddress);
                    System.out.println(CHECK_MARK + GREEN + " Address has updated successfully!!!" + RESET_TEXT_COLOUR + "\n");
                }
                case 3->{
                    if (type.equals("HourlySalaryEmployee")) {
                        int newHourWorked = Integer.parseInt(validation("^\\d+$", "=> Change the number of hours worked to : ", "It has to be a positive number!!!"));
                        ((HourlySalaryEmployee)staffMember).setHourWorked(newHourWorked);
                        System.out.println(CHECK_MARK + GREEN + " Hour Worked has updated successfully!!!" + RESET_TEXT_COLOUR + "\n");
                    } else {
                        double newSalary = Double.parseDouble(validation("^\\d+(\\.\\d+)?$", "=> Change salary to : ", "It has to be a positive number!!!"));
                        if (type.equals("Volunteer")) {
                            ((Volunteer)staffMember).setSalary(newSalary);
                        } else {
                            ((SalariedEmployee)staffMember).setSalary(newSalary);
                        }
                        System.out.println(CHECK_MARK + GREEN + " Salary has updated successfully!!!" + RESET_TEXT_COLOUR + "\n");
                    }
                }
                case 4->{
                    if(type.equals("SalariedEmployee")){
                        double newBonus = Double.parseDouble(validation("^\\d+(\\.\\d+)?$", "=> Change bonus amount to : ", "It has to be a positive number!!!"));
                        ((SalariedEmployee) staffMember).setBonus(newBonus);
                        System.out.println(CHECK_MARK + GREEN + " Bonus has updated successfully!!!" + RESET_TEXT_COLOUR + "\n");
                    }else if(type.equals("HourlySalaryEmployee")){
                        double newRate = Double.parseDouble(validation("^\\d+(\\.\\d+)?$", "=> Change rate to : ", "It has to be a positive number!!!"));
                        ((HourlySalaryEmployee) staffMember).setRate(newRate);
                        System.out.println(CHECK_MARK + GREEN + " Rate has updated successfully!!!" + RESET_TEXT_COLOUR + "\n");
                    }
                }
                case 0->{
                    System.out.println("\n");
                    break MAIN;
                }
            }
        }
    }

    public static int updateMenu(String type){
        System.out.println("Choose one column to update : ");
        System.out.print("1.Name   2.Address   ");
        switch (type) {
            case "Volunteer" -> System.out.print("3.Salary");
            case "SalariedEmployee" -> System.out.print("3.Salary   4.Bonus");
            case "HourlySalaryEmployee" -> System.out.print("3.Hour worked   4.Rate");
        }
        System.out.print("   0.Cancel\n");
        return Integer.parseInt(validation(type.equals("Volunteer") ? "^[0-3]$" : "^[0-4]$", "=> Select Column Number : ", "Option only accepts only value between 0 to 3!!!"));
    }

    public static void displayEmployeeList(List<StaffMember> staffs){
        int pageSize = 3;
        int currentPage = 1;
        int totalPages = (int) Math.ceil((double) staffs.size() / pageSize);
        System.out.println("\n============* DISPLAY EMPLOYEE *============");
        while(true){
            System.out.println("\n");
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, staffs.size());
            List<StaffMember> staffsList = staffs.subList(start, end);
            Table table = getTable();
            CellStyle style = new CellStyle(CellStyle.HorizontalAlign.center);
            staffsList.forEach(staff ->{
                String type = staff.getClass().getName();
                table.addCell(type, style);
                table.addCell(staff.getId() + "", style);
                table.addCell(staff.getName(), style);
                table.addCell(staff.getAddress(), style);
                switch (type) {
                    case "Volunteer" -> {
                        table.addCell("$" + ((Volunteer) staff).getSalary(), style);
                        table.addCell("---", style);
                        table.addCell("---", style);
                        table.addCell("---", style);
                    }
                    case "SalariedEmployee" -> {
                        table.addCell("$" + ((SalariedEmployee) staff).getSalary(), style);
                        table.addCell("$" + ((SalariedEmployee) staff).getBonus(), style);
                        table.addCell("---", style);
                        table.addCell("---", style);
                    }
                    case "HourlySalaryEmployee" -> {
                        table.addCell("---", style);
                        table.addCell("---", style);
                        table.addCell(((HourlySalaryEmployee) staff).getHourWorked() + "", style);
                        table.addCell("$" + ((HourlySalaryEmployee) staff).getRate(), style);
                    }
                }
                table.addCell("$" + staff.pay(), style);
            });
            System.out.println(table.render());
            System.out.println("Page: " + currentPage + "/" + totalPages + "\n");
            System.out.println("1.First Page    2.Next Page    3.Previous Page    4.Last Page    5.Exit");
            int choice = Integer.parseInt(validation("^[1-5]$", "=> Choose : ", "Option Only Accepts Value Between 1 to 5!!!"));
            switch (choice) {
                case 1 -> currentPage = 1;

                case 2 -> {
                    if (currentPage < totalPages) currentPage++;
                }
                case 3 -> {
                    if (currentPage > 1) currentPage--;
                }
                case 4 -> currentPage = totalPages;
                case 5 -> {
                    System.out.println("\n");
                    return;
                }
            }
        }
    }

    private static Table getTable() {
        Table table = new Table(9, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        CellStyle style = new CellStyle(CellStyle.HorizontalAlign.center);
        table.addCell("Type", style);
        table.addCell("ID", style);
        table.addCell("Name", style);
        table.addCell("Address", style);
        table.addCell("Salary", style);
        table.addCell("Bonus", style);
        table.addCell("Hour", style);
        table.addCell("Rate", style);
        table.addCell("Pay", style);
        return table;
    }

    public static void removeEmployeeByID(List<StaffMember> staffs){
        System.out.println("\n============* REMOVE EMPLOYEE *=============");
        int id = Integer.parseInt(validation("^\\d+$", "=> Enter ID to remove : ", "ID accepts only a positive number!!!"));
        boolean isRemoved = staffs.removeIf(staffMember -> staffMember.getId() == id);
        if(isRemoved){
            System.out.println(CHECK_MARK + GREEN + " Staff Member with ID: " + id + ", has been Removed successfully!!!" + RESET_TEXT_COLOUR + "\n");
        }else {
            System.out.println(X_MARK + RED + " Staff Member with ID: " + id + ", doesn't exist!!!" + RESET_TEXT_COLOUR + "\n");
        }
    }

    public static String validation(String regex, String inputMessage, String errorMessage){
        Scanner sc = new Scanner(System.in);
        String string;
        while (true){
            System.out.print(inputMessage);
            string = sc.nextLine();
            if (string.matches(regex)){
                return string;
            }else {
                System.out.println(RED + X_MARK + " Invalid format. " + errorMessage + RESET_TEXT_COLOUR + "\n");
            }
        }
    }
}
