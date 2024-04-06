module Irrgarten
class Labyrinth
@@BLOCK_CHAR = 'X'
@@EMPTY_CHAR = '-'
@@MONSTER_CHAR = 'M'
@@COMBAT_CHAR = 'C'
@@EXIT_CHAR = 'E'
@@ROW = 0
@@COL = 1

def initialize(n_rows, n_cols, exit_col, exit_row)
    @exit_row = exit_row
    @exit_col = exit_col
    @n_rows = n_rows
    @n_cols = n_cols
    @monsters = Array.new(@n_rows) {Array.new(@n_cols, nil)}
    @players = Array.new(@n_rows) {Array.new(@n_cols, nil)}
    @labyrinth = Array.new(@n_rows) {Array.new(@n_cols,@@EMPTY_CHAR) }
    @labyrinth[@exit_row][@exit_col] = @@EXIT_CHAR
end

def spread_players(players)

end

def have_a_winner
    (@players[@exit_row][@exit_col] != nil)
end

def to_s
string = ""
    string = ""
    @labyrinth.each do |row|
        string <<  "|"
        row.each do |box| 
            string << "#{box} |"
        end
        string << "\n"
    end

    string
end

def add_monster(row, col, monster)
    if (pos_OK(row,col) && empty_pos(row,col))
        @monsters[row][col] = monster
        @labyrinth[row][col] = @@MONSTER_CHAR
        monster.set_pos(row,col)
    end
end

def put_player(direction, player)
end

def add_block(orientation, start_row, start_col, length)
end

def valid_moves(row, col)
end

private
def pos_OK(row, col)
    (0 <= row) && (row < @n_rows) && (0 <= col) && (col < @n_cols)
end

def empty_pos(row, col) 
    @labyrinth[row][col] == @@EMPTY_CHAR
end

def monster_pos(row, col) 
    @labyrinth[row][col] == @@MONSTER_CHAR
end

def exit_pos(row, col)
    @labyrinth[row][col] == @@EXIT_CHAR
end

def combat_pos(row, col)
    @labyrinth[row][col] == @@COMBAT_CHAR
end

def can_step_on(row, col)
    pos_OK(row,col) && (pos_OK(row,col) || empty_pos(row,col) || monster_pos(row, col) || exit_pos(row, col))
end

def update_old_pos(row, col) 
    if pos_OK(row, col) 
        if combat_pos(row, col) 
            @labyrinth[row][col] = @@MONSTER_CHAR
        else 
            @labyrinth[row][col] = @@EMPTY_CHAR
        end
    end        
end


def dir2pos(row, col, direction)
    pos = [row,col]
    case direction
    when Irrgarten::Directions::LEFT
        pos[@@COL]-=1
    when Irrgarten::Directions::RIGHT
        pos[@@COL]+=1
    when Irrgarten::Directions::UP
        pos[@@ROW]-=1
    when Irrgarten::Directions::DOWN
        pos[@@ROW]+=1
    end
    pos
end

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
end


end
end

