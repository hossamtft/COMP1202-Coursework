package university;

import facilities.Facility;
import facilities.buildings.Building;
import facilities.buildings.Hall;
import facilities.buildings.Lab;
import facilities.buildings.Theatre;
import java.util.ArrayList;

public class Estate {
  ArrayList<Facility> facilities;

  public Estate() {
    facilities = new ArrayList<>();
  }

  public Facility[] getFacilities() {
    return this.facilities.toArray(new Facility[0]);
  }

  public Facility addFacility(String type, String name) {

    Facility addedFacility;
    if (type.equals("Hall")) {
      addedFacility = new Hall(name);
    } else if (type.equals("Lab")) {
      addedFacility = new Lab(name);
    } else if (type.equals("Theatre")) {
      addedFacility = new Theatre(name);
    } else {
      addedFacility = null;
    }
    if (addedFacility != null) {
      this.facilities.add(addedFacility);
    }
    return addedFacility;
  }

  public float getMaintenanceCost() {
    float totalCost = 0;
    for (Facility i : this.facilities) {
      if (i instanceof Building) {
        totalCost += (float) (0.10 * ((Building) i).getCapacity());
      }
    }
    return totalCost;
  }

  public int getNumberOfStudents() {
    int totalHallsCapacity = 0;
    int totalLabsCapacity = 0;
    int totalTheatresCapacity = 0;

    for (Facility x : facilities) {
      if (x instanceof Hall) {
        totalHallsCapacity += ((Building) x).getCapacity();
      } else if (x instanceof Lab) {
        totalLabsCapacity += ((Building) x).getCapacity();
      } else if (x instanceof Theatre) {
        totalTheatresCapacity += ((Building) x).getCapacity();
      }
    }
    return Math.min(Math.min(totalHallsCapacity, totalLabsCapacity), totalTheatresCapacity);
  }
}
