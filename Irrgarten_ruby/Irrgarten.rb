# encoding:utf-8
# @author: Luis Esteban Valdivieso González
# Module for the Irrgarten game developed in Ruby
# This module contains the enums for the game

# Module for the Irrgarten game
module Irrgarten
    # Enum type for the directions
    module Directions
        LEFT  = :left
        RIGHT = :right
        UP    = :up
        DOWN  = :down
    end

    #Enum type for the orientation
    module Orientation
        VERTICAL   = :vertical
        HORIZONTAL = :horizontal
    end

    #Enum type for the game characters
    module GameCharacter
        PLAYER  = :player
        MONSTER = :monster
    end

    class Weapon
        
        # Parameterized constructor
        # @param power power of the weapon
        # @param uses left for the weapon
        def initialize (power, uses)
            # Here the instance fields are being initialized
            @power = power;
            @uses = uses;
        end

        # @return a float number representing the power of the weapon
        def attack 
            if (@uses > 0)
                @uses=@uses-1
                return @power
            else
                return 0
            end
        end
        
        # Decides if a weapon must be descarted, using the method DiscardElement
        # of the Dice class
        def discard 
            return Dice.discard_element(@uses)
        end 

        # Print the uses and power of the weapon
        # @return A string containing the information of the weapon
        def to_s
            return "W["+@power.to_s+","+@uses.to_s+"]"
        end

    end

    
    class Shield

        # Parameterized constructor
        # @param protection Protection offered by the shield
        # @param Uses left by the shield
        def initialize (protection, uses)
            @protection = protection
            @uses = uses
        end

        # @return The shield protection. If there are no uses left, it is 0
        def protect
            if @uses > 0
                @uses= @uses-1
                return @protection
            else
                return 0
            end
        end

        # Decides if a shield must be descarted, using the method DiscardElement
        # of the Dice class
        def discard 
            return Dice.discard_element(@uses)
        end 

        # Makes a string that contains information about the object
        # @return String with the info of the shield
        def to_s
            return "S["+@protection.to_s+","+@uses.to_s+"]"
        end
        

    end
    
    
    class Dice
        
        # Max uses of weapons and shields
        MAX_USES = 5
        
        # Max intelligence value for players and monsters
        MAX_INTELLIGENCE = 10.0

        # Max value for players and monsters strength
        MAX_STRENGTH = 10.0

        # Probability of a player to be resurrected
        RESURRECT_PROB = 0.3

        # Max number of weapons received when winning a combat
        WEAPONS_REWARD = 2

        # Max number of shield received when winning a combat
        SHIELDS_REWARD = 2

        # Max number of health points received when winning a combat
        HEALTH_REWARD = 5

        # Max power of weapons
        MAX_ATTACK = 3

        # Max protection of shields
        MAX_PROTECTION = 2

        # Make all the constants private within the class
        private_constant :MAX_USES,:MAX_INTELLIGENCE,:MAX_STRENGTH,:RESURRECT_PROB,:WEAPONS_REWARD,:SHIELDS_REWARD,:HEALTH_REWARD,:MAX_ATTACK,:MAX_PROTECTION

        # Random numbers and booleans generator. In this case the use of a 
        # class variable isnt recommended, because if the generator object is
        # shared across all the methods it could lead to patterns in the 
        # generation of random numbers
        @generator = Random.new

        # Return a random column or row between [0,max[
        def self.random_pos (max)
            return @generator.rand(max)
        end

        # Return the index of the player who will start the game, between
        # [0,n_players[
        def self.who_starts(n_players)
            return @generator.rand(n_players)
        end

        # Return a value for intelligence between [0,MAX_INTELLIGENCE[
        def self.random_intelligence
            return @generator.rand(MAX_INTELLIGENCE)
        end

        # Return a random value of strength between [0,MAX_STRENGTH[
        def self.random_strength
            return @generator.rand(MAX_STRENGTH)
        end

        # Returns a boolean representing if the player must be revived.
        # True if the player must be revived or false otherwise.
        def self.resurrect_player 
            if (@generator.rand(1.0)<RESURRECT_PROB)
                return true
            else
                return false
            end
        end

        # Return the number of weapons the player should receive when winning
        # a combat, a integer between [0,WEAPONS_REWARD[
        def self.weapons_reward 
            return @generator.rand(WEAPONS_REWARD+1)
        end 

        # Return the number of shields the player should receive when winning
        # a combat, a integer between [0,SHIELDS_REWARD[
        def self.shields_reward 
            return @generator.rand(SHIELDS_REWARD+1)
        end  

        # Return the number of health points the player should receive when winning
        # a combat, a integer between [0,SHIELDS_REWARD[
        def self.health_reward 
            return @generator.rand(HEALTH_REWARD+1)
        end  

        # Return a random value between [0, MAX_ATTACK[
        def self.weapon_power
            return @generator.rand(MAX_ATTACK)
        end

        # Return a random value between [0, MAX_PROTECTION[
        def self.shield_power
            return @generator.rand(MAX_PROTECTION)
        end

        # Return the uses a weapon or shield will have, a number between 
        # [0,MAX_USES]
        def self.uses_left
            return @generator.rand(MAX_USES+1)
        end 

        # Return the competence applied between [0,competence[
        def self.intensity(competence)
            return @generator.rand(competence)
        end 

        # Decides if the element must be discarded. The probability of this 
        # event will be greater as uses_left approaches MAX_USES
        def self.discard_element(uses_left)
            bound = (MAX_USES-uses_left)/MAX_USES
            if @generator.rand(1.0)<bound || uses_left == MAX_USES
                return false
            else
                return true
            end 
        end
    end

    class GameState
        def initialize (labyrinth, players,monsters,current_player,winner,log)
            @labyrinth = labyrinth
            @players = players
            @monsters = monsters
            @current_player = current_player
            @winner = winner
            @log = log
        end 
    end 
    
end

