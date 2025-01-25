package university;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class HumanResource {
  HashMap<Staff, Float> staffSalary;

  HumanResource() {
    staffSalary = new HashMap<>();
  }

  public void addStaff(Staff newStaff) {
    float newStaffSalary;
    Random randomSalary = new Random();
    newStaffSalary =
        randomSalary.nextFloat((float) (newStaff.skill * 0.095), (float) (newStaff.skill * 0.105));
    staffSalary.put(newStaff, newStaffSalary);
  }

  public Iterator<Staff> getStaff() {
    return staffSalary.keySet().iterator();
  }

  public float getTotalSalary() {
    float totalSalary = 0;
    for (float i : staffSalary.values()) {
      totalSalary += i;
    }
    return totalSalary;
  }

  public HashMap<Staff, Float> getStaffHashMap() {
    return staffSalary;
  }

  public void removeStaff(Staff i) {
    staffSalary.remove(i);
  }
}
