# encoding:utf-8
module Irrgarten

# Class that simulates the dice used in the game. It has methods to generate
# random numbers and booleans, and to decide the next step of a player or
# monster.
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

    # Decides the next step of a player or monster. If the random
    # number generated is less than the intelligence, the player will move
    # in the desired direction. Otherwise, the player will move in a random
    # valid direction.
    # @param preference [String] the desired direction of the player
    # @param valid_moves [Array<String>] the valid directions the player can move
    # @param intelligence [Float] the intelligence of the player
    # @return [String] the next step of the player
    def self.next_step(preference, valid_moves, intelligence)
        # Probability of moving in the desire direction
        probability = @generator.rand*@@MAX_INTELLIGENCE
        if probability < intelligence
            preference
        else
            # Generate a random valid move
            random_index_direction = @generator.rand(valid_moves.length)
            valid_moves[random_index_direction]
        end
    end
end

end
