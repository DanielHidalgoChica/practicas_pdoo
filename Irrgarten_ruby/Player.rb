module Irrgarten
class Player
    @@MAX_WEAPONS = 2
    @@MAX_SHIELDS = 3
    @@INITIAL_HEALTH = 10
    @@HITS2LOSE = 3
    def self.initialize(number, intelligence, strength, health, row, col) 
        @number = number
        @name = "Player #{@number}"
        @intelligence = intelligence
        @strength = strength
        @health = health
        @row = row
        @col = col
        @consecutive_hits = 0
        @weapons = Array.new
        @shields = Array.new
    end
    
    attr_reader :row, :col, :number

    def resurrect 
        @weapons.clear()
        @shields.clear()
        @health = @@INITIAL_HEALTH
        @consecutive_hits = 0;
    end

    def set_pos(row, col)
        @row = row
        @col = col
    end
    
    def dead 
        @health <= 0
    end

    def attack
        @strength + self.sum_weapons
    end

    def defend(received_attack)
    end

    def to_s
         ret = "\nPlayer State" + 
               "\nName:" + @name + 
               "\nIntelligence:" + @intelligence.to_s + 
               "\nStrength:"+ @strength.to_s + 
               "\nHealth:"+  @health.to_s + 
               "\nPosition: (" + @row.to_s + "," + @col.to_s + ")"+
               "\nConsecutive Hits: " + @consecutive_hits.to_s + 
               "\nWeapons:"
         @weapons.each {|a_weapon| ret += "\n\t" + a_weapon.to_s}
         ret += "\nShields:"
         @shields.each {|a_shield| ret += "\n\t" + a_shield.to_s}
         ret
    end

    def move(direction, valid_moves)
    end

    def receive_reward
    end

    def receive_weapon
    end

    def receive_shield
    end

    private
    def new_weapon
        r_power = Dice.weapon_power
        r_uses = Dice.weapon_uses
        new_weapon = Weapon.new(r_power, r_uses)
    end

    def sum_weapons
        sum = 0
        @weapons.each { |a_weapon| sum += a_weapon.attack }
    end

    def new_shield
        r_protection = Dice.shield_power
        r_uses = Dice.uses_left
        new_shield = shield.new(shield_power, uses_left)
    end

    def sum_shields
        sum = 0
        @shields.each { |a_shield| sum += a_shield.protect}
    end

    def defensive_energy 
        self.sum_shields + @intelligence
    end

    def reset_hits
        @consecutive_hits = 0
    end

    def inc_consecutive_hits
        @consecutive_hits = @consecutive_hits+1
    end

    def receive_weapon(w)
    end

    def receive_shield(s)
    end

    def manage_hit(received_attack)
    end

end
end
