package project.gui;

import java.util.*;

public class Utils {

    Scanner sc;

    public Utils(Scanner scanner) {
        sc = scanner;
    }
    
    public String inputTime() {

        String time = "";

        int year, month, day, hour, minute;
        
        while(true) {
            try {
                year = sc.nextInt();
                sc.nextLine();
                if(year <= 1970 || year >= 2100)
                    System.out.println("Invalid year");
                else
                    break;
            } catch(InputMismatchException e) {
                System.out.println("Invalid year");
                sc.next();
            }
        }

        while(true) {
            try {
                month = sc.nextInt();
                sc.nextLine();
                if(month < 1 || month > 12)
                    System.out.println("Invalid month");
                else
                    break;
            } catch(InputMismatchException e) {
                System.out.println("Invalid month");
                sc.next();
            }
        }

        while(true) {
            try {
                day = sc.nextInt();
                sc.nextLine();
                boolean check1 = year % 4 == 0 && month == 2 && (day > 29 || day < 1);
                boolean check2 = year % 4 != 0 && month == 2 && (day > 28 || day < 1);
                boolean check3 = (month == 4 || month == 6 || month == 9 || month == 11) && (day > 30 || day < 1);
                boolean check4 = day > 31 || day < 1;

                if(check1 || check2 || check3 || check4)
                    System.out.println("Invalid day");
                else
                    break;
            } catch(InputMismatchException e) {
                System.out.println("Invalid day");
                sc.next();
            }
        }

        while(true) {
            try {
                hour = sc.nextInt();
                sc.nextLine();
                boolean check5 = hour < 1 || hour > 12;

                if(check5)
                    System.out.println("Invalid hour");
                else {
                    hour %= 12;
                    break;
                }
            } catch(InputMismatchException e) {
                System.out.println("Invalid hour");
                sc.next();
            }
        }

        while(true) {
            try {
                minute = sc.nextInt();
                sc.nextLine();
                boolean check6 = minute < 0 || minute > 59;

                if(check6)
                    System.out.println("Invalid minute");
                else
                    break;
            } catch(InputMismatchException e) {
                System.out.println("Invalid minute");
                sc.next();
            }
        }

        String ampm;

        while(true) {
            ampm = sc.nextLine();
            if(!(ampm.equals("AM") || ampm.equals("PM")))
                System.out.println("Invalid input");
            else
                break;
            
            if(ampm.equals("PM"))
                hour += 12;

        }

        time = Integer.toString(year) + "-";
        if(month < 10)
            time += "0" + Integer.toString(month) + "-";
        else
            time += month + "-";
        if(day < 10)
            time += "0" + Integer.toString(day) + " ";
        else
            time += day + " ";
        if(hour < 10)
            time += "0" + Integer.toString(hour) + ":";
        else
            time += hour + ":";
        if(minute < 10)
            time += "0" + Integer.toString(minute);
        else
            time += minute;

        return time;

    }
    
    public String inputTime(String time) {

        String y, m, d, h, min;

        y = time.split("-")[0];
        m = time.split("-")[1];
        d = time.split("-")[2].split(" ")[0];
        h = time.split("-")[2].split(" ")[1].split(":")[0];
        min = time.split("-")[2].split(" ")[1].split(":")[1];

        System.out.println("Press 0 to skip");
        System.out.println("Current year: " + y);
        int year, month, day, hour, minute;
        
        while(true) {
            try {
                year = sc.nextInt();
                sc.nextLine();
                if(year != 0 && (year <= 1970 || year >= 2100))
                    System.out.println("Invalid year");
                else
                    break;
            } catch(InputMismatchException e) {
                System.out.println("Invalid year");
                sc.next();
            }
        }

        if(year == 0)
            year = Integer.parseInt(y);

        System.out.println("Current month: " + m);

        while(true) {
            try {
                month = sc.nextInt();
                sc.nextLine();
                if(month != 0 && (month < 1 || month > 12))
                    System.out.println("Invalid month");
                else
                    break;
            } catch(InputMismatchException e) {
                System.out.println("Invalid month");
                sc.next();
            }
        }

        if(month == 0)
            month = Integer.parseInt(m);

        System.out.println("Current day: " + d);

        while(true) {
            try {
                day = sc.nextInt();
                sc.nextLine();
                boolean check0 = day != 0;
                boolean check1 = year % 4 == 0 && month == 2 && (day > 29 || day < 1);
                boolean check2 = year % 4 != 0 && month == 2 && (day > 28 || day < 1);
                boolean check3 = (month == 4 || month == 6 || month == 9 || month == 11) && (day > 30 || day < 1);
                boolean check4 = day > 31 || day < 1;

                if(check0 && (check1 || check2 || check3 || check4))
                    System.out.println("Invalid day");
                else
                    break;
            } catch(InputMismatchException e) {
                System.out.println("Invalid day");
                sc.next();
            }
        }

        if(day == 0)
            day = Integer.parseInt(d);

        System.out.println("Current hour: " + h);

        while(true) {
            try {
                hour = sc.nextInt();
                sc.nextLine();
                boolean check5 = hour < 1 || hour > 12;
                boolean check6 = hour != 0;

                if(check6 && check5)
                    System.out.println("Invalid hour");
                else {
                    break;
                }
            } catch(InputMismatchException e) {
                System.out.println("Invalid hour");
                sc.next();
            }
        }

        if(hour == 0)
            hour = Integer.parseInt(h);
        hour %= 12;

        System.out.println("Current minute(Enter 60 to skip): " + min);
        
        while(true) {
            try {
                minute = sc.nextInt();
                sc.nextLine();
                boolean check7 = minute < 0 || minute > 60;

                if(check7)
                    System.out.println("Invalid minute");
                else
                    break;
            } catch(InputMismatchException e) {
                System.out.println("Invalid minute");
                sc.next();
            }
        }

        if(minute == 60)
            minute = Integer.parseInt(min);

        String ampm, currentAMPM;
        if(Integer.parseInt(h) > 11)
            currentAMPM = "PM";
        else
            currentAMPM = "AM";

        System.out.println("Current AM/PM(Enter to skip): " + currentAMPM);

        while(true) {
            ampm = sc.nextLine();
            if(ampm.isEmpty()) {
                ampm = currentAMPM;
                break;
            }
            else if(!(ampm.equals("AM") || ampm.equals("PM")))
                System.out.println("Invalid input");
            else
                break;
        }

        if(ampm.equals("PM"))
            hour += 12;

        time = Integer.toString(year) + "-";
        if(month < 10)
            time += "0" + Integer.toString(month) + "-";
        else
            time += month + "-";
        if(day < 10)
            time += "0" + Integer.toString(day) + " ";
        else
            time += day + " ";
        if(hour < 10)
            time += "0" + Integer.toString(hour) + ":";
        else
            time += hour + ":";
        if(minute < 10)
            time += "0" + Integer.toString(minute);
        else
            time += minute;

        return time;

    }

}
