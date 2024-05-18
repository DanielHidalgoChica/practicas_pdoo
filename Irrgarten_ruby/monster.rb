#encoding: utf-8
require_relative "dice"
require_relative 'labyrinth_character'
module Irrgarten
    # The Monster class represents a monster in the Irrgarten game.
    class Monster < LabyrinthCharacter
        # The initial health of a monster.
        @@INITIAL_HEALTH = 5

        # Initializes a new instance of the Monster class.
        #
        # @param name [String] The name of the monster.
        # @param intelligence [Integer] The intelligence level of the monster.
        # @param strength [Integer] The strength level of the monster.
        def initialize(name, intelligence, strength)
            super(name, intelligence, strength, @@INITIAL_HEALTH)
        end


        # Performs an attack action.
        #
        # @return [Integer] Returns the intensity of the attack based on the monster's strength.
        def attack
            Dice.intensity(@strength)
        end

        # Defends against an attack.
        # If the monster is dead, it will not defend.
        # If the defensive energy is less than the received attack, the monster will get wounded.
        # If the monster is dead after the attack, it will return true.
        # Otherwise, it will return false.
        # @param received_attack [Integer] The attack received by the monster.
        # @return [Boolean] Returns true if the monster is dead after the attack, false otherwise.
        # @see Dice#intensity
        def defend(received_attack)
            is_dead = self.dead
            if !is_dead
                defensive_energy = Dice.intensity(@intelligence)
                if defensive_energy < received_attack
                    self.got_wounded
                    is_dead = self.dead
                end
            end
            is_dead
        end

        # Returns a string representation of the monster's state.
        # @return [String] Returns a string containing the monster's name, intelligence, strength, health, and position.
        def to_s
            "M[#{super}]"
        end

    end
end
