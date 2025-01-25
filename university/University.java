package university;

import facilities.Facility;
import facilities.buildings.Building;

public class University {
  float budget;
  Estate estate;
  int reputation;
  HumanResource humanResource;

  public University(int funding) {
    this.budget = funding;
    this.estate = new Estate();
    this.reputation = 0;
    this.humanResource = new HumanResource();
  }

  public Facility build(String type, String name) {
    Facility newFacility = this.estate.addFacility(type, name);
    if (newFacility != null) {
      if (newFacility instanceof Building) {
        Building building = (Building) newFacility;
        this.budget -= building.getBuildingCost();
        this.reputation += 100;
      }
      return newFacility;
    } else {
      return null;
    }
  }

  public void upgrade(Building building) throws Exception {
    if (building.getLevel() == building.getMaxLevelOfBuilding()) {
      throw new Exception("Building already at max Level");
    }
    if (building.getUpgradeCost() > this.budget) {
      throw new Exception("Not enough EcsCoins to upgrade building");
    } else {
      this.reputation += 50;
      this.budget -= building.getUpgradeCost();
      building.increaseLevel();
    }
  }

  public float getBudget() {
    return this.budget;
  }

  public int getReputation() {
    return this.reputation;
  }

  public Estate getEstate() {
    return this.estate;
  }

  public void reduceBudget(int amountToReduce) {
    this.budget -= amountToReduce;
  }

  public void increaseBudget(int amountToIncrease) {
    this.budget += amountToIncrease;
  }

  public HumanResource getHumanResource() {
    return this.humanResource;
  }

  public void increaseReputation(int amount) {
    this.reputation += amount;
  }

  public void decreaseReputation(int amount) {
    this.reputation = this.reputation - amount;
  }
}
