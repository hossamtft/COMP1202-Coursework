import facilities.Facility;
import facilities.buildings.Building;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import university.Staff;
import university.University;

/**
 * Class for ECS simulation
 */
public class EcsSim {
  University university;
  public ArrayList<Staff> staffMarket;
  float reservedBudget;
  float budgetToSpend;

  /**
   * Simulation method.
   * @param university1 university to simulate
   */
  EcsSim(University university1) {
    this.university = university1;
    staffMarket = new ArrayList<>();
  }

  /**
   * reserve Maintenance cost + Existing staff Salaries and the maximum salary of an extra staff
   * that we expect to hire. Allocates budgetToSpend as what is left over from the reserved budget
   */
  public void allocateBudgets() {
    // Reserving budget
    reservedBudget =
        (float)
            (this.university.getEstate().getMaintenanceCost()
                + this.university.getHumanResource().getTotalSalary()
                + 10.5);
    // Starting budget
    budgetToSpend = this.university.getBudget() - reservedBudget;
    if (reservedBudget > this.university.getBudget()) {
      System.out.println("Budget isn't enough at start of year");
    }
  }

  /** Check if there is one of each building, if none exist build 1. */
  public void buildOneOfEach() {
    // Check if there is at least one of each building
    int hallAmount = 0;
    int labAmount = 0;
    int theatreAmount = 0;
    for (Facility xz : this.university.getEstate().getFacilities()) {
      if (xz instanceof Building) {
        if (((Building) xz).getBuildingType().equals("Hall")) {
          hallAmount += 1;
        } else if (((Building) xz).getBuildingType().equals("Lab")) {
          labAmount += 1;
        } else if (((Building) xz).getBuildingType().equals("Theatre")) {
          theatreAmount += 1;
        }
      }
    }
    // if not build one if budget allows you to
    if (theatreAmount == 0) {
      if (budgetToSpend > 200) { // 200 is base building cost of Theatre
        budgetToSpend = budgetToSpend - 200;
        this.university.build("Theatre", "Theatre 1");
        System.out.println("no Theatre in university, Building a Theatre Now");
      }
    }
    if (labAmount == 0) {
      if (budgetToSpend > 300) { // 300 is base building cost of Lab
        budgetToSpend = budgetToSpend - 300;
        this.university.build("Lab", "Lab 1");
        System.out.println("no Labs in university, Building a Lab Now");
      }
    }
    if (hallAmount == 0) {
      if (budgetToSpend > 100) { // 100 is base building cost of Hall
        budgetToSpend = budgetToSpend - 100;
        this.university.build("Hall", "Student Hall 1");
        System.out.println("no Halls in university, Building a Hall Now");
      }
    }
  }

  /**
   * A budget of over 5000 is considered an excess budget. This is used to build extra facilities to
   * make simulation grow faster.
   */
  public void excessBudget() {
    if (budgetToSpend > 5000) {
      // Excess Money So Take Advantage and build facilities
      // Use more than half available budget to build new facilities
      // Builds 6 of each type of building
      this.university.build("Theatre", "Theatre Expansion");
      this.university.build("Theatre", "Theatre Expansion");
      this.university.build("Theatre", "Theatre Expansion");
      this.university.build("Theatre", "Theatre Expansion");
      this.university.build("Theatre", "Theatre Expansion");
      this.university.build("Theatre", "Theatre Expansion");
      this.university.build("Hall", "Hall Expansion");
      this.university.build("Hall", "Hall Expansion");
      this.university.build("Hall", "Hall Expansion");
      this.university.build("Hall", "Hall Expansion");
      this.university.build("Hall", "Hall Expansion");
      this.university.build("Hall", "Hall Expansion");
      this.university.build("Lab", "Lab Expansion");
      this.university.build("Lab", "Lab Expansion");
      this.university.build("Lab", "Lab Expansion");
      this.university.build("Lab", "Lab Expansion");
      this.university.build("Lab", "Lab Expansion");
      this.university.build("Lab", "Lab Expansion");
      budgetToSpend -= 3600;
    }
  }

