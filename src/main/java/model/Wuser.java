package model;

import java.util.List;

public class Wuser {

    private int id;
    private String first_name;
    private String last_name;
    private int result = 0;
    private List<WordCard> cards;
    private int cardListSize;
    private int currentCardIndex = 0;

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
        int currentIndex = currentCardIndex;
        currentCardIndex++;
        System.out.println(currentIndex);
            return cards.get(currentIndex);
    }

    public boolean evalCard(String hun, String eng){
        WordCard card = cards.get(currentCardIndex-1);
        return card.getHun().equals(hun) && card.getEng().equals(eng);
    }

    public boolean  evalCardWithIndexZero(String hun, String eng){
        WordCard card = cards.get(0);
        return card.getHun().equals(hun) && card.getEng().equals(eng);
    }

    public int getCardListSize() {
        return cardListSize;
    }

    public boolean isLastIndex(){
        return currentCardIndex == cardListSize;
    }

    public int getResult() {
        System.out.println("Eredmény: " + result);
        return result;
    }

    public void setResultToZero(){
        result = 0;
    }

    public void setCurrentCardIndexToZero(){
        currentCardIndex = 0;
    }
    
    public int getCurrentIndex(){
        return currentCardIndex;
    }

    public void setResult() {
        result++;
    }
}
