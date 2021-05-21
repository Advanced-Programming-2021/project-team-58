public class YamiEffect {
    public static void activate(Game game){
        for (Position position: game.getTurnOfPlayer().getBoard().getMonsterCards()) {
            if (position.getCard() instanceof MonsterCard) {
                if (((MonsterCard) position.getCard()).getMonsterType().equals("Fiend")||
                        ((MonsterCard) position.getCard()).getMonsterType().equals("Spellcaster")) {
//                    increase 200 ATK
//                    increase 200 DEF
                }
                else if (((MonsterCard) position.getCard()).getMonsterType().equals("Fairy")){
//                    decrease 200 ATk
//                    decrease 200 DEF
                }
            }
        }
    }
}
