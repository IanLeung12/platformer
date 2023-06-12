public class ShopItem extends GameObject{

    private String name;

    private String description;

    private int price;

    ShopItem(int x, int y, int width, int height, String name, int price) {
        super(x, y, width, height);
        this.name = name;
        switch (name) {
            case "Hammer":
                this.description = "A bigger, stronger sword with a blindspot";
                break;
            case "Bow":
                this.description = "A ranged weapon that gets stronger the longer you aim it";
                break;
            case "Rocket":
                this.description = "Use 100 energy to create a massive explosion";
                break;
            case "TripleJump":
                this.name = "Triple Jump";
                this.description = "I wonder what this does";
                break;
            case "Health":
                this.name = "Health Boost";
                this.description = "Increase max health by 100";
                break;
            case "Energy":
                this.name = "Energy Boost";
                this.description = "Increase max energy by 100";
                break;
            case "Damage":
                this.name = "Damage Boost";
                this.description = "Multiplies damage by 1.2x";
                break;
        }
        this.price = price;
    }

    public boolean buy(Player player) {
        if (player.getTotalGold() >= price) {
            switch (name) {
                case "Hammer":
                    player.getWeapons().add("Hammer");
                    break;
                case "Bow":
                    player.getWeapons().add("Bow");
                    break;
                case "Rocket":
                    player.getWeapons().add("Rocket");
                    break;
                case "Triple Jump":
                    player.setMaxJumps(3);
                    break;
                case "Health Boost":
                    player.setMaxHealth(player.getMaxHealth() + 100);
                    break;
                case "Energy":
                    player.setMaxEnergy(player.getEnergy() + 100);
                    break;
                case "Damage":
                    player.setDamageBoost(1.2);
            }
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
