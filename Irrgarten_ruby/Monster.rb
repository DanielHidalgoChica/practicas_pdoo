#encoding: utf-8
module Irrgarten
    # The Monster class represents a monster in the Irrgarten game.
    class Monster
        @@INITIAL_HEALTH = 5

        # Initializes a new instance of the Monster class.
        #
        # @param name [String] The name of the monster.
        # @param intelligence [Integer] The intelligence level of the monster.
        # @param strength [Integer] The strength level of the monster.
        def initialize(name, intelligence, strength)
            @name = name
            @intelligence = intelligence
            @strength = strength
            @health = @@INITIAL_HEALTH
        end

        # Checks if the monster is dead.
        #
        # @return [Boolean] Returns true if the monster's health is less than or equal to 0, false otherwise.
        def dead
            @health <= 0
        end

        # Performs an attack action.
        #
        # @return [Integer] Returns the intensity of the attack based on the monster's strength.
        def attack
            Dice.intensity(@strength)
        end

        # Performs a defend action.
        #
        # @param receive_attack [Integer] The intensity of the attack received.
        def defend(receive_attack)
            # TODO: Implement the defend method logic.
        end

        # Sets the position of the monster in the Irrgarten game.
        #
        # @param row [Integer] The row position of the monster.
        # @param col [Integer] The column position of the monster.
        def set_pos(row, col)
            @row = row
            @col = col
        end

        # Returns a string representation of the monster's state.
        #
        # @return [String] Returns a string containing the monster's name, intelligence, strength, health, and position.
        def to_s
            ret = "\nMonster State" +
                        "\nName: #{@name}" +
                        "\nIntelligence: #{@intelligence}" +
                        "\nStrength: #{@strength}" +
                        "\nHealth: #{@health}" +
                        "\nPosition: (#{@row}, #{@col})\n"
            ret
        end

        # Decreases the monster's health by 1.
        def got_wounded
            @health -= 1
        end
    end
end
