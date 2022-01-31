package com.sdm.mgp2021_1;

// author: Ho Junliang

/**
 * size of leaderboard determined by m_leaderboard_size in GameSystem class
 */
public class Leaderboard {

    public final static Leaderboard Instance = new Leaderboard();

    // Singleton Pattern : Blocks others from creating
    private Leaderboard(){
        for (int i = 0; i < leaderboardTable.length; i++)
        {
            leaderboardTable[i] = new LeaderboardData("-- Empty --", 0);
        }
    }

    private LeaderboardData[] leaderboardTable = new LeaderboardData[GameSystem.m_leaderboard_size];

    public void AddToLeaderboard(String _name, int _score)
    {
        for (int i = 0; i < leaderboardTable.length; i++)
        {
            if (leaderboardTable[i].score <= _score)
            { // if new score beats the current score at position i
                LeaderboardData temp1 = new LeaderboardData(_name, _score);
                LeaderboardData temp2;
                for (; i < leaderboardTable.length; i++)
                {
                    temp2 = leaderboardTable[i];
                    leaderboardTable[i] = temp1;
                    temp1 = temp2;
                }
                return;
            }
        }
        // if new score is not higher or equal than any current score, it is not saved
    }

    public void ForceAddToLeaderboard(int _position, String _name, int _score)
    {
        leaderboardTable[_position].name = _name;
        leaderboardTable[_position].score = _score;
    }

    public LeaderboardData GetLeaderboardData(int _position)
    {
        if (_position >= GameSystem.m_leaderboard_size) _position = GameSystem.m_leaderboard_size - 1;
        else if (_position < 0) _position = 0;
        return leaderboardTable[_position];
    }

    public boolean CompareScoreWithPosition(int _score, int _position)
    {
        if (_position >= GameSystem.m_leaderboard_size) _position = GameSystem.m_leaderboard_size - 1;
        else if (_position < 0) _position = 0;

        return (_score >= leaderboardTable[_position].score);
    }
}
