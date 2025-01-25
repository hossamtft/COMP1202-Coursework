package facilities.buildings;

import facilities.Facility;

public class Lab extends Facility implements Building {
  int level;
  int maxLevel;
  int baseBuildingCost;
  int upgradeCost;

  public Lab(String name) {
    super(name);
    level = 1;
    maxLevel = 5;
    baseBuildingCost = 300;
  }

  public int getCapacity() {
    return 5 * (int) Math.pow(2, level - 1);
  }

  public int getLevel() {
    return this.level;
  }

  public int getBuildingCost() {
    return this.baseBuildingCost;
  }

  public void increaseLevel() {
    if (this.level == this.getMaxLevelOfBuilding()) {
      System.out.println("Cant increase level, already at max level");
    } else {
      this.level = this.level + 1;
    }
  }

  public int getMaxLevelOfBuilding() {
    return this.maxLevel;
  }

  public int getUpgradeCost() {
    if (this.level == this.getMaxLevelOfBuilding()) {
      upgradeCost = -1;
    } else if (this.level < this.getMaxLevelOfBuilding()) {
      upgradeCost = baseBuildingCost * (this.level + 1);
    }
    return upgradeCost;
  }

  public String getBuildingType() {
    return "Lab";
  }
}
