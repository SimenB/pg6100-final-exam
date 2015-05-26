package no.nith.pg6100.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@SequenceGenerator(name = "SEQ_PERSON", initialValue = 100)
@NamedQueries({
    @NamedQuery(name = "Result.findAll", query = "SELECT r FROM Result r"),
    @NamedQuery(name = "Result.findAllByTeam", query = "SELECT r FROM Result r WHERE (r.winner = :team OR r.loser = :team)")
})
@XmlRootElement
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERSON")
    private int id;
    @NotNull
    @Min(0)
    private Integer winner;
    @NotNull
    @Min(0)
    private Integer loser;

    public int getId() {
        return id;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public Integer getLoser() {
        return loser;
    }

    public void setLoser(Integer loser) {
        this.loser = loser;
    }
}
