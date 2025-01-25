package university;

public class Staff {
  String name;
  int skill;
  int yearsOfTeaching;
  int stamina;

  public Staff(String name, int skill) {
    this.name = name;
    this.skill = skill;
    this.yearsOfTeaching = 0;
    this.stamina = 100;
  }

  public int instruct(int numberOfStudents) {
    int newReputation = (100 * this.skill) / (100 + numberOfStudents);
    if (this.skill < 101) {
      this.skill += 1;
    }
    this.stamina = (int) (this.stamina - Math.ceil((numberOfStudents / (20 + this.skill))) * 20);
    return newReputation;
  }

  public void replenishStamina() {
    this.stamina = Math.min(this.stamina + 20, 100);
  }

  public void increaseYearsoOfTeaching() {
    this.yearsOfTeaching += 1;
  }

  public int getSkill() {
    return this.skill;
  }

  public int getYearsOfTeaching() {
    return this.yearsOfTeaching;
  }

  public String staffName() {
    return this.name;
  }

  public int getStamina() {
    return this.stamina;
  }
}
