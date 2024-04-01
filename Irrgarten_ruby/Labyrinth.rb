module Irrgarten
class Labyrinth
@@BLOCK_CHAR = 'X'
@@EMPTY_CHAR = '-'
@@MONSTER_CHAR = 'M'
@@COMBAT_CHAR = 'C'
@@EXIT_CHAR = 'E'
@@ROW = 0
@@COL = 1

def self.initialize(num_rows, num_cols, ex_col, ex_row)
    @exit_row = ex_row
    @exit_col = ex_col
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
    @players[@exit_row][@exit_col] != nil
end

def to_s
    "NO SÉ CÓMO HACER ESTE TO_STRING"
end

def add_monster(row, col, monster)
    if (pos_ok(row,col))
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

def pos_ok(row, col)
    valid_pos = (0 <= row) && (row < @n_rows) && (0 <= col) && (col < @n_cols)
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
    ret = valid_pos(row,col) || empty_pos(row,col)
          || monster_pos(row, col) || exit_pos(row, col)
end

def upadte_old_pos(row, col) 
    if pos_ok(row, col) 
        if combat_pos(row, col) 
            @labyrinth[row][col] = @@MONSTER_CHAR
        else 
            @labyrinth[row][col] = @@EMPTY_CHAR
        end
    end        
end

def random_empty_pos
    r_pos = Array.new(2,nil)
    r_row = Dice.random_pos(@n_rows)
    r_col = Dice.random_pos(@n_cols)
    
    while !pos_ok(r_row, r_col) || !empty_pos(r_row, r_col)
        r_row = Dice.random_pos(@n_rows)
        r_col = Dice.random_pos(@n_cols)
    end

    r_pos[1] = r_row
    r_pos[2] = r_col
    r_pos
end

def dir2pos(row, col, direction)
    final_pos = Array.new(2, nil)
    case direction
    when LEFT
        final_pos[0] = row-1
        final_pos[1] = col
    when RIGHT
        final_pos[0] = row+1
        final_pos[1] = col
    when UP
        final_pos[0] = row
        final_pos[1] = col-1
    when DOWN
        final_pos[0] = row
        final_pos[1] = col+1
    end
    final_pos
end

end
end

