#encoding=utf-8
require_relative "../controller/controller.rb"
require_relative "../textUI/textUI.rb"
require_relative "../game.rb"
class GamePlay
    @N_PLAYERS = 1
    # Main method to start the game
    def self.main
        game = Irrgarten::Game.new(@N_PLAYERS)
        text_ui = UI::TextUI.new
        controller = Control::Controller.new(game, text_ui)
        controller.play
    end
end

GamePlay.main