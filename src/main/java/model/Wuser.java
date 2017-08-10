package model;

import java.util.List;

public class Wuser {

    private int id;
    private String first_name;
    private String last_name;
    private List<String> results;
    private List<WordCard> cards;
    private int cardListSize;
    private int currentCard = 0;

    public Wuser(){}

    public Wuser(int id, String first_name, String last_name){
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public List<WordCard> getCards() {
        return cards;
    }

    public void setCards(List<WordCard> cards) {
        this.cards = cards;
        this.cardListSize = cards.size();
    }

    public WordCard getCard(){
        System.out.println(currentCard);
        int currentIndex = currentCard;
        currentCard++;
        System.out.println(currentIndex);
            return cards.get(currentIndex);
    }

    public int getCardListSize() {
        return cardListSize;
    }

    public boolean isLastIndex(){
        return currentCard == cardListSize;
    }
}
