#encoding:utf-8
# The Irrgarten module represents a collection of classes and methods
# related to a game called Irrgarten. It provides functionality for
# dice rolling, random number generation, and various game mechanics.
#
# The module contains the `Dice` class, which encapsulates methods for
# generating random values, calculating rewards, and making decisions
# based on probabilities. It also defines several class variables that
# determine the game's rules and limits.
#
# The `Dice` class is designed to be used within the context of the
# Irrgarten game, but it can also be used independently for other
# purposes that require random number generation and probability-based
# calculations.
#
# Example usage:
#
#   # Generate a random position within a given range
#   pos = Irrgarten::Dice.random_pos(10)
#
#   # Roll a dice to determine the starting player
#   player_index = Irrgarten::Dice.who_starts(4)
#
#   # Generate a random value for intelligence
#   intelligence = Irrgarten::Dice.random_intelligence
#
#   # Check if a player should be resurrected
#   should_resurrect = Irrgarten::Dice.resurrect_player
#   ...
#
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
    @@MAX_SHIELD = 2

    # Random numbers and booleans generator. In this case the use of a 
    # class variable isnt recommended, because if the generator object is
    # shared across all the methods it could lead to patterns in the 
    # generation of random numbers
    @generator = Random.new

    # @return a random column or row between [0,max[
    def self.random_pos(max)
        @generator.rand(max)
    end

    # @return the index of the player who will start the game, between
    # [0,n_players[
    def self.who_starts(n_players)
        @generator.rand(n_players)
    end

    # @return a value for intelligence between [0,MAX_INTELLIGENCE[
    def self.random_intelligence
        @generator.rand(@@MAX_INTELLIGENCE)
    end

    # @return a random value of strength between [0,MAX_STRENGTH[
    def self.random_strength
        @generator.rand(@@MAX_STRENGTH)
    end

    # @return a boolean representing if the player must be revived.
    # True if the player must be revived or false otherwise.
    def self.resurrect_player 
        (@generator.rand(1.0)<@@RESURRECT_PROB)
    end

    # @return the number of weapons the player should receive when winning
    # a combat, a integer between [0,WEAPONS_REWARD]
    def self.weapons_reward 
        @generator.rand(@@WEAPONS_REWARD+1)
    end 

    # @return the number of shields the player should receive when winning
    # a combat, a integer between [0,SHIELDS_REWARD]
    def self.shields_reward 
        @generator.rand(@@SHIELDS_REWARD+1)
    end  

    # @return the number of health points the player should receive when winning
    # a combat, a integer between [0,HEALTH_REWARD]
    def self.health_reward 
        @generator.rand(@@HEALTH_REWARD+1)
    end  

    # @return a random value between [0, MAX_ATTACK[
    def self.weapon_power
        @generator.rand(@@MAX_ATTACK)
    end

    # Return a random value between [0, MAX_SHIELD[
    def self.shield_power
        @generator.rand(@@MAX_SHIELD)
    end

    # Return the uses a weapon or shield will have, a number between 
    # [0,MAX_USES]
    def self.uses_left
        @generator.rand(@@MAX_USES+1)
    end 

    # Return the competence applied between [0,competence[
    def self.intensity(competence)
        @generator.rand(competence)
    end 

    # Decides if the element must be discarded. The probability of this 
    # event will be greater as uses_left approaches MAX_USES
    def self.discard_element(uses_left)
        discard_probability = 1 - (uses_left / @@MAX_USES)
        @generator.rand < discard_probability
    end
end

end 
