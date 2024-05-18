#encoding:utf-8
require_relative 'dice'
require_relative 'combat_element'
module Irrgarten
    # The Irrgarten module contains classes related to weapons in the game.

    class Weapon < CombatElement
        # Attacks with the weapon.
        # @return [Float] the power of the weapon if it has uses left, otherwise 0
        def attack
            self.produce_effect
        end

        # Returns a string representation of the weapon.
        # @return [String] a string containing the power and uses of the weapon
        def to_s
            "W#{super}"
        end
    end
end
