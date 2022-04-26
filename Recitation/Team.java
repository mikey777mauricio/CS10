public class Team {
    public String teamMascotName;
    public int teamScore;

    public Team(String mascotName)
    {
        this.teamMascotName = mascotName;
    }

    public String getTeamMascotName(){

        return this.teamMascotName;
    }

    public int getTeamScore() {

        return teamScore;
    }

    public void score()
    {
        teamScore += 2;
    }

    public static void main(String[] args) {
        Team celtics = new Team("Leprachuan");
        Team bruins = new Team("bears");

        celtics.score();
        bruins.score();
        bruins.score();

        if(bruins.getTeamScore() > celtics.getTeamScore())
        {
            System.out.println(bruins.getTeamMascotName());
        }
        else
        {
            System.out.println(celtics.getTeamMascotName());
        }

    }


}
