import java.text.SimpleDateFormat;
import java.util.Date;

public class Voter
{
    private String name;
    private Date birthDay;

    public Voter(String name, Date birthDay)
    {
        this.name = name;
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object obj)
    {
        Voter voter = (Voter) obj;
        return name.equals(voter.name) && birthDay.equals(voter.birthDay);
    }

    @Override
    public int hashCode()
    {
        int hash = 1;
        hash = hash * 31 + name.hashCode();
        hash = hash * 31 + (birthDay == null ? 0 : birthDay.hashCode());
        return hash;
    }

    public String toString()
    {
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        return name + " (" + dayFormat.format(birthDay) + ")";
    }

    public String getName()
    {
        return name;
    }

    public Date getBirthDay()
    {
        return birthDay;
    }
}
