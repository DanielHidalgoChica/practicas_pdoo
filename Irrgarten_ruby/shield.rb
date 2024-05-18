#encoding:utf-8
require_relative 'dice'
require_relative 'combat_element'
module Irrgarten
    # This class represents a shield from the game Irrgarten
    class Shield < CombatElement

        # Return the shield protection. If there are no uses left, it is 0
        #
        # @return [Float] The shield protection
        def protect
            self.produce_effect
        end

        # Makes a string that contains information about the object
        #
        # @return [String] A string with the info of the shield
        def to_s
            "S#{super}"
        end
    end
end
