#encoding: utf-8
module Irrgarten
class Monster
   @@INITIAL_HEALTH = 5
   
   def initialize(name, intelligence, strength, health, row, col)
       @name = name 
       @intelligence = intelligence
       @strength = strength
       @health = health
       @row = row
       @col = col
   end

   def dead 
       @health <= 0
   end

   def attack
       Dice.intensity(@strength)
   end

   def set_pos(row, col)
       @row = row
       @col = col
   end

   def to_s
        ret = "\nMonster State" + 
              "\nName:" + @name + 
              "\nIntelligence:" + @intelligence.to_s + 
              "\nStrength:"+ @strength.to_s + 
              "\nHealth:"+  @health.to_s + 
              "\nPosition: (" + @row.to_s + "," + @col.to_s + ")"
        ret
   end

   def got_wounded
       @health = @health-1
   end

   def defend(receive_attack)
   end


end
end 