  /**
   * Goes through all buildings in university. If budget allows university to upgrade the building
   * it is upgraded.
   */
  public void upgradeAll() {
    // Check for each building that already exits if the budget allows upgrading it
    for (Facility i : this.university.getEstate().getFacilities()) {
      if (i instanceof Building) {
        if (((Building) i).getLevel() != ((Building) i).getMaxLevelOfBuilding()) {
          if (budgetToSpend > ((Building) i).getUpgradeCost()) {
            // if budget allows upgrading it upgrade building
            try {
              budgetToSpend = budgetToSpend - ((Building) i).getUpgradeCost();
              this.university.upgrade((Building) i);
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
            System.out.println(
                i.getName() + " has been upgraded to level " + ((Building) i).getLevel());
            System.out.println("Remaining Budget " + this.university.getBudget());
          }
        }
      }
    }
  }

  /** Check if all buildings are at max level, if they are build one of each building type. */
  public void maxCheckAndBuild() {
    // Check how many buildings at max level
    int buildingsAtMax = 0;
    for (Facility maxCheck : this.university.getEstate().getFacilities()) {
      if (maxCheck instanceof Building) {
        if (((Building) maxCheck).getLevel() == ((Building) maxCheck).getMaxLevelOfBuilding()) {
          buildingsAtMax++;
        }
      }
    }
    // if all Buildings at max level build new ones if budget allows u to build more buildings
    if (buildingsAtMax == this.university.getEstate().getFacilities().length) {
      if (budgetToSpend > 200) {
        this.university.build("Theatre", "Theatre Expansion");
        System.out.println(
            "All theatres reached max level in university," + " Building a new theatres Now");
      }
      if (budgetToSpend > 300) { // 300 is base building cost of Lab
        this.university.build("Lab", "Lab Expansion");
        System.out.println("All labs reached max level in university, Building a new lab Now");
      }
      if (budgetToSpend > 100) { // 100 is base building cost of Hall
        this.university.build("Hall", "Student Hall Expansion");
        System.out.println("All halls reached max level in university, Building a new hall Now");
      }
    }
  }

  /** Each student contributes 10 ECS coins to the university every year. */
  public void studentTuitionFees() {
    int totalStudents = this.university.getEstate().getNumberOfStudents();
    // Increase budget for every student by 10 ECS coins
    this.university.increaseBudget(totalStudents * 10);
    System.out.println(
        "The University budget after "
            + "increasing 10 ECS coins for each student is now "
            + this.university.getBudget());
  }

  /** Have a minimum of 2 staffs at all time, and at least 1 staff for each 100 students. */
  public void hireStaff() {
    int totalStudents = this.university.getEstate().getNumberOfStudents();
    while (university.getHumanResource().getStaffHashMap().size() < 2) {
      // Randomly chooses staff from staff market
      Random staffRandom = new Random();
      int staffHired = staffRandom.nextInt(staffMarket.size());
      this.university.getHumanResource().addStaff(staffMarket.get(staffHired));
      System.out.println(staffMarket.get(staffHired).staffName() + "has been hired");
      // remove hired staff from staff market, so they aren't hired again
      staffMarket.remove(staffHired);
    } // for every 100 students insure there is at least 1 staff;
    while (totalStudents / this.university.getHumanResource().getStaffHashMap().size() > 100) {
      // Randomly chooses staff to hire from staff market
      Random staffRandom = new Random();
      int staffHired = staffRandom.nextInt(staffMarket.size());
      this.university.getHumanResource().addStaff(staffMarket.get(staffHired));
      System.out.println(staffMarket.get(staffHired).staffName() + "has been hired");
      // remove hired staff from staff market, so they aren't hired again
      staffMarket.remove(staffHired);
    }
  }

  /**
   * Divide students evenly by amount of teachers assign first teacher to instruct any remainder.
   * students Deduct 1 point each uninstructed student.
   */
  public void instructStudents() {
    int totalStudents = this.university.getEstate().getNumberOfStudents();
    // Calculate how much staff you have
    int staffAmount = this.university.getHumanResource().getStaffHashMap().size();
    int studentsTaught = 0;
    int studentsBeingTaught;
    int remainderStudents = 0;

    // make sure all students are instructed
    if (totalStudents % staffAmount != 0) {
      remainderStudents = totalStudents % staffAmount;
    }
    studentsBeingTaught = totalStudents / staffAmount;
    for (Iterator<Staff> it = this.university.getHumanResource().getStaff(); it.hasNext(); ) {
      Staff i = it.next();
      i.instruct(studentsBeingTaught + remainderStudents);
      remainderStudents = 0;
      this.university.increaseReputation(i.instruct(studentsBeingTaught));
      studentsTaught = studentsTaught + studentsBeingTaught;
    }
    int uninstructedStudents = totalStudents - studentsTaught;
    System.out.println(studentsTaught + " Students have been instructed");
    System.out.println(uninstructedStudents + " Students have not been instructed");
    this.university.decreaseReputation(uninstructedStudents);
  }

  /** Pay the maintenance fees of the estate. */
  public void payMaintenanceFees() {
    // Checks if uni can pay maintenance cost and pays it
    if (this.university.getBudget() > this.university.getEstate().getMaintenanceCost()) {
      this.university.reduceBudget((int) this.university.getEstate().getMaintenanceCost());
      reservedBudget -= this.university.getEstate().getMaintenanceCost();
      System.out.println(
          "Maintenance of " + this.university.getEstate().getMaintenanceCost() + " payed");
    } else {
      System.out.println("Insufficient funds Cannot Pay Maintenance Cost");
      try {
        throw new Exception("University went bankrupt");
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  /** Pay all the staffs salaries. */
  public void payStaffSalaries() {
    // Checks if uni can pay staff salaries and pays it
    if (this.university.getBudget() > this.university.getHumanResource().getTotalSalary()) {
      this.university.reduceBudget((int) this.university.getHumanResource().getTotalSalary());
      reservedBudget -= this.university.getHumanResource().getTotalSalary();
      System.out.println(
          "Salaries of " + this.university.getHumanResource().getTotalSalary() + " payed");
    } else {
      System.out.println("Insufficient funds Cannot Pay Salaries for all Staff ");
      try {
        throw new Exception("University went bankrupt");
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  /** Increase every staffs years of teaching by 1. */
  public void increaseYearsOfTeaching() {
    // increases all staff years of teaching by 1
    for (Staff xyz : this.university.getHumanResource().getStaffHashMap().keySet()) {
      xyz.increaseYearsoOfTeaching();
    }
  }

  /**
   * Checks if years of teaching has reached 30, if yes then the staff leaves the university.
   * otherwise, the staffs stamina is the chance they stay.
   */
  public void staffLeaving() {
    ArrayList<Staff> staffRetiring = new ArrayList<>();
    // Checks according to logic provided in coursework if any staff is going to leave the uni
    for (Staff xz : this.university.getHumanResource().getStaffHashMap().keySet()) {
      if (xz.getYearsOfTeaching() == 30) { // if staff teaches 30 years they retire
        staffRetiring.add(xz);
        System.out.println(
            xz.staffName() + "Has reached 30 years of teaching and has left the university");
      } else {
        Random random = new Random();
        int chanceToStay = random.nextInt(101);
        if (chanceToStay > xz.getStamina()) {
          // stamina is the chance that the staff stay for another year
          staffRetiring.add(xz);
          System.out.println(xz.staffName() + "Has left the university");
        }
      }
    }
    for (Staff xyz : staffRetiring) {
      this.university.getHumanResource().removeStaff(xyz);
    }
  }

  /** replenish the stamina of all staff in the university. */
  public void replenishStamina() {
    for (Staff xyz : this.university.getHumanResource().getStaffHashMap().keySet()) {
      xyz.replenishStamina();
    }
  }

  /** Contains methods for beginning, middle and end of year. */
  public void simulate() {
    // Beginning Of Year
    allocateBudgets();
    buildOneOfEach();
    excessBudget();
    upgradeAll();
    maxCheckAndBuild();
    studentTuitionFees();
    hireStaff();

    // During the year
    instructStudents();

    // End of Year
    payMaintenanceFees();
    payStaffSalaries();
    increaseYearsOfTeaching();
    staffLeaving();
    replenishStamina();
  }


  /**
   *  Simulates the university for multiple amount of years.
   * @param years amount of years simulation should run
   */
  public void simulate(int years) {
    for (int i = 1; i <= years; i++) {
      System.out.println("Start of Simulation of Year" + i);
      simulate();
      System.out.println("Year " + i + " of simulation ends.");
      System.out.println("University Reputation: " + this.university.getReputation());
      System.out.println("University Budget: " + this.university.getBudget());
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        System.out.println("Simulation interrupted.");
        break;
      }
    }
  }

  /**
   * loads staff file, where format is "Name (Skill)" adds all staff from file to the Staff Market.
   * @param staffFileName name of file containing all staff that should be in my staff market
   */
  public void loadConfigurationFile(String staffFileName) {
    try {
      FileReader configReader = new FileReader(staffFileName);
      BufferedReader reader = new BufferedReader(configReader);
      String line = reader.readLine();
      while (line != null) {
        String[] parts = line.split("\\s*\\(\\s*|\\s*\\)");
        if (parts.length == 2) {
          String staffName = parts[0];
          int staffSkill = Integer.parseInt(parts[1]);
          this.staffMarket.add(new Staff(staffName, staffSkill));
        }
        line = reader.readLine();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /** Checks if amount of arguments inputted is correct.
   * Loads staff config file from first arg, initial funding from second arg
   * and amount of years to simulate from 3rd arg
   * */
  public static void main(String[] args) {
    if (args.length != 3) {
      System.out.println(
          "Try again with Usage:java EcsSim Staff_Config_File "
              + "Initial_Funding Years_To_Simulate");
      return;
    }
    String staffFileName = args[0];
    int uniFunding = Integer.parseInt(args[1]);
    int yearsToStimulate = Integer.parseInt(args[2]);
    University university = new University(uniFunding);
    EcsSim simulationEcs = new EcsSim(university);
    simulationEcs.loadConfigurationFile(staffFileName);
    simulationEcs.simulate(yearsToStimulate);
  }
}
