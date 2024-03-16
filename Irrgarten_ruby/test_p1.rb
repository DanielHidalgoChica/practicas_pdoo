# Test program to check the functionality of all the classes and modules 
# developed in Practice 1
require_relative "Directions.rb"
require_relative "Orientation.rb"
require_relative "GameCharacter.rb"
require_relative "Weapon.rb"
require_relative "Shield.rb"
require_relative "Dice.rb"
require_relative "GameState.rb"

class TestP1
    # ENUM TYPES
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
        puts "Hammer: " + hammer.to_s
        puts hammer.discard
        puts hammer.attack
        puts hammer.to_s
        puts hammer.discard
        puts "\n"

        sword = Irrgarten::Weapon.new(0,0)
        puts "Sword: " + sword.to_s
        puts sword.attack
        puts sword.to_s
        puts sword.discard
        
    end
    
    # SHIELD
    def self.test_shield

        puts "\n"
        puts "SHIELD CLASS"
        shield = Irrgarten::Shield.new(4.0,5)
        puts "Shield: " + shield.to_s
        puts shield.discard
        puts shield.protect
        puts shield.to_s
        puts shield.discard
        puts "\n"

        medieval_shield = Irrgarten::Shield.new(0,0)
        puts "Medieval Shield: " + medieval_shield.to_s
        puts medieval_shield.protect
        puts medieval_shield.to_s
        puts medieval_shield.discard
        puts "\n"
    end

    def self.test_dice_loop
        puts "\n"
        puts "DICE_LOOP_TEST"
        100.times do 
            puts "Random pos: #{Irrgarten::Dice.random_pos(50)}"
        end
        100.times do 
            puts "Who Starts: #{Irrgarten::Dice.who_starts(5)}"
        end
        100.times do
            puts "Random Intelligence: #{Irrgarten::Dice.random_intelligence}"
        end 
        100.times do
            puts "Random Strength: #{Irrgarten::Dice.random_strength}"
        end
        100.times do 
            puts "Resurrect Player: #{Irrgarten::Dice.resurrect_player}"
        end 
        100.times do 
            puts "Weapons Reward: #{Irrgarten::Dice.weapons_reward}" 
        end 
        100.times do 
            puts "Shields Reward: #{Irrgarten::Dice.shields_reward}"
        end
        100.times do 
            puts "Health Reward: #{Irrgarten::Dice.health_reward}"  
        end
        100.times do 
            puts "Weapon Power: #{Irrgarten::Dice.weapon_power}"
        end
        100.times do 
            puts "Shield Power: #{Irrgarten::Dice.shield_power}"
        end
        100.times do 
            puts "Uses left: #{Irrgarten::Dice.uses_left}"
        end
        100.times do 
            puts "Intensity: #{Irrgarten::Dice.intensity(10)}"
        end
        100.times do 
            puts "Discard Element: #{Irrgarten::Dice.discard_element(Irrgarten::Dice.uses_left)}"
        end
            
    end

    def self.test_dice
        for i in (1..5)
            puts "5 uses left:"
            puts Irrgarten::Dice.discard_element(5)
            puts "0 uses left:"
            puts Irrgarten::Dice.discard_element(0)
        end
        
    end

    def self.test_game_state
        puts "GAME STATE\n"
        game_state=Irrgarten::GameState.new("labyrinth", "players","monsters",1,true,"log")
        puts "Labyrinth: " + game_state.labyrinth
        puts "Players: " + game_state.players
        puts "Monsters: " + game_state.monsters
        puts "Current player: " + game_state.current_player.to_s
        puts "Winner: " + game_state.winner.to_s
        puts "Log: " + game_state.log

    end     

    def self.main 
        test_enum
        puts "\n"
        test_weapon
        puts "\n"
        test_shield
        puts "\n"
        test_dice_loop
        test_dice
        test_game_state
        puts "\n"
    end
    
end

TestP1.main

