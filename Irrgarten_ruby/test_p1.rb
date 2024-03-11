# Programa para probar la funcionalidad realizada en la práctica 1
require "./Irrgarten.rb"
# Enumerados
class TestP1
    def self.test_enum
        puts "\n"
        puts "ENUM_TEST"
        puts Irrgarten::Directions.constants
        puts Irrgarten::Orientation.constants
        puts Irrgarten::GameCharacter.constants
    end 
    

    # WEAPON
    def self.test_weapon
        puts "\n"
        puts "WEAPON CLASS"
        hammer = Irrgarten::Weapon.new(4.0,5)
        puts hammer.to_s
        puts hammer.attack
        puts hammer.to_s
        puts hammer.discard

        sword = Irrgarten::Weapon.new(0,0)
        puts sword.to_s
        puts sword.attack
        puts sword.to_s
        puts sword.discard
        
    end
    
    # SHIELD
    def self.test_shield

        puts "\n"
        puts "SHIELD CLASS"
        shield = Irrgarten::Shield.new(4.0,5)
        puts shield.to_s
        puts shield.protect
        puts shield.to_s
        puts shield.discard

        medieval_shield = Irrgarten::Shield.new(0,0)
        puts medieval_shield.to_s
        puts medieval_shield.protect
        puts medieval_shield.to_s
        puts medieval_shield.discard
    end

    def self.test_dice
        puts "\n"
        puts "DICE_TEST"
        100.times do |time|
            puts "Random pos: #{Irrgarten::Dice.random_pos(50)}"
            puts "Who Starts: #{Irrgarten::Dice.who_starts(5)}"
            puts "Random Intelligence: #{Irrgarten::Dice.random_intelligence}"
            puts "Random Strength: #{Irrgarten::Dice.random_strength}"
            puts "Resurrect Player: #{Irrgarten::Dice.resurrect_player}"
            puts "Weapons Reward: #{Irrgarten::Dice.weapons_reward}" 
            puts "Shields Reward: #{Irrgarten::Dice.shields_reward}"
            puts "Health Reward: #{Irrgarten::Dice.health_reward}"  
            puts "Weapon Power: #{Irrgarten::Dice.weapon_power}"
            puts "Shield Power: #{Irrgarten::Dice.shield_power}"
            puts "Uses left: #{Irrgarten::Dice.uses_left}"
            puts "Intensity: #{Irrgarten::Dice.intensity(10)}"
            puts "Discard Element: #{Irrgarten::Dice.discard_element(Irrgarten::Dice.uses_left)}"
            puts "----------------------------------------------------------------\n"
            
        end
    end

    def self.dice_test 
        puts Irrgarten::Dice.discard_element(5)
    end

    def self.test_game_state
        puts "GAME STATE\n"
        game_state=Irrgarten::GameState.new("labyrinth", "players","monsters","current_player","winner","log")
        puts game_state.inspect

    end     

    def self.main 
        test_enum
        test_weapon
        test_shield
        # test_dice_loop
        test_game_state
    end
    
end

TestP1.main

