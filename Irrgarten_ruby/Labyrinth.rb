module Irrgarten
class Labyrinth
@@BLOCK_CHAR = 'X'
@@EMPTY_CHAR = '-'
@@MONSTER_CHAR = 'M'
@@COMBAT_CHAR = 'C'
@@EXIT_CHAR = 'E'
@@ROW = 0
@@COL = 1

def initialize(num_rows, num_cols, exit_col, exit_row)
    @exit_row = exit_row
    @exit_col = exit_col
    @n_rows = num_rows
    @n_cols = num_cols
    @monsters = Array.new(@n_rows) {Array.new(@n_cols, nil)}
    @players = Array.new(@n_rows) {Array.new(@n_cols, nil)}
    @labyrinth = Array.new(@n_rows) {Array.new(@n_cols, @@EMTPY_CHAR)}
    @labyrinth[@exit_row][@exit_col] = @@EXIT_CHAR
end

# Dos constructores para poder crear el laberinto vacío
# en el constructor de la clase Game
def self.new_full(num_rows, num_cols, ex_col, ex_row)
    new(num_rows, num_cols, ex_col, ex_row)
end

def self.new_emtpy
    new(1,1,0,0)
end

private_class_method :new

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

def spread_players(players)
end

def put_player(direction, player)
end

def add_block(orientation, start_row, start_col, length)
end

def valid_moves(row, col)
end

private
def put_player_2D
end

def empty_pos(row, col) 
    @labyrinth[row][col] == @@EMTPY_CHAR
end

def pos_OK(row, col)
    (0 <= row) && (row < @n_rows) && (0 <= col) && (col < @n_cols)
end

def monster_pos(row, col) 
    @labyrinth[row][col] == @@MONSTER_CHAR
end

def exit_pos(row, col)
    @labyrinth[row][col] == @@EXIT_CHAR
end

def combat_pos(row, col)
    @labyrinth[row][col] == @@COMBAT_CHARK
end

def can_step_on(row, col)
    pos_ok(row,col) && (valid_pos(row,col) || empty_pos(row,col)
          || monster_pos(row, col) || exit_pos(row, col))
end

def update_old_pos(row, col) 
    if pos_ok(row, col) 
        if combat_pos(row, col) 
            @labyrinth[row][col] = @@MONSTER_CHAR
        else 
            @labyrinth[row][col] = @@EMPTY_CHAR
        end
    end        
end

def random_empty_pos
    r_row = Dice.random_pos(@n_rows)
    r_col = Dice.random_pos(@n_cols)
    
    while !pos_ok(r_row, r_col) || !empty_pos(r_row, r_col)
        r_row = Dice.random_pos(@n_rows)
        r_col = Dice.random_pos(@n_cols)
    end

    [r_row, r_col]
end

def dir2pos(row, col, direction)
def dir_2_pos(row,col,direction)
        pos = Array.new(2, row, col)
        case direction
        when Irrgarten::Directions::LEFT
            pos[1]=pos[1]-1
        when Irrgarten::Directions::RIGHT
            pos[1]=pos[1]+1
        when Irrgarten::Directions::UP
            pos[0]=pos[0]-1
        when Irrgarten::Directions::UP
            pos[0]=pos[0]+1
        end
        pos
end

end
end

