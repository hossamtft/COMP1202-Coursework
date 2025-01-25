package facilities.buildings;

public interface Building {
  void increaseLevel();

  int getMaxLevelOfBuilding();

  int getUpgradeCost();

  int getCapacity();

  int getLevel();

  int getBuildingCost();

  String getBuildingType();
}
