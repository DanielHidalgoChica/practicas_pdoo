#encoding:utf-8
module Irrgarten

class Weapon
    
    # Parameterized constructor
    # power power of the weapon
    # uses left for the weapon
    def initialize (power, uses)
        # Here the instance fields are being initialized
        @power = power;
        @uses = uses;
    end

    # Returns a float number representing the power of the weapon
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
    # Return a string containing the information of the weapon
    def to_s
        return "W["+@power.to_s+","+@uses.to_s+"]"
    end

end

end
