#encoding:utf-8
require_relative 'dice'
require_relative 'orientation'
require_relative 'directions'
module Irrgarten
    # The Labyrinth class represents a maze game.
    class Labyrinth
        # The character used to represent a block in the labyrinth.
        @@BLOCK_CHAR = 'X'

        # The character used to represent an empty space in the labyrinth.
        @@EMPTY_CHAR = '-'

        # The character used to represent a monster in the labyrinth.
        @@MONSTER_CHAR = 'M'

        # The character used to represent a combat position in the labyrinth.
        @@COMBAT_CHAR = 'C'

        # The character used to represent the exit in the labyrinth.
        @@EXIT_CHAR = 'E'

        # The row index in a position array.
        @@ROW = 0

        # The column index in a position array.
        @@COL = 1

        # Initializes a new Labyrinth instance.
        #
        # @param n_rows [Integer] the number of rows in the labyrinth
        # @param n_cols [Integer] the number of columns in the labyrinth
        # @param exit_col [Integer] the column index of the exit
        # @param exit_row [Integer] the row index of the exit
        def initialize(n_rows, n_cols, exit_col, exit_row)
            @exit_row = exit_row
            @exit_col = exit_col
            @n_rows = n_rows
            @n_cols = n_cols
            @monsters = Array.new(@n_rows) { Array.new(@n_cols, nil) }
            @players = Array.new(@n_rows) { Array.new(@n_cols, nil) }
            @labyrinth = Array.new(@n_rows) { Array.new(@n_cols, @@EMPTY_CHAR) }
            @labyrinth[@exit_row][@exit_col] = @@EXIT_CHAR
        end

        # Spreads the players in the labyrinth.
        #
        # @param players [Array<Player>] the players to spread
        def spread_players(players)
            players.each do |p|
                pos = self.random_empty_pos
                self.put_player_2D(-1, -1, pos[@@ROW], pos[@@COL], p)
            end
            nil
        end

        # Checks if there is a winner in the labyrinth.
        #
        # @return [Boolean] true if there is a winner, false otherwise
        def have_a_winner
            @players[@exit_row][@exit_col] != nil
        end

        # Returns a string representation of the labyrinth.
        #
        # @return [String] the labyrinth as a string
        def to_s
            string = ''
            @labyrinth.each do |row|
                string << '|'
                row.each do |box|
                    string << "#{box}|"
                end
                string << "\n"
            end
            string
        end

        # Adds a monster to the labyrinth at the specified position.
        #
        # @param row [Integer] the row index of the monster's position
        # @param col [Integer] the column index of the monster's position
        # @param monster [Monster] the monster to add
        def add_monster(row, col, monster)
            if self.pos_OK(row, col) && self.empty_pos(row, col)
                @monsters[row][col] = monster
                @labyrinth[row][col] = @@MONSTER_CHAR
                monster.set_pos(row, col)
            end
        end

        # Puts a player in the labyrinth at the specified direction.
        #
        # @param direction [Symbol] the direction to put the player
        # @param player [Player] the player to put
        def put_player(direction, player)
            old_row = player.row
            old_col = player.col
            new_pos = self.dir2pos(old_row, old_col, direction)
            self.put_player_2D(old_row, old_col, new_pos[@@ROW], new_pos[@@COL], player)
        end

        # Adds a block to the labyrinth.
        #
        # @param orientation [Symbol] the orientation of the block (:horizontal or :vertical)
        # @param start_row [Integer] the row index of the block's starting position
        # @param start_col [Integer] the column index of the block's starting position
        # @param length [Integer] the length of the block
        def add_block(orientation, start_row, start_col, length)
            if orientation == Orientation::VERTICAL
                inc_row = 1
                inc_col = 0
            else
                inc_row = 0
                inc_col = 1
            end

            row = start_row
            col = start_col
            while self.pos_OK(row, col) && self.empty_pos(row, col) && length > 0
                @labyrinth[row][col] = @@BLOCK_CHAR
                length -= 1
                row += inc_row
                col += inc_col
            end
        end

        # Returns the valid moves from the specified position.
        # @param row [Integer] the row index of the position
        # @param col [Integer] the column index of the position
        # @return [Array<Directions>] the valid moves from the position
        def valid_moves(row, col)
            output = Array.new(0)
            output << Directions::DOWN if can_step_on(row + 1, col)
            output << Directions::UP if can_step_on(row - 1, col)
            output << Directions::RIGHT if can_step_on(row, col + 1)
            output << Directions::LEFT if can_step_on(row, col - 1)
            output
        end

        private

        # Checks if the specified position is within the labyrinth bounds.
        #
        # @param row [Integer] the row index of the position
        # @param col [Integer] the column index of the position
        # @return [Boolean] true if the position is within bounds, false otherwise
        def pos_OK(row, col)
            (0 <= row) && (row < @n_rows) && (0 <= col) && (col < @n_cols)
        end

        # Checks if the specified position is empty.
        #
        # @param row [Integer] the row index of the position
        # @param col [Integer] the column index of the position
        # @return [Boolean] true if the position is empty, false otherwise
        def empty_pos(row, col)
            @labyrinth[row][col] == @@EMPTY_CHAR
        end

        # Checks if the specified position contains a monster.
        #
        # @param row [Integer] the row index of the position
        # @param col [Integer] the column index of the position
        # @return [Boolean] true if the position contains a monster, false otherwise
        def monster_pos(row, col)
            @labyrinth[row][col] == @@MONSTER_CHAR
        end

        # Checks if the specified position is the exit.
        #
        # @param row [Integer] the row index of the position
        # @param col [Integer] the column index of the position
        # @return [Boolean] true if the position is the exit, false otherwise
        def exit_pos(row, col)
            @labyrinth[row][col] == @@EXIT_CHAR
        end

        # Checks if the specified position is a combat position.
        #
        # @param row [Integer] the row index of the position
        # @param col [Integer] the column index of the position
        # @return [Boolean] true if the position is a combat position, false otherwise
        def combat_pos(row, col)
            @labyrinth[row][col] == @@COMBAT_CHAR
        end

        # Checks if the specified position can be stepped on.
        #
        # @param row [Integer] the row index of the position
        # @param col [Integer] the column index of the position
        # @return [Boolean] true if the position can be stepped on, false otherwise
        def can_step_on(row, col)
            valid_pos = self.pos_OK(row,col)
            valid_pos && (self.empty_pos(row, col) || self.monster_pos(row, col) || self.exit_pos(row, col))
        end

        # Updates the old position after moving.
        #
        # @param row [Integer] the row index of the position
        # @param col [Integer] the column index of the position
        def update_old_pos(row, col)
            if self.pos_OK(row, col)
                if self.combat_pos(row, col)
                    @labyrinth[row][col] = @@MONSTER_CHAR
                else
                    @labyrinth[row][col] = @@EMPTY_CHAR
                end
            end
        end

        # Converts a direction to a new position.
        #
        # @param row [Integer] the row index of the current position
        # @param col [Integer] the column index of the current position
        # @param direction [Symbol] the direction to move
        # @return [Array<Integer>] the new position as [row, col]
        def dir2pos(row, col, direction)
            pos = [row, col]
            case direction
            when Irrgarten::Directions::LEFT
                pos[@@COL] -= 1
            when Irrgarten::Directions::RIGHT
                pos[@@COL] += 1
            when Irrgarten::Directions::UP
                pos[@@ROW] -= 1
            when Irrgarten::Directions::DOWN
                pos[@@ROW] += 1
            end
            pos
        end

        # Returns a random empty position in the labyrinth.
        #
        # @return [Array<Integer>] the random empty position as [row, col]
        def random_empty_pos
            r_row = Dice.random_pos(@n_rows)
            r_col = Dice.random_pos(@n_cols)

            while !pos_OK(r_row, r_col) || !empty_pos(r_row, r_col)
                r_row = Dice.random_pos(@n_rows)
                r_col = Dice.random_pos(@n_cols)
            end

            [r_row, r_col]
        end

        # Puts a player in the labyrinth at the specified position.
        # @param old_row [Integer] the row index of the player's old position
        # @param old_col [Integer] the column index of the player's old position
        # @param row [Integer] the row index of the player's new position
        # @param col [Integer] the column index of the player's new position
        # @param player [Player] the player to put
        # @return [Monster] the monster in the new position, or nil if there is no monster
        def put_player_2D (old_row, old_col, row, col, player)
            output = nil
            if self.can_step_on(row, col)
                if self.pos_OK(old_row, old_col)
                    p = @players[old_row][old_col]
                    if p == player
                        self.update_old_pos(old_row, old_col)
                        @players[old_row][old_col]=nil
                    end
                end

                if self.monster_pos(row, col)
                    @labyrinth[row][col] = @@COMBAT_CHAR
                    output = @monsters[row][col]
                else
                    @labyrinth[row][col] = player.number
                end

                @players[row][col]=player
                player.set_pos(row,col)

            end

            output
        end

        public
        # Updates the player in the labyrinth.
        # @param player [Player] the player to update
        # @return [Player] the player in the new position, or nil if there is no player
        def update_player(player)
            if @players[player.row][player.col] != nil
                @players[player.row][player.col] = player
            end
        end
    end
end

