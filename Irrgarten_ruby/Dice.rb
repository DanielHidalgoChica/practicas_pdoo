#encoding:utf-8
module Irrgarten

class Dice
    
    # Max uses of weapons and shields
    @@MAX_USES = 5
    
    # Max intelligence value for players and monsters
    @@MAX_INTELLIGENCE = 10.0

    # Max value for players and monsters strength
    @@MAX_STRENGTH = 10.0

    # Probability of a player to be resurrected
    @@RESURRECT_PROB = 0.3

    # Max number of weapons received when winning a combat
    @@WEAPONS_REWARD = 2

    # Max number of shield received when winning a combat
    @@SHIELDS_REWARD = 2

    # Max number of health points received when winning a combat
    @@HEALTH_REWARD = 5

    # Max power of weapons
    @@MAX_ATTACK = 3

    # Max protection of shields
    @@MAX_PROTECTION = 2

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
        return @generator.rand(@@MAX_INTELLIGENCE)
    end

    # Return a random value of strength between [0,MAX_STRENGTH[
    def self.random_strength
        return @generator.rand(@@MAX_STRENGTH)
    end

    # Returns a boolean representing if the player must be revived.
    # True if the player must be revived or false otherwise.
    def self.resurrect_player 
        if (@generator.rand(1.0)<@@RESURRECT_PROB)
            return true
        else
            return false
        end
    end

    # Return the number of weapons the player should receive when winning
    # a combat, a integer between [0,WEAPONS_REWARD[
    def self.weapons_reward 
        return @generator.rand(@@WEAPONS_REWARD+1)
    end 

    # Return the number of shields the player should receive when winning
    # a combat, a integer between [0,SHIELDS_REWARD[
    def self.shields_reward 
        return @generator.rand(@@SHIELDS_REWARD+1)
    end  

    # Return the number of health points the player should receive when winning
    # a combat, a integer between [0,SHIELDS_REWARD[
    def self.health_reward 
        return @generator.rand(@@HEALTH_REWARD+1)
    end  

    # Return a random value between [0, MAX_ATTACK[
    def self.weapon_power
        return @generator.rand(@@MAX_ATTACK)
    end

    # Return a random value between [0, MAX_PROTECTION[
    def self.shield_power
        return @generator.rand(@@MAX_PROTECTION)
    end

    # Return the uses a weapon or shield will have, a number between 
    # [0,MAX_USES]
    def self.uses_left
        return @generator.rand(@@MAX_USES+1)
    end 

    # Return the competence applied between [0,competence[
    def self.intensity(competence)
        return @generator.rand(competence)
    end 

    # Decides if the element must be discarded. The probability of this 
    # event will be greater as uses_left approaches MAX_USES
    def self.discard_element(uses_left)
        if (uses_left == @@MAX_USES) 
            return false
        else
            bound = 1 - (uses_left/@@MAX_USES)
            return (@generator.rand(1.0)<=bound)
        end
    end
end

end 