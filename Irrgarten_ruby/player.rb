#encoding:utf-8
require_relative 'dice'
require_relative 'weapon'
require_relative 'shield'
require_relative 'labyrinth_character'
module Irrgarten
    # The Player class represents a player in the Irrgarten game.
    class Player < LabyrinthCharacter
        # The maximum number of weapons a player can have.
        @@MAX_WEAPONS = 2

        # The maximum number of shields a player can have.
        @@MAX_SHIELDS = 3

        # The initial health of a player.
        @@INITIAL_HEALTH = 10

        # The number of consecutive hits a player can take before losing.
        @@HITS2LOSE = 3

        # Initializes a new instance of the Player class.
        #
        # @param number [Integer] The player number.
        # @param intelligence [Integer] The player's intelligence level.
        # @param strength [Integer] The player's strength level.
        def initialize(number, intelligence, strength)
            @number = number
            super("Player #{@number}", intelligence, strength, @@INITIAL_HEALTH)
            self.resurrect
        end

        # Resurrects the player, resetting their state.
        def resurrect
            @weapons = Array.new
            @shields = Array.new
            @health = @@INITIAL_HEALTH
            self.reset_hits
        end

        # Copy constructor
        # @param other Player to copy
        def copy(other)
            super(other)
            @consecutive_hits = other.consecutive_hits
            @number = other.number
            @weapons = other.weapons
            @shields = other.shields
        end
        attr_reader :row, :col, :number, :consecutive_hits,  :weapons, :shields

        # Moves the player in a given direction.
        # If the direction is not valid, the player will move in the first valid direction.
        # If there are no valid directions, the player will stay in the same position.
        # @param direction [Symbol] The direction to move.
        # @param valid_moves [Array<Symbol>] The valid directions to move.
        # @return [Symbol] The direction the player moved.
        def move(direction, valid_moves)
            size = valid_moves.size
            contained = valid_moves.include?(direction)
            if size > 0 && !contained
                valid_moves[0]
            else
                direction
            end
        end

        # Calculates the player's attack power.
        #
        # @return [Integer] The player's attack power.
        def attack
            @strength + sum_weapons
        end

        # Defends against an attack.
        #
        # @param received_attack [Integer] The attack power received.
        def defend(received_attack)
            manage_hit(received_attack)
        end

        # Receives a reward for winning a combat.
        # The reward consists of new weapons, shields, and health points.
        # The number of health points, weapons and shields is determined by the Dice class.
        def receive_reward
            w_reward = Dice.weapons_reward
            s_reward = Dice.shields_reward
            w_reward.times do
                wnew = self.new_weapon
                self.receive_weapon(wnew)
            end
            s_reward.times do
                snew = self.new_shield
                self.receive_shield(snew)
            end
            extra_health = Dice.health_reward
            @health += extra_health
        end

        # Returns a string representation of the player's state.
        #
        # @return [String] The player's state.
        def to_s
            ret = "P[#{super}, ConsecHits: #{@consecutive_hits.to_s}] \n\tWeapons: "
            @weapons.each { |a_weapon| ret += "\n\t#{a_weapon.to_s}" }
            ret += "\n\tShields:"
            @shields.each { |a_shield| ret += "\n\t#{a_shield.to_s}" }
            ret
        end

        private

        # Receives a weapon.
        #
        # @param w [Weapon] The weapon to receive.
        def receive_weapon(w)
            @weapons.delete_if { |wi| wi.discard }
            size = @weapons.length
            if size < @@MAX_WEAPONS
                @weapons << w
            end
        end

        # Receives a shield.
        #
        # @param s [Shield] The shield to receive.
        def receive_shield(s)
            @shields.delete_if { |si| si.discard }
            size = @shields.length
            if size < @@MAX_SHIELDS
                @shields << s
            end
        end

        # Creates a new weapon.
        #
        # @return [Weapon] The new weapon.
        def new_weapon
            r_power = Dice.weapon_power
            r_uses = Dice.uses_left
            Weapon.new(r_power, r_uses)
        end

        # Creates a new shield.
        #
        # @return [Shield] The new shield.
        def new_shield
            r_protection = Dice.shield_power
            r_uses = Dice.uses_left
            Shield.new(r_protection, r_uses)
        end

        protected

        # Calculates the total attack power of all weapons.
        #
        # @return [Integer] The total attack power.
        def sum_weapons
            sum = 0
            @weapons.each { |a_weapon| sum += a_weapon.attack }
            sum
        end

        # Calculates the total protection power of all shields.
        #
        # @return [Integer] The total protection power.
        def sum_shields
            sum = 0
            @shields.each { |a_shield| sum += a_shield.protect }
            sum
        end

        # Calculates the defensive energy of the player.
        #
        # @return [Integer] The defensive energy.
        def defensive_energy
            self.sum_shields + @intelligence
        end

        private

        # Manages the player's state when hit.
        # If the player is hit, the consecutive hits counter is increased.
        # If the player is not hit, the counter is reset.
        # If the player has received the maximum number of consecutive hits, the player loses.
        # @param received_attack [Integer] The attack power received.
        # @return [Boolean] True if the player has lost, false otherwise.
        def manage_hit(received_attack)
            defense = self.defensive_energy
            if defense < received_attack
                self.got_wounded
                self.inc_consecutive_hits
            else
                self.reset_hits
            end
            if ((@consecutive_hits == @@HITS2LOSE) || self.dead)
                self.reset_hits
                lose = true
            else
                lose = false
            end
            lose
        end

        # Resets the consecutive hits counter.
        def reset_hits
            @consecutive_hits = 0
        end<

        # Increases the consecutive hits counter.
        def inc_consecutive_hits
            @consecutive_hits += 1
        end
    end
end
