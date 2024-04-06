module Irrgarten
    # The Labyrinth class represents a maze game.
    class Labyrinth
        @@BLOCK_CHAR = 'X'
        @@EMPTY_CHAR = '-'
        @@MONSTER_CHAR = 'M'
        @@COMBAT_CHAR = 'C'
        @@EXIT_CHAR = 'E'
        @@ROW = 0
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
            # Implementation goes here
        end

        # Checks if there is a winner in the labyrinth.
        #
        # @return [Boolean] true if there is a winner, false otherwise
        def have_a_winner
            (@players[@exit_row][@exit_col] != nil)
        end

        # Returns a string representation of the labyrinth.
        #
        # @return [String] the labyrinth as a string
        def to_s
            string = ""
            @labyrinth.each do |row|
                string << "|"
                row.each do |box|
                    string << "#{box} |"
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
            if (pos_OK(row, col) && empty_pos(row, col))
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
            # Implementation goes here
        end

        # Adds a block to the labyrinth.
        #
        # @param orientation [Symbol] the orientation of the block (:horizontal or :vertical)
        # @param start_row [Integer] the row index of the block's starting position
        # @param start_col [Integer] the column index of the block's starting position
        # @param length [Integer] the length of the block
        def add_block(orientation, start_row, start_col, length)
            # Implementation goes here
        end

        # Returns the valid moves from the specified position.
        #
        # @param row [Integer] the row index of the position
        # @param col [Integer] the column index of the position
        # @return [Array<Symbol>] the valid moves from the position
        def valid_moves(row, col)
            # Implementation goes here
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
            pos_OK(row, col) && (pos_OK(row, col) || empty_pos(row, col) || monster_pos(row, col) || exit_pos(row, col))
        end

        # Updates the old position after moving.
        #
        # @param row [Integer] the row index of the position
        # @param col [Integer] the column index of the position
        def update_old_pos(row, col)
            if pos_OK(row, col)
                if combat_pos(row, col)
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

        def put_player_2D
            # Implementation goes here
        end
    end
end

