package entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Words {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "wordID")
    private int wordId;
    @Basic
    @Column(name = "word")
    private String word;

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Words words = (Words) o;

        if (wordId != words.wordId) return false;
        return Objects.equals(word, words.word);
    }

    @Override
    public int hashCode() {
        int result = wordId;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        return result;
    }
}
